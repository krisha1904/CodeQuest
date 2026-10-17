package com.codequest.config;

import com.codequest.entity.*;
import com.codequest.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseModuleRepository courseModuleRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private StudentBadgeRepository studentBadgeRepository;

    @Autowired
    private LessonProgressRepository lessonProgressRepository;

    @Autowired
    private StreakRecordRepository streakRecordRepository;

    @Autowired
    private DoubtRepository doubtRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (courseRepository.count() > 0) {
            return; // Syllabus already seeded
        }

        System.out.println(">>> Seeding CodeQuest Educational Syllabus & Question Bank (Clean User State)...");

        // 1. Seed Badges Catalog
        Badge b1 = new Badge("FIRST_STEP", "First Step", "Completed your very first Java lesson on CodeQuest.", "award", 50);
        Badge b2 = new Badge("QUIZ_STARTER", "Quiz Starter", "Passed your first topic-based multiple choice quiz.", "check-circle", 75);
        Badge b3 = new Badge("PERFECT_SCORE", "Perfect Score", "Achieved 100% on any topic quiz.", "star", 100);
        Badge b4 = new Badge("THREE_DAY_STREAK", "3-Day Streak", "Maintained a daily learning streak for 3 consecutive days.", "flame", 150);
        Badge b5 = new Badge("SEVEN_DAY_STREAK", "7-Day Streak", "Committed to Java learning for 7 consecutive days.", "zap", 300);
        Badge b6 = new Badge("MODULE_MASTER", "Module Master", "Completed all lessons in a full syllabus module.", "book-open", 250);
        Badge b7 = new Badge("CHALLENGE_CHAMPION", "Challenge Champion", "Won an asynchronous quiz challenge against a peer.", "trophy", 200);
        Badge b8 = new Badge("JAVA_EXPLORER", "Java Explorer", "Completed 5 foundational Java lessons.", "compass", 300);

        badgeRepository.save(b1);
        badgeRepository.save(b2);
        badgeRepository.save(b3);
        badgeRepository.save(b4);
        badgeRepository.save(b5);
        badgeRepository.save(b6);
        badgeRepository.save(b7);
        badgeRepository.save(b8);

        // 3. Seed Course & 12 Modules
        Course course = new Course("College Core Java Programming", "Comprehensive university-aligned Java course covering fundamentals, OOP concepts, data structures, exception handling, and exam preparation.");
        courseRepository.save(course);

        CourseModule m1 = new CourseModule(course, "Module 1: Introduction to Java", "JVM architecture, bytecode, JDK vs JRE, and your first Java program.", 1);
        CourseModule m2 = new CourseModule(course, "Module 2: Variables and Data Types", "Primitive types, naming conventions, type casting, and Scanner input.", 2);
        CourseModule m3 = new CourseModule(course, "Module 3: Operators & Expressions", "Arithmetic, relational, logical, and increment/decrement operators.", 3);
        CourseModule m4 = new CourseModule(course, "Module 4: Conditional Statements", "Control flow using if, if-else, else-if ladder, and switch.", 4);
        CourseModule m5 = new CourseModule(course, "Module 5: Loops & Iteration", "while, do-while, for loops, nested loops, break and continue.", 5);
        CourseModule m6 = new CourseModule(course, "Module 6: Arrays", "1D arrays, 2D matrices, array traversal, and standard algorithms.", 6);
        CourseModule m7 = new CourseModule(course, "Module 7: Strings & Text Handling", "String immutability, methods, StringPool, StringBuilder vs StringBuffer.", 7);
        CourseModule m8 = new CourseModule(course, "Module 8: Methods & Modular Code", "Defining methods, parameter passing, return types, method overloading.", 8);
        CourseModule m9 = new CourseModule(course, "Module 9: Classes and Objects", "Class definitions, object instantiation, constructors, and the this keyword.", 9);
        CourseModule m10 = new CourseModule(course, "Module 10: Object-Oriented Programming (OOP)", "Inheritance, Polymorphism, Abstraction, and Encapsulation.", 10);
        CourseModule m11 = new CourseModule(course, "Module 11: Exception Handling", "try-catch blocks, throw, throws, finally, and custom exceptions.", 11);
        CourseModule m12 = new CourseModule(course, "Module 12: Revision & Exam Practice", "Important university exam questions, output prediction, and mock assessments.", 12);

        courseModuleRepository.save(m1);
        courseModuleRepository.save(m2);
        courseModuleRepository.save(m3);
        courseModuleRepository.save(m4);
        courseModuleRepository.save(m5);
        courseModuleRepository.save(m6);
        courseModuleRepository.save(m7);
        courseModuleRepository.save(m8);
        courseModuleRepository.save(m9);
        courseModuleRepository.save(m10);
        courseModuleRepository.save(m11);
        courseModuleRepository.save(m12);

        // 4. Seed Lesson 1
        Lesson l1 = new Lesson();
        l1.setModule(m1);
        l1.setTitle("Introduction to Java & Program Structure");
        l1.setSequenceOrder(1);
        l1.setEstimatedMinutes(15);
        l1.setDefinition("Java is a high-level, class-based, object-oriented programming language designed following the principle of Write Once, Run Anywhere (WORA).");
        l1.setConceptExplanation("Java source code (.java) is compiled into architecture-neutral bytecode (.class) by javac. The Java Virtual Machine (JVM) executes this bytecode on any operating system with a compatible JRE. Standalone Java applications must have a public static void main() entry point.");
        l1.setSyntaxBlock("class ClassName {\n    public static void main(String[] args) {\n        // statements\n    }\n}");
        l1.setJavaProgram("class Main {\n    public static void main(String[] args) {\n        System.out.println(\"Welcome to CodeQuest Java!\");\n        System.out.print(\"Learning Java step-by-step for university exams.\");\n    }\n}");
        l1.setProgramOutput("Welcome to CodeQuest Java!\nLearning Java step-by-step for university exams.");
        l1.setProgramExplanation("1. class Main: Declares a class named Main.\n2. public: Accessible anywhere.\n3. static: Invoked without creating an object.\n4. void: No return value.\n5. String[] args: Command-line arguments.\n6. System.out.println(): Prints and moves to next line.");
        l1.setImportantPoints("• Bytecode is platform-independent; JVM is platform-dependent.\n• The public class name must match the file name.\n• Java is strictly case-sensitive.");
        l1.setCommonMistakes("• Forgetting semicolon (;) at the end of statements.\n• Writing 'main' with a capital 'M' or 'string' with lowercase 's'.");
        l1.setPracticeQuestions("1. Write a Java program to print your name, roll number, and college name on three lines.\n2. Predict the compiler error if the return type of main() is changed to int.");
        lessonRepository.save(l1);

        questionRepository.save(new Question(l1, "Which component of Java executes bytecode?", "JDK", "JVM", "JRE", "Javac", "B", "The Java Virtual Machine (JVM) interprets and executes architecture-neutral bytecode.", "EASY"));
        questionRepository.save(new Question(l1, "What is the exact signature of the main method?", "public void main(String[] args)", "public static void main(String[] args)", "static void main(String[] args)", "public static int main(String[] args)", "B", "The mandatory entry point signature is public static void main(String[] args).", "EASY"));
        questionRepository.save(new Question(l1, "What is the file extension of compiled bytecode files?", ".java", ".bytecode", ".class", ".obj", "C", "The Java compiler produces .class files.", "EASY"));
        questionRepository.save(new Question(l1, "Which keyword allows calling a method without creating an instance?", "void", "final", "static", "public", "C", "static methods belong to the class and are called directly.", "MEDIUM"));
        questionRepository.save(new Question(l1, "What does WORA stand for?", "Write Once, Read Always", "Write Once, Run Anywhere", "Work Once, Repeat Anywhere", "Wire Once, Run All", "B", "WORA stands for Write Once, Run Anywhere.", "EASY"));

        // 5. Seed Lesson 2
        Lesson l2 = new Lesson();
        l2.setModule(m2);
        l2.setTitle("Variables and Primitive Data Types");
        l2.setSequenceOrder(1);
        l2.setEstimatedMinutes(20);
        l2.setDefinition("A variable in Java is a named memory location used to store data that can be modified during program execution. Java is strongly typed, meaning every variable must have a declared data type.");
        l2.setConceptExplanation("Java supports 8 primitive data types:\n- Integers: byte (1 byte), short (2 bytes), int (4 bytes), long (8 bytes)\n- Floating-point: float (4 bytes), double (8 bytes)\n- Character: char (2 bytes, 16-bit Unicode)\n- Boolean: boolean (1 bit, true/false)\nConstants are declared using the 'final' keyword.");
        l2.setSyntaxBlock("dataType variableName = initialValue;\nfinal dataType CONSTANT_NAME = value;");
        l2.setJavaProgram("class Main {\n    public static void main(String[] args) {\n        int studentAge = 19;\n        double gpa = 8.75;\n        char grade = 'A';\n        boolean isEnrolled = true;\n        final double COLLEGE_PASS_MARKS = 40.0;\n\n        System.out.println(\"Age: \" + studentAge);\n        System.out.println(\"GPA: \" + gpa);\n        System.out.println(\"Grade: \" + grade);\n        System.out.println(\"Enrolled: \" + isEnrolled);\n        System.out.println(\"Passing Threshold: \" + COLLEGE_PASS_MARKS);\n    }\n}");
        l2.setProgramOutput("Age: 19\nGPA: 8.75\nGrade: A\nEnrolled: true\nPassing Threshold: 40.0");
        l2.setProgramExplanation("1. int studentAge = 19: Stores 4-byte integer in memory.\n2. double gpa = 8.75: Stores 8-byte floating point number.\n3. char grade = 'A': Stores Unicode 65.\n4. final double COLLEGE_PASS_MARKS: Creates an immutable constant.");
        l2.setImportantPoints("• Local variables inside methods are NOT initialized automatically.\n• Java char uses 16-bit Unicode, not 8-bit ASCII.\n• Constants cannot be modified after initial assignment.");
        l2.setCommonMistakes("• Using double quotes for char or single quotes for Strings.\n• Attempting to reassign a 'final' variable.");
        l2.setPracticeQuestions("1. Write a program to convert Celsius temperature to Fahrenheit.\n2. Explain byte overflow when assigning 130 to a byte variable.");
        lessonRepository.save(l2);

        questionRepository.save(new Question(l2, "What is the size of the int primitive data type in Java?", "2 bytes", "4 bytes", "8 bytes", "16 bytes", "B", "In Java, an int always occupies 4 bytes (32 bits).", "EASY"));
        questionRepository.save(new Question(l2, "Which keyword declares a constant in Java?", "const", "static", "final", "immutable", "C", "The 'final' keyword prevents reassignment.", "EASY"));
        questionRepository.save(new Question(l2, "Why does char occupy 2 bytes in Java?", "For ASCII", "For 16-bit Unicode international characters", "Because memory is cheap", "To hold integers", "B", "Java uses Unicode to support worldwide alphabets and characters.", "MEDIUM"));
        questionRepository.save(new Question(l2, "What is the default value of a local int inside a method?", "0", "null", "No default value (compile error)", "Garbage value", "C", "Local variables must be initialized explicitly before reading.", "MEDIUM"));
        questionRepository.save(new Question(l2, "Which identifier name is valid in Java?", "1stNum", "total_score", "class", "final-val", "B", "total_score is a valid identifier. Identifiers cannot start with digits or use reserved keywords.", "EASY"));

        System.out.println(">>> Initial Educational Seed Data Generated Successfully (Clean Database - Zero Users)!");
    }
}
