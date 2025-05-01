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
    public void testMemorySubtract() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                // Clear everything
                getPrivateButton("clearButton").doClick();
                getPrivateButton("memoryClearButton").doClick();

                // Perform an operation resulting in 20 (e.g., 10 + 10)
                getNumberButton(1).doClick();
                getNumberButton(0).doClick();
                getPrivateButton("addButton").doClick();
                getNumberButton(1).doClick();
                getNumberButton(0).doClick();
                getPrivateButton("equalsButton").doClick();
                assertEquals("20", view.getDisplayText());

                // Add the result (20) to memory
                getPrivateButton("memoryAddButton").doClick();

                // Perform another operation resulting in 80 (e.g., 40 + 40)
                getNumberButton(4).doClick();
                getNumberButton(0).doClick();
                getPrivateButton("addButton").doClick();
                getNumberButton(4).doClick();
                getNumberButton(0).doClick();
                getPrivateButton("equalsButton").doClick();
                assertEquals("80", view.getDisplayText());

                // Subtract memory (20) from the result (80)
                getPrivateButton("memorySubButton").doClick();

                // Verify the result is 60 (80 - 20)
                assertEquals("60", view.getDisplayText());

            } catch (Exception e) {
                fail("Exception during test: " + e.getMessage());
            }
        });
    }

}