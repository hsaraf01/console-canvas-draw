package com.canvasdraw.service;

import com.canvasdraw.command.Command;
import com.canvasdraw.command.CommandFactory;
import com.canvasdraw.exception.CanvasException;
import com.canvasdraw.validator.CanvasCommandValidator;

import static com.canvasdraw.utils.Constants.*;

public class CanvasDrawService {

    private static final String COMMAND_ERROR_MSG = USER_INPUT_ERROR_MSG +" '%s' is not a valid command. %s";
    private final Canvas canvas;
    private final CommandFactory commandFactory;
    private final CanvasCommandValidator canvasCommandValidator;

    public CanvasDrawService(Canvas canvas, CanvasCommandValidator canvasCommandValidator) {
        this.canvas = canvas;
        this.commandFactory = new CommandFactory();
        this.canvasCommandValidator = canvasCommandValidator;
    }

    public void processCommand(String canvasCommand) throws CanvasException {
        if (canvasCommandValidator.validate(canvasCommand)) {
            String[] commandValues = canvasCommand.split(" ");
            Command command = commandFactory.create(canvas, commandValues);
            command.execute();
            canvas.printCanvas();
        } else {
            throw new CanvasException(String.format(COMMAND_ERROR_MSG, canvasCommand, commandHelpMessage()));
        }

    }
    private String commandHelpMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append("Following is the list of valid commands\n\n");
        builder.append(CANVAS_COMMAND_STARTS_WITH + " w h              -Creates a new canvas of width w and height h\n");
        builder.append(LINE_COMMAND_STARTS_WITH + " x1 y1 x2 y2      -Creates a new line from (x1,y1) to (x2,y2). Currently only" +
                " horizontal or vertical lines are supported. Horizontal and vertical lines" +
                " will be drawn using the 'x' character \n");
        builder.append(RECTANGLE_COMMAND_STARTS_WITH + " x1 y1 x2 y2      -Creates a new rectangle, whose upper left corner is (x1,y1) and" +
                " lower right corner is (x2,y2). Horizontal and vertical lines will be drawn using the 'x' character.\n");
        builder.append(BUCKET_COMMAND_STARTS_WITH + " x y c            -Fills the entire area connected to (x,y) with \"colour\" c. The" +
                " behaviour of this is the same as that of the \"bucket fill\" tool in paint" +
                " programs.\n");
        builder.append(QUIT_COMMAND + "                  -Quits the program\n");
        return builder.toString();
    }

}
