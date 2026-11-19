/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.prog5121_poe_part1;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Login login = new Login();

            System.out.println("=== USER REGISTRATION ===");

            System.out.print("Enter First Name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter Last Name: ");
            String lastName = scanner.nextLine();

            System.out.print("Enter Username: ");
            String username = scanner.nextLine();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            System.out.print("Enter Cell Phone Number: ");
            String cellNumber = scanner.nextLine();

            // Perform registration check
            String registrationStatus = login.registerUser(username, password, cellNumber, firstName, lastName);
            System.out.println("\n" + registrationStatus + "\n");

            // Only proceed to login if registration was successful
            if (registrationStatus.equals("Password successfully captured.")) {
                System.out.println("=== USER LOGIN ===");

                System.out.print("Enter Username: ");
                String loginUsername = scanner.nextLine();

                System.out.print("Enter Password: ");
                String loginPassword = scanner.nextLine();

                // Verify credentials and print result
                boolean isLoggedIn = login.loginUser(loginUsername, loginPassword);
                String loginStatusMessage = login.returnLoginStatus(isLoggedIn);

                System.out.println("\n" + loginStatusMessage);

                // QuickChat Part 2 & Part 3 Menu Flow
                if (isLoggedIn) {
                    Message messageSystem = new Message();
                    messageSystem.setCurrentSender(loginUsername);
                    messageSystem.displayWelcomeMessage();

                    boolean running = true;

                    while (running) {
                        messageSystem.displayMenu();
                        String choiceInput = scanner.nextLine().trim();

                        switch (choiceInput) {
                            case "1" -> messageSystem.processMessages(scanner);
                            case "2" -> System.out.println("\n" + messageSystem.printMessages());
                            case "3" -> {
                                System.out.println("\nExiting application. Goodbye!");
                                running = false;
                            }
                            case "4" -> handleStoredMessagesMenu(messageSystem, scanner);
                            default -> System.out.println("\nInvalid option. Please enter 1, 2, 3, or 4.");
                        }
                    }
                }

            } else {
                System.out.println("Registration failed. Please restart the program and try again.");
            }
        }
    }

    private static void handleStoredMessagesMenu(Message messageSystem, Scanner scanner) {
        boolean inSubMenu = true;

        while (inSubMenu) {
            System.out.println("\n=== STORED MESSAGES MENU ===");
            System.out.println("a) Display sender and recipient of all stored messages");
            System.out.println("b) Display the longest message");
            System.out.println("c) Search for a message ID (displays recipient and message)");
            System.out.println("d) Search all messages sent or stored for a particular recipient");
            System.out.println("e) Delete a message using the message hash");
            System.out.println("f) Display full message report");
            System.out.println("x) Return to main menu");
            System.out.print("Choose an option (a-f or x): ");

            String subChoice = scanner.nextLine().trim().toLowerCase();

            switch (subChoice) {
                case "a" -> System.out.println("\n" + messageSystem.displayStoredSendersAndRecipients());
                case "b" -> System.out.println("\n" + messageSystem.displayLongestMessage());
                case "c" -> {
                    System.out.print("Enter Message ID to search: ");
                    String searchId = scanner.nextLine();
                    System.out.println("\n" + messageSystem.searchByMessageID(searchId));
                }
                case "d" -> {
                    System.out.print("Enter recipient cell number to search: ");
                    String targetRecipient = scanner.nextLine();
                    System.out.println("\n" + messageSystem.searchMessagesByRecipient(targetRecipient));
                }
                case "e" -> {
                    System.out.print("Enter Message Hash to delete: ");
                    String targetHash = scanner.nextLine();
                    System.out.println("\n" + messageSystem.deleteMessageByHash(targetHash));
                }
                case "f" -> System.out.println("\n" + messageSystem.displayMessageReport());
                case "x" -> inSubMenu = false;
                default -> System.out.println("\nInvalid sub-option. Please choose a, b, c, d, e, f, or x.");
            }
        }
    }
}