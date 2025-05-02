import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class CalculatorView extends JFrame {
    public JTextField display;
    public JButton[] numberButtons;
    public JButton addButton, subButton, mulButton, divButton;
    public JButton equalsButton, clearButton, deleteButton;
    public JButton squareButton, sqrtButton, negateButton;
    public JButton memoryAddButton, memorySubButton, memoryRecallButton, memoryClearButton;
    public JButton decimalButton;  // Added the decimal button

    // Track the last operation button pressed
    private JButton lastOperationButton = null;

    public CalculatorView() {
        setTitle("Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        add(display, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 4)); // 6 rows x 4 columns = 24 slots

        // Number buttons
        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setName("numberButton" + i);
            buttonPanel.add(numberButtons[i]);
        }

        // Operation and function buttons
        addButton = new JButton("+");
        addButton.setName("addButton");

        subButton = new JButton("-");
        subButton.setName("subButton");

        mulButton = new JButton("*");
        mulButton.setName("mulButton");

        divButton = new JButton("/");
        divButton.setName("divButton");

        equalsButton = new JButton("=");
        equalsButton.setName("equalsButton");

        clearButton = new JButton("C");
        clearButton.setName("clearButton");

        deleteButton = new JButton("DEL");
        deleteButton.setName("deleteButton");

        squareButton = new JButton("x²");
        squareButton.setName("squareButton");

        sqrtButton = new JButton("√");
        sqrtButton.setName("sqrtButton");

        negateButton = new JButton("±");
        negateButton.setName("negateButton");

        memoryAddButton = new JButton("M+");
        memoryAddButton.setName("memoryAddButton");

        memorySubButton = new JButton("M-");
        memorySubButton.setName("memorySubButton");

        memoryRecallButton = new JButton("MR");
        memoryRecallButton.setName("memoryRecallButton");

        memoryClearButton = new JButton("MC");
        memoryClearButton.setName("memoryClearButton");

        // Decimal Button
        decimalButton = new JButton(".");
        decimalButton.setName("decimalButton");

        // Add buttons to pannel
        buttonPanel.add(decimalButton);
        buttonPanel.add(addButton);
        buttonPanel.add(subButton);
        buttonPanel.add(mulButton);
        buttonPanel.add(divButton);
        buttonPanel.add(squareButton);
        buttonPanel.add(sqrtButton);
        buttonPanel.add(negateButton);
        buttonPanel.add(equalsButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(memoryAddButton);
        buttonPanel.add(memorySubButton);
        buttonPanel.add(memoryRecallButton);
        buttonPanel.add(memoryClearButton);
        

        // Fill remaining slots (if any) to reach 24
        int total = buttonPanel.getComponentCount();
        while (total++ < 24) {
            buttonPanel.add(new JLabel(""));
        }

        add(buttonPanel, BorderLayout.CENTER);

        // Add pressed-style effect
        addPressedStyling(numberButtons);
        addPressedStyling(
                addButton, subButton, mulButton, divButton,
                equalsButton, clearButton, deleteButton,
                squareButton, sqrtButton, negateButton,
                memoryAddButton, memorySubButton,
                memoryRecallButton, memoryClearButton,
                decimalButton  // Add decimal button styling
        );
        
        // Add listener to equalsButton to reset operation button colors
        equalsButton.addActionListener(e -> resetOperationButtons());
    }

    private void addPressedStyling(JButton... buttons) {
        for (JButton b : buttons) {
            b.getModel().addChangeListener(e -> {
                if (b.getModel().isArmed() || b.getModel().isPressed()) {
                    b.setBackground(Color.LIGHT_GRAY);
                } else {
                    // Reset color if it is not the last clicked operation button
                    if (b != lastOperationButton) {
                        b.setBackground(UIManager.getColor("Button.background"));
                    }
                }
            });

            // Add action listener to handle button click events
            b.addActionListener(e -> {
                // If an operation button is clicked, update the lastOperationButton
                if (b == addButton || b == subButton || b == mulButton || b == divButton) {
                    if (lastOperationButton != null) {
                        lastOperationButton.setBackground(UIManager.getColor("Button.background"));
                    }
                    b.setBackground(Color.LIGHT_GRAY);
                    lastOperationButton = b;
                }
            });
        }
    }

    // Reset operation buttons when equals button is clicked
    private void resetOperationButtons() {
        if (lastOperationButton != null) {
            lastOperationButton.setBackground(UIManager.getColor("Button.background"));
        }
    }

    public String getDisplayText() {
        return display.getText();
    }

    public String getLastOperationButtonColor(){
        return lastOperationButton.getBackground().toString();
    }

    public void setDisplayText(String text) {
        display.setText(text);
    }

    public void addButtonListener(ActionListener listener) {
        for (JButton button : numberButtons) {
            button.addActionListener(listener);
        }
        addButton.addActionListener(listener);
        subButton.addActionListener(listener);
        mulButton.addActionListener(listener);
        divButton.addActionListener(listener);
        equalsButton.addActionListener(listener);
        clearButton.addActionListener(listener);
        deleteButton.addActionListener(listener);
        squareButton.addActionListener(listener);
        sqrtButton.addActionListener(listener);
        negateButton.addActionListener(listener);
        memoryAddButton.addActionListener(listener);
        memorySubButton.addActionListener(listener);
        memoryRecallButton.addActionListener(listener);
        memoryClearButton.addActionListener(listener);

        // Add action listener for the decimal button
        decimalButton.addActionListener(listener);
    }
}
