-- =====================================================================
-- CodeQuest: Database Seed Data (MySQL 8.0)
-- Classroom-aligned Java syllabus with all 10 required lesson sections
-- =====================================================================

USE codequest_db;

-- 1. Educational Syllabus Seed Data
-- Note: Starts with completely fresh database. Zero users or activity seeded.


-- 2. Insert Core Java Course
INSERT INTO courses (id, title, description, is_published)
VALUES (1, 'College Core Java Programming', 'Comprehensive university-aligned Java course covering fundamentals, OOP concepts, data structures, exception handling, and exam preparation.', TRUE)
ON DUPLICATE KEY UPDATE title=VALUES(title);

-- 3. Insert 12 Modules
INSERT INTO modules (id, course_id, title, description, sequence_order) VALUES
(1, 1, 'Module 1: Introduction to Java', 'JVM architecture, bytecode, JDK vs JRE, and your first Java program.', 1),
(2, 1, 'Module 2: Variables and Data Types', 'Primitive types, naming conventions, type casting, and Scanner input.', 2),
(3, 1, 'Module 3: Operators & Expressions', 'Arithmetic, relational, logical, bitwise, and ternary operators.', 3),
(4, 1, 'Module 4: Conditional Statements', 'Control flow using if, if-else, nested if, else-if ladder, and switch.', 4),
(5, 1, 'Module 5: Loops & Iteration', 'while, do-while, for loops, nested loops, break and continue.', 5),
(6, 1, 'Module 6: Arrays', '1D arrays, 2D matrices, array traversal, and standard algorithms.', 6),
(7, 1, 'Module 7: Strings & Text Handling', 'String immutability, methods, StringPool, StringBuilder vs StringBuffer.', 7),
(8, 1, 'Module 8: Methods & Modular Code', 'Defining methods, parameter passing, return types, method overloading.', 8),
(9, 1, 'Module 9: Classes and Objects', 'Class definitions, object instantiation, constructors, and the this keyword.', 9),
(10, 1, 'Module 10: Object-Oriented Programming (OOP)', 'Inheritance, Polymorphism, Abstraction, and Encapsulation.', 10),
(11, 1, 'Module 11: Exception Handling', 'try-catch blocks, throw, throws, finally, and custom exceptions.', 11),
(12, 1, 'Module 12: Revision & Exam Practice', 'Important university exam questions, output prediction, and mock assessments.', 12)
ON DUPLICATE KEY UPDATE title=VALUES(title);

-- 4. Insert Comprehensive Lessons (All 10 required sections)
INSERT INTO lessons (id, module_id, title, sequence_order, estimated_minutes, definition, concept_explanation, syntax_block, java_program, program_output, program_explanation, important_points, common_mistakes, practice_questions, is_published) VALUES
(
1, 1, 'Introduction to Java & Structure of a Java Program', 1, 15,
'Java is a high-level, class-based, object-oriented programming language designed to have as few implementation dependencies as possible, following the principle of "Write Once, Run Anywhere" (WORA).',
'Java source code (.java) is compiled into architecture-neutral bytecode (.class) by the Java compiler (javac). The Java Virtual Machine (JVM) executes this bytecode on any operating system that has a compatible JRE/JDK installed. Every standalone Java application must have at least one class and an entry point called the main() method.',
'// General Structure:
class ClassName {
    public static void main(String[] args) {
        // Program statements
    }
}',
'// College Standard First Java Program
class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to CodeQuest Java!");
        System.out.print("Learning Java step-by-step for university exams.");
    }
}',
'Welcome to CodeQuest Java!
Learning Java step-by-step for university exams.',
'1. "class Main": Declares a class named Main. In Java, all code must reside inside a class.
2. "public static void main(String[] args)": The main method where JVM begins execution.
   - public: Accessible from anywhere outside the class.
   - static: Can be invoked without creating an instance of the class.
   - void: Does not return any value.
   - String[] args: Command-line arguments passed as an array of strings.
