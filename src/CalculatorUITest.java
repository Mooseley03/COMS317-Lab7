import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class CalculatorUITest {
    private CalculatorView view;
    private CalculatorController controller;

    @Before
    public void setup() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            view = new CalculatorView(); // already a JFrame
            CalculatorModel model = new CalculatorModel();
            controller = new CalculatorController(model, view);
            view.setVisible(true); // show the window
        });
    }

    private JButton getNumberButton(int index) throws Exception {
        Field field = view.getClass().getDeclaredField("numberButtons");
        field.setAccessible(true);
        JButton[] buttons = (JButton[]) field.get(view);
        return buttons[index];
    }

    private JButton getPrivateButton(String name) throws Exception {
        Field field = view.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return (JButton) field.get(view);
    }

    @Test
    public void testAddition() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                getNumberButton(2).doClick();
                getPrivateButton("addButton").doClick();
                getNumberButton(3).doClick();
                getPrivateButton("equalsButton").doClick();

                assertEquals("5", view.getDisplayText());
            } catch (Exception e) {
                fail("Exception during test: " + e.getMessage());
            }
        });
    }
    
    @Test
    public void testSubtraction() throws Exception {
    	SwingUtilities.invokeAndWait(() -> {
    		try {
    			getNumberButton(5).doClick();
    			getPrivateButton("subButton").doClick();
    			getNumberButton(2).doClick();
    			getPrivateButton("equalsButton").doClick();
    			assertEquals("3", view.getDisplayText());
    		} catch (Exception e) {
    			fail("Exception during test: " + e.getMessage());
    		}
    	});
    }
    
    @Test
    public void testMultiplication() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                getNumberButton(6).doClick();
                getPrivateButton("mulButton").doClick();
                getNumberButton(7).doClick();
                getPrivateButton("equalsButton").doClick();
                assertEquals("42", view.getDisplayText());
            } catch (Exception e) {
                fail("Exception during test: " + e.getMessage());
            }
        });
    }
    
    @Test
    public void testDivision() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                getNumberButton(8).doClick();
                getPrivateButton("divButton").doClick();
                getNumberButton(2).doClick();
                getPrivateButton("equalsButton").doClick();
                assertEquals("4", view.getDisplayText());
            } catch (Exception e) {
                fail("Exception during test: " + e.getMessage());
            }
        });
    }
    
    @Test
    public void testSquare() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                getNumberButton(7).doClick();
                getPrivateButton("squareButton").doClick();
                assertEquals("49", view.getDisplayText());
            } catch (Exception e) {
                fail("Exception during test: " + e.getMessage());
            }
        });
    }
    
    @Test
    public void testSquareRoot() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                getNumberButton(9).doClick();
                getPrivateButton("sqrtButton").doClick();
                assertEquals("3", view.getDisplayText());
            } catch (Exception e) {
                fail("Exception during test: " + e.getMessage());
            }
        });
    }
    
    @Test
    public void testNegateButton() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                getNumberButton(5).doClick(); // enter 5
                getPrivateButton("negateButton").doClick(); // press ±
                assertEquals("-5", view.getDisplayText());

                // press ± again to return to positive
                getPrivateButton("negateButton").doClick();
                assertEquals("5", view.getDisplayText());
            } catch (Exception e) {
                fail("Exception during test: " + e.getMessage());
            }
        });
    }
    
    @Test
    public void testInvalidSquareRoot() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                getPrivateButton("negateButton").doClick(); // press minus
                getNumberButton(4).doClick(); // type "-4"
                getPrivateButton("sqrtButton").doClick();
                System.out.println(view.getDisplayText());
                assertEquals("Error", view.getDisplayText());
            } catch (Exception e) {
                fail("Exception during test: " + e.getMessage());
            }
        });
    }
    
    @Test
    public void testMemoryRecallAfterAddition() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                getPrivateButton("clearButton").doClick();

                getNumberButton(3).doClick();
                getPrivateButton("addButton").doClick();
                getNumberButton(3).doClick();
                getPrivateButton("equalsButton").doClick();
                assertEquals("6", view.getDisplayText());
                
                getPrivateButton("memoryAddButton").doClick();
                getPrivateButton("clearButton").doClick();
                getPrivateButton("memoryRecallButton").doClick();
                assertEquals("0", view.getDisplayText());
            } catch (Exception e) {
                fail("Exception during test: " + e.getMessage());
            }
        });
    }
    
    @Test
    public void testDivisionByZero() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                getNumberButton(8).doClick();
                getPrivateButton("divButton").doClick();
                getNumberButton(0).doClick();
                getPrivateButton("equalsButton").doClick();
                assertEquals("Error", view.getDisplayText());
            } catch (Exception e) {
                fail("Exception during test: " + e.getMessage());
            }
        });
    }
}