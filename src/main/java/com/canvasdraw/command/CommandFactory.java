package com.canvasdraw.command;

import com.canvasdraw.exception.CanvasException;
import com.canvasdraw.service.Canvas;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import static com.canvasdraw.utils.Constants.*;

public class CommandFactory {

    private final Map<String,Class<? extends Command>> commandMap = new HashMap<>();

    public CommandFactory() {
        commandMap.put(CANVAS_COMMAND_STARTS_WITH, CanvasCommand.class);
        commandMap.put(LINE_COMMAND_STARTS_WITH,LineCommand.class);
        commandMap.put(RECTANGLE_COMMAND_STARTS_WITH,RectangleCommand.class);
        commandMap.put(BUCKET_COMMAND_STARTS_WITH, PaintCommand.class);
        commandMap.put(QUIT_COMMAND, QuitCommand.class);
    }

    public Command create(final Canvas canvas, final String [] commandValues) throws CanvasException {
        final String commandType =  commandValues[0];
        try {
            Class<? extends Command> command = commandMap.get(commandType);
            if(QuitCommand.class == command) {
                return new QuitCommand();
            }
            Constructor<? extends Command> constructor = command.getDeclaredConstructor(Canvas.class, Array.newInstance(String.class,commandValues.length).getClass());
            return constructor.newInstance(canvas, commandValues);
        } catch (Exception e) {
            throw new CanvasException(SYSTEM_ERROR_MSG +"Error creating command for " + commandType + ".", e);
        }

    }
}
