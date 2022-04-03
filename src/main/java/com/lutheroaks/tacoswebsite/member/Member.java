package com.lutheroaks.tacoswebsite.member;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lutheroaks.tacoswebsite.bio.Bio;
import com.lutheroaks.tacoswebsite.comment.Comment;
import com.lutheroaks.tacoswebsite.kb.KBPost;
import com.lutheroaks.tacoswebsite.ticket.Ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
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

    // memberId is the key for each row
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;

    @Column(length = 50)
    @NonNull private String firstName;

    @Column(length = 50)
    @NonNull private String lastName;

    @Column(length = 50)
    @NonNull private String email;

    @Column(length = 255)
    @NonNull private String password;

    @ManyToMany
    @JoinTable(
        name = "Ticket_Member_Assignments", 
        joinColumns = @JoinColumn(name = "memberId"), 
        inverseJoinColumns = @JoinColumn(name = "ticketNum"))
    @ElementCollection(targetClass = Ticket.class)
    private Set<Ticket> associatedTickets;

    @OneToOne(mappedBy = "member", optional = true, cascade = CascadeType.ALL)
    private Bio biography;

    @OneToMany(mappedBy = "member")
    @NonNull private List<Comment> comments;

    @OneToMany(mappedBy = "member")
    @NonNull private List<KBPost> kbPosts;

    @Column(length = 50)
    private String role;
    
    private boolean enabled;

}
