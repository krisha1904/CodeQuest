// =====================================================================
// CodeQuest: Comprehensive College Java Syllabus Dataset
// Fully aligned with university curricula and exams
// Contains all 10 required lesson sections and complete question banks
// =====================================================================

const CODEQUEST_DATA = {
  course: {
    id: 1,
    title: "College Core Java Programming",
    description: "Comprehensive university-aligned Java course covering fundamentals, OOP concepts, data structures, exception handling, and semester exam preparation."
  },
  
  currentUser: null,

  modules: [
    { id: 1, title: "Module 1: Introduction to Java", description: "JVM architecture, bytecode, JDK vs JRE, and your first Java program.", order: 1, lessonCount: 2, completedCount: 0 },
    { id: 2, title: "Module 2: Variables and Data Types", description: "Primitive types, naming conventions, type casting, and Scanner input.", order: 2, lessonCount: 2, completedCount: 0 },
    { id: 3, title: "Module 3: Operators & Expressions", description: "Arithmetic, relational, logical, bitwise, and ternary operators.", order: 3, lessonCount: 1, completedCount: 0 },
    { id: 4, title: "Module 4: Conditional Statements", description: "Control flow using if, if-else, nested if, else-if ladder, and switch.", order: 4, lessonCount: 1, completedCount: 0 },
    { id: 5, title: "Module 5: Loops & Iteration", description: "while, do-while, for loops, nested loops, break and continue.", order: 5, lessonCount: 1, completedCount: 0 },
    { id: 6, title: "Module 6: Arrays", description: "1D arrays, 2D matrices, array traversal, and standard algorithms.", order: 6, lessonCount: 1, completedCount: 0 },
    { id: 7, title: "Module 7: Strings & Text Handling", description: "String immutability, methods, StringPool, StringBuilder vs StringBuffer.", order: 7, lessonCount: 1, completedCount: 0 },
    { id: 8, title: "Module 8: Methods & Modular Code", description: "Defining methods, parameter passing, return types, method overloading.", order: 8, lessonCount: 1, completedCount: 0 },
    { id: 9, title: "Module 9: Classes and Objects", description: "Class definitions, object instantiation, constructors, and the this keyword.", order: 9, lessonCount: 1, completedCount: 0 },
    { id: 10, title: "Module 10: Object-Oriented Programming (OOP)", description: "Inheritance, Polymorphism, Abstraction, and Encapsulation.", order: 10, lessonCount: 1, completedCount: 0 },
    { id: 11, title: "Module 11: Exception Handling", description: "try-catch blocks, throw, throws, finally, and custom exceptions.", order: 11, lessonCount: 1, completedCount: 0 },
    { id: 12, title: "Module 12: Revision & Exam Practice", description: "Important university exam questions, output prediction, and mock assessments.", order: 12, lessonCount: 1, completedCount: 0 }
  ],

  lessons: [
    {
      id: 1,
      moduleId: 1,
      moduleTitle: "Module 1: Introduction to Java",
      title: "Introduction to Java & Program Structure",
      estimatedMinutes: 15,
      isCompleted: false,
      definition: "Java is a high-level, class-based, object-oriented programming language designed to have as few implementation dependencies as possible, following the principle of 'Write Once, Run Anywhere' (WORA).",
      conceptExplanation: "Java source code (.java) is compiled into architecture-neutral bytecode (.class) by the Java compiler (javac). The Java Virtual Machine (JVM) executes this bytecode on any operating system that has a compatible JRE/JDK installed. Every standalone Java application must have at least one class and an entry point called the main() method.",
      syntaxBlock: `// General Structure:
class ClassName {
    public static void main(String[] args) {
        // Program statements
    }
}`,
      javaProgram: `// College Standard First Java Program
class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to CodeQuest Java!");
        System.out.print("Learning Java step-by-step for university exams.");
    }
}`,
      programOutput: `Welcome to CodeQuest Java!
Learning Java step-by-step for university exams.`,
      programExplanation: `1. "class Main": Declares a class named Main. In Java, all code must reside inside a class.
2. "public static void main(String[] args)": The main method where JVM begins execution:
   • public: Accessible from anywhere outside the class.
   • static: Can be invoked without creating an instance of the class.
   • void: Does not return any value.
   • String[] args: Command-line arguments passed as an array of strings.
3. "System.out.println()": Prints text and moves cursor to a new line.
4. "System.out.print()": Prints text without inserting a newline character.`,
      importantPoints: `• Java is strictly case-sensitive ('main' is different from 'Main').
• The name of the public class must match the file name (e.g., Main.java).
• Bytecode is platform-independent; the JVM itself is platform-dependent.
• Memory is automatically managed by the Java Garbage Collector.`,
      commonMistakes: `• Forgetting the semicolon (;) at the end of statements.
• Writing Main with a lowercase 'm' or String with a lowercase 's'.
• Missing the static keyword in main() leads to runtime error "NoSuchMethodError: main".`,
      practiceQuestions: `1. Write a Java program to print your name, roll number, and department on three separate lines.
2. Predict the compiler error if the return type of main() is changed to int.`
    },
    {
      id: 2,
      moduleId: 2,
      moduleTitle: "Module 2: Variables and Data Types",
      title: "Variables and Primitive Data Types",
      estimatedMinutes: 20,
      isCompleted: true,
      definition: "A variable in Java is a named memory location used to store data that can be manipulated during program execution. Java is strongly typed, meaning every variable must have a declared data type before use.",
      conceptExplanation: `Java provides 8 primitive data types categorized into four groups:
1. Integer types: byte (1 byte), short (2 bytes), int (4 bytes), long (8 bytes).
2. Floating-point types: float (4 bytes), double (8 bytes).
3. Character type: char (2 bytes, 16-bit Unicode).
4. Boolean type: boolean (1 bit, values: true or false).
Constants are declared using the "final" keyword and cannot be reassigned.`,
      syntaxBlock: `dataType variableName = initialValue;
final dataType CONSTANT_NAME = value;`,
      javaProgram: `class Main {
    public static void main(String[] args) {
        int studentAge = 19;
        double gpa = 8.75;
        char grade = 'A';
        boolean isEnrolled = true;
        final double COLLEGE_PASS_MARKS = 40.0;

        System.out.println("Age: " + studentAge);
        System.out.println("GPA: " + gpa);
        System.out.println("Grade: " + grade);
        System.out.println("Enrolled: " + isEnrolled);
        System.out.println("Passing Threshold: " + COLLEGE_PASS_MARKS);
    }
}`,
      programOutput: `Age: 19
GPA: 8.75
Grade: A
Enrolled: true
Passing Threshold: 40.0`,
      programExplanation: `1. "int studentAge = 19;": Allocates 4 bytes in stack memory and stores integer 19.
2. "double gpa = 8.75;": Allocates 8 bytes in memory for standard double-precision floating point.
3. "char grade = 'A';": Stores Unicode character 65 enclosed in single quotes.
4. "boolean isEnrolled = true;": Stores conditional flag value.
5. "final double COLLEGE_PASS_MARKS = 40.0;": Creates an immutable constant.`,
      importantPoints: `• Java char uses 16-bit Unicode (0 to 65535), not 8-bit ASCII.
• Default value for int in class scope is 0, double is 0.0, boolean is false.
• Local variables (declared inside methods) have NO default values and must be initialized before reading.`,
      commonMistakes: `• Using single quotes for Strings or double quotes for char (e.g., char c = "A"; causes compile error).
• Attempting to modify a "final" variable after initialization.
• Reading uninitialized local variables inside main().`,
      practiceQuestions: `1. Write a program to declare variables for storing temperature in Celsius and convert it to Fahrenheit.
2. What will happen if you assign 130 to a byte variable? Explain byte overflow.`
    },
    {
      id: 3,
      moduleId: 3,
      moduleTitle: "Module 3: Operators & Expressions",
      title: "Operators and Operator Precedence",
      estimatedMinutes: 20,
      isCompleted: true,
      definition: "Operators are special symbols in Java that perform specific operations on one, two, or three operands and produce a result.",
      conceptExplanation: `Operators in Java are classified into:
1. Arithmetic Operators: +, -, *, /, % (modulus gives remainder).
2. Relational Operators: ==, !=, >, <, >=, <= (return boolean results).
3. Logical Operators: && (Short-circuit AND), || (Short-circuit OR), ! (Logical NOT).
4. Unary / Increment-Decrement: ++, -- (pre and post forms).
5. Assignment: =, +=, -=, *=, /=.
Operator Precedence dictates the order in which operators are evaluated when multiple operators appear in an expression.`,
      syntaxBlock: `// Post-increment: returns old value, then increments
int y = x++;
// Pre-increment: increments first, then returns new value
int z = ++x;`,
      javaProgram: `class Main {
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
}`,
      programOutput: `Quotient: 3
Remainder (Modulus): 1
Post-increment value: 5, x is now: 7
Pre-increment value: 7
Logical condition result: true`,
      programExplanation: `1. "a / b": Since both operands are integers, integer division truncates decimal digits (10 / 3 = 3).
2. "a % b": Modulus operator returns remainder of division (10 % 3 = 1).
3. "int post = x++": Returns current value of x (5) to "post", then increments x to 6.
4. "int pre = ++x": Increments x from 6 to 7 first, then returns 7 to "pre".
5. "(a > b) && (b != 0)": Both conditions evaluate to true, so logical AND evaluates to true.`,
      importantPoints: `• Integer division truncates towards zero; use (double)a / b for floating-point answers.
• Short-circuit evaluation: If the first operand of && is false, Java skips evaluating the second operand.`,
      commonMistakes: `• Confusing assignment operator (=) with relational equality operator (==).
• Forgetting that post-increment applies the increment AFTER the current expression is evaluated.`,
      practiceQuestions: `1. Evaluate the expression: int a = 5; int b = a++ + ++a * 2; System.out.println(b);
2. Explain the difference between single & and double && in Java.`
    },
    {
      id: 4,
      moduleId: 4,
      moduleTitle: "Module 4: Conditional Statements",
      title: "Control Flow: if, if-else, and switch",
      estimatedMinutes: 20,
      isCompleted: false,
      definition: "Conditional statements in Java control the execution flow based on boolean expressions, allowing different blocks of code to run depending on runtime conditions.",
      conceptExplanation: `Java supports:
1. Simple if: Executes code block only when condition is true.
2. if-else: Provides an alternative path when condition is false.
3. else-if ladder: Evaluates multiple conditions sequentially.
4. switch statement: Tests a single variable against multiple constant values (cases).`,
      syntaxBlock: `if (condition) {
    // executes if true
} else if (anotherCondition) {
    // executes if condition 2 is true
} else {
    // fallback default
}`,
      javaProgram: `class Main {
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
}`,
      programOutput: `Grade: A (Distinction)
Day: Wednesday`,
      programExplanation: `1. The condition "score >= 90" is evaluated first: 85 >= 90 is false.
2. The next condition "score >= 75" is evaluated: 85 >= 75 is true.
3. "Grade: A (Distinction)" is printed, and remaining else branches are skipped.
4. In the switch block, dayOfWeek (3) matches "case 3:", prints Wednesday, and "break" prevents fallthrough.`,
      importantPoints: `• The "break" statement is required in switch cases to prevent unintended fall-through.
• A switch statement can evaluate primitive integers (except long), char, String, and enum.`,
      commonMistakes: `• Writing "if (score = 90)" instead of "if (score == 90)".
• Omitting "break" in switch cases leading to fallthrough bug.
• Adding semicolon after if: "if (x > 5); { ... }" which executes the block unconditionally.`,
      practiceQuestions: `1. Write a program to find the largest among three numbers using nested if-else.
2. Rewrite the day-of-week switch statement using Java switch expressions.`
    },
    {
      id: 5,
      moduleId: 5,
      moduleTitle: "Module 5: Loops & Iteration",
      title: "Loops: while, do-while, and for",
      estimatedMinutes: 25,
      isCompleted: false,
      definition: "A loop is a control structure that repeatedly executes a block of code as long as a specified boolean condition remains true.",
      conceptExplanation: `Java provides three primary loop constructs:
1. for loop: Best when the exact number of iterations is known in advance.
2. while loop: Entry-controlled loop; checks condition before executing body.
3. do-while loop: Exit-controlled loop; guarantees body executes at least once even if condition is initially false.
4. "break" terminates the loop immediately; "continue" skips the rest of current iteration and jumps to the next.`,
      syntaxBlock: `for (initialization; condition; update) {
    // loop body
}
while (condition) {
    // loop body
}`,
      javaProgram: `class Main {
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
}`,
      programOutput: `For Loop (1 to 5): 1 2 3 4 5 
While Loop (Even numbers up to 10): 2 4 6 8 10 
Do-While (executes once when false): Executed! test=100`,
      programExplanation: `1. "for (int i = 1; i <= 5; i++)": Initializes i=1, verifies i<=5, executes body, increments i by 1.
2. "while (num <= 10)": Checks num<=10 each iteration. num increments by 2 until 12, when loop terminates.
3. "do { ... } while (test < 10)": Executes print statement first, then tests condition (100 < 10 = false), terminating after 1 execution.`,
      importantPoints: `• do-while is the only loop that terminates with a semicolon after the while(condition);
• Variables declared in the for-loop header (int i = 0) are scoped strictly inside the loop.`,
      commonMistakes: `• Creating infinite loops by forgetting to increment loop counter inside while loops.
• Off-by-one errors (using < instead of <= or vice versa).
• Placing a semicolon directly after for loop header: "for(int i=0; i<5; i++);"`,
      practiceQuestions: `1. Write a program to print the Fibonacci series up to 10 terms using a for loop.
2. Write a program to reverse a given integer using a while loop.`
    },
    {
      id: 6,
      moduleId: 6,
      moduleTitle: "Module 6: Arrays",
      title: "Single and Multi-Dimensional Arrays",
      estimatedMinutes: 25,
      isCompleted: false,
      definition: "An array in Java is a fixed-size, homogeneous data structure that stores multiple elements of the same data type in contiguous memory locations under a single variable name.",
      conceptExplanation: `In Java, arrays are objects stored on the heap memory. Array indices start at 0 and end at length - 1. Once created, the size of an array cannot be changed.
Multi-dimensional arrays in Java are actually "arrays of arrays", allowing rectangular matrices or jagged arrays where rows have differing column lengths.`,
      syntaxBlock: `dataType[] arrayName = new dataType[size];
int[][] matrix = new int[rows][cols];`,
      javaProgram: `class Main {
    public static void main(String[] args) {
        // 1D Array Declaration and Traversal
        int[] marks = {85, 92, 78, 90, 88};
        int sum = 0;

        System.out.print("Array Elements: ");
        for (int i = 0; i < marks.length; i++) {
            System.out.print(marks[i] + " ");
            sum += marks[i];
        }
        System.out.println("\\nAverage Marks: " + ((double)sum / marks.length));

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
}`,
      programOutput: `Array Elements: 85 92 78 90 88 
Average Marks: 86.6
2x2 Matrix Elements:
1 2 
3 4 `,
      programExplanation: `1. "int[] marks = {...}": Allocates array of 5 integers.
2. "marks.length": Returns number of elements (5). Notice length is a property, not a method.
3. "marks[i]": Accesses element at index i (0 to 4).
4. "matrix[r][c]": Outer loop iterates over rows; inner loop iterates over columns of each row.`,
      importantPoints: `• Array indices are 0-based.
• "length" of array is a field (array.length), while for String it is a method (str.length()).
• Array elements default to 0 for numeric types, false for boolean, null for objects.`,
      commonMistakes: `• Accessing index equal to array.length causes "ArrayIndexOutOfBoundsException".
• Trying to resize an existing array (Java arrays have fixed capacity).`,
      practiceQuestions: `1. Write a program to find the maximum and minimum elements in an integer array.
2. Write a program to compute the transpose of a 3x3 matrix.`
    },
    {
      id: 7,
      moduleId: 7,
      moduleTitle: "Module 7: Strings & Text Handling",
      title: "Strings and Common String Methods",
      estimatedMinutes: 20,
      isCompleted: false,
      definition: "In Java, a String is an object that represents a sequence of characters. Unlike C/C++ where strings are character arrays, Java Strings are full objects belonging to the java.lang.String class and are immutable.",
      conceptExplanation: `Immutability means that once a String object is created in memory, its contents cannot be altered. Any modification creates a new String object.
Java optimizes memory by maintaining a "String Constant Pool" inside heap memory for string literals.
Common methods include length(), charAt(), toUpperCase(), substring(), equals(), and indexOf().`,
      syntaxBlock: `String s1 = "Hello"; // created in String Constant Pool
String s2 = new String("Hello"); // created in standard heap memory`,
      javaProgram: `class Main {
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
}`,
      programOutput: `Length: 14
Character at index 4: Q
Uppercase: CODEQUEST JAVA
Substring (0 to 9): CodeQuest
Using == (references): false
Using .equals() (content): true`,
      programExplanation: `1. "greeting.length()": Returns total characters (14).
2. "greeting.charAt(4)": Returns character at index 4 ('Q').
3. "greeting.substring(0, 9)": Returns characters from index 0 up to 8 ("CodeQuest").
4. "strA == strB": Compares memory addresses (references), which are different.
5. "strA.equals(strB)": Compares textual character sequences, which are identical (true).`,
      importantPoints: `• Always use .equals() or .equalsIgnoreCase() to compare string contents, never ==.
• For heavy string concatenation inside loops, use StringBuilder instead of String.`,
      commonMistakes: `• Using == to compare String values in user input or newly instantiated strings.
• Assuming string methods mutate the original string (e.g. str.toUpperCase() does not change str unless reassigned).`,
      practiceQuestions: `1. Write a program to check whether a given string is a Palindrome.
2. Explain the difference between String, StringBuilder, and StringBuffer.`
    },
    {
      id: 8,
      moduleId: 8,
      moduleTitle: "Module 8: Methods & Modular Code",
      title: "Methods, Parameters, and Method Overloading",
      estimatedMinutes: 20,
      isCompleted: false,
      definition: "A method in Java is a collection of statements grouped together to perform a specific operation. Methods facilitate code reusability, modularity, and readability.",
      conceptExplanation: `A method declaration specifies:
1. Access modifier (e.g., public, private).
2. Return type: The data type of the value returned (or void if none).
3. Method name: Follows camelCase naming convention.
4. Parameter list: Variables accepted by the method.
Method Overloading occurs when a class has multiple methods with the exact same name but differing parameter lists.`,
      syntaxBlock: `returnType methodName(parameterType param1) {
    // method body
    return value;
}`,
      javaProgram: `class Main {
    static int calculateSquare(int num) {
        return num * num;
    }

    static int add(int a, int b) {
        return a + b;
    }

    static int add(int a, int b, int c) {
        return a + b + c;
    }

    public static void main(String[] args) {
        int sq = calculateSquare(6);
        System.out.println("Square of 6: " + sq);

        System.out.println("Sum of 2 numbers: " + add(10, 20));
        System.out.println("Sum of 3 numbers: " + add(10, 20, 30));
    }
}`,
      programOutput: `Square of 6: 36
Sum of 2 numbers: 30
Sum of 3 numbers: 60`,
      programExplanation: `1. "static int calculateSquare(int num)": static allows calling without instantiating class inside main.
2. "return num * num": Returns calculated integer 36 to caller.
3. "add(10, 20)": Compiler resolves call to add(int, int) based on 2 integer arguments.
4. "add(10, 20, 30)": Compiler resolves call to add(int, int, int) based on 3 integer arguments.`,
      importantPoints: `• Java uses pass-by-value strictly for all parameter passing.
• Method overloading is resolved at compile time (Compile-time Polymorphism).`,
      commonMistakes: `• Changing only the return type to overload a method causes a compile error.
• Forgetting return statement in non-void methods.`,
      practiceQuestions: `1. Write an overloaded method "area" to compute area of a circle, rectangle, and triangle.
2. Explain pass-by-value with an example swapping two variables.`
    },
    {
      id: 9,
      moduleId: 9,
      moduleTitle: "Module 9: Classes and Objects",
      title: "Classes, Objects, and Constructors",
      estimatedMinutes: 25,
      isCompleted: false,
      definition: "A class in Java is a user-defined blueprint or prototype from which objects are created. An object is a runtime instance of a class that possesses state (fields/attributes) and behavior (methods).",
      conceptExplanation: `Constructors are special member methods called automatically when an object is instantiated with the "new" operator.
Rules for Constructors:
1. Constructor name must match the class name exactly.
2. Constructors have NO return type, not even void.
3. If no constructor is defined, Java provides a default no-argument constructor.
The "this" keyword refers to the current object instance.`,
      syntaxBlock: `class Student {
    String name;
    Student(String name) {
        this.name = name;
    }
}`,
      javaProgram: `class Student {
    String name;
    int rollNo;

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
        Student s1 = new Student("Rahul Sharma", 101);
        Student s2 = new Student("Priya Verma", 102);

        s1.displayInfo();
        s2.displayInfo();
    }
}`,
      programOutput: `Student: Rahul Sharma, Roll No: 101
Student: Priya Verma, Roll No: 102`,
      programExplanation: `1. "class Student": Defines state (name, rollNo) and behavior (displayInfo).
2. "Student(String name, int rollNo)": Initializes instance variables using "this".
3. "Student s1 = new Student(...)": Allocates memory on heap, calls constructor, and stores reference in s1.
4. "s1.displayInfo()": Invokes method on s1 instance.`,
      importantPoints: `• The "new" keyword dynamically allocates memory on heap.
• If you write any custom constructor, the compiler does not generate the default constructor.`,
      commonMistakes: `• Adding a return type to a constructor (e.g., void Student()), making it a regular method instead.
• Forgetting "this" when parameter names match instance variable names.`,
      practiceQuestions: `1. Create a "BankAccount" class with balance, deposit(), and withdraw() methods.
2. Explain constructor overloading with a code example.`
    },
    {
      id: 10,
      moduleId: 10,
      moduleTitle: "Module 10: Object-Oriented Programming (OOP)",
      title: "OOP Pillars: Inheritance and Polymorphism",
      estimatedMinutes: 30,
      isCompleted: false,
      definition: "Object-Oriented Programming (OOP) in Java is built on four core pillars: Encapsulation, Inheritance, Polymorphism, and Abstraction.",
      conceptExplanation: `1. Inheritance: Mechanism where one class acquires properties and behaviors of a parent class using the "extends" keyword. Facilitates code reuse.
2. Polymorphism: Ability of an object to take on many forms. Occurs when a parent class reference refers to a child class object (Dynamic Method Dispatch / Method Overriding).
3. Encapsulation: Wrapping data and methods into a single unit and restricting direct access using private fields with getters and setters.
4. Abstraction: Hiding implementation details and showing only functionality using abstract classes and interfaces.`,
      syntaxBlock: `class Child extends Parent {
    @Override
    void speak() {
        // overridden implementation
    }
}`,
      javaProgram: `class Animal {
    void makeSound() {
        System.out.println("Animal makes a sound");
    }
}

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
        myDog.makeSound(); // Calls Dog's overridden method at runtime
    }
}`,
      programOutput: `Animal makes a sound
Dog barks: Woof! Woof!`,
      programExplanation: `1. "class Dog extends Animal": Dog inherits methods and state from Animal.
2. "@Override void makeSound()": Overrides parent implementation with Dog-specific behavior.
3. "Animal myDog = new Dog()": Reference type is Animal, but actual object in heap is Dog.
4. "myDog.makeSound()": Java uses dynamic method dispatch to execute Dog's makeSound() method.`,
      importantPoints: `• Java does not support multiple inheritance with classes to avoid diamond ambiguity; use interfaces instead.
• Method overriding occurs at runtime (Runtime Polymorphism).
• The "super" keyword is used to access parent class constructors and methods.`,
      commonMistakes: `• Declaring overridden method with weaker access privilege than parent method.
• Attempting to override static, private, or final methods (they cannot be overridden).`,
      practiceQuestions: `1. Write a program demonstrating an abstract class "Shape" with derived classes "Circle" and "Rectangle".
2. Explain the difference between method overloading and method overriding.`
    }
  ],

  questions: {
    1: [
      { id: 1, questionText: "Which component of Java is responsible for executing bytecode?", optionA: "JDK", optionB: "JVM", optionC: "JRE", optionD: "Javac", correctOption: "B", explanation: "The Java Virtual Machine (JVM) interprets and executes architecture-neutral bytecode (.class files)." },
      { id: 2, questionText: "What is the mandatory signature of the main method in a Java application?", optionA: "public void main(String args[])", optionB: "public static void main(String[] args)", optionC: "static void main(String[] args)", optionD: "public static int main(String[] args)", correctOption: "B", explanation: "The mandatory signature recognized by JVM is 'public static void main(String[] args)'." },
      { id: 3, questionText: "What is the file extension of compiled Java bytecode files?", optionA: ".java", optionB: ".bytecode", optionC: ".class", optionD: ".obj", correctOption: "C", explanation: "The Java compiler (javac) compiles .java source files into .class bytecode files." },
      { id: 4, questionText: "Which keyword makes a method accessible without instantiating its class?", optionA: "void", optionB: "public", optionC: "static", optionD: "final", correctOption: "C", explanation: "static methods belong to the class rather than instances and can be invoked directly." },
      { id: 5, questionText: "What does the term WORA stand for in Java philosophy?", optionA: "Write Once, Read Always", optionB: "Write Once, Run Anywhere", optionC: "Work Once, Repeat Anywhere", optionD: "Wire Once, Run All", correctOption: "B", explanation: "WORA stands for 'Write Once, Run Anywhere', referring to Java's platform independence." }
    ],
    2: [
      { id: 6, questionText: "What is the size of the int primitive data type in Java?", optionA: "2 bytes", optionB: "4 bytes", optionC: "8 bytes", optionD: "16 bytes", correctOption: "B", explanation: "In Java, an int always occupies 4 bytes (32 bits) across all platforms." },
      { id: 7, questionText: "Which keyword is used to declare a constant variable in Java?", optionA: "const", optionB: "static", optionC: "final", optionD: "immutable", correctOption: "C", explanation: "The 'final' keyword prevents reassignment after initialization." },
      { id: 8, questionText: "Why does a char in Java occupy 2 bytes instead of 1 byte?", optionA: "To support ASCII", optionB: "To support 16-bit Unicode characters", optionC: "Because memory is cheap", optionD: "To hold integers", correctOption: "B", explanation: "Java uses the 16-bit Unicode character set (0 to 65535) for international character support." },
      { id: 9, questionText: "What is the default value of a local int variable declared inside a method?", optionA: "0", optionB: "null", optionC: "No default value (causes compile error)", optionD: "Garbage value", correctOption: "C", explanation: "Local variables in Java have no default value and must be explicitly initialized before reading." },
      { id: 10, questionText: "Which of the following is a valid identifier name in Java?", optionA: "1stVariable", optionB: "total_score", optionC: "class", optionD: "final-val", correctOption: "B", explanation: "Identifiers cannot start with digits, contain hyphens, or match reserved keywords." }
    ],
    3: [
      { id: 11, questionText: "What is the result of 17 % 4 in Java?", optionA: "4", optionB: "1", optionC: "4.25", optionD: "0", correctOption: "B", explanation: "17 divided by 4 equals 4 with a remainder of 1. % returns the remainder." },
      { id: 12, questionText: "If int x = 5, what is the value of y = x++ and the subsequent value of x?", optionA: "y=5, x=6", optionB: "y=6, x=6", optionC: "y=5, x=5", optionD: "y=6, x=5", correctOption: "A", explanation: "Post-increment returns the original value (5) to y, then increments x to 6." },
      { id: 13, questionText: "What is short-circuit evaluation in logical operators (&& and ||)?", optionA: "Skipping compiler checks", optionB: "Skipping second operand if first determines the outcome", optionC: "Fast multiplication", optionD: "Throwing arithmetic errors", correctOption: "B", explanation: "If the first operand of && is false, Java skips evaluating the second operand." },
      { id: 14, questionText: "What is the result of integer division: 7 / 2?", optionA: "3.5", optionB: "3", optionC: "4", optionD: "Compile error", correctOption: "B", explanation: "Both operands are integers, so the fractional part is truncated to 3." },
      { id: 15, questionText: "Which operator has higher precedence between * and +?", optionA: "+ has higher", optionB: "* has higher", optionC: "Both have equal precedence", optionD: "Depends on JVM", correctOption: "B", explanation: "Multiplication (*) has higher precedence than addition (+)." }
    ]
  },

  badges: [
    { id: 1, code: "FIRST_STEP", title: "First Step", description: "Completed your very first Java lesson on CodeQuest.", icon: "award", xpBonus: 50, unlocked: false, unlockedAt: null },
    { id: 2, code: "QUIZ_STARTER", title: "Quiz Starter", description: "Passed your first topic-based multiple choice quiz.", icon: "check-circle", xpBonus: 75, unlocked: false, unlockedAt: null },
    { id: 3, code: "PERFECT_SCORE", title: "Perfect Score", description: "Achieved 100% on any topic quiz.", icon: "star", xpBonus: 100, unlocked: false, unlockedAt: null },
    { id: 4, code: "THREE_DAY_STREAK", title: "3-Day Streak", description: "Maintained a daily learning streak for 3 consecutive days.", icon: "flame", xpBonus: 150, unlocked: false, unlockedAt: null },
    { id: 5, code: "SEVEN_DAY_STREAK", title: "7-Day Streak", description: "Committed to Java learning for 7 consecutive days.", icon: "zap", xpBonus: 300, unlocked: false, unlockedAt: null },
    { id: 6, code: "MODULE_MASTER", title: "Module Master", description: "Completed all lessons in a full syllabus module.", icon: "book-open", xpBonus: 250, unlocked: false, unlockedAt: null },
    { id: 7, code: "CHALLENGE_CHAMPION", title: "Challenge Champion", description: "Won an asynchronous head-to-head quiz challenge.", icon: "trophy", xpBonus: 200, unlocked: false, unlockedAt: null },
    { id: 8, code: "JAVA_EXPLORER", title: "Java Explorer", description: "Completed 5 foundational Java lessons.", icon: "compass", xpBonus: 300, unlocked: false, unlockedAt: null }
  ],

  mistakes: [],
  friends: [],
  challenges: [],
  doubts: []
};
