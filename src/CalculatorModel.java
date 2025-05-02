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
    
    public double square(double a) {
        result = a * a;
        return result;
    }

    public double squareRoot(double a) {
        if (a < 0) {
            throw new ArithmeticException("Cannot take square root of negative number");
        }
        result = Math.sqrt(a);
        return result;
    }
    
    public double negate(double a) {
        result = -a;
        return result;
    }

    public double getResult() {
        return result;
    }

    public void memoryAdd(double value) {
        memory += value;
    }

    public void memorySubtract(double value) {
        double originalMemory = memory;
        memory = originalMemory - value;
    }

    public double memoryRecall() {
        return memory;
    }

    public void memoryClear() {
        memory = 0.0;
    }
    
    public void setResult(double value) {
    	this.result = value;
    }
}
