package com.lutheroaks.tacoswebsite.resident;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import com.lutheroaks.tacoswebsite.ticket.Ticket;

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
    int roomNum;

    @OneToMany(mappedBy = "resident", fetch=FetchType.LAZY)
    @ElementCollection(targetClass = Ticket.class)
    private Set<Ticket> associatedTickets;
}

