package com.dp.sleep.utils;

import org.springframework.mail.SimpleMailMessage;

public class MailUtils {

  public static SimpleMailMessage createEmailMessage(
      String email,
      String subject,
      String text
  ) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(email);
    message.setSubject(subject);
    message.setText(text);
    return message;
  }
}
