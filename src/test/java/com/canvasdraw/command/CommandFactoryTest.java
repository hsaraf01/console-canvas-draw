package com.canvasdraw.command;

import com.canvasdraw.exception.CanvasException;
import com.canvasdraw.service.Canvas;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommandFactoryTest {

    private static CommandFactory commandFactory;
    private static Canvas canvas;

    @BeforeAll
    public static void setup() {
        canvas = new Canvas();
        commandFactory = new CommandFactory();
    }

    @Test
    public void testQuitCommandInstanceCreated() throws CanvasException {
        String[] commandValues = {"Q"};
        Command command = commandFactory.create(canvas, commandValues);
        assertThat(command, instanceOf(QuitCommand.class));
    }

    @Test
    public void testCanvasCommandInstanceCreated() throws CanvasException {
        String[] commandValues = {"C", "10", "3"};
        Command command = commandFactory.create(canvas, commandValues);
        assertThat(command, instanceOf(CanvasCommand.class));
    }

    @Test
    public void testLineCommandInstanceCreated() throws CanvasException {
        String[] commandValues = {"L", "5", "4", "5", "7"};
        Command command = commandFactory.create(canvas, commandValues);
        assertThat(command, instanceOf(LineCommand.class));
    }

    @Test
    public void testRectangleCommandInstanceCreated() throws CanvasException {
        String[] commandValues = {"R", "14", "1", "19", "5"};
        Command command = commandFactory.create(canvas, commandValues);
        assertThat(command, instanceOf(RectangleCommand.class));
    }

    @Test
    public void testPaintCommandInstanceCreated() throws CanvasException {
        String[] commandValues = {"B", "4", "2", "x"};
        Command command = commandFactory.create(canvas, commandValues);
        assertThat(command, instanceOf(PaintCommand.class));
    }

    @Test
    public void exceptionThrownWhenCommandNotImplemented() {
        String[] commandValues = {"S", "4","4"};
        Exception exception = assertThrows(CanvasException.class, () -> commandFactory.create(canvas,commandValues));
        assertThat(exception.getMessage(), containsString("Error creating command for"));
    }

}
