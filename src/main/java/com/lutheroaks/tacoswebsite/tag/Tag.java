package com.lutheroaks.tacoswebsite.tag;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.lutheroaks.tacoswebsite.kb.KBPost;
import com.lutheroaks.tacoswebsite.ticket.Ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id
    @Column(nullable = false, unique = false, length = 100)
    private String tagString;

    @ManyToMany(mappedBy = "associatedTags")
    private Set<Ticket> taggedTickets;

    @ManyToMany(mappedBy = "associatedTags")
    private Set<KBPost> taggedPosts;
}
