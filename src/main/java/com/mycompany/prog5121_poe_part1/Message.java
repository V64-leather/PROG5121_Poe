/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author oraex
 */
package com.mycompany.prog5121_poe_part1;

import java.util.Random;
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
                continue;
            } else {
                System.out.println("Message sent");
            }

            // 3. Generate Message ID and Hash
            String messageID = createMessageID();
            String messageHash = createMessageHash(messageID, i, messageText);

            System.out.println("Message ID generated: " + messageID);
            System.out.println("Message Hash: " + messageHash);

            // 4. Send Options Menu
            System.out.println("\nSelect an option for this message:");
            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message (Press 0 to delete)");
            System.out.println("3) Store Message to send later");
            System.out.print("Enter choice (1-3): ");
            
            String sendChoice = scanner.nextLine();
            switch (sendChoice) {
                case "1":
                    System.out.println("Message successfully sent.");
                    break;
                case "2":
                    System.out.println("Press 0 to delete the message.");
                    String deleteChoice = scanner.nextLine();
                    if (deleteChoice.equals("0")) {
                        System.out.println("Message successfully deleted.");
                    } else {
                        System.out.println("Action cancelled.");
                    }
                    break;
                case "3":
                    System.out.println("Message successfully stored.");
                    break;
                default:
                    System.out.println("Invalid option selected. Defaulting to stored.");
                    break;
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

    // Generates a random 10-digit Message ID
    public String createMessageID() {
        Random rand = new Random();
        long id = 1000000000L + rand.nextLong(9000000000L);
        return String.valueOf(id);
    }

    // Creates the message hash using string manipulation
    public String createMessageHash(String messageID, int messageNumber, String messageText) {
        String firstTwoID = messageID.substring(0, 2);
        
        // Extract first and last word from messageText
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;

        // Format: [first 2 digits of ID]:[message index - 1]:[FIRSTWORDLASTWORD] in uppercase
        // (Note: assignment shows index starting from 0 or 1 depending on loop context; we use (messageNumber - 1) to align with standard indexing)
        int indexNum = messageNumber - 1;
        String combinedWords = (firstWord + lastWord).toUpperCase();

        return firstTwoID + ":" + indexNum + ":" + combinedWords;
    }
}