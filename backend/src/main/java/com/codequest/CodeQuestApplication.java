package com.codequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CodeQuestApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodeQuestApplication.class, args);
        System.out.println("=================================================");
        System.out.println("   CodeQuest Java Platform Backend Started!     ");
        System.out.println("   REST APIs: http://localhost:8080/api         ");
        System.out.println("   Swagger/Health Check: Active                 ");
        System.out.println("=================================================");
    }
}
