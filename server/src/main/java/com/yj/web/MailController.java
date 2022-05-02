package com.yj.web;

import com.yj.common.AuthUtil;
import com.yj.service.mail.MailService;
import com.yj.web.dto.email.EmailSendResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mail")
public class MailController {

    private static final Logger logger = Logger.getLogger(MailController.class.getName());

    private final MailService mailService;

    @PostMapping("todo")
    public ResponseEntity<?> sendTodoData() throws ExecutionException, InterruptedException {

        String memberId = AuthUtil.getCurrentUser().getId();
        CompletableFuture<EmailSendResultDTO> future = mailService.sendTodoData(memberId);

        future.join();
        EmailSendResultDTO res = future.get();

        HttpStatus status;
        if(res.getStatus() == 200) status = HttpStatus.OK;
        else if(res.getStatus() == 400) status = HttpStatus.NOT_FOUND;
        else status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(res.getMessage(), status);
    }

}
