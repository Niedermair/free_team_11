package at.sw2017.calculator;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

public class CalculatorUnitTest {
    @Test
    public void testDoAddition(){
        int result = Calculations.doAddition(2,3);
        assertEquals(5, result);
    }

    @Test
    public void testDoSubtraction(){
        int result = Calculations.doSubtraction(7,2);
        assertEquals(5, result);
    }

    @Test
    public void testDoMultiplication(){
        int result = Calculations.doMultiplication(2,3);
        assertEquals(6, result);
    }

    @Test
    public void testDoDivision(){
        int result = Calculations.doDivision(8,4);
        assertEquals(2, result);
    }

    @Test
    public void testDoDivision1(){
        int result = Calculations.doDivision(8,0);
        assertEquals(0, result);
    }

    @Test
    public void testDoDivision2(){
        int result = Calculations.doDivision(11,4);
        assertEquals(2, result);
    }

    @Test
    public void testCalculationsConstructorIsPrivate() throws Exception {
        Constructor constructor = Calculations.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testEnumStatesNotNull(){
        assertNotNull(Calculator.State.valueOf("ADD"));
        assertNotNull(Calculator.State.valueOf("SUB"));
        assertNotNull(Calculator.State.valueOf("MUL"));
        assertNotNull(Calculator.State.valueOf("DIV"));
        assertNotNull(Calculator.State.valueOf("INIT"));
        assertNotNull(Calculator.State.valueOf("NUM"));
    }
}