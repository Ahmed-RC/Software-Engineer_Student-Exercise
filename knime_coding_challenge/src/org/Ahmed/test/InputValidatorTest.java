package org.Ahmed.test;

import org.Ahmed.InputValidator;
import org.junit.Test;
import java.util.Arrays;

public class InputValidatorTest {

    @Test
    public void testValidateType_valid() {
        InputValidator.validateType("int");
        InputValidator.validateType("string");
        InputValidator.validateType("double");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateType_invalid() {
        InputValidator.validateType("float");
    }

    @Test
    public void testValidateOperations_valid() {
        InputValidator.validateOperations(Arrays.asList("capitalize", "neg"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateOperations_invalid() {
        InputValidator.validateOperations(Arrays.asList("explode"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateInputFile_invalid() {
        InputValidator.validateInputFile("non_existing.txt");
    }
}
