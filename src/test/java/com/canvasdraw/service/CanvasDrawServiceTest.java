package com.canvasdraw.service;

import com.canvasdraw.exception.CanvasException;
import com.canvasdraw.validator.CanvasCommandValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.canvasdraw.utils.Constants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class CanvasDrawServiceTest {

    private CanvasDrawService canvasDrawService;
    private Canvas canvas;
    private CanvasCommandValidator canvasCommandValidator;

    @BeforeEach
    public void setup() {
        canvas = spy(new Canvas());
        canvasCommandValidator = spy(new CanvasCommandValidator());
        canvasDrawService = new CanvasDrawService(canvas, canvasCommandValidator);
    }

    @Test
    public void testCanvasCreated() throws CanvasException {
        int width = 20, height = 7;
        final String command = "C " + width + " " + height;
        canvasDrawService.processCommand(command);
        verify(canvasCommandValidator, times(1)).validate(command);
        verify(canvas, times(1)).initialize(any());
        verify(canvas, times(1)).printCanvas();
        char[][] canvasBoard = canvas.getCanvasBoard();
        assertNotNull(canvasBoard);
        //check border added
        assertThat(canvasBoard.length, is(height + 2));
        assertThat(canvasBoard[0].length, is(width + 2));
        assertThat(canvas.getHeight(), is(height));
        assertThat(canvas.getWidth(), is(width));
        for (int y = 1; y < canvasBoard.length - 1; y++) {
            assertThat(canvasBoard[y][0], is(VERTICAL_BORDER));
            assertThat(canvasBoard[y][width + 1], is(VERTICAL_BORDER));
        }
        for (int x = 0; x < canvasBoard[0].length; x++) {
            assertThat(canvasBoard[0][x], is(HORIZONTAL_BORDER));
            assertThat(canvasBoard[height + 1][x], is(HORIZONTAL_BORDER));
        }

        for (int y = 1; y < canvasBoard.length - 1; y++) {
            for (int x = 1; x < canvasBoard[0].length - 1; x++) {
                assertThat(canvasBoard[y][x], is(EMPTY));
            }
        }
    }

    @Test
    public void testVerticalLineCreated() {
        final String[] lineCommands = {"L 1 4 6 4", "L 6 4 1 4"};
        Arrays.stream(lineCommands).forEach(lineCommand -> processAndVerifyCommand(getCanvasCommand(), lineCommand, Axis.XAXIS));
    }

    @Test
    public void testHorizontalLineCreated() {
        final String[] lineCommands = {"L 5 3 5 7", "L 5 7 5 3"};
        Arrays.stream(lineCommands).forEach(lineCommand -> processAndVerifyCommand(getCanvasCommand(), lineCommand, Axis.YAXIS));
    }

    @Test
    public void testRectangleCreated() {
        final String[] rectangleCommands = {"R 14 1 18 4", "R 18 4 14 1", "R 10 7 15 3", "R 19 1 17 3", "R 4 2 8 2"};
        Arrays.stream(rectangleCommands).forEach(rectangleCommand -> processAndVerifyCommand(getCanvasCommand(), rectangleCommand, null));
    }

    @Test
    public void testCanvasPaint() throws CanvasException {
        int[] rectanglePts = {4, 1, 9, 4};
        int[] paintPts = {10, 3};
        processAndVerifyPaintCommand(getCanvasCommand(), rectanglePts, paintPts, false);
    }

    @Test
    public void testCanvasPaintInRectangleOnly() throws CanvasException {
        int[] rectanglePts = {14, 1, 19, 5};
        int[] paintPts = {16, 2};
        processAndVerifyPaintCommand(getCanvasCommand(), rectanglePts, paintPts, true);
    }

    private boolean verifyCoordinate(int x1, int x2, int y1, int y2, List<Integer> horizontalPts, List<Integer> verticalPts, int y, int x, String commandStartWith, Axis axis) {
        boolean result = false;
        switch (commandStartWith) {
            case RECTANGLE_COMMAND_STARTS_WITH:
                result = (verticalPts.contains(y) && (x == x1 || x == x2)) || (horizontalPts.contains(x) && (y == y1 || y == y2));
                break;
            case LINE_COMMAND_STARTS_WITH:
                result = verifyXOrYAxisForLineCommand(x, y, verticalPts, axis) && verifyPtsXOrYAxisForLineCommand(x, y, horizontalPts, axis);
                break;

        }
        return result;
    }

    private List<Integer> getHorizontalAxisPoints(String commandStartWith, int x1, int x2, int y1, int y2, Axis axis) {
        switch (commandStartWith) {
            case RECTANGLE_COMMAND_STARTS_WITH:
                return getRange(x1, x2);
            case LINE_COMMAND_STARTS_WITH:
                return axis == Axis.XAXIS ? getRange(x1, x2) : getRange(y1, y2);
        }
        return Collections.emptyList();
    }

    private List<Integer> getVerticalAxisPoints(String commandStartWith, int x1, int y1, int y2, Axis axis) {
        switch (commandStartWith) {
            case RECTANGLE_COMMAND_STARTS_WITH:
                return getRange(y1, y2);
            case LINE_COMMAND_STARTS_WITH:
                return axis == Axis.XAXIS ? getRange(y1, y1) : getRange(x1, x1);
        }
        return Collections.emptyList();
    }

    private boolean verifyPtsXOrYAxisForLineCommand(int xAxis, int yAxis, List<Integer> axisPts, Axis axis) {
        return axis == Axis.XAXIS ? axisPts.contains(xAxis) : axisPts.contains(yAxis);
    }

    private boolean verifyXOrYAxisForLineCommand(int xAxis, int yAxis, List<Integer> axisPts, Axis axis) {
        return axis == Axis.XAXIS ? axisPts.contains(yAxis) : axisPts.contains(xAxis);
    }

    private List<Integer> getRange(int start, int end) {
        return start < end ? IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList()) :
                IntStream.rangeClosed(end, start).boxed().collect(Collectors.toList());
    }

    private String getCanvasCommand() {
        return "C 20 7";
    }

    private void processAndVerifyCommand(String canvasCommand, String command, Axis axis) {
        try {
            reset(canvasCommandValidator);
            reset(canvas);
            String[] values = command.split(" ");
            int x1 = Integer.parseInt(values[1]), y1 = Integer.parseInt(values[2]), x2 = Integer.parseInt(values[3]), y2 = Integer.parseInt(values[4]);
            List<Integer> horizontalAxisPoints = getHorizontalAxisPoints(values[0], x1, x2, y1, y2, axis);
            List<Integer> verticalAxisPoints = getVerticalAxisPoints(values[0], x1, y1, y2, axis);
            canvasDrawService.processCommand(canvasCommand);
            canvasDrawService.processCommand(command);
            verify(canvasCommandValidator, times(1)).validate(command);
            verify(canvas, times(2)).printCanvas();
            char[][] canvasBoard = canvas.getCanvasBoard();
            for (int y = 1; y < canvasBoard.length - 1; y++) {
                for (int x = 1; x < canvasBoard[0].length - 1; x++) {
                    if (verifyCoordinate(x1, x2, y1, y2, horizontalAxisPoints, verticalAxisPoints, y, x, values[0], axis)) {
                        assertThat(canvasBoard[y][x], is(DRAWING_CHAR));
                    } else {
                        assertThat(canvasBoard[y][x], is(EMPTY));
                    }
                }
            }
        } catch (CanvasException e) {
            fail();
        }
    }

    private void processAndVerifyPaintCommand(String canvasCommand, int[] rectanglePts, int[] paintPts, boolean paintOnlyInRectangle) throws CanvasException {
        char c = '*';
        int x1 = rectanglePts[0], x2 = rectanglePts[2], y1 = rectanglePts[1], y2 = rectanglePts[3];
        final String rectangleCommand = "R " + x1 + " " + y1 + " " + x2 + " " + y2;
        final String paintCommand = "B " + paintPts[0] + " " + paintPts[1] + " " + c;
        List<Integer> horizontalPts = getRange(paintOnlyInRectangle ? x1 + 1 : x1, paintOnlyInRectangle ? x2 - 1 : x2);
        List<Integer> verticalPts = getRange(paintOnlyInRectangle ? y1 + 1 : y1, paintOnlyInRectangle ? y2 - 1 : y2);
        canvasDrawService.processCommand(canvasCommand);
        canvasDrawService.processCommand(rectangleCommand);
        canvasDrawService.processCommand(paintCommand);
        verify(canvasCommandValidator, times(1)).validate(paintCommand);
        verify(canvas, times(3)).printCanvas();
        char[][] canvasBoard = canvas.getCanvasBoard();
        for (int h = 1; h < canvasBoard.length - 1; h++) {
            for (int w = 1; w < canvasBoard[0].length - 1; w++) {
                boolean contains = verticalPts.contains(h) && (horizontalPts.contains(w));
                if (paintOnlyInRectangle != contains) {
                    assertThat(canvasBoard[h][w], anyOf(is(DRAWING_CHAR), is(EMPTY)));
                } else {
                    assertThat(canvasBoard[h][w], is(c));
                }

            }
        }
    }

    private enum Axis {
        XAXIS,
        YAXIS
    }
}
