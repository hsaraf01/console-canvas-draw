package com.canvasdraw.command;

import com.canvasdraw.exception.CanvasException;
import com.canvasdraw.service.Canvas;
import com.canvasdraw.service.Line;

public class LineCommand implements Command {

    private final Line line;

    public LineCommand(final Canvas canvas, final String[] commandValues) {
        this.line = new Line(canvas, commandValues);
    }

    @Override
    public void execute() throws CanvasException {
        line.draw();
    }
}
