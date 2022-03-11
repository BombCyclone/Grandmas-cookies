package com.lutheroaks.tacoswebsite.tickets;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import com.lutheroaks.tacoswebsite.member.Member;
import com.lutheroaks.tacoswebsite.resident.Resident;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

// This class contains the attributes and getters/setters for the Tickets table in the database
@Entity
@Table(name = "Tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Tickets {

    // Ticketid is the key for each row
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketNum;

    @Column(nullable = false, unique = false)
    private boolean ticketStatusActive;

    @Column(nullable = false, unique = true, length = 50)
    @NonNull private String issueDesc;

    @Column(nullable = false, length = 50)
    @NonNull private String techType;
    
    @Column(nullable = false, length = 10)
    @ElementCollection(targetClass = String.class)
    @NonNull private List<String> comments;

    @OrderColumn
    @Column(nullable = false, length = 50)
    @NonNull private Date timestamp;

    @ManyToMany(mappedBy = "associatedTickets")
    @Column(nullable = false, length = 3)
    @ElementCollection(targetClass = Member.class)
    @NonNull private List<Member> assignedMembers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name="associatedTickets")
    @NonNull private Resident resident;

    @Column(nullable = false, length = 50)
    @ElementCollection(targetClass = String.class)
    @NonNull private List<String> tags;
}
