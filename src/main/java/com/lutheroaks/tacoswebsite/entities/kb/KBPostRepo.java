package com.lutheroaks.tacoswebsite.entities.kb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KBPostRepo extends JpaRepository<KBPost,Integer> {

    @Query("SELECT x FROM KBPost x WHERE x.postId = :id")
    KBPost findPostById(@Param("id") int id);

    @Modifying
    @Query(value = "DELETE FROM kbpost WHERE post_id = ?1", nativeQuery = true)
    void deleteKBPostById(int postId);

   /* @Modifying
    @Query(value = "SELECT FROM kbpost WHERE post_id = ?1", nativeQuery = true)
    KBPost findKBPostById(int postId);
    */

    @Query("SELECT x FROM KBPost x WHERE x.postid = :id")
    KBPost findKBPostById(@Param("id")int id);
}
