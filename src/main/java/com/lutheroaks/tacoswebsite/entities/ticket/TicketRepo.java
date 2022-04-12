package com.lutheroaks.tacoswebsite.entities.ticket;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepo extends JpaRepository<Ticket,Integer> {

    @Query("SELECT x FROM Ticket x WHERE x.ticketNum = :ticketNum")
    Ticket findTicketById(int ticketNum);

    @Query(value ="SELECT associated_tickets from Ticket", nativeQuery = true)
    List<Integer> findAssociatedTickets();

    @Modifying
    @Query("DELETE FROM Ticket WHERE ticket_num = ?1")
    void deleteTicketByTicketNum(int ticketNum);
    
}  

