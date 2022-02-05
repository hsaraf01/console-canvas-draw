package com.canvasdraw.service;


import com.canvasdraw.exception.CanvasException;

import static com.canvasdraw.utils.Constants.*;

public class Canvas {

    private int height;
    private int width;
    private int widthWithBorder;
    private int heightWithBorder;
    private char[][] canvasBoard;

    public void initialize(final String[] commandValues) {
        this.width = Integer.parseInt(commandValues[1]);
        this.height = Integer.parseInt(commandValues[2]);
        this.widthWithBorder = width + 2;
        this.heightWithBorder = height + 2;
        canvasBoard = new char[heightWithBorder][widthWithBorder];
        generateCanvas();
    }

    public void printCanvas() {
        for (int yAxisVal = 0; yAxisVal < heightWithBorder; yAxisVal++) {
            for (int xAxisVal = 0; xAxisVal < widthWithBorder; xAxisVal++) {
                System.out.print(canvasBoard[yAxisVal][xAxisVal]);
            }
            System.out.println();
        }
    }

    public void drawLine(final String[] commandValues) throws CanvasException {
        Shape line = new Line(this, commandValues);
        line.draw();
    }

    public void drawRectangle(final String[] commandValues) throws CanvasException {
        Shape rectangle = new Rectangle(this, commandValues);
        rectangle.draw();
    }

    public void paint(final String[] commandValues) throws CanvasException {
        Paint paint = new Paint(this, commandValues);
        paint.fill();
    }

    public char[][] getCanvasBoard() {
        return canvasBoard;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    private void generateCanvas() {

        for (int yAxisVal = 0; yAxisVal < heightWithBorder; yAxisVal++) {
            for (int xAxisVal = 0; xAxisVal < widthWithBorder; xAxisVal++) {
                if (yAxisVal == 0 || yAxisVal == heightWithBorder - 1) {
                    canvasBoard[yAxisVal][xAxisVal] = HORIZONTAL_BORDER;
                } else if (xAxisVal == 0 || xAxisVal == widthWithBorder - 1) {
                    canvasBoard[yAxisVal][xAxisVal] = VERTICAL_BORDER;
                } else {
                    canvasBoard[yAxisVal][xAxisVal] = EMPTY;
                }
            }
        }
    }
}
