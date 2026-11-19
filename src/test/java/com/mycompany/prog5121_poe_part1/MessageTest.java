/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog5121_poe_part1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
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

    private Message createPopulatedTestSystem() {
        Message msgSys = new Message();
        msgSys.setCurrentSender("Developer");

        // Message 1: Sent
        String id1 = "1000000001";
        String text1 = "Did you get the cake?";
        String rec1 = "+27834557896";
        String hash1 = msgSys.createMessageHash(id1, 1, text1);
        msgSys.getMessageIDs().add(id1);
        msgSys.getMessageHashes().add(hash1);
        msgSys.getSentMessages().add(text1);
        msgSys.getMessageRecords().add(new Message.MessageRecord(id1, hash1, "Developer", rec1, text1, "Sent"));

        // Message 2: Stored
        String id2 = "1000000002";
        String text2 = "Where are you? You are late! I have asked you to be on time.";
        String rec2 = "+27838884567";
        String hash2 = msgSys.createMessageHash(id2, 2, text2);
        msgSys.getMessageIDs().add(id2);
        msgSys.getMessageHashes().add(hash2);
        msgSys.getStoredMessages().add(text2);
        msgSys.getMessageRecords().add(new Message.MessageRecord(id2, hash2, "Developer", rec2, text2, "Stored"));

        // Message 3: Disregard
        String id3 = "1000000003";
        String text3 = "Yohoooo, I am at your gate.";
        String rec3 = "+27834484567";
        String hash3 = msgSys.createMessageHash(id3, 3, text3);
        msgSys.getMessageIDs().add(id3);
        msgSys.getMessageHashes().add(hash3);
        msgSys.getDisregardedMessages().add(text3);
        msgSys.getMessageRecords().add(new Message.MessageRecord(id3, hash3, "Developer", rec3, text3, "Disregarded"));

        // Message 4: Sent
        String id4 = "0838884567";
        String text4 = "It is dinner time !";
        String rec4 = "0838884567";
        String hash4 = msgSys.createMessageHash(id4, 4, text4);
        msgSys.getMessageIDs().add(id4);
        msgSys.getMessageHashes().add(hash4);
        msgSys.getSentMessages().add(text4);
        msgSys.getMessageRecords().add(new Message.MessageRecord(id4, hash4, "Developer", rec4, text4, "Sent"));

        // Message 5: Stored
        String id5 = "1000000005";
        String text5 = "Ok, I am leaving without you.";
        String rec5 = "+27838884567";
        String hash5 = msgSys.createMessageHash(id5, 5, text5);
        msgSys.getMessageIDs().add(id5);
        msgSys.getMessageHashes().add(hash5);
        msgSys.getStoredMessages().add(text5);
        msgSys.getMessageRecords().add(new Message.MessageRecord(id5, hash5, "Developer", rec5, text5, "Stored"));

        return msgSys;
    }

    @Test
    public void testSentMessagesArrayPopulated() {
        Message msgSys = createPopulatedTestSystem();
        assertEquals(List.of("Did you get the cake?", "It is dinner time !"), msgSys.getSentMessages());
    }

    @Test
    public void testDisplayLongestMessage() {
        Message msgSys = createPopulatedTestSystem();
        assertEquals("Where are you? You are late! I have asked you to be on time.", msgSys.getLongestMessage());
    }

    @Test
    public void testSearchByMessageID() {
        Message msgSys = createPopulatedTestSystem();
        assertEquals("Recipient: 0838884567\nMessage: It is dinner time !", msgSys.searchByMessageID("0838884567"));
    }

    @Test
    public void testSearchMessagesByRecipient() {
        Message msgSys = createPopulatedTestSystem();
        List<String> recipientMessages = msgSys.getMessagesForRecipient("+27838884567");
        assertEquals(
                List.of(
                        "Where are you? You are late! I have asked you to be on time.",
                        "Ok, I am leaving without you."
                ),
                recipientMessages
        );
    }

    @Test
    public void testDeleteMessageByHash() {
        Message msgSys = createPopulatedTestSystem();
        String msg2Text = "Where are you? You are late! I have asked you to be on time.";
        String msg2Hash = msgSys.createMessageHash("1000000002", 2, msg2Text);

        String result = msgSys.deleteMessageByHash(msg2Hash);
        assertEquals("Message: \"" + msg2Text + "\" successfully deleted.", result);
    }

    @Test
    public void testDisplayMessageReport() {
        Message msgSys = createPopulatedTestSystem();
        String report = msgSys.displayMessageReport();
        assertTrue(report.contains("Did you get the cake?"));
        assertTrue(report.contains("It is dinner time !"));
        assertTrue(report.contains("+27834557896"));
    }

    @Test
    public void testLoadStoredMessagesFromJson() throws IOException {
        Path tempJson = Files.createTempFile("test_messages", ".json");
        String jsonContent = "{\n  \"MessageID\": \"1000000002\",\n  \"MessageHash\": \"10:1:WHERETIME\",\n  \"Recipient\": \"+27838884567\",\n  \"Message\": \"Where are you? You are late! I have asked you to be on time.\"\n},\n";
        Files.writeString(tempJson, jsonContent, StandardCharsets.UTF_8);

        Message msgSys = new Message();
        List<String> loaded = msgSys.loadStoredMessagesFromJson(tempJson.toString());
        assertEquals(List.of("Where are you? You are late! I have asked you to be on time."), loaded);

        Files.deleteIfExists(tempJson);
    }
}