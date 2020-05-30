package com.example.threadlocal.demo.cotoller;

import com.example.threadlocal.demo.business.SampleBusinessRule;
import com.example.threadlocal.demo.service.AccessTokenContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class SampleController {


    private final SampleBusinessRule sampleBusinessRule;
    Logger log = LoggerFactory.getLogger(SampleController.class);

    public SampleController(SampleBusinessRule sampleBusinessRule) {
        this.sampleBusinessRule = sampleBusinessRule;
    }


    @GetMapping(name = "m1", value = "thread")
    public ResponseEntity<String> greet() {
        return ResponseEntity.ok().body(Thread.currentThread().getName());
    }

    @GetMapping(name = "m2", value = "db-ops", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<String>> dbConnectionStatus() {
        log.info("from thread {}", Thread.currentThread().getName());
        return ResponseEntity.ok().body(sampleBusinessRule
                .getListOfCollections(Thread.currentThread().getName()));
    }

    @GetMapping(name = "m3", value = "thread-local", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> checkThreadLocalFunctionality() {
        String accessToken = UUID.randomUUID().toString();
        log.info("setting access token {} on thread {}", accessToken, Thread.currentThread().getName());
        AccessTokenContext.setCurrentAccessToken(accessToken);
        sampleBusinessRule.verifyAccessTokenThreadLocalContext(accessToken);
        return ResponseEntity.ok("access token matched");
    }


    @GetMapping(name = "m4", value = "thread-local-async-simple", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> checkThreadLocalSimpleAsyncFunctionality() {
        String accessToken = UUID.randomUUID().toString();
        log.info("setting access token {} on thread {}", accessToken, Thread.currentThread().getName());
        AccessTokenContext.setCurrentAccessToken(accessToken);
        sampleBusinessRule.verifyAccessTokenSimpleAsyncThreadLocalContext();
        return ResponseEntity.ok("access token not found");
    }

    @GetMapping(name = "m5", value = "thread-local-async", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> checkThreadLocalAsyncFunctionality() {
        String accessToken = UUID.randomUUID().toString();
        log.info("setting access token {} on thread {}", accessToken, Thread.currentThread().getName());
        AccessTokenContext.setCurrentAccessToken(accessToken);
        sampleBusinessRule.verifyAccessTokenAsyncThreadLocalContext(accessToken);
        return ResponseEntity.ok("access token matched");
    }


}
