package com.canvasdraw.validator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.canvasdraw.utils.Constants.*;

public class CanvasCommandValidator {

    private static final String CANVAS_COMMAND_PATTERN = "^" + CANVAS_COMMAND_STARTS_WITH + "\\s[0-9]+\\s[0-9]+$";
    private static final String LINE_COMMAND_PATTERN = "^" + LINE_COMMAND_STARTS_WITH + "\\s[0-9]+\\s[0-9]+\\s[0-9]+\\s[0-9]+$";
    private static final String RECTANGLE_COMMAND_PATTERN = "^" + RECTANGLE_COMMAND_STARTS_WITH + "\\s[0-9]+\\s[0-9]+\\s[0-9]+\\s[0-9]+$";
    private static final String PAINT_COMMAND_PATTERN = "^" + BUCKET_COMMAND_STARTS_WITH + "\\s[0-9]+\\s[0-9]+\\s[\\w|\\W]$";
    private static final String QUIT_COMMAND_PATTERN = "^" + QUIT_COMMAND + "$";
    private static final List<String> PATTERNS = Arrays.asList(CANVAS_COMMAND_PATTERN,
            LINE_COMMAND_PATTERN,
            RECTANGLE_COMMAND_PATTERN,
            PAINT_COMMAND_PATTERN,
            QUIT_COMMAND_PATTERN);

    private boolean match(final String commandPattern, final String command) {
        Pattern pattern = Pattern.compile(commandPattern);
        return pattern.matcher(command).find();
    }

    public boolean validate(final String command) {
        return PATTERNS.stream().anyMatch(pattern -> match(pattern, command));
    }

}
