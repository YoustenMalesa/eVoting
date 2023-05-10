package com.mrkenobii.ecommerceapp.service;

import com.mrkenobii.ecommerceapp.model.Topic;
import com.mrkenobii.ecommerceapp.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;

    public Topic saveTopic(Topic topic) {
        return  topicRepository.saveAndFlush(topic);
    }

    public List<Topic> getTopics() {
        return topicRepository.findAll();
    }
}
