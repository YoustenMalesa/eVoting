package com.mrkenobii.ecommerceapp.controller;

import com.mrkenobii.ecommerceapp.model.Topic;
import com.mrkenobii.ecommerceapp.model.TopicDTO;
import com.mrkenobii.ecommerceapp.model.TopicResponse;
import com.mrkenobii.ecommerceapp.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/topic")
@RequiredArgsConstructor
public class TopicController {
    private final TopicService topicService;

    @PostMapping("/add")
    public ResponseEntity addVote(@RequestBody TopicDTO topic) {
        Topic entity = new Topic();
        entity.setArticle(topic.getArticle());
        entity.setClosingDate(topic.getClosingDate());
        entity.setStartDate(topic.getStartDate());
        entity.setTitle(topic.getTitle());
        entity.setSubHeading(topic.getSubHeading());

        topicService.saveTopic(entity);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<TopicResponse> getTopics() {
        return new ResponseEntity<>(new TopicResponse(topicService.getTopics()), HttpStatus.OK);
    }
}
