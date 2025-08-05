package org.Ahmed;

import java.io.File;
import java.util.Arrays;
import java.util.List;


/**
 * Utility class for validating command-line input parameters.
 */
public class InputValidator {
    
	private static final List<String> validinputTypes = Arrays.asList("int", "string", "double");
	private static final List<String> validOperations = Arrays.asList("neg", "reverse", "capitalize");

    public static void validateType(String inputType) {
		if(!validinputTypes.contains(inputType)) {
			throw new IllegalArgumentException("Invalid input type: " + inputType);
		}
	}

	public static void validateOperations(List<String> operations) {
		for (String operation : operations) {
			if (!validOperations.contains(operation)) {
				throw new IllegalArgumentException("Invalid operation: " + operation);
			}
		}
	}

	public static void validateThreads(int threads) {
		if (threads <= 0) {
			throw new IllegalArgumentException("Number of threads must be greater than 0");
		}
	}

    public static void validateInputFile(String inputRaw) {
        if (inputRaw == null || inputRaw.isEmpty()) {
            throw new IllegalArgumentException("Input file cannot be null or empty.");
        }

        File inputFile = new File(inputRaw);
        if (!inputFile.exists() || !inputFile.isFile()) {
            throw new IllegalArgumentException("Input file does not exist or is not a file: " + inputRaw);
        }
    }
    
}
