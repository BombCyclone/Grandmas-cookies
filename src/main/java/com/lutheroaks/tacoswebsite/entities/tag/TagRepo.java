package com.lutheroaks.tacoswebsite.entities.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepo extends JpaRepository<Tag,Integer> {
    
    @Query("SELECT x FROM Tag x WHERE x.tagString = :tagString")
    Tag findTag(String tagString);

    @Modifying
    @Query("DELETE FROM Tag WHERE tag_string = ?1")
    void deleteTag(String tagString);
}
