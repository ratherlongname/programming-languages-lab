import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;
import java.awt.event.FocusListener;

public class DifferentCalculator implements ActionListener, FocusListener {
    String variant;
    JFrame frame;

    Timer timer;

    JPanel display_panel;
    JLabel calc_label;
    String operand_1;
    String operand_2;
    String operator;

    JPanel number_keys_panel;
    JButton zero;
    JButton one;
    JButton two;
    JButton three;
    JButton four;
    JButton five;
    JButton six;
    JButton seven;
    JButton eight;
    JButton nine;

    JPanel function_keys_panel;
    JButton add;
    JButton subtract;
    JButton multiply;
    JButton divide;
    JButton equals;

    public DifferentCalculator(String variant) {
        this.variant = variant;

        UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
        frame = new JFrame("Different Calculator");
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setSize(new Dimension(500, 700));

        display_panel = new JPanel(new GridLayout(1, 1));
        number_keys_panel = new JPanel(new GridLayout(4, 3));
        function_keys_panel = new JPanel(new GridLayout(1, 5));
        frame.add(display_panel);
        frame.add(number_keys_panel);
        frame.add(function_keys_panel);

        calc_label = new JLabel("");
        calc_label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        display_panel.add(calc_label);

        one = new JButton("1");
        two = new JButton("2");
        three = new JButton("3");
        four = new JButton("4");
        five = new JButton("5");
        six = new JButton("6");
        seven = new JButton("7");
        eight = new JButton("8");
        nine = new JButton("9");
        zero = new JButton("0");
        one.addActionListener(this);
        two.addActionListener(this);
        three.addActionListener(this);
        four.addActionListener(this);
        five.addActionListener(this);
        six.addActionListener(this);
        seven.addActionListener(this);
        eight.addActionListener(this);
        nine.addActionListener(this);
        zero.addActionListener(this);
        one.addFocusListener(this);
        one.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        two.addFocusListener(this);
        two.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        three.addFocusListener(this);
        three.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        four.addFocusListener(this);
        four.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        five.addFocusListener(this);
        five.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        six.addFocusListener(this);
        six.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        seven.addFocusListener(this);
        seven.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        eight.addFocusListener(this);
        eight.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        nine.addFocusListener(this);
        nine.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        zero.addFocusListener(this);
        zero.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));

        number_keys_panel.add(one);
        number_keys_panel.add(two);
        number_keys_panel.add(three);
        number_keys_panel.add(four);
        number_keys_panel.add(five);
        number_keys_panel.add(six);
        number_keys_panel.add(seven);
        number_keys_panel.add(eight);
        number_keys_panel.add(nine);
        number_keys_panel.add(zero);

        add = new JButton("+");
        subtract = new JButton("-");
        multiply = new JButton("*");
        divide = new JButton("/");
        equals = new JButton("=");
        add.addActionListener(this);
        subtract.addActionListener(this);
        multiply.addActionListener(this);
        divide.addActionListener(this);
        equals.addActionListener(this);
        add.addFocusListener(this);
        add.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        subtract.addFocusListener(this);
        subtract.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        multiply.addFocusListener(this);
        multiply.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        divide.addFocusListener(this);
        divide.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        equals.addFocusListener(this);
        equals.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));

        function_keys_panel.add(add);
        function_keys_panel.add(subtract);
        function_keys_panel.add(multiply);
        function_keys_panel.add(divide);
        function_keys_panel.add(equals);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        operand_1 = "";
        operand_2 = "";
        operator = "";

        timer = new Timer(800, this);
        timer.setRepeats(true);
        timer.start();

        one.requestFocus();

        // String test_num = "-0";
        // int conv = Integer.parseInt(test_num);
        // System.out.println("conv is " + conv);
    }

    public static void main(String[] args) {
        if (!args[0].equals("variant-a") && !args[0].equals("variant-b")) {
            System.out.println("Usage:");
            System.out.println("    java DifferentCalculator variant-a \t\t Variant (a) of answer-03");
            System.out.println("    java DifferentCalculator variant-b \t\t Variant (b) of answer-03");
            return;
        }

        System.out.println("Different Calculator");
        System.out.println("====================");
        new DifferentCalculator(args[0]);
    }

    @Override
    public void focusGained(FocusEvent fe) {
        if (fe.getSource() == one) {
            one.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
        } else if (fe.getSource() == two) {
            two.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
        } else if (fe.getSource() == three) {
            three.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
        } else if (fe.getSource() == four) {
            four.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
        } else if (fe.getSource() == five) {
            five.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
        } else if (fe.getSource() == six) {
            six.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
        } else if (fe.getSource() == seven) {
            seven.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
        } else if (fe.getSource() == eight) {
            eight.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
        } else if (fe.getSource() == nine) {
            nine.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
        } else if (fe.getSource() == zero) {
            zero.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
        } else if (fe.getSource() == add) {
            add.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
        } else if (fe.getSource() == subtract) {
            subtract.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
        } else if (fe.getSource() == multiply) {
            multiply.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
        } else if (fe.getSource() == divide) {
            divide.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
        } else if (fe.getSource() == equals) {
            equals.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
        }

        return;
    }

    @Override
    public void focusLost(FocusEvent fe) {
        if (fe.getSource() == one) {
            one.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        } else if (fe.getSource() == two) {
            two.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        } else if (fe.getSource() == three) {
            three.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        } else if (fe.getSource() == four) {
            four.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        } else if (fe.getSource() == five) {
            five.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        } else if (fe.getSource() == six) {
            six.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        } else if (fe.getSource() == seven) {
            seven.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        } else if (fe.getSource() == eight) {
            eight.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        } else if (fe.getSource() == nine) {
            nine.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        } else if (fe.getSource() == zero) {
            zero.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        } else if (fe.getSource() == add) {
            add.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        } else if (fe.getSource() == subtract) {
            subtract.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        } else if (fe.getSource() == multiply) {
            multiply.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        } else if (fe.getSource() == divide) {
            divide.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        } else if (fe.getSource() == equals) {
            equals.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false));
        }

        return;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == timer) {
            System.out.println("Timer event");
            if (variant.equals("variant-a")) {
                focus_next_button_variant_a();
            } else if (variant.equals("variant-b")) {
                focus_next_button_variant_b();
            }
        } else if (ae.getSource() == one || ae.getSource() == two || ae.getSource() == three || ae.getSource() == four
                || ae.getSource() == five || ae.getSource() == six || ae.getSource() == seven || ae.getSource() == eight
                || ae.getSource() == nine || ae.getSource() == zero) {
            System.out.println(ae.getActionCommand() + " pressed");
            handle_digit(ae.getActionCommand());
        } else if (ae.getSource() == add || ae.getSource() == subtract || ae.getSource() == multiply
                || ae.getSource() == divide || ae.getSource() == equals) {
            System.out.println(ae.getActionCommand() + " pressed");
            handle_operator(ae.getActionCommand());
        }

        return;
    }

    public void focus_next_button_variant_a() {
        if (one.hasFocus()) {
            two.requestFocus();
        } else if (two.hasFocus()) {
            three.requestFocus();
        } else if (three.hasFocus()) {
            four.requestFocus();
        } else if (four.hasFocus()) {
            five.requestFocus();
        } else if (five.hasFocus()) {
            six.requestFocus();
        } else if (six.hasFocus()) {
            seven.requestFocus();
        } else if (seven.hasFocus()) {
            eight.requestFocus();
        } else if (eight.hasFocus()) {
            nine.requestFocus();
        } else if (nine.hasFocus()) {
            zero.requestFocus();
        } else if (zero.hasFocus()) {
            one.requestFocus();
        } else if (add.hasFocus()) {
            subtract.requestFocus();
        } else if (subtract.hasFocus()) {
            multiply.requestFocus();
        } else if (multiply.hasFocus()) {
            divide.requestFocus();
        } else if (divide.hasFocus()) {
            equals.requestFocus();
        } else if (equals.hasFocus()) {
            add.requestFocus();
        }

        return;
    }

    public void focus_next_button_variant_b() {
        if (one.hasFocus()) {
            two.requestFocus();
        } else if (two.hasFocus()) {
            three.requestFocus();
        } else if (three.hasFocus()) {
            four.requestFocus();
        } else if (four.hasFocus()) {
            five.requestFocus();
        } else if (five.hasFocus()) {
            six.requestFocus();
        } else if (six.hasFocus()) {
            seven.requestFocus();
        } else if (seven.hasFocus()) {
            eight.requestFocus();
        } else if (eight.hasFocus()) {
            nine.requestFocus();
        } else if (nine.hasFocus()) {
            zero.requestFocus();
        } else if (zero.hasFocus()) {
            add.requestFocus();
        } else if (add.hasFocus()) {
            subtract.requestFocus();
        } else if (subtract.hasFocus()) {
            multiply.requestFocus();
        } else if (multiply.hasFocus()) {
            divide.requestFocus();
        } else if (divide.hasFocus()) {
            equals.requestFocus();
        } else if (equals.hasFocus()) {
            one.requestFocus();
        }

        return;
    }

    public void handle_digit(String digit) {
        if (variant.equals("variant-a")) {
            if (operator.equals("")) {
                operand_1 = digit;
                calc_label.setText(operand_1);
                add.requestFocus();
            } else {
                operand_2 = digit;
                calc_label.setText(operand_1 + " " + operator + " " + operand_2);
                equals.doClick();
            }
        } else if (variant.equals("variant-b")) {
            if (operator.equals("")) {
                operand_1 += digit;
                calc_label.setText(operand_1);
            } else {
                operand_2 += digit;
                calc_label.setText(operand_1 + " " + operator + " " + operand_2);
            }
        }

        return;
    }

    public void handle_operator(String op) {
        if (operand_1.equals("") || operand_1.equals("-") || operand_1.equals("+")) {
            if (!variant.equals("variant-a") && (op.equals("-") || op.equals("+"))) {
                operand_1 = op;
                calc_label.setText(operand_1);
            }
        } else if (operator.equals("")) {
            if (!op.equals("=")) {
                operator = op;
                calc_label.setText(operand_1 + " " + operator + " ");
            }
        } else if (operand_2.equals("")) {
            if (variant.equals("variant-b") && (op.equals("-") || op.equals("+"))) {
                operand_2 = op;
                calc_label.setText(operand_1 + " " + operator + " " + operand_2);
            } else if (!op.equals("=")) {
                operator = op;
                calc_label.setText(operand_1 + " " + operator);
            }
        } else if (operand_2.equals("-") || operand_2.equals("+")) {
            if (op.equals("-") || op.equals("+")) {
                operand_2 = op;
                calc_label.setText(operand_1 + " " + operator + " " + operand_2);
            }
        } else {
            if (op.equals("=")) {
                // result
                int a = Integer.parseInt(operand_1);
                int b = Integer.parseInt(operand_2);
                float result = 0;
                String result_str = "";

                if (a == 0 && b == 0 && operator.equals("/")) {
                    result_str = "0 / 0 is not defined";
                } else if (b == 0 && operator.equals("/")) {
                    result_str = "Any number divided by 0 is Infinity";
                } else {
                    switch (operator) {
                        case "+":
                            result = a + b;
                            result_str = Float.toString(result);
                            break;

                        case "-":
                            result = a - b;
                            result_str = Float.toString(result);
                            break;

                        case "*":
                            result = a * b;
                            result_str = Float.toString(result);
                            break;

                        case "/":
                            result = (float) a / (float) b;
                            result_str = Float.toString(result);
                            break;

                        default:
                            result_str = "Invalid operator: " + operator;
                            break;
                    }
                }

                calc_label.setText(result_str);
                operand_1 = "";
                operand_2 = "";
                operator = "";
            }
        }

        if (!op.equals("=") && variant.equals("variant-a"))
            one.requestFocus();

        return;
    }
}
