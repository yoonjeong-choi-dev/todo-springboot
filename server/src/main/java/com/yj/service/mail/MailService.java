package com.yj.service.mail;

import com.yj.domain.todo.TodoItem;
import com.yj.domain.todo.TodoItemRepository;
import com.yj.domain.user.Member;
import com.yj.domain.user.MemberRepository;
import com.yj.dto.email.EmailSendResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class MailService {
    private static final Logger logger = Logger.getLogger(MailService.class.getName());

    private final JavaMailSender mailSender;
    private final TodoItemRepository todoItemRepository;
    private final MemberRepository memberRepository;

    @Async("emailTreadPool")
    @Transactional
    public CompletableFuture<EmailSendResultDTO> sendTodoData(String memberId) {

        Member member = memberRepository.getById(memberId);
        if (member.getEmail().isEmpty()) {
            return CompletableFuture.completedFuture(new EmailSendResultDTO(400, memberId + " has not email"));
        }

        List<TodoItem> todoItems = todoItemRepository.findByMemberId(memberId);
        String emailBody = parseTodoToCsv(todoItems);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(member.getEmail());
        message.setSubject(String.format("Todo Data for %s", memberId));
        message.setText(emailBody);

        EmailSendResultDTO result = null;
        try {
            logger.info("Send email...");
            mailSender.send(message);
            logger.info("Success to send...");

            result = new EmailSendResultDTO(200, "Success to send mail to " + member.getEmail());
        } catch (MailException ex) {
            logger.warning(ex.getMessage());
            result = new EmailSendResultDTO(500, "Some error in email service");
        }


        return CompletableFuture.completedFuture(result);
    }

    private String parseTodoToCsv(List<TodoItem> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("id,content,completed,created_at,modified_at");

        for (TodoItem item : items) {
            sb.append("\n")
                    .append(item.getId()).append(",")
                    .append(item.getContent()).append(",")
                    .append(item.isCompleted()).append(",")
                    .append(item.getCreatedAt()).append(",")
                    .append(item.getModifiedAt());
        }

        return sb.toString();
    }
}
