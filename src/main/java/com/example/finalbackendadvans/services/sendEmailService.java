package com.example.finalbackendadvans.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class sendEmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("$(spring.mail.username)")
    private  String fromEmailId;
    public  void  sendEmail(String name,String code, String recipient ,String subject) throws MessagingException {
        String emailBody = "<p>Dear "+name+",</p>" +
                "<p>Welcome to Advans Tunisie!</p>" +
                "<p>We are pleased to have you with us. As a valued customer, we are providing you with a unique code that you can use to create an account on our platform and apply for a loan through our app.</p>" +
                "<p><strong>Your Unique Code: "+code+"</strong></p>" +
                "<p>To get started, please follow these simple steps:</p>" +
                "<ol>" +
                "  <li>Download the Advans Tunisie app from the App Store or Google Play Store.</li>" +
                "  <li>Open the app and select \"Create Account.\"</li>" +
                "  <li>Enter your unique code and fill in the required details.</li>" +
                "  <li>Once your account is created, you can use the app to explore our services and apply for a loan.</li>" +
                "</ol>" +
                "<p>If you have any questions or need assistance, feel free to contact our support team at [support email] or call us at 22333909.</p>" +
                "<p>Thank you for choosing Advans Tunisie. We look forward to serving you and helping you achieve your financial goals.</p>" +
                "<p>Best regards,</p>" +
                "<p>The Advans Tunisie Team</p>";
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(fromEmailId);
        helper.setTo(recipient);
       helper.setText(emailBody,true);
        helper.setSubject(subject);
        javaMailSender.send(message);

    }
}
