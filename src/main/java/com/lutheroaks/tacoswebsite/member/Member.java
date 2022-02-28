package com.lutheroaks.tacoswebsite.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "Member")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberid;

    @Column(nullable = false, unique = false, length = 50)
    @NonNull private String firstname;

    @Column(nullable = false, unique = false, length = 50)
    @NonNull private String lastname;

    @Column(nullable = false, unique = true, length = 50)
    @NonNull String email;
}