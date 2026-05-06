package com.appointment.observer;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 * Real Gmail SMTP email sender implementation.
 * 
 * @author awwadaa
 * @version 1.0
 */
public class GmailEmailSender implements EmailSender {

    private final String senderEmail;
    private final String appPassword;

    /**
     * Constructs a Gmail email sender.
     *
     * @param senderEmail sender Gmail address
     * @param appPassword Gmail app password
     */
    public GmailEmailSender(String senderEmail, String appPassword) {
        this.senderEmail = senderEmail;
        this.appPassword = appPassword;
    }

    /**
     * Sends an email using Gmail SMTP.
     *
     * @param recipientEmail recipient email address
     * @param subject email subject
     * @param body email body
     */
    @Override
    public void send(String recipientEmail, String subject, String body) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, appPassword);
            }
        });

        try {
            Message emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(senderEmail));
            emailMessage.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail)
            );
            emailMessage.setSubject(subject);
            emailMessage.setText(body);

            Transport.send(emailMessage);
            System.out.println("Real email sent successfully to " + recipientEmail);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }
    }
}