3. "System.out.println()": Prints text and moves cursor to a new line.
4. "System.out.print()": Prints text without inserting a newline character.',
'• Java is case-sensitive (main is different from Main).
• The name of the public class must match the file name (e.g., Main.java).
• Bytecode is platform-independent; the JVM is platform-dependent.
• Memory is automatically managed by Java Garbage Collector.',
'• Forgetting semicolon (;) at the end of statements.
• Writing Main with a lowercase "m" or String with a lowercase "s".
• Missing static keyword in main() leads to runtime error "NoSuchMethodError".',
'1. Write a Java program to print your name, roll number, and department on three separate lines.
2. Predict the compiler error if the return type of main() is changed to int.',
TRUE
),
(
2, 2, 'Variables and Primitive Data Types', 1, 20,
'A variable in Java is a named memory location used to store data that can be manipulated during program execution. Java is strongly typed, meaning every variable must have a declared data type before use.',
'Java provides 8 primitive data types categorized into four groups:
1. Integer types: byte (1 byte), short (2 bytes), int (4 bytes), long (8 bytes).
2. Floating-point types: float (4 bytes), double (8 bytes).
3. Character type: char (2 bytes, 16-bit Unicode).
4. Boolean type: boolean (1 bit, values: true or false).
Constants are declared using the "final" keyword and cannot be reassigned.',
'dataType variableName = initialValue;
final dataType CONSTANT_NAME = value;',
'class Main {
    public static void main(String[] args) {
        int studentAge = 19;
        double gpa = 8.75;
        char grade = \'A\';
        boolean isEnrolled = true;
        final double COLLEGE_PASS_MARKS = 40.0;

        System.out.println("Age: " + studentAge);
        System.out.println("GPA: " + gpa);
        System.out.println("Grade: " + grade);
        System.out.println("Enrolled: " + isEnrolled);
        System.out.println("Passing Threshold: " + COLLEGE_PASS_MARKS);
    }
}',
'Age: 19
GPA: 8.75
Grade: A
Enrolled: true
Passing Threshold: 40.0',
'1. "int studentAge = 19;": Allocates 4 bytes in stack memory and stores integer 19.
2. "double gpa = 8.75;": Allocates 8 bytes in memory for standard double-precision floating point.
3. "char grade = \'A\';": Stores Unicode character 65 enclosed in single quotes.
4. "boolean isEnrolled = true;": Stores conditional flag value.
5. "final double COLLEGE_PASS_MARKS = 40.0;": Creates an immutable constant.',
'• Java char uses 16-bit Unicode (0 to 65535), not 8-bit ASCII.
• Default value for int in class scope is 0, double is 0.0, boolean is false.
• Local variables (declared inside methods) have NO default values and must be initialized before reading.',
'• Using single quotes for Strings or double quotes for char (e.g., char c = "A"; causes compile error).
• Attempting to modify a "final" variable after initialization.
• Reading uninitialized local variables inside main().',
'1. Write a program to declare variables for storing temperature in Celsius and convert it to Fahrenheit.
2. What will happen if you assign 130 to a byte variable? Explain byte overflow.',
TRUE
),
(
3, 3, 'Operators and Operator Precedence', 1, 20,
'Operators are special symbols in Java that perform specific operations on one, two, or three operands and produce a result.',
'Operators in Java are classified into:
1. Arithmetic Operators: +, -, *, /, % (modulus gives remainder).
2. Relational Operators: ==, !=, >, <, >=, <= (return boolean results).
3. Logical Operators: && (Short-circuit AND), || (Short-circuit OR), ! (Logical NOT).
4. Unary / Increment-Decrement: ++, -- (pre and post forms).
5. Assignment: =, +=, -=, *=, /=.
Operator Precedence dictates the order in which operators are evaluated when multiple operators appear in an expression.',
'// Post-increment: returns old value, then increments
int y = x++;
// Pre-increment: increments first, then returns new value
int z = ++x;',
'class Main {
    public static void main(String[] args) {
        int a = 10;
        int b = 3;

        System.out.println("Quotient: " + (a / b));
        System.out.println("Remainder (Modulus): " + (a % b));

        int x = 5;
        int post = x++;
        int pre = ++x;

        System.out.println("Post-increment value: " + post + ", x is now: " + x);
        System.out.println("Pre-increment value: " + pre);

        boolean result = (a > b) && (b != 0);
        System.out.println("Logical condition result: " + result);
    }
}',
'Quotient: 3
Remainder (Modulus): 1
Post-increment value: 5, x is now: 7
Pre-increment value: 7
Logical condition result: true',
'1. "a / b": Since both operands are integers, integer division truncates decimal digits (10 / 3 = 3).
2. "a % b": Modulus operator returns remainder of division (10 % 3 = 1).
3. "int post = x++": Returns current value of x (5) to "post", then increments x to 6.
4. "int pre = ++x": Increments x from 6 to 7 first, then returns 7 to "pre".
5. "(a > b) && (b != 0)": Both conditions evaluate to true, so logical AND evaluates to true.',
'• Integer division truncates towards zero; use (double)a / b for floating-point answers.
• Short-circuit evaluation: If the first operand of && is false, Java skips evaluating the second operand.',
'• Confusing assignment operator (=) with relational equality operator (==).
• Forgetting that post-increment applies the increment AFTER the current expression is evaluated.',
'1. Evaluate the expression: int a = 5; int b = a++ + ++a * 2; System.out.println(b);
2. Explain the difference between single & and double && in Java.',
TRUE
),
(
4, 4, 'Conditional Statements: if, if-else, and switch', 1, 20,
'Conditional statements in Java control the execution flow based on boolean expressions, allowing different blocks of code to run depending on runtime conditions.',
'Java supports:
1. Simple if: Executes code block only when condition is true.
2. if-else: Provides an alternative path when condition is false.
3. else-if ladder: Evaluates multiple conditions sequentially.
4. switch statement: Tests a single variable against multiple constant values (cases). Since Java 7, switch supports Strings, enums, byte, short, char, and int.',
'if (condition) {
    // executes if true
} else if (anotherCondition) {
    // executes if condition 2 is true
} else {
    // fallback default
}',
'class Main {
    public static void main(String[] args) {
        int score = 85;

        // else-if ladder for college grading
        if (score >= 90) {
            System.out.println("Grade: A+ (Outstanding)");
        } else if (score >= 75) {
            System.out.println("Grade: A (Distinction)");
        } else if (score >= 50) {
            System.out.println("Grade: B (Pass)");
        } else {
            System.out.println("Grade: F (Fail)");
        }

        // switch statement demonstration
        int dayOfWeek = 3;
        switch (dayOfWeek) {
            case 1: System.out.println("Day: Monday"); break;
            case 2: System.out.println("Day: Tuesday"); break;
            case 3: System.out.println("Day: Wednesday"); break;
            default: System.out.println("Day: Weekend/Other"); break;
        }
    }
}',
'Grade: A (Distinction)
Day: Wednesday',
'1. The condition "score >= 90" is evaluated first: 85 >= 90 is false.
2. The next condition "score >= 75" is evaluated: 85 >= 75 is true.
3. "Grade: A (Distinction)" is printed, and remaining else branches are skipped.
4. In the switch block, dayOfWeek (3) matches "case 3:", prints Wednesday, and "break" prevents fallthrough.',
'• The "break" statement is required in switch cases to prevent unintended fall-through.
• A switch statement can evaluate primitive integers (except long), char, String, and enum.',
'• Writing "if (score = 90)" instead of "if (score == 90)".
• Omitting "break" in switch cases leading to fallthrough bug.
• Adding semicolon after if: "if (x > 5); { ... }" which executes the block unconditionally.',
'1. Write a program to find the largest among three numbers using nested if-else.
2. Rewrite the day-of-week switch statement using Java switch expressions.',
TRUE
),
(
5, 5, 'Loops in Java: while, do-while, and for', 1, 25,
'A loop is a control structure that repeatedly executes a block of code as long as a specified boolean condition remains true.',
'Java provides three primary loop constructs:
1. for loop: Best when the exact number of iterations is known in advance.
2. while loop: Entry-controlled loop; checks condition before executing body.
3. do-while loop: Exit-controlled loop; guarantees body executes at least once even if condition is initially false.
4. "break" terminates the loop immediately; "continue" skips the rest of current iteration and jumps to the next.',
'for (initialization; condition; update) {
    // loop body
}
while (condition) {
    // loop body
}',
'class Main {
    public static void main(String[] args) {
        System.out.print("For Loop (1 to 5): ");
        for (int i = 1; i <= 5; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.print("While Loop (Even numbers up to 10): ");
        int num = 2;
        while (num <= 10) {
            System.out.print(num + " ");
            num += 2;
        }
        System.out.println();

        System.out.print("Do-While (executes once when false): ");
        int test = 100;
        do {
            System.out.print("Executed! test=" + test);
        } while (test < 10);
        System.out.println();
    }
}',
'For Loop (1 to 5): 1 2 3 4 5 
While Loop (Even numbers up to 10): 2 4 6 8 10 
Do-While (executes once when false): Executed! test=100',
'1. "for (int i = 1; i <= 5; i++)": Initializes i=1, verifies i<=5, executes body, increments i by 1.
2. "while (num <= 10)": Checks num<=10 each iteration. num increments by 2 until 12, when loop terminates.
3. "do { ... } while (test < 10)": Executes print statement first, then tests condition (100 < 10 = false), terminating after 1 execution.',
'• do-while is the only loop that terminates with a semicolon after the while(condition);
• Variables declared in the for-loop header (int i = 0) are scoped strictly inside the loop.',
'• Creating infinite loops by forgetting to increment loop counter inside while loops.
• Off-by-one errors (using < instead of <= or vice versa).
• Placing a semicolon directly after for loop header: "for(int i=0; i<5; i++);"',
'1. Write a program to print the Fibonacci series up to 10 terms using a for loop.
2. Write a program to reverse a given integer using a while loop.',
TRUE
),
(
6, 6, 'Single and Multi-Dimensional Arrays', 1, 25,
'An array in Java is a fixed-size, homogeneous data structure that stores multiple elements of the same data type in contiguous memory locations under a single variable name.',
'In Java, arrays are objects stored on the heap memory. Array indices start at 0 and end at length - 1. Once created, the size of an array cannot be changed.
Multi-dimensional arrays in Java are actually "arrays of arrays", allowing rectangular matrices or jagged arrays where rows have differing column lengths.',
'dataType[] arrayName = new dataType[size];
int[][] matrix = new int[rows][cols];',
'class Main {
    public static void main(String[] args) {
        // 1D Array Declaration and Traversal
        int[] marks = {85, 92, 78, 90, 88};
        int sum = 0;

        System.out.print("Array Elements: ");
        for (int i = 0; i < marks.length; i++) {
            System.out.print(marks[i] + " ");
            sum += marks[i];
        }
        System.out.println("\nAverage Marks: " + ((double)sum / marks.length));

        // 2D Array (2x2 Matrix)
        int[][] matrix = {
            {1, 2},
            {3, 4}
        };

        System.out.println("2x2 Matrix Elements:");
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[r].length; c++) {
                System.out.print(matrix[r][c] + " ");
            }
            System.out.println();
        }
    }
}',
'Array Elements: 85 92 78 90 88 
Average Marks: 86.6
2x2 Matrix Elements:
1 2 
3 4 ',
'1. "int[] marks = {...}": Allocates array of 5 integers.
2. "marks.length": Returns number of elements (5). Notice length is a property, not a method.
3. "marks[i]": Accesses element at index i (0 to 4).
4. "matrix[r][c]": Outer loop iterates over rows; inner loop iterates over columns of each row.',
'• Array indices are 0-based.
• "length" of array is a field (array.length), while for String it is a method (str.length()).
• Array elements default to 0 for numeric types, false for boolean, null for objects.',
'• Accessing index equal to array.length causes "ArrayIndexOutOfBoundsException".
• Trying to resize an existing array (Java arrays have fixed capacity).',
'1. Write a program to find the maximum and minimum elements in an integer array.
2. Write a program to compute the transpose of a 3x3 matrix.',
TRUE
),
(
7, 7, 'Strings and Common String Methods', 1, 20,
'In Java, a String is an object that represents a sequence of characters. Unlike C/C++ where strings are character arrays, Java Strings are full objects belonging to the java.lang.String class and are immutable.',
'Immutability means that once a String object is created in memory, its contents cannot be altered. Any modification creates a new String object.
Java optimizes memory by maintaining a "String Constant Pool" inside heap memory for string literals.
Common methods include length(), charAt(), toUpperCase(), substring(), equals(), and indexOf().',
'String s1 = "Hello"; // created in String Constant Pool
String s2 = new String("Hello"); // created in standard heap memory',
'class Main {
    public static void main(String[] args) {
        String greeting = "CodeQuest Java";

        System.out.println("Length: " + greeting.length());
        System.out.println("Character at index 4: " + greeting.charAt(4));
        System.out.println("Uppercase: " + greeting.toUpperCase());
        System.out.println("Substring (0 to 9): " + greeting.substring(0, 9));

        String strA = "Java";
        String strB = new String("Java");

        System.out.println("Using == (references): " + (strA == strB));
        System.out.println("Using .equals() (content): " + strA.equals(strB));
    }
}',
'Length: 14
Character at index 4: Q
Uppercase: CODEQUEST JAVA
Substring (0 to 9): CodeQuest
Using == (references): false
Using .equals() (content): true',
'1. "greeting.length()": Returns total characters (14).
2. "greeting.charAt(4)": Returns character at index 4 (\'Q\').
3. "greeting.substring(0, 9)": Returns characters from index 0 up to 8 ("CodeQuest").
4. "strA == strB": Compares memory addresses (references), which are different.
5. "strA.equals(strB)": Compares textual character sequences, which are identical (true).',
'• Always use .equals() or .equalsIgnoreCase() to compare string contents, never ==.
• For heavy string concatenation inside loops, use StringBuilder instead of String.',
'• Using == to compare String values in user input or newly instantiated strings.
• Assuming string methods mutate the original string (e.g. str.toUpperCase() does not change str unless reassigned).',
'1. Write a program to check whether a given string is a Palindrome.
2. Explain the difference between String, StringBuilder, and StringBuffer.',
TRUE
),
(
8, 8, 'Methods, Parameters, and Method Overloading', 1, 20,
'A method in Java is a collection of statements grouped together to perform a specific operation. Methods facilitate code reusability, modularity, and readability.',
'A method declaration specifies:
1. Access modifier (e.g., public, private).
2. Return type: The data type of the value returned (or void if none).
3. Method name: Follows camelCase naming convention.
4. Parameter list: Variables accepted by the method.
Method Overloading occurs when a class has multiple methods with the exact same name but differing parameter lists (different number of parameters or different parameter data types). Return type alone cannot overload a method.',
'returnType methodName(parameterType param1) {
    // method body
    return value;
}',
'class Main {
    // Method with parameters and return value
    static int calculateSquare(int num) {
        return num * num;
    }

    // Overloaded method: adds two integers
    static int add(int a, int b) {
        return a + b;
    }

    // Overloaded method: adds three integers
    static int add(int a, int b, int c) {
        return a + b + c;
    }

    public static void main(String[] args) {
        int sq = calculateSquare(6);
        System.out.println("Square of 6: " + sq);

        System.out.println("Sum of 2 numbers: " + add(10, 20));
        System.out.println("Sum of 3 numbers: " + add(10, 20, 30));
    }
}',
'Square of 6: 36
Sum of 2 numbers: 30
Sum of 3 numbers: 60',
'1. "static int calculateSquare(int num)": static allows calling without instantiating class inside main.
2. "return num * num": Returns calculated integer 36 to caller.
3. "add(10, 20)": Compiler resolves call to add(int, int) based on 2 integer arguments.
4. "add(10, 20, 30)": Compiler resolves call to add(int, int, int) based on 3 integer arguments.',
'• Java uses pass-by-value strictly for all parameter passing.
• Method overloading is resolved at compile time (Compile-time Polymorphism).',
'• Changing only the return type to overload a method causes a compile error.
• Forgetting return statement in non-void methods.',
'1. Write an overloaded method "area" to compute area of a circle, rectangle, and triangle.
2. Explain pass-by-value with an example swapping two variables.',
TRUE
),
(
9, 9, 'Classes, Objects, and Constructors', 1, 25,
'A class in Java is a user-defined blueprint or prototype from which objects are created. An object is a runtime instance of a class that possesses state (fields/attributes) and behavior (methods).',
'Constructors are special member methods called automatically when an object is instantiated with the "new" operator.
Rules for Constructors:
1. Constructor name must match the class name exactly.
2. Constructors have NO return type, not even void.
3. If no constructor is defined, Java provides a default no-argument constructor.
The "this" keyword refers to the current object instance and is commonly used to resolve shadowing between instance variables and parameter names.',
'class Student {
    String name;
    Student(String name) {
        this.name = name;
    }
}',
'class Student {
    String name;
    int rollNo;

    // Parameterized Constructor
    Student(String name, int rollNo) {
        this.name = name;
        this.rollNo = rollNo;
    }

    void displayInfo() {
        System.out.println("Student: " + this.name + ", Roll No: " + this.rollNo);
    }
}

