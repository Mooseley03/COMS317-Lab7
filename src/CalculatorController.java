import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorController {
    private CalculatorModel model;
    private CalculatorView view;
    private String currentOperand = "";
    private String operator = "";
    private boolean operatorPressed = false;
    private boolean isResultDisplayed = false;


    public CalculatorController(CalculatorModel model, CalculatorView view) {
        this.model = model;
        this.view = view;
        this.view.addButtonListener(new ButtonClickListener());
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            // ---Clear button handling ---
            if ("C".equals(command)) {
                handleClear();
            }
            // ---Delete button handling ---
            else if ("DEL".equals(command)) {
                handleDelete();
            } else if (command.charAt(0) == 'M') {
                handleMemoryFunctions(command);
            } else if (command.equals("=")) {
                handleEquals();
            } else if ("0123456789.".contains(command)) {
                handleOperand(command);
            } else {
                handleOperator(command);
            }
        }

        private void handleClear() {
            model.setResult(0);
            model.memoryClear();
            currentOperand = "";
            operator = "";
            operatorPressed = false;
            view.setDisplayText("");
        }

        private void handleDelete() {
            // Delete should only work on operand entry, not on results
            if (!currentOperand.isEmpty() && !isResultDisplayed) {
                currentOperand = currentOperand.substring(0, currentOperand.length() - 1);
                view.setDisplayText(currentOperand);
            }
        }

        private void handleOperand(String operand) {

            // If we have a result displayed and start typing a new number, reset
            if (isResultDisplayed) {
                currentOperand = operand;
                isResultDisplayed = false;
            } else if (operatorPressed) {
                currentOperand = operand;
                operatorPressed = false;
            } else {
                currentOperand += operand;
            }
            view.setDisplayText(currentOperand);
        }

        private void handleOperator(String op) {
            try {
                // For operations that require operands
                if (currentOperand.isEmpty() && !op.equals("√") && !op.equals("x²")) {
                    return; // Need an operand for most operations
                }

                double operand = currentOperand.isEmpty() ? 0 : Double.parseDouble(currentOperand);

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
                        // for standard operators (+,-,*,/) store the first operand
                        // and set the operator
                        model.setResult(operand);  // Store first operand in the model
                        operator = op;
                        operatorPressed = true;
                        // First operand remains on screen, operation button shows active
                        break;

                }
            } catch (NumberFormatException | ArithmeticException ex) {
                view.setDisplayText("Error");
            }
        }

        private void handleEquals() {
            try {
                if (currentOperand.isEmpty() || operator.isEmpty()) {
                    return; // Need both operand and operator
                }

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
                String resultText = formatResult(result);
                view.setDisplayText(resultText);

                //Reset the operator and set currentOperand to the result
                //This allows starting a fresh calculation or continuing with the result
                currentOperand = resultText;
                operator = "";
                operatorPressed = false;
                isResultDisplayed = true;


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
            try {
                switch (command) {
                    case "M+": // Add to memory
                        if (isResultDisplayed) {
                            double value = model.getResult();
                            model.memoryAdd(value);
                        } else {
                            view.setDisplayText("Error");
                        }
                        break;
                    case "M-":
                        if (isResultDisplayed) {
                            double result = model.getResult();
                            double memoryValue = model.memoryRecall();
                            double updatedMemory = result - memoryValue; // Subtract memory from result
                            model.memoryClear();
                            model.memoryAdd(updatedMemory); // Update memory with the new value
                            String memoryText = formatResult(updatedMemory);
                            view.setDisplayText(memoryText);
                            currentOperand = memoryText;
                            operator = ""; // Reset operator
                            operatorPressed = false;
                            isResultDisplayed = true; // Mark as a valid result
                        } else {
                            view.setDisplayText("Error");
                        }
                        break;
                    case "MR":
                        double memoryValue = model.memoryRecall();
                        String memoryText = formatResult(memoryValue);
                        view.setDisplayText(memoryText);
                        currentOperand = memoryText;
                        isResultDisplayed = true; // Treat recalled memory as a valid result
                        break;
                    case "MC":
                        model.memoryClear();
                        break;
                    default:
                        break;
                }
            } catch (Exception ex) {
                view.setDisplayText("Error");
            }
        }
    }//end private class ButtonClickListener

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

