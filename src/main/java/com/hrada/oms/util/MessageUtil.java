package com.hrada.oms.util;

import com.hrada.oms.dao.common.MessageRepository;
import com.hrada.oms.dao.common.UserRepository;
import com.hrada.oms.model.common.Message;
import com.hrada.oms.model.log.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by shin on 2018/12/13.
 */
@Async
@Component
public class MessageUtil {

    @Autowired
    MailUtil mailUtil;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    public void send(String email, String title, String content){
        send(email, title, content, true, true);
    }

    public void send(String email, String title, String content, Boolean mail, Boolean message){
        if(message){
            sendMessage(email, title, content);
        }
        if(mail){
            sendMail(email, title, content);
        }
    }

    public void sendMail(String email, String title, String content){
        Mail mail = new Mail();
        mail.setReceiver(email);
        mail.setSubject(title);
        mail.setContent(content);
        mailUtil.sendEmail(mail);
    }

    public void sendMessage(String email, String title, String content){
        String[] emails = email.split(",");
        for(String e:emails){
            Message message = new Message();
            message.setUser(userRepository.findByUname(e));
            message.setTitle(title);
            message.setContent(content);
            messageRepository.save(message);
        }
    }
}