class Main {
    public static void main(String[] args) {
        // Instantiating objects using constructor
        Student s1 = new Student("Rahul Sharma", 101);
        Student s2 = new Student("Priya Verma", 102);

        s1.displayInfo();
        s2.displayInfo();
    }
}',
'Student: Rahul Sharma, Roll No: 101
Student: Priya Verma, Roll No: 102',
'1. "class Student": Defines state (name, rollNo) and behavior (displayInfo).
2. "Student(String name, int rollNo)": Initializes instance variables using "this".
3. "Student s1 = new Student(...)": Allocates memory on heap, calls constructor, and stores reference in s1.
4. "s1.displayInfo()": Invokes method on s1 instance.',
'• The "new" keyword dynamically allocates memory on heap.
• If you write any custom constructor, the compiler does not generate the default constructor.',
'• Adding a return type to a constructor (e.g., void Student()), making it a regular method instead.
• Forgetting "this" when parameter names match instance variable names.',
'1. Create a "BankAccount" class with balance, deposit(), and withdraw() methods.
2. Explain constructor overloading with a code example.',
TRUE
),
(
10, 10, 'Object-Oriented Programming: Inheritance and Polymorphism', 1, 30,
'Object-Oriented Programming (OOP) in Java is built on four core pillars: Encapsulation, Inheritance, Polymorphism, and Abstraction.',
'1. Inheritance: Mechanism where one class acquires properties and behaviors of a parent class using the "extends" keyword. Facilitates code reuse.
2. Polymorphism: Ability of an object to take on many forms. Occurs when a parent class reference refers to a child class object (Dynamic Method Dispatch / Method Overriding).
3. Encapsulation: Wrapping data and methods into a single unit and restricting direct access using private fields with getters and setters.
4. Abstraction: Hiding implementation details and showing only functionality using abstract classes and interfaces.',
'class Child extends Parent {
    @Override
    void speak() {
        // overridden implementation
    }
}',
'// Base Parent Class
class Animal {
    void makeSound() {
        System.out.println("Animal makes a sound");
    }
}

