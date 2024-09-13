package example.cafekiosk.spring.api.service.mail;

import example.cafekiosk.spring.client.mail.MailSendClient;
import example.cafekiosk.spring.domain.history.mail.MailSendHistory;
import example.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailSendClient mailSendClient;
    private final MailSendHistoryRepository mailSendHistoryRepository;
    public boolean sendMail(String from, String to, String subject, String content) {
        boolean result = mailSendClient.sendMail(from, to, subject, content);
        if(result){
            mailSendHistoryRepository.save(MailSendHistory.builder()
                    .fromEmail(from)
                    .toEmail(to)
                    .subject(subject)
                    .content(content)
                    .build()
            );
            return true;
        }
        return false;

    }
}
