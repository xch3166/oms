package com.hrada.oms.util;

import com.hrada.oms.dao.log.MailRepository;
import com.hrada.oms.model.log.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

/**
 * Created by shin on 2018/10/30.
 */
@Component
public class MailUtil {

    @Value("${spring.mail.username:#{null}}")
    private String from;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    MailRepository mailRepository;

    public void sendEmail(Mail mail){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);

            messageHelper.setFrom(from);
            messageHelper.setTo(mail.getReceiver().split(","));
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mail.getContent(), mail.getIsHtml());

            if(mail.getCc()!=null){
                messageHelper.setCc(mail.getCc().split(","));
            }

            if(mail.getFilePath()!=null){
                File file = new File(mail.getFilePath());
                String fileName = mail.getFilePath().substring(mail.getFilePath().lastIndexOf(File.separator));
                messageHelper.addAttachment(fileName, file);
            }

            if(mail.getTemplate()!=null){
                Context context = new Context();
                String errorMessage;

                try {
                    throw new NullPointerException();
                } catch (NullPointerException e) {
                    Writer writer = new StringWriter();
                    PrintWriter printWriter = new PrintWriter(writer);
                    e.printStackTrace(printWriter);
                    errorMessage = writer.toString();
                }

                context.setVariable("username", "xc");
                context.setVariable("methodName", "com.hrada.oms.MailUtil.sendTemplateEmail()");
                context.setVariable("errorMessage", errorMessage);
                context.setVariable("occurredTime", new Date());
                messageHelper.setText(templateEngine.process(mail.getTemplate(), context), mail.getIsHtml());
            }

            mailSender.send(message);
            mailRepository.save(mail);
        } catch (MessagingException e) {
            e.printStackTrace( );
        }
    }

}
