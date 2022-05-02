package com.yj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig  {

    // 이메일 서비스를 위한 스레드 풀 빈 등록
    @Bean(name="emailTreadPool")
    public Executor emailThreadPool() {
        // 스레드 풀을 만들어 처리해야 하는 비동기 작업을 할당할 스레드를 미리 생성
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(30);
        executor.setThreadNamePrefix("async-email-thread-");
        executor.initialize();

        return executor;
    }
}
