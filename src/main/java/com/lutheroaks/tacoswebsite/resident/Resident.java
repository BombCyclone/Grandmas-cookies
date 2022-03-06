package com.lutheroaks.tacoswebsite.resident;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import com.lutheroaks.tacoswebsite.tickets.Tickets;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

// This class contains the attributes and getters/setters for the Resident table in the database
@Entity
@Table(name = "Resident")
@RequiredArgsConstructor
public class Resident {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int residentid;

    @Getter
    @Column(nullable = false, unique = false, length = 50)
    @NonNull private String fname;

    @Getter
    @Column(nullable = false, unique = false, length = 50)
    @NonNull private String lname;

    @Getter
    @Column(nullable = false, unique = true, length = 50)
    final int roomNum;

    // @Getter
    // @OrderColumn
    // @OneToMany(targetEntity = Tickets.class, mappedBy = "ticketNum", fetch=FetchType.EAGER)
    // @Column(nullable = false, length = 3)
    // private Tickets[] associatedTickets;
}

