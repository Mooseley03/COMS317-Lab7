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

            if ("C".equals(command)) {
                handleClear();
            } else if ("DEL".equals(command)) {
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
            String displayText = view.getDisplayText();
            if (!displayText.isEmpty()) {
                displayText = displayText.substring(0, displayText.length() - 1);
                view.setDisplayText(displayText);
                currentOperand = displayText;
                isResultDisplayed = false;
            }
        }

        private void handleOperand(String operand) {
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
//                if (op.equals("-") && currentOperand.isEmpty() && !operatorPressed && !isResultDisplayed) {
//                    currentOperand = "-";
//                    view.setDisplayText(currentOperand);
//                    return;
//                }

                if (currentOperand.isEmpty() && !op.equals("√") && !op.equals("x²") && !op.equals("±")) {
                    return;
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
                    case "±":
                        operand = -operand;
                        currentOperand = String.valueOf(operand);
                        view.setDisplayText(formatResult(operand));
                        break;
                    default:
                        model.setResult(operand);
                        operator = op;
                        operatorPressed = true;
                        break;
                }
            } catch (NumberFormatException | ArithmeticException ex) {
                view.setDisplayText("Error");
            }
        }

        private void handleEquals() {
            try {
                if (currentOperand.isEmpty() || operator.isEmpty()) {
                    return;
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
                            view.setDisplayText("Error");
                            return;
                        }
                        break;
                    default:
                        return;
                }

                String resultText = formatResult(result);
                view.setDisplayText(resultText);
                currentOperand = resultText;
                operator = "";
                operatorPressed = false;
                isResultDisplayed = true;

            } catch (NumberFormatException ex) {
                view.setDisplayText("Error");
            }
        }

        private void handleMemoryFunctions(String command) {
            try {
                switch (command) {
                    case "M+":
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
                            double updatedMemory = memoryValue - result;
                            model.memoryClear();
                            model.memoryAdd(updatedMemory);
                            String memoryText = formatResult(updatedMemory);
                            view.setDisplayText(memoryText);
                            currentOperand = memoryText;
                            operator = "";
                            operatorPressed = false;
                            isResultDisplayed = true;
                        } else {
                            view.setDisplayText("Error");
                        }
                        break;
                    case "MR":
                        double memoryValue = model.memoryRecall();
                        String memoryText = formatResult(memoryValue);
                        view.setDisplayText(memoryText);
                        currentOperand = memoryText;
                        isResultDisplayed = true;
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
    }

    public void updateView() {
        view.setDisplayText(formatResult(model.getResult()));
    }

    private String formatResult(double value) {
        if (value == (long) value) {
            return String.format("%d", (long) value);
        } else {
            return String.valueOf(value);
        }
    }
}