// Derived Child Class
class Dog extends Animal {
    @Override
    void makeSound() {
        System.out.println("Dog barks: Woof! Woof!");
    }
}

class Main {
    public static void main(String[] args) {
        Animal myAnimal = new Animal();
        Animal myDog = new Dog(); // Polymorphic reference (Upcasting)

        myAnimal.makeSound();
        myDog.makeSound(); // Calls Dog\'s overridden method at runtime
    }
}',
'Animal makes a sound
Dog barks: Woof! Woof!',
'1. "class Dog extends Animal": Dog inherits methods and state from Animal.
2. "@Override void makeSound()": Overrides parent implementation with Dog-specific behavior.
3. "Animal myDog = new Dog()": Reference type is Animal, but actual object in heap is Dog.
4. "myDog.makeSound()": Java uses dynamic method dispatch to execute Dog\'s makeSound() method.',
'• Java does not support multiple inheritance with classes to avoid diamond ambiguity; use interfaces instead.
• Method overriding occurs at runtime (Runtime Polymorphism).
• The "super" keyword is used to access parent class constructors and methods.',
'• Declaring overridden method with weaker access privilege than parent method.
• Attempting to override static, private, or final methods (they cannot be overridden).',
'1. Write a program demonstrating an abstract class "Shape" with derived classes "Circle" and "Rectangle".
2. Explain the difference between method overloading and method overriding.',
TRUE
)
ON DUPLICATE KEY UPDATE title=VALUES(title);

