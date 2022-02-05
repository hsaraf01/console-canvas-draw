package com.canvasdraw.service;

import com.canvasdraw.exception.CanvasException;
import com.canvasdraw.validator.CanvasCommandValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.canvasdraw.utils.Constants.*;
import static com.canvasdraw.utils.Constants.INVALID_PAINT_INPUTS_ERROR_MSG;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CanvasDrawServiceExceptionsTest {
    private CanvasDrawService canvasDrawService;
    private Canvas canvas;
    private CanvasCommandValidator canvasCommandValidator;

    @BeforeEach
    public void setup() {
        canvas = new Canvas();
        canvasCommandValidator = new CanvasCommandValidator();
        canvasDrawService = new CanvasDrawService(canvas, canvasCommandValidator);
    }

    @Test
    public void exceptionThrownWhenCanvasNotCreatedBeforeLineCommand() {
        Exception exception = assertThrows(CanvasException.class, () -> canvasDrawService.processCommand("L 1 4 6 4"));
        assertThat(exception.getMessage(), is(CANVAS_NOT_AVAILABLE_ERROR_MSG));
    }

    @Test
    public void exceptionThrownWhenCanvasNotCreatedBeforeRectangleCommand() {
        Exception exception = assertThrows(CanvasException.class, () -> canvasDrawService.processCommand("R 14 1 18 4"));
        assertThat(exception.getMessage(), is(CANVAS_NOT_AVAILABLE_ERROR_MSG));
    }

    @Test
    public void exceptionThrownWhenCanvasNotCreatedBeforePaintCommand() {
        Exception exception = assertThrows(CanvasException.class, () -> canvasDrawService.processCommand("B 10 3 k"));
        assertThat(exception.getMessage(), is(CANVAS_NOT_AVAILABLE_ERROR_MSG));
    }

    @Test
    public void exceptionThrownWhenInvalidLineInput() throws CanvasException {
        canvasDrawService.processCommand("C 20 7");
        Exception exception1 = assertThrows(CanvasException.class, () -> canvasDrawService.processCommand("L 0 0 6 4"));
        assertThat(exception1.getMessage(), is(INVALID_LINE_INPUTS_ERROR_MSG));
        Exception exception2 = assertThrows(CanvasException.class, () -> canvasDrawService.processCommand("L 1 8 6 8"));
        assertThat(exception2.getMessage(), is(INVALID_LINE_INPUTS_ERROR_MSG));
        Exception exception3 = assertThrows(CanvasException.class, () -> canvasDrawService.processCommand("L 21 4 21 3"));
        assertThat(exception3.getMessage(), is(INVALID_LINE_INPUTS_ERROR_MSG));
        Exception exception4 = assertThrows(CanvasException.class, () -> canvasDrawService.processCommand("L 15 8 15 3"));
        assertThat(exception4.getMessage(), is(INVALID_LINE_INPUTS_ERROR_MSG));
        Exception exception5 = assertThrows(CanvasException.class, () -> canvasDrawService.processCommand("L 1 1 20 4"));
        assertThat(exception5.getMessage(), is(ONLY_HORIZONTAL_VERTICAL_ERROR_MSG));
        Exception exception6 = assertThrows(CanvasException.class, () -> canvasDrawService.processCommand("L 6 4 0 0"));
        assertThat(exception6.getMessage(), is(INVALID_LINE_INPUTS_ERROR_MSG));
    }

    @Test
    public void exceptionThrownWhenInvalidRectangleInput() throws CanvasException {
        canvasDrawService.processCommand("C 20 7");
        Exception exception1 = assertThrows(CanvasException.class, () -> canvasDrawService.processCommand("R 0 0 18 4"));
        assertThat(exception1.getMessage(), is(INVALID_RECTANGLE_INPUTS_ERROR_MSG));
        Exception exception2 = assertThrows(CanvasException.class, () -> canvasDrawService.processCommand("R 1 1 21 4"));
        assertThat(exception2.getMessage(), is(INVALID_RECTANGLE_INPUTS_ERROR_MSG));
        Exception exception3 = assertThrows(CanvasException.class, () -> canvasDrawService.processCommand("R 1 8 20 4"));
        assertThat(exception3.getMessage(), is(INVALID_RECTANGLE_INPUTS_ERROR_MSG));
    }

    @Test
    public void exceptionThrownWhenInvalidPaintInput() throws CanvasException {
        canvasDrawService.processCommand("C 20 7");
        Exception exception1 = assertThrows(CanvasException.class, () -> canvasDrawService.processCommand("B 0 3 k"));
        assertThat(exception1.getMessage(), is(INVALID_PAINT_INPUTS_ERROR_MSG));
        Exception exception2 = assertThrows(CanvasException.class, () -> canvasDrawService.processCommand("B 3 0 k"));
        assertThat(exception2.getMessage(), is(INVALID_PAINT_INPUTS_ERROR_MSG));
        Exception exception3 = assertThrows(CanvasException.class, () -> canvasDrawService.processCommand("B 21 5 k"));
        assertThat(exception3.getMessage(), is(INVALID_PAINT_INPUTS_ERROR_MSG));
        Exception exception4 = assertThrows(CanvasException.class, () -> canvasDrawService.processCommand("B 1 8 k"));
        assertThat(exception4.getMessage(), is(INVALID_PAINT_INPUTS_ERROR_MSG));
    }

    @Test
    public void exceptionThrownWhenInvalidCanvasCommand() {
        Exception exception = assertThrows(CanvasException.class, () -> canvasDrawService.processCommand("C "));
        assertThat(exception.getMessage(), containsString(USER_INPUT_ERROR_MSG));
    }

}
