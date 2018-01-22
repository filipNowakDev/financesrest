package com.filip.financesrest.repositories;


import com.filip.financesrest.models.FinanceEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntriesRepository extends JpaRepository<FinanceEntry, Long> {
}
