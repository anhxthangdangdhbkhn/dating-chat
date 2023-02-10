package vn.dating.chat.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import vn.dating.chat.utils.NotificationEmail;


@AllArgsConstructor
@Slf4j
@Service
class MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SimpleMailMessage preConfiguredMessage;

    @Async
    public void sendMail(NotificationEmail notificationEmail)
    {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(notificationEmail.getRecipient());
            message.setSubject(notificationEmail.getSubject());
            message.setText(notificationEmail.getBody());
            mailSender.send(message);
        }catch(Exception e){
            log.error("sendMail error {}",e.toString());
        }

    }

    /**
     * This method will send a pre-configured message
     * */
    public void sendPreConfiguredMail(String message)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage(preConfiguredMessage);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }


}
