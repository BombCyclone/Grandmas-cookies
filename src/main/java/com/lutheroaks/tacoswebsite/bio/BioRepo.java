package com.lutheroaks.tacoswebsite.bio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BioRepo extends JpaRepository<Bio,Integer> {
    
}
