package com.canvasdraw.command;

import com.canvasdraw.exception.CanvasException;
import com.canvasdraw.service.Canvas;
import com.canvasdraw.service.Paint;

public class PaintCommand implements Command {

    private final Paint paint;

    public PaintCommand(final Canvas canvas, final String[] commandValues) {
        this.paint = new Paint(canvas, commandValues);
    }

    @Override
    public void execute() throws CanvasException {
        paint.fill();
    }
}
