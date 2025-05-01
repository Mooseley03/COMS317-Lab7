import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorController {
    private CalculatorModel model;
    private CalculatorView view;
    private String currentOperand = "";
    private String operator = "";
    private boolean operatorPressed = false;

    public CalculatorController(CalculatorModel model, CalculatorView view) {
        this.model = model;
        this.view = view;
        this.view.addButtonListener(new ButtonClickListener());
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.charAt(0) == 'M') {
                handleMemoryFunctions(command);
            } else if (command.equals("=")) {
                handleEquals();
            } else if ("0123456789.".contains(command)) {
                handleOperand(command);
            } else {
                handleOperator(command);
            }
        }

        private void handleOperand(String operand) {
            if (operatorPressed) {
                currentOperand = operand;
                operatorPressed = false;
            } else {
                currentOperand += operand;
            }
            view.setDisplayText(currentOperand);
        }

        private void handleOperator(String op) {
            try {
                double operand = Double.parseDouble(currentOperand);

                switch (op) {
                    case "x²":
                        double squareResult = model.square(operand);
                        view.setDisplayText(formatResult(squareResult));
                        currentOperand = String.valueOf(squareResult);
                        break;
                    case "√":
                        double sqrtResult = model.squareRoot(operand);
                        view.setDisplayText(formatResult(sqrtResult));
                        currentOperand = String.valueOf(sqrtResult);
                        break;
                    default:
                        if (operator.isEmpty()) {
                            model.setResult(operand); // Store first number
                            operator = op;
                            operatorPressed = true;
                        }
                        break;
                }
            } catch (NumberFormatException | ArithmeticException ex) {
                view.setDisplayText("Error");
            }
        }

        private void handleEquals() {
            try {
                double operand = Double.parseDouble(currentOperand);
                double result = 0;

                switch (operator) {
                    case "+":
                        result = model.add(model.getResult(), operand);
                        break;
                    case "-":
                        result = model.subtract(model.getResult(), operand);
                        break;
                    case "*":
                        result = model.multiply(model.getResult(), operand);
                        break;
                    case "/":
                        result = model.divide(model.getResult(), operand);
                        if (Double.isNaN(result) || Double.isInfinite(result)) {
                            view.setDisplayText("Error"); // Display error if the result is NaN or Infinity
                            return;
                        }
                        break;
                    default:
                        return;
                }

                view.setDisplayText(formatResult(result));

            } catch (NumberFormatException ex) {
                view.setDisplayText("Error");
            }
        }

        private String formatResult(double value) {
            if (value == (long) value) {
                return String.format("%d", (long) value);  // Removes .0 if unnecessary
            } else {
                return String.valueOf(value);              // Keeps decimal for non-integers
            }
        }

        private void handleMemoryFunctions(String command) {
            switch (command) {
                case "M+":
                    model.memoryAdd(Double.parseDouble(currentOperand));
                    break;
                case "M-":
                    model.memorySubtract(Double.parseDouble(currentOperand));
                    break;
                case "MR":
                    view.setDisplayText(formatResult(model.memoryRecall()));
                    break;
                case "MC":
                    model.memoryClear();
                    break;
                default:
                    break;
            }
        }
    }

    // After each operation, update the view with the result.
    public void updateView() {
        view.setDisplayText(formatResult(model.getResult()));
    }
    
    private String formatResult(double value) {
        if (value == (long) value) {
            return String.format("%d", (long) value);  // Removes .0 if unnecessary
        } else {
            return String.valueOf(value);              // Keeps decimal for non-integers
        }
    }
}

