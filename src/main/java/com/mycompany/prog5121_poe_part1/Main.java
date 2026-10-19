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
                
                // ==============================================================
                // PART 2: START OF QUICKCHAT MENU (ONLY ACCESSING IF LOGGED IN)[cite: 7]
                // ==============================================================
                if (isLoggedIn) {
                    Message messageSystem = new Message();
                    messageSystem.displayWelcomeMessage(); //[cite: 7]
                    
                    boolean running = true;
                    
                    while (running) { //[cite: 7]
                        messageSystem.displayMenu(); //[cite: 7]
                        String choiceInput = scanner.nextLine();
                        
                        switch (choiceInput) {
                            case "1" -> System.out.println("\n[Send Messages selected]");
                            case "2" -> System.out.println("\nComing Soon."); //[cite: 7]
                            case "3" -> {
                                System.out.println("\nExiting application. Goodbye!");
                                running = false; //[cite: 7]
                            }
                            default -> System.out.println("\nInvalid option. Please enter 1, 2, or 3.");
                        }
                    }
                }
                
                
            } else {
                System.out.println("Registration failed. Please restart the program and try again.");
            }
        }
    }
}