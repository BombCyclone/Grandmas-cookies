package com.lutheroaks.tacoswebsite.kb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KBPostRepo extends JpaRepository<KBPost,Integer> {

}