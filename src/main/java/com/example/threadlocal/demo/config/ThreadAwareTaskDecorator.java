package com.example.threadlocal.demo.config;

import com.example.threadlocal.demo.service.AccessTokenContext;
import org.springframework.core.task.TaskDecorator;

public class ThreadAwareTaskDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        String token = AccessTokenContext.getCurrentAccessToken();
        return () -> {
            try {
                AccessTokenContext.setCurrentAccessToken(token);
                runnable.run();
            } finally {
                AccessTokenContext.setCurrentAccessToken(null);
            }
        };
    }
}
