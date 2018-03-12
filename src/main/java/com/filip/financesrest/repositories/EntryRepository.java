package com.filip.financesrest.repositories;


import com.filip.financesrest.models.FinanceEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntriesRepository extends JpaRepository<FinanceEntry, Long> {
    List<FinanceEntry> findByUser_UsernameOrderByDateAsc(String username);
    List<FinanceEntry> findByUser_UsernameOrderByDateDesc(String username);
    List<FinanceEntry> findByUser_UsernameOrderByValueAsc(String username);
    List<FinanceEntry> findByUser_UsernameOrderByValueDesc(String username);
}
