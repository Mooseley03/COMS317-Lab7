import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CalculatorModelTest {
    private CalculatorModel model;
    private CalculatorView view;
    private CalculatorController controller;

    @Before
    public void setUp() {
        model = new CalculatorModel();
        view = new CalculatorView();
        controller = new CalculatorController(model, view);
    }

    @Test
    public void testAddition() {
        // Perform the addition operation
        model.add(2, 3);
        // Check that the result is correct (using double comparison)
        assertEquals(5.0, model.getResult(), 0.0001); // Double comparison
    }

    @Test
    public void testSubtraction() {
        model.subtract(3, 2);
        assertEquals(1.0, model.getResult(), 0.0001); // Double comparison
    }

    @Test
    public void testMultiplication() {
        // Perform the multiplication operation
        model.multiply(6, 7);
        // Check that the result is correct (6 * 7 = 42)
        assertEquals(42.0, model.getResult(), 0.0001); // Double comparison
    }
    
    @Test
    public void testSubtractionBelowZero() {
        // Perform the subtraction operation that results in a negative value
        model.subtract(3, 5);
        // Check that the result is correct (3 - 5 = -2)
        assertEquals(-2.0, model.getResult(), 0.0001); // Double comparison
    }
    
    @Test
    public void testDivideByDecimal() {
        // Perform the division operation
        model.divide(5, 2);
        // Check that the result is correct (5 / 2 = 2.5)
        assertEquals(2.5, model.getResult(), 0.0001); // Double comparison
    }
    
    @Test
    public void testMultiplyByDecimal() {
    	model.multiply(2, 1.5);
    	assertEquals(3, model.getResult(), 0.0001);
    }
    
    @Test
    public void testMemoryAdd() {
        model.memoryAdd(5);
        assertEquals(5.0, model.memoryRecall(), 0.0001); // Compare memory value
    }
    

    @Test
    public void testDivideByZero() {
        model.divide(1, 0);
        controller.updateView();  // Ensure the view gets updated after division by zero
        System.out.println(view.getDisplayText());  // Should print "Error"
        assertEquals("Infinity", view.getDisplayText());
    }
}
