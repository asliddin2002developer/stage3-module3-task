package com.mjc.school.controller.menumanager;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component("inputHandler")
public class InputHandler {
    private final Scanner scanner;

    public InputHandler() {
        scanner = new Scanner(System.in);
    }

    public String ask(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public Long isValidId(String id) {
        try {
            return Long.valueOf(id);
        } catch (Exception e) {
            throw new RuntimeException("ID is not valid!");
        }
    }
}