package com.canvasdraw.command;

import com.canvasdraw.exception.CanvasException;
import com.canvasdraw.service.Canvas;

public class CanvasCommand implements Command {

    private final Canvas canvas;
    private final String[] commandValues;

    public CanvasCommand(final Canvas canvas, final String[] commandValues) {
        this.canvas = canvas;
        this.commandValues = commandValues;
    }

    @Override
    public void execute() throws CanvasException {
        canvas.draw(commandValues);
    }
}
