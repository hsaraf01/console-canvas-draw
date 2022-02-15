package com.canvasdraw.service;

import com.canvasdraw.exception.CanvasException;

import static com.canvasdraw.utils.Constants.*;

public class Line {

    private int xAxisStartPoint;
    private int yAxisStartPoint;
    private int xAxisEndPoint;
    private int yAxisEndPoint;
    private final Canvas canvas;

    public Line(final Canvas canvas, final String[] commandValues) {
        this.canvas = canvas;
        this.xAxisStartPoint = Integer.parseInt(commandValues[1]);
        this.yAxisStartPoint = Integer.parseInt(commandValues[2]);
        this.xAxisEndPoint = Integer.parseInt(commandValues[3]);
        this.yAxisEndPoint = Integer.parseInt(commandValues[4]);
    }

    public void draw() throws CanvasException {
        if (canvas != null && canvas.getCanvasBoard() != null) {
            if (isLineInputPointsValid(canvas.getWidth(), canvas.getHeight())) {
                drawLine();
            } else {
                throw new CanvasException(INVALID_LINE_INPUTS_ERROR_MSG);
            }
        } else {
            throw new CanvasException(CANVAS_NOT_AVAILABLE_ERROR_MSG);
        }
    }

    private void drawLine() throws CanvasException {
        char[][] canvasBoard = this.canvas.getCanvasBoard();
        if (xAxisStartPoint == xAxisEndPoint) { // vertical line
            flipInputIfNeededForHorizontalLine();
            for (int yAxisVal = yAxisStartPoint; yAxisVal <= yAxisEndPoint; yAxisVal++) {
                canvasBoard[yAxisVal][xAxisStartPoint] = DRAWING_CHAR;
            }
        } else if (yAxisStartPoint == yAxisEndPoint) { // horizontal line
            flipInputIfNeededForVerticalLine();
            for (int xAxisVal = xAxisStartPoint; xAxisVal <= xAxisEndPoint; xAxisVal++) {
                canvasBoard[yAxisStartPoint][xAxisVal] = DRAWING_CHAR;
            }
        } else {
            throw new CanvasException(ONLY_HORIZONTAL_VERTICAL_ERROR_MSG);
        }
    }

    private boolean isLineInputPointsValid(final int canvasWidth, final int canvasHeight) {
        if (xAxisStartPoint < 1 || yAxisStartPoint < 1 || xAxisStartPoint > canvasWidth || yAxisStartPoint > canvasHeight) {
            return false;
        } else if (xAxisEndPoint < 1 || yAxisEndPoint < 1 || xAxisEndPoint > canvasWidth || yAxisEndPoint > canvasHeight) {
            return false;
        }
        return true;
    }

    private void flipInputIfNeededForVerticalLine() {
        if (xAxisStartPoint > xAxisEndPoint) {
            flip();
        }
    }

    private void flipInputIfNeededForHorizontalLine() {
        if (yAxisStartPoint > yAxisEndPoint) {
            flip();
        }
    }

    private void flip() {
        int xAxistmp = xAxisStartPoint;
        xAxisStartPoint = xAxisEndPoint;
        xAxisEndPoint = xAxistmp;
        int yAxistmp = yAxisStartPoint;
        yAxisStartPoint = yAxisEndPoint;
        yAxisEndPoint = yAxistmp;
    }

}
