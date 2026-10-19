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
            
            System.out.print("Enter recipient cell number: ");
            
            System.out.print("Enter message text: ");
            
            System.out.println("-> [Placeholder] Message " + i + " captured successfully.");
        }
    }
}