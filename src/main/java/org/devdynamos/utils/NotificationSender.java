package org.devdynamos.utils;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.sun.mail.iap.ByteArray;
import org.apache.commons.codec.binary.Base64;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.*;

public class NotificationSender {
    private static final String SENDER = "info.shipshapemanager@gmail.com";
    private static final String APPLICATION_NAME = "ShipshapeManager";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = new ArrayList<>(Arrays.asList(
            GmailScopes.GMAIL_LABELS,
            GmailScopes.GMAIL_SEND
    ));
    private static final String CREDENTIALS_FILE_PATH = "/client_secret_447264114816-8cgr1cga8b3504qptpu8s3vcou7227l7.apps.googleusercontent.com.json";
    private static Gmail service;

    public static final int TEXT = 0;
    public static final int HTML = 1;

    private static void initialize() throws Exception {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        // create the gmail api client
        service = new Gmail.Builder(httpTransport,
                jsonFactory,
                getCredentials(httpTransport))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // load client secrets
        InputStream in = NotificationSender.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if(in == null){
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }

        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // build flow and trigger user authorization request
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(Paths.get(TOKENS_DIRECTORY_PATH).toFile()))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

        // returns an authorized credential object.
        return credential;
    }

    public static Message sendEmail(String toEmailAddress, String subject, String body, int sendType) throws Exception {
        initialize();

        // create the email content
        String messageSubject = subject;
        String bodyText = body;

        // encode as MIME message
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(SENDER));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(toEmailAddress));
        email.setSubject(messageSubject);

        if(sendType == TEXT)
            email.setText(bodyText);
        else
            email.setContent(bodyText, "text/html");

        // encode and wrap the MIME message into a gmail message
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
        Message message = new Message();
        message.setRaw(encodedEmail);

        try{
            message = service.users().messages().send("me", message).execute();
            System.out.println("Message id : " + message.getId());
            System.out.println(message.toPrettyString());

            return message;
        }catch (GoogleJsonResponseException e){
            GoogleJsonError error = e.getDetails();
            if(error.getCode() == 403){
                System.err.println("Unable to send message : " + e.getDetails());
            }else{
                throw e;
            }
        }

        return null;
    }
}
