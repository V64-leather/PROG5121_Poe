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
            } else {
                System.out.println("Registration failed. Please restart the program and try again.");
            }
        }
    }
}