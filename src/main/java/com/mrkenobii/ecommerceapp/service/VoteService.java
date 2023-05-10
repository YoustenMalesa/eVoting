package com.mrkenobii.ecommerceapp.service;

import com.mrkenobii.ecommerceapp.model.Vote;
import com.mrkenobii.ecommerceapp.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;

    public Vote saveVote(Vote vote) {
        return voteRepository.saveAndFlush(vote);
    }

    public List<Vote> findVotesByTopicId(Integer topicId) {
        return voteRepository.findVotesByTopicId(topicId);
    }
    public List<Vote> findVotesByUserEmail(String userEmail) {
        return voteRepository.findVotesByUserEmail(userEmail);
    }

    public List<Vote> findAllVotes() {
        return voteRepository.findAll();
    }
}