-- 5. Insert Multiple-Choice Questions (At least 5 questions per lesson = 50 Questions)
INSERT INTO questions (id, lesson_id, question_text, option_a, option_b, option_c, option_d, correct_option, explanation, difficulty) VALUES
-- Lesson 1 MCQs
(1, 1, 'Which component of Java is responsible for executing bytecode?', 'JDK', 'JVM', 'JRE', 'Javac', 'B', 'The Java Virtual Machine (JVM) executes architecture-neutral bytecode (.class files).', 'EASY'),
(2, 1, 'What is the signature of the main method in a Java application?', 'public void main(String args[])', 'public static void main(String[] args)', 'static void main(String[] args)', 'public static int main(String[] args)', 'B', 'The required signature is public static void main(String[] args).', 'EASY'),
(3, 1, 'What is the file extension of compiled Java bytecode files?', '.java', '.bytecode', '.class', '.obj', 'C', 'The Java compiler produces .class files containing bytecode.', 'EASY'),
(4, 1, 'Which keyword makes a method accessible without instantiating its class?', 'void', 'public', 'static', 'final', 'C', 'static methods belong to the class rather than instances and can be invoked directly.', 'MEDIUM'),
(5, 1, 'What does the term WORA stand for in Java philosophy?', 'Write Once, Read Always', 'Write Once, Run Anywhere', 'Work Once, Repeat Anywhere', 'Wire Once, Run All', 'B', 'WORA stands for "Write Once, Run Anywhere", referring to platform independence of bytecode.', 'EASY'),

