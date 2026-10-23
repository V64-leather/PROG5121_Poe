/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog5121_poe_part1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    private final Message message = new Message();

    @Test
    public void testCheckMessageLength_Success() {
        String validMsg = "Hi Mike, can you join us for dinner tonight?";
        assertTrue(message.checkMessageLength(validMsg), "Message should be valid and under 250 characters.");
    }

    @Test
    public void testCheckMessageLength_Failure() {
        // Generates a string with 251 characters to test failure condition
        String longMsg = "A".repeat(251);
        assertFalse(message.checkMessageLength(longMsg), "Message exceeding 250 characters should fail.");
    }

    @Test
    public void testCheckRecipientCell_Success() {
        assertTrue(message.checkRecipientCell("+27718693002"), "Recipient cell with international code should pass.");
    }

    @Test
    public void testCheckRecipientCell_Failure() {
        assertFalse(message.checkRecipientCell("08575975889"), "Cell number missing international code should fail.");
    }

    @Test
    public void testCreateMessageID() {
        String id = message.createMessageID();
        assertNotNull(id, "Message ID should not be null.");
        assertEquals(10, id.length(), "Message ID must be exactly 10 characters long.");
    }

    @Test
    public void testCreateMessageHash() {
        String id = "1234567890";
        String text = "Hi Mike, can you join us for dinner tonight?";
        String hash = message.createMessageHash(id, 1, text);
        
        // Expected: First 2 digits of ID (12), index (0), FIRST + LAST word uppercase (HITONIGHT) -> 12:0:HITONIGHT
        assertEquals("12:0:HITONIGHT", hash, "Message hash should match the required string manipulation format.");
    }
}
