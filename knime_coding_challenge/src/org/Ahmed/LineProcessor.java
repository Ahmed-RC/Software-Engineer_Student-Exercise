package org.Ahmed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for processing a list of input lines according to the input type and the specified operations.
 */
public class LineProcessor {

    /**
     * Processes a list of lines by applying operations based on the input type (int, double, string).
     * Also updates shared statistics in a thread-safe manner (synchronized) meaning only one Thread updates the statistics.
     *
     * @param lines The list of lines to process
     * @param args The argument reader containing input type and operations
     * @return List of processed lines as strings
     * @throws IOException if any processing error occurs
     */
    public static List<String> processLines(List<String> lines, ArguementReader args) throws IOException {
        String inputType = args.getInputType();
        List<String> operations = args.getOperations();
        Statistics statistics = Statistics.getInstance();

        List<String> result = new ArrayList<>();

        for (String line : lines) {
            updateStatistics(line, statistics);
            result.add(applyOperations(line, operations, inputType));
        }

        return result;
    }

    /**
     * Applies the specified operations to the input line based on the declared input type.
     *
     * @param line The input line
     * @param operations List of operations to apply (neg, reverse, capitalize)
     * @param type The declared input type (int, double, string)
     * @return The transformed string after applying operations
     */
    private static String applyOperations(String line, List<String> operations, String type) {
        switch (type) {
            case "int":
                int intValue = Integer.parseInt(line);
                if (operations.contains("neg")) intValue = -intValue;
                return String.valueOf(intValue);

            case "double":
                double doubleValue = Double.parseDouble(line);
                if (operations.contains("neg")) doubleValue = -doubleValue;
                return String.valueOf(doubleValue);

            case "string":
                if (operations.contains("reverse")) line = new StringBuilder(line).reverse().toString();
                if (operations.contains("capitalize")) line = line.toUpperCase();
                return line;

            default:
                throw new IllegalArgumentException("Unsupported input type: " + type);
        }
    }

    private static void updateStatistics(String line, Statistics statistics) {
        synchronized (statistics) {
            statistics.updateStatisticsWithLine(line);
        }
    }
}
