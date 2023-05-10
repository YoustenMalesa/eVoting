package com.mrkenobii.ecommerceapp.controller;

import com.mrkenobii.ecommerceapp.model.Vote;
import com.mrkenobii.ecommerceapp.model.VoteResponse;
import com.mrkenobii.ecommerceapp.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vote")
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @PostMapping("/cast")
    public ResponseEntity saveVote(@RequestBody Vote vote) {
        voteService.saveVote(vote);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/find/topic/votes")
    public ResponseEntity<VoteResponse> findVotesByTopicId(@RequestParam("topicId") Integer topicId) {
        return new ResponseEntity<>(new VoteResponse(voteService.findVotesByTopicId(topicId)), HttpStatus.OK);
    }

    @GetMapping("/find/user/votes")
    public ResponseEntity<VoteResponse> findVotesByUserEmail(@RequestParam("email") String userEmail) {
        return new ResponseEntity<>(new VoteResponse(voteService.findVotesByUserEmail(userEmail)), HttpStatus.OK);
    }

    @GetMapping("/find/votes")
    public ResponseEntity<VoteResponse> findAllVotes() {
        return new ResponseEntity<>(new VoteResponse(voteService.findAllVotes()), HttpStatus.OK);
    }
}
