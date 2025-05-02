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

    @Test
    public void noOperationSymbolDisplayedWhilePerformingAdditionOperation() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                getNumberButton(5).doClick();
                getPrivateButton("addButton").doClick();
                assertEquals("5", view.getDisplayText());
                getNumberButton(3).doClick();
                assertEquals("3", view.getDisplayText());
                getPrivateButton("equalsButton").doClick();
                assertEquals("8", view.getDisplayText());
            } catch (Exception e) {
                fail("Exception during test: " + e.getMessage());
            }
        });
    }

    @Test
    public void noOperationSymbolDisplayedWhilePerformingMultiplicationOperation() throws Exception{
        SwingUtilities.invokeAndWait(() -> {
            try {
                getNumberButton(5).doClick();
                getPrivateButton("mulButton").doClick();
                assertEquals("5", view.getDisplayText());
                getNumberButton(3).doClick();
                assertEquals("3", view.getDisplayText());
                getPrivateButton("equalsButton").doClick();
                assertEquals("15", view.getDisplayText());
            } catch (Exception e) {
                fail("Exception during test: " + e.getMessage());
            }
        });
    }


    @Test
    public void noOperationSymbolDisplayedWhilePerformingSubtractionOperation() throws Exception{
        SwingUtilities.invokeAndWait(() -> {
            try {
                getNumberButton(5).doClick();
                getPrivateButton("subButton").doClick();
                assertEquals("5", view.getDisplayText());
                getNumberButton(3).doClick();
                assertEquals("3", view.getDisplayText());
                getPrivateButton("equalsButton").doClick();
                assertEquals("2", view.getDisplayText());
            } catch (Exception e) {
                fail("Exception during test: " + e.getMessage());
            }
        });
    }

    @Test
    public void noOperationSymbolDisplayedWhilePerformingDivisionOperation() throws Exception{
        SwingUtilities.invokeAndWait(() -> {
            try {
                getNumberButton(5).doClick();
                getPrivateButton("divButton").doClick();
                assertEquals("5", view.getDisplayText());
                getNumberButton(3).doClick();
                assertEquals("3", view.getDisplayText());
                getPrivateButton("equalsButton").doClick();
                assertEquals("1.6666666666666667", view.getDisplayText());
            } catch (Exception e) {
                fail("Exception during test: " + e.getMessage());
            }
        });
    }

    //tests for grayed out buttons upon clicking


    @Test
    public void testAdditionButtonGrayedOutWhenOperating() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                getNumberButton(5).doClick();
                getPrivateButton("addButton").doClick();
                //tests that button changed color to gray
                assertEquals("java.awt.Color[r=192,g=192,b=192]", getPrivateButton("addButton").getBackground().toString());
                getNumberButton(3).doClick();
                //tests that button color remained when pressing a number to be added
                assertEquals("java.awt.Color[r=192,g=192,b=192]", getPrivateButton("addButton").getBackground().toString());
                getPrivateButton("equalsButton").doClick();
                //tests button returned to normal color
                assertEquals("javax.swing.plaf.ColorUIResource[r=238,g=238,b=238]", getPrivateButton("addButton").getBackground().toString());
                assertEquals("8", view.getDisplayText());
            } catch (Exception e) {
                fail("Exception during test: " + e.getMessage());
            }
        });
    }

    @Test
    public void testSubtractionButtonGrayedOutWhenOperating() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                getNumberButton(5).doClick();
                getPrivateButton("subButton").doClick();
                //tests that button changed color to gray
                assertEquals("java.awt.Color[r=192,g=192,b=192]", getPrivateButton("subButton").getBackground().toString());
                getNumberButton(3).doClick();
                //tests that button color remained when pressing a number to be added
                assertEquals("java.awt.Color[r=192,g=192,b=192]", getPrivateButton("subButton").getBackground().toString());
                getPrivateButton("equalsButton").doClick();
                //tests button returned to normal color
                assertEquals("javax.swing.plaf.ColorUIResource[r=238,g=238,b=238]", getPrivateButton("subButton").getBackground().toString());
                assertEquals("2", view.getDisplayText());
            } catch (Exception e) {
                fail("Exception during test: " + e.getMessage());
            }
        });
    }

    @Test
    public void testMultiplicationButtonGrayedOutWhenOperating() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                getNumberButton(5).doClick();
                getPrivateButton("mulButton").doClick();
                //tests that button changed color to gray
                assertEquals("java.awt.Color[r=192,g=192,b=192]", getPrivateButton("mulButton").getBackground().toString());
                getNumberButton(3).doClick();
                //tests that button color remained when pressing a number to be added
                assertEquals("java.awt.Color[r=192,g=192,b=192]", getPrivateButton("mulButton").getBackground().toString());
                getPrivateButton("equalsButton").doClick();
                //tests button returned to normal color
                assertEquals("javax.swing.plaf.ColorUIResource[r=238,g=238,b=238]", getPrivateButton("mulButton").getBackground().toString());
                assertEquals("15", view.getDisplayText());
            } catch (Exception e) {
                fail("Exception during test: " + e.getMessage());
            }
        });
    }

    @Test
    public void testDivisionButtonGrayedOutWhenOperating() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                getNumberButton(5).doClick();
                getPrivateButton("divButton").doClick();
                //tests that button changed color to gray
                assertEquals("java.awt.Color[r=192,g=192,b=192]", getPrivateButton("divButton").getBackground().toString());
                getNumberButton(3).doClick();
                //tests that button color remained when pressing a number to be added
                assertEquals("java.awt.Color[r=192,g=192,b=192]", getPrivateButton("divButton").getBackground().toString());
                getPrivateButton("equalsButton").doClick();
                //tests button returned to normal color
                assertEquals("javax.swing.plaf.ColorUIResource[r=238,g=238,b=238]", getPrivateButton("divButton").getBackground().toString());
                assertEquals("1.6666666666666667", view.getDisplayText());
            } catch (Exception e) {
                fail("Exception during test: " + e.getMessage());
            }
        });
    }



}