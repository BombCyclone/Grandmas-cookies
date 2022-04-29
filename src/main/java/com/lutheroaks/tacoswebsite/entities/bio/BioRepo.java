package com.lutheroaks.tacoswebsite.entities.bio;

import com.lutheroaks.tacoswebsite.entities.member.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BioRepo extends JpaRepository<Bio,Integer> {
    @Query("SELECT x FROM Bio x WHERE x.member = :member")
    Bio findBioByMember(@Param("member") Member member);

    @Query (nativeQuery = true, value="SELECT profile_picture FROM Bio where bio_id = ?1")
    byte[] getProfilePicture(int bioId);

    @Modifying
    @Query("DELETE FROM Bio WHERE bio_id = ?1")
    void deleteBioById(int bioId);
}
