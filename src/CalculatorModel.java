public class CalculatorModel {
    private double result = 0;
    private double memory = 0;

    public double add(double a, double b) {
        result = a + b;
        return result;
    }

    public double subtract(double a, double b) {
        result = a - b;
        return result;
    }

    public double multiply(double a, double b) {
        result = a * b;
        return result;
    }

    public double divide(double a, double b) {
        result = a / b;
        return result;
    }

    public double getResult() {
        return result;
    }

    public void memoryAdd(double value) {
        memory += value;
    }

    public void memorySubtract(double value) {
        memory -= value;
    }

    public double memoryRecall() {
        return memory;
    }

    public void memoryClear() {
        memory = 0;
    }
    
    public void setResult(double value) {
    	this.result = value;
    }
}
