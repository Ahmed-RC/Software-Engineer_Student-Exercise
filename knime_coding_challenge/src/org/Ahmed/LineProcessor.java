package org.Ahmed;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LineProcessor {

    private String stringOutput = "";
    private int integerOutput = 0;  
    private double doubleOutput = 0.0;
    private final Statistics statistics = Statistics.getInstance();
    


    public void processLines(List<String> chunk, ArguementReader arguements, FileWriter outputFile,int numberOfThread) throws IOException {
        String inputType = arguements.getInputType();
        List<String> operations = arguements.getOperations();
        boolean createOutputFile = arguements.createOutputFile();

			for (int i = 0; i < chunk.size(); i++) {
				String line = chunk.get(i).trim();

				synchronized (Statistics.getInstance()) {
                     statistics.updateStatisticsWithLine(line);
                }

			if (inputType.equals("int")) {

				integerOutput= Integer.parseInt(line);
				if(operations.contains("neg")){
					integerOutput = -integerOutput;
				}

				if(createOutputFile){
					synchronized (outputFile) {
                    outputFile.write(Integer.toString(integerOutput) + System.lineSeparator());
					}
				}else{
					System.out.println(integerOutput);
				}
			}else if(inputType.equals("double")){
				doubleOutput = Double.parseDouble(line);
					if(operations.contains("neg")){
						doubleOutput = -doubleOutput;
				}
				if(createOutputFile){
					synchronized (outputFile) {
					outputFile.write(Double.toString(doubleOutput) + System.lineSeparator());
					}
				}else{
					System.out.println(doubleOutput);
				}
			}else{
				stringOutput = line;
				if(operations.contains("reverse")){
					stringOutput = new StringBuilder(stringOutput).reverse().toString();
				}
				if(operations.contains("capitalize")){
					stringOutput=stringOutput.toUpperCase();
				}
				if(createOutputFile){
					synchronized (outputFile) {
					outputFile.write(stringOutput+ System.lineSeparator());
					}
				}else{
					System.out.println(stringOutput+numberOfThread);
				}
			}
		
	}
}
}
