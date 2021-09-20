package com.confluent.kafkaChargebackReport.service;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailService {
  public void sendBill() {
      final String username = "";
      final String password = "";

      Properties prop = new Properties();
      prop.put("mail.smtp.host", "smtp.gmail.com");
      prop.put("mail.smtp.port", "587");
      prop.put("mail.smtp.auth", "true");
      prop.put("mail.smtp.starttls.enable", "true"); //TLS

      Session session = Session.getInstance(prop,
              new javax.mail.Authenticator() {
                  protected PasswordAuthentication getPasswordAuthentication() {
                      return new PasswordAuthentication(username, password);
                  }
              });

      try {

          Message message = new MimeMessage(session);
          message.setFrom(new InternetAddress("rahulbats@gmail.com"));
          message.setRecipients(
                  Message.RecipientType.TO,
                  InternetAddress.parse("rahulbats@gmail.com, rahul@confluent.io")
          );
          message.setSubject("Testing Gmail TLS");
          message.setText("Dear Mail Crawler,"
                  + "\n\n Please do not spam my email!");

          Transport.send(message);

          System.out.println("Done");

      } catch (MessagingException e) {
          e.printStackTrace();
      }
  }
}
