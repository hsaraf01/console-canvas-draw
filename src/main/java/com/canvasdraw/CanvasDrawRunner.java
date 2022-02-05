package com.canvasdraw;

import com.canvasdraw.exception.CanvasException;
import com.canvasdraw.service.*;
import com.canvasdraw.validator.CanvasCommandValidator;

import java.util.Scanner;

import static com.canvasdraw.utils.Constants.USER_INPUT_ERROR_MSG;

public class CanvasDrawRunner {


    public static void main(String[] args) {
        final Canvas canvas = new Canvas();
        final CanvasCommandValidator canvasCommandValidator = new CanvasCommandValidator();
        final CanvasDrawService canvasDrawService = new CanvasDrawService(canvas, canvasCommandValidator);
        final Scanner scanner = new Scanner(System.in);
        System.out.println("*********************************************");
        System.out.println("***********   Canvas Drawing   **************");
        System.out.println("*********************************************");
        System.out.println();
        while (true) {
            System.out.print("Enter command: ");
            String command = scanner.nextLine();
            try {
                canvasDrawService.processCommand(command);
            } catch (CanvasException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("System error: " + e.getMessage());
            }
        }
    }

}
