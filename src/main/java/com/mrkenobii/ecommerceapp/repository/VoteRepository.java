package com.mrkenobii.ecommerceapp.repository;

import com.mrkenobii.ecommerceapp.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
    List<Vote> findVotesByTopicId(Integer topicId);
    List<Vote> findVotesByUserEmail(String userEmail);

}
