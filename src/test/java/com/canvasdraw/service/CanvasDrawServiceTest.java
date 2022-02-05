package com.canvasdraw.service;

import com.canvasdraw.exception.CanvasException;
import com.canvasdraw.validator.CanvasCommandValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
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
        for (int h = 1; h < canvasBoard.length - 1; h++) {
            assertThat(canvasBoard[h][0], is(VERTICAL_BORDER));
            assertThat(canvasBoard[h][width + 1], is(VERTICAL_BORDER));
        }
        for (int w = 0; w < canvasBoard[0].length; w++) {
            assertThat(canvasBoard[0][w], is(HORIZONTAL_BORDER));
            assertThat(canvasBoard[height + 1][w], is(HORIZONTAL_BORDER));
        }

        for (int h = 1; h < canvasBoard.length - 1; h++) {
            for (int w = 1; w < canvasBoard[0].length - 1; w++) {
                assertThat(canvasBoard[h][w], is(EMPTY));
            }
        }
    }

    @Test
    public void testVerticalLineCreated() {
        final String canvasCommand = "C 20 7";
        final String[] lineCommands = {"L 1 4 6 4", "L 6 4 1 4"};
        Arrays.stream(lineCommands).forEach(lineCommand -> {
            reset(canvasCommandValidator);
            reset(canvas);
            String[] values = lineCommand.split(" ");
            int x1 = Integer.parseInt(values[1]), x2 = Integer.parseInt(values[3]), y = Integer.parseInt(values[2]);
            List<Integer> horizontalPts = getRange(x1, x2);
            try {
                canvasDrawService.processCommand(canvasCommand);
                canvasDrawService.processCommand(lineCommand);
                verify(canvasCommandValidator, times(1)).validate(lineCommand);
                verify(canvas, times(2)).printCanvas();
                char[][] canvasBoard = canvas.getCanvasBoard();
                for (int h = 1; h < canvasBoard.length - 1; h++) {
                    for (int w = 1; w < canvasBoard[0].length - 1; w++) {
                        if (h == y && horizontalPts.contains(w)) {
                            assertThat(canvasBoard[h][w], is(DRAWING_CHAR));
                        } else {
                            assertThat(canvasBoard[h][w], is(EMPTY));
                        }

                    }
                }
            } catch (CanvasException e) {
                fail();
            }
        });
    }

    @Test
    public void testHorizontalLineCreated() {
        final String canvasCommand = "C 20 7";
        final String[] lineCommands = {"L 5 3 5 7", "L 5 7 5 3"};
        Arrays.stream(lineCommands).forEach(lineCommand -> {
            reset(canvasCommandValidator);
            reset(canvas);
            String[] values = lineCommand.split(" ");
            int x = Integer.parseInt(values[1]), y1 = Integer.parseInt(values[2]), y2 = Integer.parseInt(values[4]);
            List<Integer> verticalPts = getRange(y1, y2);
            try {
                canvasDrawService.processCommand(canvasCommand);
                canvasDrawService.processCommand(lineCommand);
                verify(canvasCommandValidator, times(1)).validate(lineCommand);
                verify(canvas, times(2)).printCanvas();
                char[][] canvasBoard = canvas.getCanvasBoard();
                for (int h = 1; h < canvasBoard.length - 1; h++) {
                    for (int w = 1; w < canvasBoard[0].length - 1; w++) {
                        if (w == x && verticalPts.contains(h)) {
                            assertThat(canvasBoard[h][w], is(DRAWING_CHAR));
                        } else {
                            assertThat(canvasBoard[h][w], is(EMPTY));
                        }
                    }
                }
            } catch (CanvasException e) {
                fail();
            }
        });
    }

    @Test
    public void testRectangleCreated() {
        final String canvasCommand = "C 20 7";
        final String[] rectangleCommands = {"R 14 1 18 4", "R 18 4 14 1", "R 10 7 15 3", "R 19 1 17 3", "R 4 2 8 2"};
        Arrays.stream(rectangleCommands).forEach(rectangleCommand -> {
            reset(canvasCommandValidator);
            reset(canvas);
            String[] values = rectangleCommand.split(" ");
            int x1 = Integer.parseInt(values[1]), x2 = Integer.parseInt(values[3]), y1 = Integer.parseInt(values[2]), y2 = Integer.parseInt(values[4]);
            List<Integer> horizontalPts = getRange(x1, x2);
            List<Integer> verticalPts = getRange(y1, y2);
            try {
                canvasDrawService.processCommand(canvasCommand);
                canvasDrawService.processCommand(rectangleCommand);
                verify(canvasCommandValidator, times(1)).validate(rectangleCommand);
                verify(canvas, times(2)).printCanvas();

                char[][] canvasBoard = canvas.getCanvasBoard();
                for (int h = 1; h < canvasBoard.length - 1; h++) {
                    for (int w = 1; w < canvasBoard[0].length - 1; w++) {
                        if ((verticalPts.contains(h) && (w == x1 || w == x2)) || (horizontalPts.contains(w) && (h == y1 || h == y2))) {
                            assertThat(canvasBoard[h][w], is(DRAWING_CHAR));
                        } else {
                            assertThat(canvasBoard[h][w], is(EMPTY));
                        }

                    }
                }
            } catch (CanvasException e) {
                fail();
            }

        });

    }

    @Test
    public void testCanvasPaint() throws CanvasException {
        int x1 = 4, x2 = 9, y1 = 1, y2 = 4, p1 = 10, p2 = 3;
        char c = '*';
        final String canvasCommand = "C 20 7";
        final String rectangleCommand = "R " + x1 + " " + y1 + " " + x2 + " " + y2;
        final String paintCommand = "B " + p1 + " " + p2 + " " + c;
        List<Integer> horizontalPts = getRange(x1, x2);
        List<Integer> verticalPts = getRange(y1, y2);
        canvasDrawService.processCommand(canvasCommand);
        canvasDrawService.processCommand(rectangleCommand);
        canvasDrawService.processCommand(paintCommand);
        verify(canvasCommandValidator, times(1)).validate(paintCommand);
        verify(canvas, times(3)).printCanvas();

        char[][] canvasBoard = canvas.getCanvasBoard();
        for (int h = 1; h < canvasBoard.length - 1; h++) {
            for (int w = 1; w < canvasBoard[0].length - 1; w++) {
                if (verticalPts.contains(h) && (horizontalPts.contains(w))) {
                    assertThat(canvasBoard[h][w], anyOf(is(DRAWING_CHAR), is(EMPTY)));
                } else {
                    assertThat(canvasBoard[h][w], is(c));
                }

            }
        }
    }

    @Test
    public void testCanvasPaintInRectangleOnly() throws CanvasException {
        int x1 = 14, x2 = 19, y1 = 1, y2 = 5, p1 = 16, p2 = 2;
        char c = '*';
        final String canvasCommand = "C 20 7";
        final String rectangleCommand = "R " + x1 + " " + y1 + " " + x2 + " " + y2;
        final String paintCommand = "B " + p1 + " " + p2 + " " + c;
        List<Integer> paintHorizontalPts = getRange(x1 + 1, x2 - 1);
        List<Integer> paintVerticalPts = getRange(y1 + 1, y2 - 1);
        canvasDrawService.processCommand(canvasCommand);
        canvasDrawService.processCommand(rectangleCommand);
        canvasDrawService.processCommand(paintCommand);
        verify(canvasCommandValidator, times(1)).validate(paintCommand);
        verify(canvas, times(3)).printCanvas();

        char[][] canvasBoard = canvas.getCanvasBoard();
        for (int h = 1; h < canvasBoard.length - 1; h++) {
            for (int w = 1; w < canvasBoard[0].length - 1; w++) {
                if (paintVerticalPts.contains(h) && (paintHorizontalPts.contains(w))) {
                    assertThat(canvasBoard[h][w], is(c));
                } else {
                    assertThat(canvasBoard[h][w], anyOf(is(DRAWING_CHAR), is(EMPTY)));
                }

            }
        }
    }

    private List<Integer> getRange(int start, int end) {
        return start < end ? IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList()) :
                IntStream.rangeClosed(end, start).boxed().collect(Collectors.toList());

    }
}
