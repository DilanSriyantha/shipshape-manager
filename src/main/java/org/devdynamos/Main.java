package org.devdynamos;

import org.devdynamos.Utils.NotificationSender;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String toEmail;
        String subject;
        String body;
        Scanner scanner = new Scanner(System.in);

        System.out.println("toEmail -> ");
        toEmail = scanner.nextLine();

        System.out.println("subject -> ");
        subject = scanner.nextLine();

        System.out.println("body -> ");
        body = scanner.nextLine();

        try {
            NotificationSender.sendEmail(toEmail, subject, body);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}