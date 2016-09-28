package gui;

import app.CashRegister;
import com.sun.tools.internal.jxc.ap.Const;
import com.sun.tools.internal.xjc.util.StringCutter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CashBox extends JFrame {

    /* Feel free to add more instance variables. */
    private boolean isScreenCleared = true;             // Checks if screen is cleared
    private boolean operatorClicked = false;            // Checks if operator is clicked to clear the screen for next term input
    private boolean consecutiveOperators = true;        // Checks if user clicks two operators consecutively
    private boolean buttonClicked = false;              // Checks if any Cash Register button is clicked
    private double sum = 0.0;                           // Tracks the sum of terms entered by the user
    private double addend = 0.0;                        // Refers to the value on the screen when user presses + or -
    private double product = 1.0;                       // Tracks the result of all multiplication and division operations
    private double multiplier = 0.0;                    // Refers to the value on the screen when the user presses * or /
    private String operator = "";                       // To hold the operator pressed by the user
    private double recurrentEnterTerm = 0.0;            // Tracks recurrent presses of Enter
    private String recurrentEnterOperator = "";

    private JLabel screen;
    private CashRegister cr = new CashRegister();

    /**
     * Sets up the Cash Register GUI. I can't call it CashRegister
     * because that's already the name of the CashRegister class.
     */
    public CashBox() {
        setTitle("Cash Register");
        Container c = this.getContentPane();
        c.add(createScreen(), BorderLayout.PAGE_START);
        c.add(getKeys(), BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        setVisible(true);//making the frame visible

    }

    /**
     * Creates the JLabel that represents the screen of the CashBox.
     * The screen is where the numbers are displayed.
     * You shouldn't need to make any changes to this method.
     */
    private JLabel createScreen() {
        screen = new JLabel();
        screen.setOpaque(true);
        screen.setBackground(Color.WHITE);
        screen.setFont(Constants.SCREEN_FONT);
        screen.setText(Constants.START_SCREEN);
        return screen;
    }

    /**
     * Returns the panel containing all of the keys available to this cash box.
     * You shouldn't need to make any changes to this method.
     */
    private JPanel getKeys() {
        JPanel keys = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        keys.add(getNumberButtons());
        keys.add(getOtherKeys());
        return keys;
    }

    /**
     * This creates the grid with the numbers 9 down to 00 and the decimal point.
     * You shouldn't need to make any changes to this method.
     */
    private JPanel getNumberButtons() {
        JPanel numbers = new JPanel(new GridLayout(4, 3, 10, 10));
        for (int i = 9; i >= 0; i--) {
            numbers.add(createNumberButton(Integer.toString(i)));
        }
        numbers.add(createNumberButton("00"));
        numbers.add(createNumberButton("."));
        return numbers;
    }

    /**
     * Creates a number button that appends it's value to the screen
     * when pressed.
     */
    private JButton createNumberButton(final String text) {
        JButton button = createButtonWithFont(text);
        button.setBackground(Color.CYAN);
        button.setPreferredSize(new Dimension(55, 55));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (operatorClicked) {
                    /*
                     * If operator is clicked, clear the screen and starts adding user input to the screen
                     */
                    screen.setText("");
                    isScreenCleared = true;
                    operatorClicked = false;
                }

                if (buttonClicked){
                    /*
                     * If button from Cash Register is clicked, clear the screen and starts adding user input
                     */
                    screen.setText("");
                    isScreenCleared = true;
                    buttonClicked = false;

                    /*
                     * Set consecutive operators to false since an input, other than the operator has been
                     * entered.
                     */
                    consecutiveOperators = false;
                }

                if (isScreenCleared) {
                    /*
                     * If the screen is clear, decimal point and double zero behave differently.
                     */
                    switch(text) {
                        case Constants.DOUBLE_ZERO: // do nothing
                            return;
                        case Constants.DECIMAL_POINT: // Create decimal point with leading zero.
                            screen.setText(Constants.LEADING_DECIMAL);
                            break;
                        default: // Set this new value as the screen value.
                            screen.setText(text);
                            break;
                    }
                    isScreenCleared = false;
                } else {
                    // Append this text to the existing screen text.
                    String screenText = screen.getText();
                    switch(text) {
                        case Constants.DECIMAL_POINT:
                            // Don't add another decimal point if there's already one.
                            if (!screenText.contains(Constants.DECIMAL_POINT)) {
                                screen.setText(screenText.concat(Constants.DECIMAL_POINT));
                            }
                            break;
                        default:
                            screen.setText(screenText.concat(text));
                            break;
                    }
                }
            }
        });
        return button;
    }

    /**
     * Each of these buttons needs to be implemented correctly.
     *
     * The names that match methods in the cash register should call
     * that method in the cash register with whatever value is on the screen.
     * *** TODO: You need to add a button "Give change" that calls the give change method in CashRegister. ***
     * If that method returns some value, then that returned value should be displayed on the screen.
     *
     * The operators (+, -, *, /, =/Enter) should operate just like the calculator.
     * A value is entered. Then an operator is pressed. Then the second value is typed in.
     * No matter what the next button pressed is, the result of the first operation should be displayed
     * on the screen and should be the input for the method called or
     * the first operand of the next operator pressed.
     *
     * I've already started the createOtherButton helper method that you should add the ENTER functionality to.
     * Also you should implement the createCalculatorButton and createCashRegisterButton methods.
     */
    public JPanel getOtherKeys() {
        JPanel keys = new JPanel(new GridLayout(6, 2, 3, 3));
        // Add each of the required keys to the panel.
        keys.add(createCashRegisterButton(Constants.RECORD_PURCHASE));
        keys.add(createCashRegisterButton(Constants.ENTER_PAYMENT));
        keys.add(createCashRegisterButton(Constants.GIVE_CHANGE));      // Added
        keys.add(createCashRegisterButton(Constants.ADD_MONEY));
        keys.add(createCashRegisterButton(Constants.REGISTER_TOTAL));
        keys.add(createCalculatorButton(Constants.CLEAR));
        keys.add(createCalculatorButton(Constants.ADD));
        keys.add(createCalculatorButton(Constants.SUBTRACT));
        keys.add(createCalculatorButton(Constants.MULTIPLY));
        keys.add(createCalculatorButton(Constants.DIVIDE));
        keys.add(createCalculatorButton(Constants.ENTER));

        /*
         * Sets all of the keys to the same size and gives them a red outline.
         */
        for (Component key : keys.getComponents()) {
            key.setPreferredSize(new Dimension(175, 50));
            key.setBackground(Color.RED);
        }
        return keys;
    }

    /**
     * This is where you will implement the calculator buttons (+, -, *, /, =/Enter)
     * I've already implemented the CLEAR button.
     */
    private JButton createCalculatorButton(final String text) {
        JButton button = createButtonWithFont(text);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch(text) {
                    case Constants.CLEAR: // Clear the screen to its original value
                        isScreenCleared = true;
                        screen.setText(Constants.START_SCREEN);
                        ClearData();
                        break;
                    case Constants.ADD:
                        /*
                         * Adds amounts considering the priority of operators.
                         * The operation should behave just like on a normal calculator
                         */
                        if (consecutiveOperators){
                            sum = 0.0;
                        }

                        if (operator != Constants.MULTIPLY && operator != Constants.DIVIDE){ // Addition or subtraction
                            addend = Double.parseDouble(screen.getText()); // The number on the screen before operator click

                            screen.setText(String.format("%.2f", sum + addend));
                            sum = sum + addend; // Actualize sum for eventual addition

                        }else{ // When the previous operator was either a * or a /
                            multiplier = Double.parseDouble(screen.getText());
                            product = operator == Constants.MULTIPLY ? product * multiplier : product / multiplier;

                            screen.setText(String.format("%.2f", sum + product));
                            sum = sum + product;

                            // Reinitialize product and multiplier
                            product = 1.0;
                            multiplier = 0.0;
                        }
                        operator = Constants.ADD;           // Updates operator
                        operatorClicked = true;             // Updates operator state
                        break;
                    case Constants.SUBTRACT:
                        /*
                         * Same idea with addition
                         * In case the user is pressing the same operator over and over again
                         */
                        if (consecutiveOperators){
                            sum = 0.0;
                        }

                        if (operator != Constants.MULTIPLY && operator != Constants.DIVIDE){ // Addition or subtraction
                            addend = Double.parseDouble(screen.getText()); // The number on the screen before operator click

                            double display1 = operator == Constants.ADD ? sum + addend : (operator == Constants.SUBTRACT ? sum - addend : addend);
                            screen.setText(String.format("%.2f", display1));
                            sum = display1; // Actualize sum for eventual addition

                        }else{ // When the previous operator was either * or /
                            multiplier = Double.parseDouble(screen.getText());
                            product = operator == Constants.MULTIPLY ? product * multiplier : product / multiplier;

                            screen.setText(String.format("%.2f", sum + product));
                            sum = sum + product;

                            // Reinitialize product and multiplier
                            product = 1.0;
                            multiplier = 0.0;
                        }
                        operator = Constants.SUBTRACT;          // Updates operator
                        operatorClicked = true;                 // Updates operator state
                        break;
                    case Constants.MULTIPLY:
                        /*
                         * Based on the previous operator, compute the number that is supposed to go on the screen
                         * If the previous operator was minus, make sure the minus is not left behind in calculations
                         * The amount shown on screen should behave just like on a regular calculator
                         *
                         * NOTE: In case the user presses the same operator over and over again, this should not alter
                         *       the result.
                         */
                        if (consecutiveOperators){
                            product = 1.0;
                        }

                        multiplier = Double.parseDouble(screen.getText());
                        double onscreen1 = operator == Constants.ADD || operator == Constants.SUBTRACT ? multiplier : (operator == Constants.MULTIPLY ? product * multiplier : multiplier / product);
                        screen.setText(String.format("%.2f", onscreen1));

                        if (operator == Constants.SUBTRACT) multiplier = (-1.0) * multiplier;

                        product = onscreen1; // operator == Constants.MULTIPLY ? product * multiplier : multiplier / product;

                        operator = Constants.MULTIPLY;          // Updates operator
                        operatorClicked = true;                 // Updates operator state
                        break;

                    case Constants.DIVIDE:
                        /*
                         * Based on the previous operator, compute the number that is supposed to go on the screen
                         * Same logic as for multiplication.
                         *
                         * NOTE: In case the user presses the same operator over and over again, this should not alter
                         *       the result.
                         */
                        if (consecutiveOperators){
                            product = 1.0;
                        }

                        multiplier = Double.parseDouble(screen.getText());
                        double onscreen3 = operator == Constants.ADD || operator == Constants.SUBTRACT ? multiplier : (operator == Constants.MULTIPLY ? product * multiplier : multiplier / product);
                        screen.setText(String.format("%.2f", onscreen3));

                        if (operator == Constants.SUBTRACT) multiplier = (-1.0) * multiplier;

                        product = operator == Constants.MULTIPLY ? product * multiplier : multiplier / product;

                        operator = Constants.DIVIDE;            // Updates operator
                        operatorClicked = true;                 // Updates operator state
                        break;

                    case Constants.ENTER:
                        /*
                         * Three conditions/sub-cases:
                         * 1. If the last operation was + or -, execute the operation and display final answer
                         * 2. If the last operation was * or /, find the result from * or /, then compute the total
                         * 3. If the user decides to keep pressing Enter, the last operation will be performed over
                         *    and over again on the last term (just like on an usual calculator)
                         */
                        if (operator == Constants.ADD || operator == Constants.SUBTRACT){
                            addend = Double.parseDouble(screen.getText());
                            double onscreen2 = operator == Constants.SUBTRACT ? sum - addend : sum + addend;
                            screen.setText(String.format("%.2f", onscreen2));

                            // Save temporary term and operator for eventual recurrent presses of Enter
                            recurrentEnterTerm = addend;
                            recurrentEnterOperator = operator;
                        }else if (operator == Constants.MULTIPLY || operator == Constants.DIVIDE){
                            multiplier = Double.parseDouble(screen.getText());
                            product = operator == Constants.MULTIPLY ? product * multiplier : product / multiplier;
                            screen.setText(String.format("%.2f", sum + product));

                            // Save temporary term and operator for eventual recurrent presses of Enter
                            recurrentEnterTerm = multiplier;
                            recurrentEnterOperator = operator;
                        }else{ // In case the user decides to keep pressing Enter button
                            double temp = Double.parseDouble(screen.getText());
                            switch (recurrentEnterOperator) {
                                case Constants.ADD:
                                    screen.setText(String.format("%.2f", temp + recurrentEnterTerm));
                                    break;
                                case Constants.SUBTRACT:
                                    screen.setText(String.format("%.2f", temp - recurrentEnterTerm));
                                    break;
                                case Constants.MULTIPLY:
                                    screen.setText(String.format("%.2f", temp * recurrentEnterTerm));
                                    break;
                                case Constants.DIVIDE:
                                    screen.setText(String.format("%.2f", temp / recurrentEnterTerm));
                                    break;
                                default:
                                    break;
                            }
                        }
                        buttonClicked = true;
                        ClearData();
                        break;
                    default: // do nothing
                        break;
                }
            }
        });
        return button;
    }

    /**
     * This is where you will implement the buttons that call CashRegister methods.
     * Record purchase, Enter payment, Add money, Register total
     *
     * The returned values should never use more than two decimal places.
     * So if a user calls Register total, the total should read 13.85 (hint: String.format())
     */
    private JButton createCashRegisterButton(final String text) {
        /*
         * Call functions from the Cash Register based on the user's actions
         */
        JButton button = createButtonWithFont(text);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (text) {
                    case Constants.RECORD_PURCHASE:
                        screen.setText(String.format("%.2f", cr.recordPurchase(Double.parseDouble(screen.getText()))));
                        break;
                    case Constants.ENTER_PAYMENT:
                        screen.setText(String.format("%.2f", cr.enterPayment(Double.parseDouble(screen.getText()))));
                        break;
                    case Constants.GIVE_CHANGE:
                        screen.setText(String.format("%.2f", cr.giveChange()));
                        break;
                    case Constants.ADD_MONEY:
                        screen.setText(String.format("%.2f", cr.addMoney(Double.parseDouble(screen.getText()))));
                        break;
                    case Constants.REGISTER_TOTAL:
                        screen.setText(String.format("%.2f", cr.getTotal()));
                        break;
                    default:
                        break;
                }
                buttonClicked = true;
            }
        });

        return button;
    }

    /**
     * This method creates a button with the preferred font and sets the opacity
     * to true to allow the color of the button to show.
     * You shouldn't need to make any changes to this method.
     */
    private JButton createButtonWithFont(String text) {
        JButton button = new JButton(text);
        button.setOpaque(true);
        button.setFont(Constants.BUTTON_FONT);
        return button;
    }

    /*
     * Helper function
     * Clears or reinitialize some data in the calculator
     */
    private void ClearData(){
        operatorClicked = false;
        sum = 0.0;
        addend = 0.0;
        product = 1.0;
        multiplier = 0.0;
        operator = "";
    }

    // Use this to start up the GUI.
    public static void main(String[] argv) {
        CashBox cb = new CashBox();
    }
}
