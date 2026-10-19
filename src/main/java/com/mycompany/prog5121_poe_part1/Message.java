/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author oraex
 */
package com.mycompany.prog5121_poe_part1;

import java.util.Scanner;

public class Message {

    public void displayWelcomeMessage() {
        System.out.println("=== Welcome to QuickChat. ===");
    }

    public void displayMenu() {
        System.out.println("\nPlease choose an option from the menu:");
        System.out.println("1) Send Messages");
        System.out.println("2) Show recently sent messages");
        System.out.println("3) Quit");
        System.out.print("Enter option (1-3): ");
    }

    public void processMessages(Scanner scanner) {
        System.out.print("\nHow many messages would you like to enter? ");
        int numMessages = Integer.parseInt(scanner.nextLine());

        for (int i = 1; i <= numMessages; i++) {
            System.out.println("\n--- Entering Message " + i + " of " + numMessages + " ---");
            
            // 1. Recipient Cell Validation
            System.out.print("Enter recipient cell number: ");
            String recipient = scanner.nextLine();
            
            if (!checkRecipientCell(recipient)) {
                System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                i--; // Repeat iteration if invalid
                continue;
            } else {
                System.out.println("Cell phone number successfully captured.");
            }
            
            // 2. Message Length Validation
            System.out.print("Enter message text: ");
            String messageText = scanner.nextLine();
            
            if (!checkMessageLength(messageText)) {
                int surplus = messageText.length() - 250;
                System.out.println("Please enter a message of less than 250 characters. (Exceeds by " + surplus + " characters)");
                i--; // Repeat iteration if invalid
            } else {
                System.out.println("Message sent");
            }
        }
    }

    public boolean checkMessageLength(String message) {
        return message != null && message.length() <= 250;
    }

    public boolean checkRecipientCell(String cellNumber) {
        if (cellNumber == null) {
            return false;
        }
        return cellNumber.startsWith("+") && cellNumber.length() <= 12 && cellNumber.length() >= 10;
    }
}