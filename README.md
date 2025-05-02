# Scientific Calculator
### Overview
This is a simple scientific calculator built using Java and Swing. It follows the **Model-View-Controller (MVC)** design pattern for better code structure and modularity. The calculator provides both basic (addition, subtraction, multiplication, division) and advanced operations (square, square root) along with memory features.
## Features
- **Basic Operations**: Addition, Subtraction, Multiplication, and Division.
- **Advanced Operations**:
    - Square (`x²`)
    - Square Root (`√`)

- **Memory Functions**:
    - : Adds the result of the last calculation to memory. **M+**
    - : Subtracts the result of the last calculation from memory. **M-**
    - **MR (Memory Recall)**: Recalls the stored value from memory and displays it.
    - **MC (Memory Clear)**: Clears the memory.

- **Delete Function**: Deletes the last entered digit or decimal.
- **Clear Function**: Resets the calculator, including clearing memory.
- Operands and results are the only values displayed on the screen for simplicity.

## Deployment Instructions
### Prerequisites
- You must have **Java Development Kit (JDK)** installed.
- Any Java IDE (e.g., IntelliJ IDEA, Eclipse, or NetBeans) or a simple text editor and terminal.
- Basic familiarity with running Java applications.

### How to Deploy
1. **Clone the Repository**:
``` bash
   git clone <repository-url>
```
_(Replace `<repository-url>` with your repository link)_
1. **Open in Your IDE**:
    - If using IntelliJ IDEA:
        - Open IntelliJ IDEA and click on `Open`.
        - Select the project folder you just cloned.

    - If using another IDE, follow its procedure to import a Java project.

2. **Compile and Run**:
    - **In IntelliJ IDEA**:
        - Navigate to the main file: . `CalculatorApp.java`
        - Right-click on the file and select `Run CalculatorApp.main()`.

    - **Using Command Line**:
        - Navigate to the project directory.
        - Compile the project:
``` bash
       javac CalculatorApp.java
```
- Run the application:
``` bash
       java CalculatorApp
```
## How to Use
### User Interface
1. **Basic Buttons**:
    - Use digit buttons (`0` - `9`) and the decimal point (`.`) to build numbers.
    - Use operation buttons (`+`, `-`, `*`, ) for basic math calculations. `/`

2. **Advanced Operations**:
    - `x²` button calculates the square of the displayed number.
    - `√` button calculates the square root of the displayed number. (Displays "Error" if the number is negative.)

3. **Memory Buttons**:
    - adds the result of the last calculation to memory (only works if a valid result is displayed). `M+`
    - subtracts the result of the last calculated value from memory. `M-`
    - `MR` recalls the stored value and displays it.
    - `MC` clears the value stored in memory.

4. **Clear and Delete**:
    - `C` clears everything, including the current number, memory, and operations.
    - deletes the last entered digit or decimal (works only on input numbers). `DEL`

### Workflow
1. Enter the first number using the digit buttons.
2. Select an operation (`+`, `-`, `*`, , `x²`, or `√`). `/`
3. Enter the second number (if needed).
4. Press the `=` button to execute the operation.
5. View the result on the screen.

### Example Scenarios
- **Addition**:
Input `12`, press `+`, input `8`, press `=`, and see `20` on display.
- **Square**:
Input `5`, press `x²`, and see `25` on display.
- **Memory Add**:
Perform any operation (e.g., `5 + 5 = 10`) and press to add `10` to memory. You can recall this later with `MR`. `M+`

## Testing
This project includes unit tests and GUI tests:
1. **Unit Tests**:
    - Tests are present in the class. `CalculatorModelTest`
    - You can add more cases and run the tests using your IDE or a testing framework (e.g., JUnit).

2. **GUI Tests**:
    - Automated UI tests are included in the class. `CalculatorUITest`
    - The tests use simulated button clicks for validation.

## Known Limitations
- The calculator does not support very large numbers or scientific notation.
- Division by zero results in "Error" without additional explanation.

## Notes and Credits
- Designed and implemented as part of an interactive GUI system testing lab assignment.
- Uses Java Swing for the graphical user interface.

If you encounter any issues or have feature suggestions, feel free to raise them in the repository or contact [Your Name/Contact Info].
