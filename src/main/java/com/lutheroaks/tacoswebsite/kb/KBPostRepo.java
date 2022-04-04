package com.lutheroaks.tacoswebsite.kb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KBPostRepo extends JpaRepository<KBPost,Integer> {

    @Modifying
    @Query(value = "DELETE FROM kbpost WHERE post_id = ?1", nativeQuery = true)
    public void deleteKBPostById(int postId);
}
