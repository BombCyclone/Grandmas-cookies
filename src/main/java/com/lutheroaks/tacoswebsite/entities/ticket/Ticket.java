package com.lutheroaks.tacoswebsite.entities.ticket;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lutheroaks.tacoswebsite.entities.comment.Comment;
import com.lutheroaks.tacoswebsite.entities.member.Member;
import com.lutheroaks.tacoswebsite.entities.resident.Resident;

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

    @OrderColumn
    @Column(nullable = false, length = 50)
    @NonNull private Timestamp timestamp;

    @ManyToMany(mappedBy = "associatedTickets")
    @Size(min=0, max=3)
    @NonNull private Set<Member> assignedMembers;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="associatedTickets", nullable = false)
    @NonNull private Resident resident;

    @ManyToMany
    @ElementCollection(targetClass = Ticket.class)
    @Size(min=0, max=3)
    private Set<Ticket> associatedTags;

    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
    @NonNull private List<Comment> comments;
}
