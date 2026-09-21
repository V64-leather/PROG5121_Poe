/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog5121_poe_part1;

/**
 *
 * @author oraex
 */
public class Login {
    private String registeredUsername;
    private String registeredPassword;
    private String firstName;
    private String lastName;
    
    //Method to check if the username contains an '_' and character length of 5 or less 
    public boolean checkUserName(String username){
        return username.contains("_") && username.length() <= 5;
    }
    
    //Method to check if password meets complexity requirements
    public boolean checkPasswordComplexity(String password) {
        boolean hasLength = password.length() >=8;
        boolean hasCapital = password.matches(".*[A-Z].*");
        boolean hasNumber = password.matches(".*[0-9].*");
        boolean hasSpecial = password.matches(".*[^a-zA-Z0-9].*");
        
        return hasLength && hasCapital && hasNumber && hasSpecial;
    }
    
    //Method to check if the cell phone number conforms to SA international format
    public boolean checkCellPhoneNumber(String cellNumber){
        //Checks that the number starts with +27 followed by 9 digits
        return cellNumber.matches("\\+27\\d{9}$");
    }
    
    //Method to return registration status messages
    //Checks if the methods pass their validation checks
    public String registerUser(String username, String password, String cellNumber, String firstName, String lastName){
        if (!checkUserName(username)){
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";         
        } else if (!checkPasswordComplexity(password)){
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number and try again.";
        } else if (!checkCellPhoneNumber(cellNumber)){
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        } else {
            this.registeredUsername = username;
            this.registeredPassword = password;
            this.firstName = firstName;
            this.lastName = lastName;
            return "Password successfully captured.";
        }
    }
    
    //Verifies that entered credentials match saved registration details
    public boolean loginUser(String username, String password){
        return username.equals(this.registeredUsername) && password.equals(this.registeredPassword);
    }
    
    //Returns custom login feedback message based on login success or failure
    public String returnLoginStatus(boolean isLoggedIn){
        if (isLoggedIn){
            return "Welcome " + this.firstName + ", " + this.lastName + " it is great to see you.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
