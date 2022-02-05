package com.canvasdraw.validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CanvasCommandValidatorTest {

    private static CanvasCommandValidator canvasCommandValidator;

    @BeforeAll
    public static void setup() {
        canvasCommandValidator = new CanvasCommandValidator();
    }

    @Test
    public void testValidCanvasCommand() {
        assertTrue(canvasCommandValidator.validate("C 20 7"));
        assertTrue(canvasCommandValidator.validate("C 3 40"));
    }

    @Test
    public void testInValidCanvasCommand() {
        assertFalse(canvasCommandValidator.validate("C"));
        assertFalse(canvasCommandValidator.validate("C 40 "));
        assertFalse(canvasCommandValidator.validate("C 40 7 "));
        assertFalse(canvasCommandValidator.validate(" C 40 7"));
        assertFalse(canvasCommandValidator.validate("C 40  7"));
        assertFalse(canvasCommandValidator.validate("C  40 7"));
        assertFalse(canvasCommandValidator.validate("c 20 7"));
    }

    @Test
    public void testValidLineCommand() {
        assertTrue(canvasCommandValidator.validate("L 1 4 6 4"));
        assertTrue(canvasCommandValidator.validate("L 5 4 5 7"));
        assertTrue(canvasCommandValidator.validate("L 1 4 5 7"));
    }

    @Test
    public void testInValidLineCommand() {
        assertFalse(canvasCommandValidator.validate("L"));
        assertFalse(canvasCommandValidator.validate("L 1"));
        assertFalse(canvasCommandValidator.validate("L 1 4"));
        assertFalse(canvasCommandValidator.validate("L 1 4 6"));
        assertFalse(canvasCommandValidator.validate("L 1 4 6 4 "));
        assertFalse(canvasCommandValidator.validate(" L 1 4 6 4"));
        assertFalse(canvasCommandValidator.validate("L  1  4  6  4 "));
        assertFalse(canvasCommandValidator.validate("l 1 4 6 4"));
    }

    @Test
    public void testValidRectangleCommand() {
        assertTrue(canvasCommandValidator.validate("R 14 1 18 4"));
        assertTrue(canvasCommandValidator.validate("R 11 2 15 6"));
    }

    @Test
    public void testInValidRectangleCommand() {
        assertFalse(canvasCommandValidator.validate("R"));
        assertFalse(canvasCommandValidator.validate("R 14"));
        assertFalse(canvasCommandValidator.validate("R 14 1"));
        assertFalse(canvasCommandValidator.validate("R 14 1 18"));
        assertFalse(canvasCommandValidator.validate("R 14 1 18 4 "));
        assertFalse(canvasCommandValidator.validate(" R 14 1 18 4"));
        assertFalse(canvasCommandValidator.validate("R  14  1  18  4 "));
        assertFalse(canvasCommandValidator.validate("r 14 1 18 4 "));
    }

    @Test
    public void testValidPaintCommand() {
        assertTrue(canvasCommandValidator.validate("B 10 3 o"));
        assertTrue(canvasCommandValidator.validate("B 3 10 *"));
    }

    @Test
    public void testInValidPaintCommand() {
        assertFalse(canvasCommandValidator.validate("B"));
        assertFalse(canvasCommandValidator.validate("B 10"));
        assertFalse(canvasCommandValidator.validate("B 10 3"));
        assertFalse(canvasCommandValidator.validate("B 10 3 o "));
        assertFalse(canvasCommandValidator.validate(" B 10 3 o"));
        assertFalse(canvasCommandValidator.validate("B  10  3  o "));
        assertFalse(canvasCommandValidator.validate("b 10 3 o"));
    }

    @Test
    public void testValidQuitCommand() {
        assertTrue(canvasCommandValidator.validate("Q"));
    }

    @Test
    public void testInValidQuitCommand() {
        assertFalse(canvasCommandValidator.validate("Q "));
        assertFalse(canvasCommandValidator.validate("q"));
        assertFalse(canvasCommandValidator.validate(" Q "));
    }
}
