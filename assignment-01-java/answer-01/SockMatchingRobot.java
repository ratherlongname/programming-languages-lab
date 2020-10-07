import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class SockMatchingRobot {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Sock Matching Robot");
        System.out.println("===================");
        // Configuration variables
        Integer num_socks = 16;
        Integer num_robot_arms = 2;
        System.out.println("No. of socks: " + num_socks);
        System.out.println("No. of robot arms: " + num_robot_arms + "\n");

        // Add socks to heap of socks
        Vector<Sock> sock_heap = new Vector<>(num_socks);
        for (int i = 0; i < num_socks; i++) {
            // Give each sock a colour and a number
            if (i % 4 == 0)
                sock_heap.add(new Sock("white", i));
            else if (i % 4 == 1)
                sock_heap.add(new Sock("black", i));
            else if (i % 4 == 2)
                sock_heap.add(new Sock("blue", i));
            else if (i % 4 == 3)
                sock_heap.add(new Sock("grey", i));
        }

        // Create buffer of socks for Matching machine to pick from
        Vector<Sock> matching_buffer = new Vector<>(num_socks);

        // Create Robot arms to pick socks from heap and place in Matching machine's
        // buffer
        Vector<RobotArm> robot_arms = new Vector<>(num_robot_arms);
        for (int i = 0; i < num_robot_arms; i++) {
            RobotArm arm = new RobotArm(sock_heap, matching_buffer);
            arm.setName("RobotArm-" + (i + 1)); // Give each Robot arm a name
            robot_arms.add(arm);
        }

        // Create buffer of sock pairs for Shelf manager robot to pick from
        Vector<SockPair> shelf_manager_buffer = new Vector<>(num_socks / 2);

        // Create Matching machine to pick socks from its buffer and place sock pairs in
        // Shelf manager's buffer
        MatchingMachine matching_machine = new MatchingMachine(matching_buffer, shelf_manager_buffer);
        matching_machine.setName("Matching machine");

        // Create shelves to hold sock pairs of every colour
        HashMap<String, Vector<SockPair>> all_shelves = new HashMap<>();
        all_shelves.put("white", new Vector<SockPair>());
        all_shelves.put("black", new Vector<SockPair>());
        all_shelves.put("blue", new Vector<SockPair>());
        all_shelves.put("grey", new Vector<SockPair>());

        // Create Shelf manager to pick sock pairs from its buffer and place them on
        // shelf of their colour
        ShelfManager shelf_manager = new ShelfManager(shelf_manager_buffer, all_shelves);
        shelf_manager.setName("Shelf manager");

        // Start Robot arms
        System.out.println("Starting up the Robot arms ...");
        for (int i = 0; i < robot_arms.size(); i++) {
            robot_arms.get(i).start();
        }

        // Start Matching machine
        System.out.println("Starting up the Matching machine ...");
        matching_machine.start();

        // Start Shelf manager
        System.out.println("Starting up the Shelf manager ...\n");
        shelf_manager.start();

        System.out.println("SOCKS \t\t\t FROM \t\t\t\t TO");
        System.out.println("===== \t\t\t ==== \t\t\t\t ==");
    }
}

class Sock {
    // Each sock has colour and a unique id
    String colour;
    Integer unique_id;

    Sock(String colour, Integer unique_id) {
        this.colour = colour;
        this.unique_id = unique_id;
    }
}

class SockPair {
    // Each sock pair contains 2 socks
    Sock left_sock;
    Sock right_sock;

    SockPair(Sock left_sock, Sock right_sock) {
        this.left_sock = left_sock;
        this.right_sock = right_sock;
    }
}

class RobotArm extends Thread {
    Vector<Sock> sock_heap; // Pick socks from here
    Vector<Sock> matching_buffer; // Put socks here
    Sock picked_sock = null; // Sock that is picked up currently

    RobotArm(Vector<Sock> sock_heap, Vector<Sock> matching_buffer) {
        this.sock_heap = sock_heap;
        this.matching_buffer = matching_buffer;
    }

    public void run() {
        while (true) { // Keep trying to pick up socks
            // Starting to to pick up sock takes 1 second
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Pick up sock from heap
            synchronized (sock_heap) {
                if (sock_heap.isEmpty())
                    break;
                else {
                    this.picked_sock = sock_heap.remove(sock_heap.size() - 1);
                    System.out.println("(" + this.picked_sock.colour + "-" + this.picked_sock.unique_id
                            + ") :\t\t Sock heap \t\t --> \t " + getName());
                }
            }

            // Moving sock takes 1 second
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Put sock in front of the Matching machine
            synchronized (matching_buffer) {
                matching_buffer.add(this.picked_sock);
                System.out.println("(" + this.picked_sock.colour + "-" + this.picked_sock.unique_id + ") :\t\t "
                        + getName() + " \t\t --> \t Matching buffer");
            }
            this.picked_sock = null;
        }
    }
}

