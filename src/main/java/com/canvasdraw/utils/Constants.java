package com.canvasdraw.utils;

public final class Constants {

    public static final String CANVAS_COMMAND_STARTS_WITH = "C";
    public static final String LINE_COMMAND_STARTS_WITH = "L";
    public static final String RECTANGLE_COMMAND_STARTS_WITH = "R";
    public static final String BUCKET_COMMAND_STARTS_WITH = "B";
    public static final String QUIT_COMMAND = "Q";

    public static final char DRAWING_CHAR = 'x';
    public static final char HORIZONTAL_BORDER = '-';
    public static final char VERTICAL_BORDER = '|';
    public static final char EMPTY = ' ';

    public static final String USER_INPUT_ERROR_MSG = "User input error:";
    public static final String SYSTEM_ERROR_MSG = "System error: ";
    public static final String CANVAS_NOT_AVAILABLE_ERROR_MSG = "Canvas is not created.";
    public static final String INVALID_RECTANGLE_INPUTS_ERROR_MSG = "Rectangle drawing inputs outside canvas boundaries or invalid.";
    public static final String INVALID_LINE_INPUTS_ERROR_MSG = "Line drawing inputs outside canvas boundaries or invalid.";
    public static final String ONLY_HORIZONTAL_VERTICAL_ERROR_MSG = "Drawing only horizontal or vertical line is supported.";
    public static final String INVALID_PAINT_INPUTS_ERROR_MSG = "Paint starting point outside the canvas.";

}
