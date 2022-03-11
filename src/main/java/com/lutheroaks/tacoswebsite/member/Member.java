package com.lutheroaks.tacoswebsite.member;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.lutheroaks.tacoswebsite.tickets.Tickets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

// This class contains the attributes and getters/setters for the Member table in the database
@Entity
@Table(name = "Member")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Member {

    // memberid is the key for each row
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int memberid;

    @Getter
    @Column(nullable = false, unique = false, length = 50)
    @NonNull private String firstname;

    @Getter
    @Column(nullable = false, unique = false, length = 50)
    @NonNull private String lastname;

    @Getter
    @Column(nullable = false, unique = true, length = 50)
    @NonNull String email;

    @ManyToMany
    @JoinTable(
        name = "Ticket_Member_Assignments", 
        joinColumns = @JoinColumn(name = "memberid"), 
        inverseJoinColumns = @JoinColumn(name = "ticketNum"))
    @ElementCollection(targetClass = Tickets.class)
    private Set<Tickets> associatedTickets;
}