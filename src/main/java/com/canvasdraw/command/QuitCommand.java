package com.canvasdraw.command;

public class QuitCommand implements Command {

    @Override
    public void execute() {
        System.out.print("Quiting the program... ");
        System.exit(0);
    }
}
