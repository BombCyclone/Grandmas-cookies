package com.lutheroaks.tacoswebsite.comment;

import java.util.List;

import com.lutheroaks.tacoswebsite.ticket.Ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Integer> {
    @Query("SELECT x FROM Comment x WHERE x.ticket = :ticket")
    List<Comment> findBioByMember(@Param("ticket") Ticket ticket);
}
