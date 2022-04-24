package com.lutheroaks.tacoswebsite.entities.kb;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.lutheroaks.tacoswebsite.entities.member.Member;
import com.lutheroaks.tacoswebsite.entities.tag.Tag;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "KBPost")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class KBPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @Column(nullable = false, unique = false, length = 200)
    @NonNull private String title;

    @Column(nullable = false, unique = false, length = 2000)
    @NonNull private String content;

    @ManyToOne
    @JoinColumn(name="member", nullable = false)
    @NonNull private Member member;

    @Column(nullable = false, unique = false, length = 100)
    @NonNull private Timestamp timeStamp;
    
    @ManyToMany
    @ElementCollection(targetClass = Tag.class)
    @Size(min=0, max=3)
    private List<Tag> associatedTags;
}
