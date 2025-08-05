# Multithreaded File Processing Application

This Java application demonstrates efficient file processing using parallelism. The program reads an input file, applies a sequence of configurable operations on each line (e.g., string manipulation or numeric transformation), and outputs the processed results either to the console or a specified output file. It is designed with modularity, thread safety, and performance in mind.

---

## Features

-  **Multithreaded processing** using Java’s `ExecutorService`
-  Supports input types: `int`, `double`, and `string`
-  Applies operations like `neg` (negate), `capitalize`, and `reverse`
-  Collects statistics: total and unique lines processed
-  Graceful handling of exceptions across threads
-  Output can be redirected to a file or printed to console
-  Thread-safe singleton for shared statistics

---

## Usage

### 1. Compile

```bash
javac -d bin src/org/Ahmed/*.java
```

### 2. Run

```bash
java -cp bin org.Ahmed.Main   --input <path_to_input_file>   --inputtype <int|string|double>   --operations <comma_separated_operations>   --threads <number_of_threads>   --output <path_to_output_file>  # Optional
```

### Arguments

| Argument         | Required | Description                                                 |
|------------------|----------|-------------------------------------------------------------|
| `--input`        | ✅       | Path to the input file                                      |
| `--inputtype`    | ✅       | Data type: `int`, `double`, or `string`                     |
| `--operations`   | ✅       | Operations to apply: `neg`, `capitalize`, `reverse`         |
| `--threads`      | ✅       | Number of threads to use                                    |
| `--output`       | Optional | File path to write the output. If omitted, prints to console|

---

## Example

### Input File (`input.txt`)
```
hello
world
123
```

### Command

```bash
java -cp bin org.Ahmed.Main   --input input.txt   --inputtype string   --operations reverse,capitalize   --threads 2   --output result.txt
```

### Output
```
OLLEH
DLROW
321
```

---

## Testing

JUnit 4 tests are included under the `test/` directory. To run:

```bash
javac -cp .;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar -d bin test/org/Ahmed/test/*.java
java -cp .;bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore org.Ahmed.test.LineProcessorTest
