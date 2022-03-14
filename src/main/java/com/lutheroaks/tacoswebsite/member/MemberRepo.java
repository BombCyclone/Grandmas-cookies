package com.lutheroaks.tacoswebsite.member;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepo extends JpaRepository<Member,Integer> {

    //returns list of members by email
    @Query(nativeQuery = true, value = "select * from Member where email = ?1")
    List<Object> findMemberByEmail(String email);
    
}