-- Lesson 2 MCQs
(6, 2, 'What is the size of the int primitive data type in Java?', '2 bytes', '4 bytes', '8 bytes', '16 bytes', 'B', 'In Java, an int always occupies 4 bytes (32 bits) in memory.', 'EASY'),
(7, 2, 'Which keyword is used to declare a constant variable in Java?', 'const', 'static', 'final', 'immutable', 'C', 'The "final" keyword prevents reassignment after initialization.', 'EASY'),
(8, 2, 'Why does a char in Java occupy 2 bytes instead of 1 byte?', 'To support ASCII', 'To support 16-bit Unicode characters', 'Because memory is cheap', 'To hold integers', 'B', 'Java uses the 16-bit Unicode character set (0 to 65535) for international language support.', 'MEDIUM'),
(9, 2, 'What is the default value of a local int variable declared inside a method?', '0', 'null', 'No default value (causes compile error)', 'Garbage value', 'C', 'Local variables in Java are not automatically initialized and cause compile error if read before initialization.', 'MEDIUM'),
(10, 2, 'Which of the following is a valid identifier name in Java?', '1stVariable', 'total_score', 'class', 'final-val', 'B', 'Identifiers cannot start with numbers, contain hyphens, or use reserved keywords like class.', 'EASY'),

-- Lesson 3 MCQs
(11, 3, 'What is the result of 17 % 4 in Java?', '4', '1', '4.25', '0', 'B', '17 divided by 4 equals 4 with a remainder of 1. % returns the remainder.', 'EASY'),
(12, 3, 'If int x = 5, what is the value of y = x++ and the subsequent value of x?', 'y=5, x=6', 'y=6, x=6', 'y=5, x=5', 'y=6, x=5', 'A', 'Post-increment returns the original value (5) to y, then increments x to 6.', 'MEDIUM'),
(13, 3, 'What is short-circuit evaluation in logical operators (&& and ||)?', 'Skipping compiler checks', 'Skipping second operand if first determines the outcome', 'Fast multiplication', 'Throwing arithmetic errors', 'B', 'If first operand of && is false, the second is not evaluated as result is guaranteed false.', 'MEDIUM'),
(14, 3, 'What is the result of integer division: 7 / 2?', '3.5', '3', '4', 'Compile error', 'B', 'Both operands are integers, so fractional part is truncated to 3.', 'EASY'),
(15, 3, 'Which operator has higher precedence between * and +?', '+ has higher', '* has higher', 'Both have equal precedence', 'Depends on JVM', 'B', 'Multiplication (*) has higher precedence than addition (+).', 'EASY'),

-- Lesson 4 MCQs
(16, 4, 'Which statement is used inside a switch block to prevent fallthrough?', 'exit', 'break', 'return', 'stop', 'B', 'The "break" statement terminates the switch block immediately.', 'EASY'),
(17, 4, 'Which of the following types CANNOT be used in a switch expression prior to Java 7?', 'int', 'char', 'String', 'byte', 'C', 'Strings were only introduced into switch statements starting in Java 7.', 'MEDIUM'),
(18, 4, 'What does the default case in a switch statement do?', 'Executes on every run', 'Executes when no case matches', 'Resets all variables', 'Throws an exception', 'B', 'default executes when none of the defined case values match the switch variable.', 'EASY'),
(19, 4, 'What is the output of: if (true) System.out.print("A"); else System.out.print("B");?', 'A', 'B', 'AB', 'Compile error', 'A', 'Condition is true, so only "A" is printed.', 'EASY'),
(20, 4, 'Can an if statement exist without an else clause?', 'No, else is mandatory', 'Yes, else is optional', 'Only in static methods', 'Only inside loops', 'B', 'An if statement can be used alone without an else branch.', 'EASY'),

