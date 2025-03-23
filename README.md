# PDL Compiler

This repository contains a compiler written in Java.

## Prerequisites
- Ensure you have the Java Development Kit (JDK) installed on your system. You can verify the installation by running `java -version` in your terminal.

## Cloning the Repository
1. Open your terminal.
2. Navigate to the directory where you want to clone the repository.
3. Execute the following command:
   ```bash
   git clone https://github.com/jorgemzv7/PDL.git
   ```

## Compiling the Compiler
Navigate to the cloned repository:
```bash
  cd PDL
```

## Compile the Java files:
```bash
  javac *.java
```

## Running the Compiler
Assuming the main class is AnalizadorSintactico, run the compiler with:
```bash
java AnalizadorSintactico
```

Example Input
If the compiler expects input from a file, ensure you have a source file ready. For instance, create a file named source.pdl with the following content:

```
program Example;
var x: integer;
begin
  x := 10;
  writeln(x);
end.
```

Then, run the compiler specifying the input file:
java AnalizadorSintactico source.pdl
Note: The exact class names and usage might differ based on the implementation details of the compiler. It's advisable to review the source code or any provided documentation for specific instructions.
