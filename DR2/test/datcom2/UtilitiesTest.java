/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datcom2;

import javax.swing.JTextField;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author -B-
 */
public class UtilitiesTest {

    public UtilitiesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of processTextField method, of class Utilities.
     */
    @Test
    public void testProcessTextField() {
        System.out.println("processTextField");
        JTextField target = null;
        Utilities instance = new Utilities();
        String expResult = "";
        String result = instance.processTextField(target);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validateInput method, of class Utilities.
     */
    @Test
    public void testValidateInput() {
        System.out.println("validateInput");
        String input = "";
        Utilities instance = new Utilities();
        String expResult = "";
        String result = instance.validateInput(input);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeComments method, of class Utilities.
     */
    @Test
    public void testRemoveComments() {
        System.out.println("removeComments");
        String input = "";
        Utilities instance = new Utilities();
        String expResult = "";
        String result = instance.removeComments(input);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}