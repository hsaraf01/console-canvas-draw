package com.canvasdraw.command;

import com.canvasdraw.exception.CanvasException;
import com.canvasdraw.service.Canvas;
import com.canvasdraw.service.Rectangle;

public class RectangleCommand implements Command {

    private final Rectangle rectangle;

    public RectangleCommand(final Canvas canvas, final String[] commandValues) {
        rectangle = new Rectangle(canvas, commandValues);
    }

    @Override
    public void execute() throws CanvasException {
        rectangle.draw();
    }
}
