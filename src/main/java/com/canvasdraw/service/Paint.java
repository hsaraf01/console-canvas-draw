package com.canvasdraw.service;


import com.canvasdraw.exception.CanvasException;

import static com.canvasdraw.utils.Constants.*;

public class Paint {

    private final int xAxisStartPoint;
    private final int yAxisStartPoint;
    private final char colour;
    private final Canvas canvas;

    public Paint(final Canvas canvas, final String[] commandValues) {
        this.canvas = canvas;
        this.xAxisStartPoint = Integer.parseInt(commandValues[1]);
        this.yAxisStartPoint = Integer.parseInt(commandValues[2]);
        this.colour = commandValues[3].charAt(0);
    }

    public void fill() throws CanvasException {
        if (canvas != null && canvas.getCanvasBoard() != null) {
            if (xAxisStartPoint < 1 || yAxisStartPoint < 1 || xAxisStartPoint > canvas.getWidth() || yAxisStartPoint > canvas.getHeight()) {
                throw new CanvasException(INVALID_PAINT_INPUTS_ERROR_MSG);
            }
            fillColor(canvas.getCanvasBoard(), xAxisStartPoint, yAxisStartPoint, colour);
        } else {
            throw new CanvasException(CANVAS_NOT_AVAILABLE_ERROR_MSG);
        }
    }

    private void fillColor(final char[][] canvasBoard, final int xAxisVal, final int yAxisVal, final char color) {
        if (canvasBoard[yAxisVal][xAxisVal] == HORIZONTAL_BORDER || canvasBoard[yAxisVal][xAxisVal] == VERTICAL_BORDER) {
            return;
        }
        if (canvasBoard[yAxisVal][xAxisVal] == DRAWING_CHAR || canvasBoard[yAxisVal][xAxisVal] == color) {
            return;
        }
        canvasBoard[yAxisVal][xAxisVal] = color;
        fillColor(canvasBoard, xAxisVal + 1, yAxisVal, color); //east
        fillColor(canvasBoard, xAxisVal - 1, yAxisVal, color); //west
        fillColor(canvasBoard, xAxisVal, yAxisVal + 1, color); //north
        fillColor(canvasBoard, xAxisVal, yAxisVal - 1, color); //south
    }
}
