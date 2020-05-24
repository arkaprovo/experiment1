package com.example.threadlocal.demo.cotoller;

import com.mongodb.MongoTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/")
public class SampleController {

    Logger log = LoggerFactory.getLogger(SampleController.class);


    private final MongoTemplate mongoTemplate;

    public SampleController(@Qualifier("demoDB") MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping(name = "m1",value = "thread", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> greet(){
        return ResponseEntity.ok().body(Thread.currentThread().getName());
    }

    @GetMapping(name ="m2",value = "db-ops", produces =  MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Set<String>> dbConnectionStatus(){
        log.info("from thread {}",Thread.currentThread().getName());
        Set<String> response = Collections.emptySet();
        try{
            response = mongoTemplate.getCollectionNames();
        }catch (Exception e){
            log.error("error {} of thread {}",e.getStackTrace()[e.getStackTrace().length-1],Thread.currentThread().getName());
        }
        return ResponseEntity.ok().body(response);
    }

}
