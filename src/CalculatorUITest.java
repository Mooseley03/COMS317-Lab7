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
    public void testMemoryRecall() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                getNumberButton(5).doClick();
                getPrivateButton("memoryAddButton").doClick();
                getPrivateButton("memoryRecallButton").doClick();
//                System.out.println("Worked");
                assertEquals("5", view.getDisplayText());
            } catch (Exception e) {
//            	System.out.println(e.getMessage());
                fail("Exception during test: " + e.getMessage());
            }
        });
    }
}