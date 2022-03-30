package com.lutheroaks.tacoswebsite.ticket;

import java.sql.Timestamp;
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
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lutheroaks.tacoswebsite.comment.Comment;
import com.lutheroaks.tacoswebsite.member.Member;
import com.lutheroaks.tacoswebsite.resident.Resident;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

// This class contains the attributes and getters/setters for the Ticket table in the database
@Entity
@Table(name = "Ticket")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Ticket {

    // Ticketid is the key for each row
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketNum;

    @Column(nullable = false, unique = false)
    private boolean ticketStatusActive;

    @Column(nullable = false, unique = false, length = 50)
    @NonNull private String issueDesc;

    @Column(nullable = false, length = 50)
    @NonNull private String techType;

    @OrderColumn
    @Column(nullable = false, length = 50)
    @NonNull private Timestamp timestamp;

    @ManyToMany(mappedBy = "associatedTickets")
    @Column(nullable = false, length = 3)
    @ElementCollection(targetClass = Member.class)
    @NonNull private List<Member> assignedMembers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(nullable = false, name="associatedTickets")
    @NonNull private Resident resident;

    @Column(nullable = false, length = 50)
    @ElementCollection(targetClass = String.class)
    @NonNull private List<String> tags;

    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
    @NonNull protected List<Comment> comments;
}