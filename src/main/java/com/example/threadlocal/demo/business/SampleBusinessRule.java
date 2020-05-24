package com.example.threadlocal.demo.business;

import com.example.threadlocal.demo.cotoller.SampleController;
import com.example.threadlocal.demo.service.AccessTokenContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SampleBusinessRule {

    private final MongoTemplate mongoTemplate;
    Logger log = LoggerFactory.getLogger(SampleBusinessRule.class);


    public SampleBusinessRule(@Qualifier("demoDB") MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Set<String> getListOfCollections(String threadName) {
        log.info("executing on thread {}", Thread.currentThread().getName());
        try {
            return mongoTemplate.getCollectionNames();
        } catch (Exception e) {
            log.error("error {} of thread {}",
                    e.getStackTrace()[e.getStackTrace().length - 1],
                    Thread.currentThread().getName());

            return Arrays.stream(e.getStackTrace())
                    .map(StackTraceElement::toString)
                    .collect(Collectors.toSet());

        }

    }


    public void verifyAccessTokenThreadLocalContext(String accessToken){
        String token = AccessTokenContext.getCurrentAccessToken();
        log.info("processing access token {} on thread {}",token,Thread.currentThread().getName());
        Assert.isTrue(accessToken.equals(token),"ThreadLocal is not working as expected");
        AccessTokenContext.removeAccessTokenContext();
    }


}