-- Lesson 5 MCQs
(21, 5, 'Which loop guarantees execution of its body at least once?', 'for loop', 'while loop', 'do-while loop', 'enhanced for loop', 'C', 'do-while is exit-controlled, checking its condition after executing the body once.', 'EASY'),
(22, 5, 'What does the "continue" statement do inside a loop?', 'Terminates the loop', 'Skips remaining statements of current iteration and proceeds to next', 'Restarts loop from beginning', 'Exits method', 'B', 'continue skips the rest of the current iteration and jumps to the update/condition step.', 'MEDIUM'),
(23, 5, 'How many times will: for (int i = 0; i < 5; i++) execute?', '4 times', '5 times', '6 times', 'Infinite times', 'B', 'Iterates for i = 0, 1, 2, 3, 4 (total 5 iterations).', 'EASY'),
(24, 5, 'What happens if the loop update expression is omitted in a while loop?', 'Compiler error', 'Loop runs once', 'Loop can become infinite if condition remains true', 'Auto-increments by 1', 'C', 'Without an update expression, the condition never changes, creating an infinite loop.', 'EASY'),
(25, 5, 'What is the scope of a variable declared in the for-loop header: for (int i = 0; ...)?', 'Entire class', 'Entire method', 'Only inside the for-loop body', 'Global', 'C', 'Variables declared in for header are strictly local to that loop block.', 'MEDIUM'),

-- Lesson 6 MCQs
(26, 6, 'What is the index of the first element in a Java array?', '0', '1', '-1', 'Any user-defined index', 'A', 'Java arrays are strictly 0-indexed.', 'EASY'),
(27, 6, 'How do you obtain the number of elements in an array named "arr"?', 'arr.length()', 'arr.size()', 'arr.length', 'arr.count()', 'C', 'In Java, arrays have a public read-only field called "length".', 'EASY'),
(28, 6, 'What exception is thrown when accessing arr[5] on an array of length 5?', 'NullPointerException', 'ArrayIndexOutOfBoundsException', 'ArrayOverflowException', 'InvalidIndexException', 'B', 'Valid indices for length 5 are 0 through 4; index 5 causes ArrayIndexOutOfBoundsException.', 'MEDIUM'),
(29, 6, 'Where are array objects stored in Java memory architecture?', 'Stack memory', 'Heap memory', 'Metaspace', 'CPU Registers', 'B', 'All arrays in Java are dynamic objects allocated on the Heap memory.', 'MEDIUM'),
(30, 6, 'What is the default value of elements in a newly created boolean[5] array?', 'true', 'false', 'null', '0', 'B', 'Boolean array elements default to false.', 'EASY'),

-- Lesson 7 MCQs
(31, 7, 'Why is String immutable in Java?', 'For security, caching in StringPool, and thread safety', 'Because Java has no pointers', 'To save disk space', 'It is a primitive type', 'A', 'Immutability ensures security in classloading/networking, string pool reuse, and multithreading safety.', 'HARD'),
(32, 7, 'How should you compare the textual contents of two String variables?', 'str1 == str2', 'str1.equals(str2)', 'str1 = str2', 'str1.compareTo(str2) == 1', 'B', 'The .equals() method compares the sequence of characters inside the String objects.', 'EASY'),
(33, 7, 'What does the method "CodeQuest".substring(0, 4) return?', 'Code', 'CodeQ', 'ode', 'odeQ', 'A', 'substring(begin, end) is inclusive of begin (0) and exclusive of end (4), returning indices 0,1,2,3 ("Code").', 'MEDIUM'),
(34, 7, 'Which class is mutable and synchronized for string manipulation?', 'String', 'StringBuilder', 'StringBuffer', 'StringTokenizer', 'C', 'StringBuffer is mutable and thread-safe (synchronized). StringBuilder is not synchronized.', 'MEDIUM'),
(35, 7, 'What happens when two String literals are created with the same text "Java"?', 'Two distinct objects created on heap', 'Both point to the same pooled instance in String Constant Pool', 'Causes duplicate identifier error', 'Compiler merges them into one byte', 'B', 'String literals are cached in the String Constant Pool to conserve memory.', 'MEDIUM'),

