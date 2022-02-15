package com.canvasdraw.command;

import com.canvasdraw.exception.CanvasException;

public interface Command {

    void execute() throws CanvasException;
}
