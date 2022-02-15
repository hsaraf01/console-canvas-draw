package com.canvasdraw.service;

import com.canvasdraw.exception.CanvasException;

import static com.canvasdraw.utils.Constants.*;

public class Rectangle {

    private final int xAxisStartPoint;
    private final int yAxisStartPoint;
    private final int xAxisEndPoint;
    private final int yAxisEndPoint;
    private final Canvas canvas;

    public Rectangle(final Canvas canvas, final String[] commandValues) {
        this.canvas = canvas;
        this.xAxisStartPoint = Integer.parseInt(commandValues[1]);
        this.yAxisStartPoint = Integer.parseInt(commandValues[2]);
        this.xAxisEndPoint = Integer.parseInt(commandValues[3]);
        this.yAxisEndPoint = Integer.parseInt(commandValues[4]);
    }

    public void draw() throws CanvasException {
        if (canvas != null && canvas.getCanvasBoard() != null) {
            char[][] canvasBoard = this.canvas.getCanvasBoard();
            if (isRectangleInputPointsValid(this.canvas.getWidth(), this.canvas.getHeight())) {
                int yAxisVal = yAxisStartPoint;
                while (compareYAxisValue(yAxisVal)) {
                    int xAxisVal = xAxisStartPoint;
                    while (compareXAxisValue(xAxisVal)) {
                        if (yAxisVal == yAxisStartPoint || yAxisVal == yAxisEndPoint) {
                            canvasBoard[yAxisVal][xAxisVal] = DRAWING_CHAR;
                        } else if (xAxisVal == xAxisStartPoint || xAxisVal == xAxisEndPoint) {
                            canvasBoard[yAxisVal][xAxisVal] = DRAWING_CHAR;
                        }
                        xAxisVal = xAxisVal + addOrSubtractXAxis();
                    }
                    yAxisVal = yAxisVal + addOrSubtractYAxisValue();
                }
            } else {
                throw new CanvasException(INVALID_RECTANGLE_INPUTS_ERROR_MSG);
            }
        } else {
            throw new CanvasException(CANVAS_NOT_AVAILABLE_ERROR_MSG);
        }
    }

    private boolean compareYAxisValue(int yAxisVal) {
        return yAxisStartPoint < yAxisEndPoint ? yAxisVal <= yAxisEndPoint : yAxisVal >= yAxisEndPoint;
    }

    private boolean compareXAxisValue(int xAxisVal) {
        return xAxisStartPoint < xAxisEndPoint ? xAxisVal <= xAxisEndPoint : xAxisVal >= xAxisEndPoint;
    }

    private int addOrSubtractYAxisValue() {
        return yAxisStartPoint < yAxisEndPoint ? 1 : -1;
    }

    private int addOrSubtractXAxis() {
        return xAxisStartPoint < xAxisEndPoint ? 1 : -1;
    }

    private boolean isRectangleInputPointsValid(final int canvasWidth, final int canvasHeight) {
        if (xAxisStartPoint < 1 || yAxisStartPoint < 1 || xAxisStartPoint > canvasWidth || yAxisStartPoint > canvasHeight) {
            return false;
        } else if (xAxisEndPoint < 1 || yAxisEndPoint < 1 || xAxisEndPoint > canvasWidth || yAxisEndPoint > canvasHeight) {
            return false;
        }
        return true;
    }
}