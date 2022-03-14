package com.lutheroaks.tacoswebsite.resident;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentRepo extends JpaRepository<Resident,Integer> {

    //returns list of residents by first AND last name
    @Query(nativeQuery = true, value = "select * from Resident where firstName = ?1 and lastName = ?1")
    List<Object> findResidentByName(String firstName, String lastName);

}
