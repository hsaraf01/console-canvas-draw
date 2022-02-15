package com.canvasdraw;

import com.canvasdraw.exception.CanvasException;
import com.canvasdraw.service.Canvas;
import com.canvasdraw.service.CanvasDrawService;
import com.canvasdraw.validator.CanvasCommandValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

import static com.canvasdraw.utils.Constants.SYSTEM_ERROR_MSG;

public class CanvasDrawRunner {
    private static final Logger logger = LoggerFactory.getLogger(CanvasDrawRunner.class);

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
                logger.error("Error occurred" ,e);
            } catch (Exception e) {
                System.out.println(SYSTEM_ERROR_MSG + e.getMessage());
                logger.error("System Error occurred", e);
            }
        }
    }

}
