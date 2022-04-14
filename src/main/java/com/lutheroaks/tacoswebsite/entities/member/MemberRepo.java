package com.lutheroaks.tacoswebsite.entities.member;

import java.util.List;

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

    @Query (nativeQuery = true, value="SELECT first_name + ' ' + last_name FROM Member where member_id = ?1")
    String findMemberName(int memberId);

    @Query (nativeQuery = true, value="SELECT member_id FROM Member")
    List<Integer> findMemberIds();

    @Modifying
    @Query("DELETE FROM Member WHERE member_id = ?1")
    void deleteMemberById(int memberId);

}
