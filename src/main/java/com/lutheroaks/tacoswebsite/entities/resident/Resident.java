package com.lutheroaks.tacoswebsite.entities.resident;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import com.lutheroaks.tacoswebsite.entities.ticket.Ticket;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

// This class contains the attributes and getters/setters for the Resident table in the database
@Entity
@Data
@Table(name = "Resident")
@RequiredArgsConstructor
@NoArgsConstructor
public class Resident {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int residentId;

    @Column(nullable = false, unique = false, length = 50)
    @NonNull private String firstName;

    @OrderColumn
    @Column(nullable = false, unique = false, length = 50)
    @NonNull private String lastName;
    
    @Column(nullable = false, unique = false, length = 50)
    private int roomNum;

    @OneToMany(mappedBy = "resident")
    private List<Ticket> associatedTickets;
}

