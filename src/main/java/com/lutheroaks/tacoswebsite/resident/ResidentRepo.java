package com.lutheroaks.tacoswebsite.resident;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentRepo extends JpaRepository<Resident,Integer> {

    //returns list of residents with matching first and last name
    @Query(nativeQuery = true, value = "select * from Resident where upper(first_name) = ?1 and upper(last_name) = ?2")
    List<Object> findResidentByName(String firstName, String lastName);

}
