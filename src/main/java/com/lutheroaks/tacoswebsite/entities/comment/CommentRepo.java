package com.lutheroaks.tacoswebsite.entities.comment;

import java.util.List;

import com.lutheroaks.tacoswebsite.entities.ticket.Ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Integer> {
    @Query("SELECT x FROM Comment x WHERE x.ticket = :ticket")
    List<Comment> findBioByMember(@Param("ticket") Ticket ticket);

    @Query("SELECT x FROM Comment x WHERE x.commentId = :id")
    Comment findCommentById(@Param("id")int id);

    @Modifying
    @Query("DELETE from Comment WHERE comment_id = ?1")
    void deleteCommentById(int commentId);
}
