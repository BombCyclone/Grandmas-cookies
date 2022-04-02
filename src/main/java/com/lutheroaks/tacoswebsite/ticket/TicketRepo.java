package com.lutheroaks.tacoswebsite.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepo extends JpaRepository<Ticket,Integer> {

    @Modifying
    @Query("DELETE FROM Ticket WHERE ticket_num = ?1")
    void deleteTicketByTicketNum(int ticketNum);
    
}
