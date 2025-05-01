import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorView extends JFrame {
    public JTextField display;
    public JButton[] numberButtons;
    public JButton addButton, subButton, mulButton, divButton, equalsButton, clearButton, squareButton, sqrtButton, memoryAddButton, memorySubButton, memoryRecallButton, memoryClearButton;

    public CalculatorView() {
        setTitle("Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4));

        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            buttonPanel.add(numberButtons[i]);
        }

        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        equalsButton = new JButton("=");
        clearButton = new JButton("C");
        squareButton = new JButton("x²");
        sqrtButton = new JButton("√");
        memoryAddButton = new JButton("M+");
        memorySubButton = new JButton("M-");
        memoryRecallButton = new JButton("MR");
        memoryClearButton = new JButton("MC");

        buttonPanel.add(addButton);
        buttonPanel.add(subButton);
        buttonPanel.add(mulButton);
        buttonPanel.add(divButton);
        buttonPanel.add(equalsButton);
        buttonPanel.add(squareButton);
        buttonPanel.add(sqrtButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(memoryAddButton);
        buttonPanel.add(memorySubButton);
        buttonPanel.add(memoryRecallButton);
        buttonPanel.add(memoryClearButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    public String getDisplayText() {
        return display.getText();
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
        squareButton.addActionListener(listener);
        sqrtButton.addActionListener(listener);
        memoryAddButton.addActionListener(listener);
        memorySubButton.addActionListener(listener);
        memoryRecallButton.addActionListener(listener);
        memoryClearButton.addActionListener(listener);
    }
}
