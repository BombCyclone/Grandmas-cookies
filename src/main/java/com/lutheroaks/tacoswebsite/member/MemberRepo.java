package com.lutheroaks.tacoswebsite.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepo extends JpaRepository<Member,Integer> {

    // queries the table for a member that matches a given email address
    @Query("SELECT x FROM Member x WHERE x.email = :email")
    Member findMemberByEmail(@Param("email") String email);

    @Modifying
    @Query("DELETE FROM Member WHERE member_id = ?1")
    void deleteMemberById(int memberId);

}
