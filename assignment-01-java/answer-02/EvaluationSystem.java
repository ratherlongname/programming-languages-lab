import java.util.Scanner;
import java.util.Vector;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.lang.Integer;

public class EvaluationSystem {
    // Paths to asset files
    static String stud_info_file_path = "./student_data/Stud_Info.txt";
    static String sorted_name_file_path = "./student_data/Sorted_Name.txt";
    static String sorted_roll_no_file_path = "./student_data/Sorted_Roll.txt";

    // ID of current user (CC or TA1 or TA2)
    static String id;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // To read input from terminal

        // Get users identity
        id = query_user("Who are you? (CC / TA1 / TA2)", scanner).toUpperCase();
        if (id.equals("CC") || id.equals("TA1") || id.equals("TA2"))
            System.out.println("Welcome, " + id);
        else {
            System.out.println(id + ", you are not authorized."); // Only CC, TA1, TA2 are allowed
            return;
        }
        System.out.println();

        System.out.println("Evaluation System");
        System.out.println("=================");
        String usr_inp = "help"; // Print help initially

        do { // User Interaction loop
            switch (usr_inp) {
                case "print":
                case "p":
                    print(); // Print data from Stud_Info.txt
                    break;

                case "edit": // Edit data in Stud_Info.txt
                case "e":
                    System.out.println("Edit commands: <NAME | ROLL> <set | incr | decr> <MARKS>");
                    System.out.println("    NAME \t\t Full name of student");
                    System.out.println("    ROLL \t\t Roll no. of student");
                    System.out.println("    set \t\t Set students marks as MARKS");
                    System.out.println("    incr \t\t Increment students marks by MARKS");
                    System.out.println("    decr \t\t Decrement students marks by MARKS");
                    System.out.println("    MARKS \t\t Integer number, can be negative");
                    System.out.println("\nExamples:");
                    System.out.println("    174101055 set 100");
                    System.out.println("    174101012 incr 20");
                    System.out.println("    Subhra Shivani decr 5");
                    System.out.print("edit> ");
                    String edit_command = scanner.nextLine(); // Get edit command from user
                    edit(edit_command); // Do the editing
                    break;

                case "export name": // Generate Sorted_Name.txt
                case "n":
                    export_sorted(1);
                    break;

                case "export roll": // Generate Sorted_Roll.txt
                case "r":
                    export_sorted(0);
                    break;

                case "help": // Show help text
                case "h":
                default:
                    System.out.println("Commands:");
                    System.out.println("    \"print\" or \"p\" \t\t Print Stud_Info.txt to terminal");
                    System.out.println("    \"edit\" or \"e\" \t\t Edit student marks");
                    System.out.println("    \"export name\" or \"n\" \t Generate Sorted_Name.txt");
                    System.out.println("    \"export roll\" or \"r\" \t Generate Sorted_Roll.txt");
                    System.out.println("    \"help\" or \"h\" \t\t Show this help page");
                    System.out.println("    \"quit\" or \"q\" \t\t Quit programme");
                    break;
            }

            usr_inp = query_user("", scanner);
        } while (!usr_inp.equals("q") && !usr_inp.equals("quit"));

    }

    public static String query_user(String s, Scanner scanner) {
        // Print message and get user response
        if (!s.equals(""))
            System.out.println(s);
        System.out.print(">>> ");

        return scanner.nextLine(); // Return user response
    }

    public static void print() {
        // Read file and print
        try {
            // Open file and get shared lock
            FileInputStream fin = new FileInputStream(stud_info_file_path);
            FileChannel channel = fin.getChannel();
            FileLock shared_lock = channel.lock(0, Long.MAX_VALUE, true);

            // Read each character and print to screen
            int i = 0;
            while ((i = fin.read()) != -1) {
                char c = (char) i;
                if (c == '\t') {
                    c = ' ';
                }
                System.out.print(c);
            }

            // Release lock and close file
            shared_lock.release();
            fin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return;
    }

    public static void edit(String edit_command) {
        // Do edit as given in edit command

        // Parse edit command to separate marks, name / rollno, operation
        Vector<String> all_tokens = new Vector<String>(Arrays.asList(edit_command.split(" ")));
        String new_marks = all_tokens.remove(all_tokens.size() - 1);
        String operation = all_tokens.remove(all_tokens.size() - 1);
        String name_or_roll_no = String.join(" ", all_tokens);

        try {
            // Open file and acquire exclusive lock
            RandomAccessFile file = new RandomAccessFile(stud_info_file_path, "rw");
            FileChannel channel = file.getChannel();
            FileLock excl_lock = channel.lock(0, Long.MAX_VALUE, false);

            // Search line by line for relevant record
            int i = 0;
            Boolean record_found = false;
            String line = "";
            long pos_curr = 0;
            long pos_line_start = 0;
            while ((i = file.read()) != -1) {
                pos_curr++;
                char c = (char) i;
                if (c != '\n')
                    line += c;
                else if (c == '\n') {
                    // Parse line into each column and its value
                    Vector<String> line_tokens = new Vector<String>(Arrays.asList(line.split("\\s+")));
                    String roll_no = line_tokens.remove(0);
                    String teacher = line_tokens.remove(line_tokens.size() - 1);
                    String marks = line_tokens.remove(line_tokens.size() - 1);
                    String mail_id = line_tokens.remove(line_tokens.size() - 1);
                    String name = String.join(" ", line_tokens);

                    if (name_or_roll_no.equals(roll_no) || name_or_roll_no.equals(name)) { // Check if it is relevant
                                                                                           // record
                        // Correct record found
                        record_found = true;

                        // Print record as it is initially
                        System.out.println(
                                "   (" + roll_no + "  " + name + "  " + mail_id + "  " + marks + "  " + teacher + ")");
                        System.out.println("\t\t\t\t|");
                        System.out.println("\t\t\t\t|");
                        System.out.println("\t\t\t\t|");
                        System.out.println("\t\t\t\tV");

                        if (teacher.equals("CC") && !id.equals("CC")) { // Check if user allowed to edit
                            // Not authorized to change marks by CC
                            System.out.println("Marks set by CC. You are not authorized to change.");
                        } else {
                            // Edit record
                            if (operation.equals("set")) {
                                // Set new marks
                                marks = new_marks;
                            } else if (operation.equals("incr")) {
                                // Increment marks
                                Integer int_marks = Integer.parseInt(marks) + Integer.parseInt(new_marks);
                                marks = int_marks.toString();
                            } else if (operation.equals("decr")) {
                                // Decrement marks
                                Integer int_marks = Integer.parseInt(marks) - Integer.parseInt(new_marks);
                                marks = int_marks.toString();
                            }

                            // Update teacher column of record
                            teacher = id;

                            // Print record as it is after edit
                            System.out.println("   (" + roll_no + "  " + name + "  " + mail_id + "  " + marks + "  "
                                    + teacher + ")");

                            // Write edit to file
                            String new_record = String.join("\t\t", roll_no, name, mail_id, marks, teacher);
                            file.seek(pos_line_start);
                            for (int j = 0; j < pos_curr - pos_line_start; j++) {
                                if (j < new_record.length()) {
                                    char ch = new_record.charAt(j);
                                    file.write(ch);
                                } else if (j == pos_curr - pos_line_start - 1) {
                                    file.write('\n');
                                } else {
                                    file.write(' ');
                                }
                            }
                        }
                    }

                    // Go to next record if relevant record not found
                    pos_line_start = pos_curr;
                    line = "";
                }

                // Stop reading if edit done
                if (record_found)
                    break;
            }

            // Error if there is no such record in file
            if (!record_found)
                System.out.println("Record not found.");

            // Release lock and close file
            excl_lock.release();
            file.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return;
    }

    public static void export_sorted(Integer col_to_sort) {
        // Read file into memory
        Vector<Vector<String>> document = new Vector<>();

        try {
            // Open file and acquire shared lock
            FileInputStream fin = new FileInputStream(stud_info_file_path);
            FileChannel channel = fin.getChannel();
            FileLock shared_lock = channel.lock(0, Long.MAX_VALUE, true);

            // Store file, line by line into memory
            String line = "";
            int i = 0;
            while ((i = fin.read()) != -1) {
                char c = (char) i;
                if (c != '\n')
                    line += c;
                else if (c == '\n') {
                    Vector<String> line_tokens = new Vector<String>(Arrays.asList(line.split("\\s+")));
                    String roll_no = line_tokens.remove(0);
                    String teacher = line_tokens.remove(line_tokens.size() - 1);
                    String marks = line_tokens.remove(line_tokens.size() - 1);
                    String mail_id = line_tokens.remove(line_tokens.size() - 1);
                    String name = String.join(" ", line_tokens);
                    line_tokens.removeAllElements();
                    line_tokens.add(roll_no);
                    line_tokens.add(name);
                    line_tokens.add(mail_id);
                    line_tokens.add(marks);
                    line_tokens.add(teacher);
                    document.add(line_tokens);

                    line = "";
                }
            }

            // Release lock and close file
            shared_lock.release();
            fin.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        // Print as file was before sorting
        for (int i = 0; i < document.size(); i++) {
            for (int j = 0; j < document.get(i).size(); j++) {
                System.out.print("  " + document.get(i).get(j) + "  ");
            }
            System.out.println();
        }

        System.out.println("\t\t\t\t|");
        System.out.println("\t\t\t\t|");
        System.out.println("\t\t\t\t|");
        System.out.println("\t\t\t\tV");

        // Sort by relevant column (0 for rollno, 1 for name)
        Vector<String> header = document.remove(0);
        Collections.sort(document, Comparator.comparing(s -> s.get(col_to_sort)));
        document.add(0, header);

        // Print as file is after sorting
        for (int i = 0; i < document.size(); i++) {
            for (int j = 0; j < document.get(i).size(); j++) {
                System.out.print("  " + document.get(i).get(j) + "  ");
            }
            System.out.println();
        }

        try {
            // Open file and get exclusive lock
            FileOutputStream fout;
            if (col_to_sort == 1)
                fout = new FileOutputStream(sorted_name_file_path);
            else
                fout = new FileOutputStream(sorted_roll_no_file_path);

            FileChannel channel = fout.getChannel();
            FileLock excl_lock = channel.lock();

            // Write file character by character
            for (int i = 0; i < document.size(); i++) {
                for (int j = 0; j < document.get(i).size(); j++) {
                    for (int k = 0; k < document.get(i).get(j).length(); k++) {
                        fout.write(document.get(i).get(j).charAt(k));
                    }
                    fout.write('\t');
                    fout.write('\t');
                }
                fout.write('\n');
            }

            // Print destination of file
            if (col_to_sort == 1) {
                System.out.println("\nFile written to: " + sorted_name_file_path);
            } else {
                System.out.println("\nFile written to: " + sorted_roll_no_file_path);
            }

            // Release lock and close file
            excl_lock.release();
            fout.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return;
    }
}