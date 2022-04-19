package com.lutheroaks.tacoswebsite.entities.tag;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lutheroaks.tacoswebsite.entities.kb.KBPost;
import com.lutheroaks.tacoswebsite.entities.ticket.Ticket;

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
    @Column(nullable = false, unique = true, length = 100)
    private String tagString;

    @ManyToMany(mappedBy = "associatedTags")
    @JsonIgnore
    private List<Ticket> taggedTickets;

    @ManyToMany(mappedBy = "associatedTags")
    @JsonIgnore
    private List<KBPost> taggedPosts;
}
