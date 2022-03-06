package com.lutheroaks.tacoswebsite.tickets;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import com.lutheroaks.tacoswebsite.member.Member;
import com.lutheroaks.tacoswebsite.resident.Resident;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

// This class contains the attributes and getters/setters for the Tickets table in the database
@Entity
@Table(name = "Tickets")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Tickets {

    // Ticketid is the key for each row
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int ticketNum;

    @Getter
    @Column(nullable = false, unique = false)
    private boolean ticketStatusActive;

    @Getter
    @Column(nullable = false, unique = true, length = 50)
    @NonNull private String issueDesc;

    @Getter
    @Column(nullable = false, length = 50)
    @NonNull private String techType;
    
    @Getter
    @OrderColumn
    @Column(nullable = false, length = 10)
    @NonNull private String[] comments;

    @Getter
    @Column(nullable = false, length = 50)
    @NonNull private Date timestamp;

    @Getter
    @OrderColumn
    @Column(nullable = false, length = 3)
    @NonNull private Member[] assignedMembers;

    // @Getter
    // @Column(nullable = false, length = 1)
    // @NonNull private Resident resident;

    @Getter
    @OrderColumn
    @Column(nullable = false, length = 50)
    @NonNull private String[] tags;
}