-- Lesson 8 MCQs
(36, 8, 'Can a method be overloaded simply by changing its return type?', 'Yes, always', 'No, parameter list must differ', 'Only if static', 'Only in abstract classes', 'B', 'Method signature consists of method name and parameter types; return type alone is not sufficient.', 'MEDIUM'),
(37, 8, 'What is the parameter passing mechanism in Java?', 'Pass by reference for objects, pass by value for primitives', 'Pass by value for everything', 'Pass by pointer', 'Pass by name', 'B', 'Java is strictly pass-by-value. For objects, the copy of the reference is passed by value.', 'HARD'),
(38, 8, 'What return type is used when a method does not return any value?', 'null', 'empty', 'void', 'zero', 'C', 'The "void" keyword indicates that the method produces no return value.', 'EASY'),
(39, 8, 'What is method signature in Java?', 'Method name + parameter types list', 'Return type + method name', 'Access modifier + method name', 'Class name + method name', 'A', 'In Java, the method signature is the method name and the sequence of parameter types.', 'MEDIUM'),
(40, 8, 'When does method overloading resolution occur?', 'Runtime', 'Compile-time', 'JVM startup', 'Deployment time', 'B', 'Overloaded methods are resolved during compilation based on the static arguments passed.', 'MEDIUM'),

-- Lesson 9 MCQs
(41, 9, 'What is the primary role of a constructor in Java?', 'To destroy unused memory', 'To initialize state of newly created objects', 'To override methods', 'To print object address', 'B', 'Constructors are executed when an object is instantiated to initialize its instance variables.', 'EASY'),
(42, 9, 'What is the return type of a constructor?', 'void', 'int', 'Class type', 'Constructors do not have a return type', 'D', 'Constructors have no return type, not even void.', 'EASY'),
(43, 9, 'What does the "this" keyword refer to inside an instance method?', 'The parent class', 'The current class blueprint', 'The current object instance', 'The JVM instance', 'C', '"this" is a reference variable that refers to the current object whose method is being executed.', 'MEDIUM'),
(44, 9, 'What happens if a programmer does not write any constructor in a class?', 'Compile error', 'Compiler provides a default no-argument constructor', 'Object cannot be created', 'JVM crashes', 'B', 'Java compiler automatically inserts a default no-arg constructor if no constructor is defined.', 'EASY'),
(45, 9, 'Can constructors be private in Java?', 'No, they must be public', 'Yes, commonly used in Singleton design patterns', 'Only in abstract classes', 'Only in interfaces', 'B', 'Private constructors prevent instantiation from outside, used in Singletons and utility classes.', 'HARD'),

-- Lesson 10 MCQs
(46, 10, 'Which keyword is used by a class to inherit from another class in Java?', 'implements', 'extends', 'inherits', 'super', 'B', 'The "extends" keyword establishes class inheritance in Java.', 'EASY'),
(47, 10, 'Can a class in Java inherit from more than one superclass directly?', 'Yes, multiple inheritance is allowed', 'No, Java does not support multiple class inheritance', 'Yes, using super keyword', 'Only if classes are abstract', 'B', 'Java avoids the diamond problem by disallowing multiple class inheritance. Interfaces are used instead.', 'MEDIUM'),
(48, 10, 'What is Dynamic Method Dispatch in Java polymorphism?', 'Calling methods via reflection', 'Resolving an overridden method at runtime based on the actual object', 'Compiling methods on the fly', 'Overloading methods in runtime', 'B', 'Dynamic method dispatch is the mechanism by which a call to an overridden method is resolved at runtime.', 'HARD'),
(49, 10, 'Which keyword refers to the direct superclass from a child class?', 'parent', 'base', 'super', 'this', 'C', 'The "super" keyword is used to access superclass constructors, methods, and fields.', 'EASY'),
(50, 10, 'What annotation should be used when overriding a superclass method?', '@Overload', '@Override', '@Inherits', '@Polymorphic', 'B', '@Override informs the compiler to verify that the method correctly overrides a parent method.', 'EASY')
ON DUPLICATE KEY UPDATE question_text=VALUES(question_text);

-- 6. Insert Badges Catalog
INSERT INTO badges (id, code, title, description, icon_name, xp_bonus) VALUES
(1, 'FIRST_STEP', 'First Step', 'Completed your very first Java lesson on CodeQuest.', 'award', 50),
(2, 'QUIZ_STARTER', 'Quiz Starter', 'Passed your first topic-based multiple choice quiz.', 'check-circle', 75),
(3, 'PERFECT_SCORE', 'Perfect Score', 'Achieved 100% on any topic quiz.', 'star', 100),
(4, 'THREE_DAY_STREAK', '3-Day Streak', 'Maintained a daily learning streak for 3 consecutive days.', 'flame', 150),
(5, 'SEVEN_DAY_STREAK', '7-Day Streak', 'Committed to Java learning for 7 consecutive days.', 'zap', 300),
(6, 'MODULE_MASTER', 'Module Master', 'Completed all lessons in a full syllabus module.', 'book-open', 250),
(7, 'CHALLENGE_CHAMPION', 'Challenge Champion', 'Won an asynchronous head-to-head quiz challenge against a friend.', 'trophy', 200),
(8, 'CONSISTENT_LEARNER', 'Consistent Learner', 'Accumulated 10 total active learning days.', 'calendar', 250),
(9, 'JAVA_EXPLORER', 'Java Explorer', 'Completed 5 foundational Java lessons.', 'compass', 300)
ON DUPLICATE KEY UPDATE title=VALUES(title);