class MatchingMachine extends Thread {
    Vector<Sock> matching_buffer;    // Pick socks from here
    Vector<SockPair> shelf_manager_buffer;    // Put sock pairs here
    SockPair matched_sock_pair = null;    // Sock pair currently picked up

    MatchingMachine(Vector<Sock> matching_buffer, Vector<SockPair> shelf_manager_buffer) {
        this.matching_buffer = matching_buffer;
        this.shelf_manager_buffer = shelf_manager_buffer;
    }

    public void run() {
        while (true) { // Keep trying to match socks
            // Starting to find sock pair takes 1 second
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Find matching pair of socks from Matching machine buffer
            synchronized (matching_buffer) {
                if (matching_buffer.size() < 2)
                    continue;
                else {
                    for (int i = 0; i < matching_buffer.size(); i++) {
                        for (int j = i + 1; j < matching_buffer.size(); j++) {
                            if (matching_buffer.get(i).colour == matching_buffer.get(j).colour) {
                                // Matching pair of socks found, make pair
                                this.matched_sock_pair = new SockPair(matching_buffer.get(i), matching_buffer.get(j));

                                // Remove socks from matching buffer
                                matching_buffer.remove(i);
                                matching_buffer.remove(j - 1); // j-1 because after removing i, socks have moved one
                                                               // element left

                                System.out.println("(" + this.matched_sock_pair.left_sock.colour + "-"
                                        + this.matched_sock_pair.left_sock.unique_id + ", "
                                        + this.matched_sock_pair.right_sock.colour + "-"
                                        + this.matched_sock_pair.right_sock.unique_id
                                        + ") :\t Matching buffer \t --> \t " + getName());
                                break;
                            }
                        }

                        if (this.matched_sock_pair != null)
                            break; // One pair of socks matched, no need to keep searching
                    }

                    if (this.matched_sock_pair == null)
                        continue; // No pair of socks matched so don't put anything in Shelf managers buffer
                }
            }

            // Moving sock pair takes 1 second
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Put sock pair in Shelf managers buffer
            synchronized (shelf_manager_buffer) {
                shelf_manager_buffer.add(matched_sock_pair);
                System.out.println("(" + this.matched_sock_pair.left_sock.colour + "-"
                        + this.matched_sock_pair.left_sock.unique_id + ", " + this.matched_sock_pair.right_sock.colour
                        + "-" + this.matched_sock_pair.right_sock.unique_id + ") :\t " + getName()
                        + " \t --> \t Shelf manager buffer");
            }
            matched_sock_pair = null;
        }
    }
}

class ShelfManager extends Thread {
    Vector<SockPair> shelf_manager_buffer;    // Pick sock pair from here
    HashMap<String, Vector<SockPair>> all_shelves;    // Put sock pairs here
    SockPair picked_sock_pair = null;    // Sock pair currently in hand

    ShelfManager(Vector<SockPair> shelf_manager_buffer, HashMap<String, Vector<SockPair>> all_shelves) {
        this.shelf_manager_buffer = shelf_manager_buffer;
        this.all_shelves = all_shelves;
    }

    public void run() {
        while (true) { // Keep trying to shelve socks
            // Starting to get sock pair takes 1 second
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Pick sock pair from shelf manager buffer
            synchronized (shelf_manager_buffer) {
                if (shelf_manager_buffer.isEmpty()) {
                    continue;
                } else {
                    this.picked_sock_pair = shelf_manager_buffer.remove(shelf_manager_buffer.size() - 1);
                    System.out.println("(" + this.picked_sock_pair.left_sock.colour + "-"
                            + this.picked_sock_pair.left_sock.unique_id + ", " + this.picked_sock_pair.right_sock.colour
                            + "-" + this.picked_sock_pair.right_sock.unique_id + ") :\t Shelf manager buffer \t --> \t "
                            + getName());
                }
            }

            // Moving sock pair takes 1 second
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Put sock pair in shelf
            all_shelves.get(picked_sock_pair.left_sock.colour).add(picked_sock_pair);
            System.out.println("(" + this.picked_sock_pair.left_sock.colour + "-"
                    + this.picked_sock_pair.left_sock.unique_id + ", " + this.picked_sock_pair.right_sock.colour
                    + "-" + this.picked_sock_pair.right_sock.unique_id + ") :\t " + getName() + " \t\t --> \t "
                    + picked_sock_pair.left_sock.colour + " Shelf");
            picked_sock_pair = null;
        }
    }
}
