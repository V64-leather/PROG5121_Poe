/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author oraex
 */
package com.mycompany.prog5121_poe_part1;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {

    // Runtime history list & counter
    private final List<String> sentMessagesList = new ArrayList<>();
    private int totalMessagesCount = 0;

    // Part 3 parallel arrays/lists (populated dynamically at runtime, never hard-coded)
    private final List<String> sentMessages = new ArrayList<>();
    private final List<String> disregardedMessages = new ArrayList<>();
    private final List<String> storedMessages = new ArrayList<>();
    private final List<String> messageHashes = new ArrayList<>();
    private final List<String> messageIDs = new ArrayList<>();

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

            // Track IDs and Hashes in parallel lists
            messageIDs.add(messageID);
            messageHashes.add(messageHash);

            // 4. Send Options Menu
            System.out.println("\nSelect an option for this message:");
            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message (Press 0 to delete)");
            System.out.println("3) Store Message to send later");
            System.out.print("Enter choice (1-3): ");

            String sendChoice = scanner.nextLine();
            String status = "";

            switch (sendChoice) {
                case "1" -> {
                    status = "Message successfully sent.";
                    System.out.println(status);
                    sentMessages.add(messageText);
                    totalMessagesCount++;
                }
                case "2" -> {
                    System.out.println("Press 0 to delete the message.");
                    String deleteChoice = scanner.nextLine();
                    if (deleteChoice.equals("0")) {
                        status = "Message successfully deleted.";
                        System.out.println(status);
                        disregardedMessages.add(messageText);
                    } else {
                        status = "Action cancelled.";
                        System.out.println(status);
                    }
                }
                case "3" -> {
                    status = "Message successfully stored.";
                    System.out.println(status);
                    storedMessages.add(messageText);
                    storeMessage(messageID, messageHash, recipient, messageText);
                }
                default -> {
                    status = "Message successfully stored.";
                    System.out.println("Invalid option selected. Defaulting to stored.");
                    storedMessages.add(messageText);
                    storeMessage(messageID, messageHash, recipient, messageText);
                }
            }

            // Format full details string for tracking history
            String messageDetails = "ID: " + messageID + " | Hash: " + messageHash + " | To: " + recipient + " | Text: " + messageText;
            sentMessagesList.add(messageDetails);
        }

        System.out.println("\nTotal number of messages processed/sent: " + returnTotalMessages());
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

    public String createMessageID() {
        Random rand = new Random();
        long id = 1000000000L + rand.nextLong(9000000000L);
        return String.valueOf(id);
    }

    public String createMessageHash(String messageID, int messageNumber, String messageText) {
        String firstTwoID = messageID.substring(0, 2);

        String[] words = messageText.trim().split("\\s+");

        // Strip out punctuation (like commas or question marks) so only letters remain for the hash
        String firstWord = words.length > 0 ? words[0].replaceAll("[^a-zA-Z]", "") : "";
        String lastWord = words.length > 1 ? words[words.length - 1].replaceAll("[^a-zA-Z]", "") : firstWord;

        int indexNum = messageNumber - 1;
        String combinedWords = (firstWord + lastWord).toUpperCase();

        return firstTwoID + ":" + indexNum + ":" + combinedWords;
    }

    // Stores message data into a JSON file format
    public void storeMessage(String id, String hash, String recipient, String text) {
        String escapedText = text.replace("\\", "\\\\").replace("\"", "\\\"");
        String escapedRecipient = recipient.replace("\\", "\\\\").replace("\"", "\\\"");
        String jsonEntry = "{\n  \"MessageID\": \"" + id + "\",\n  \"MessageHash\": \"" + hash + "\",\n  \"Recipient\": \"" + escapedRecipient + "\",\n  \"Message\": \"" + escapedText + "\"\n},\n";
        try (FileWriter file = new FileWriter("messages.json", true)) {
            file.write(jsonEntry);
            System.out.println("Message successfully stored in JSON file.");
        } catch (IOException e) {
            System.out.println("Error storing message to JSON: " + e.getMessage());
        }
    }

    /**
     * Reads stored messages from messages.json into the storedMessages list.
     * Uses standard Java NIO file reading and regex extraction for "Message" fields.
     */
    public List<String> loadStoredMessagesFromJson(String filePath) {
        storedMessages.clear();
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            return storedMessages;
        }
        try {
            String content = Files.readString(path, StandardCharsets.UTF_8);
            Pattern pattern = Pattern.compile("\"Message\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"");
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                String extracted = matcher.group(1)
                        .replace("\\\"", "\"")
                        .replace("\\\\", "\\");
                storedMessages.add(extracted);
            }
        } catch (IOException e) {
            System.out.println("Error reading stored messages from JSON: " + e.getMessage());
        }
        return storedMessages;
    }

    public List<String> loadStoredMessagesFromJson() {
        return loadStoredMessagesFromJson("messages.json");
    }

    // Getters for Part 3 arrays/lists
    public List<String> getSentMessages() {
        return sentMessages;
    }

    public List<String> getDisregardedMessages() {
        return disregardedMessages;
    }

    public List<String> getStoredMessages() {
        return storedMessages;
    }

    public List<String> getMessageHashes() {
        return messageHashes;
    }

    public List<String> getMessageIDs() {
        return messageIDs;
    }

    // Returns all sent messages
    public String printMessages() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== SENT MESSAGES HISTORY ===\n");
        for (String msg : sentMessagesList) {
            sb.append(msg).append("\n");
        }
        return sb.toString();
    }

    // Returns total number of messages sent
    public int returnTotalMessages() {
        return totalMessagesCount;
    }
}