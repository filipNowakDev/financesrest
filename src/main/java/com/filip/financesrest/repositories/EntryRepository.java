package com.filip.financesrest.repositories;


import com.filip.financesrest.models.FinanceEntry;
import com.filip.financesrest.models.ILocalDateProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<FinanceEntry, Long>
{
	List<FinanceEntry> findByUser_UsernameOrderByDateAsc(String username);

	List<FinanceEntry> findByUser_UsernameOrderByDateDesc(String username);

	List<FinanceEntry> findByUser_UsernameOrderByValueAsc(String username);

	List<FinanceEntry> findByUser_UsernameOrderByValueDesc(String username);

	List<FinanceEntry> findByUser_UsernameAndCategory_Id(String username, Long id);

	List<FinanceEntry> findByUser_UsernameAndCategory_IdOrderByDateAsc(String username, Long id);

	List<FinanceEntry> findByUser_UsernameAndCategory_IdOrderByDateDesc(String username, Long id);

	List<FinanceEntry> findByUser_UsernameAndCategory_IdOrderByValueAsc(String username, Long id);

	List<FinanceEntry> findByUser_UsernameAndCategory_IdOrderByValueDesc(String username, Long id);

	//TODO implement this query
	//select distinct month(finance_entry.date), year(finance_entry.date)  from finance_entry order by date;

	@Query(value = "SELECT DISTINCT MONTH(finance_entry.date), YEAR(finance_entry.date) FROM finance_entry ORDER BY date DESC", nativeQuery = true)
	List<ILocalDateProjection> selectDistinctEntryMonths();

	@Query(value = "SELECT SUM(value) FROM finance_entry WHERE category_id = ?1 AND MONTH(date) = ?2 AND YEAR(date) = ?3", nativeQuery=true)
	Integer selectBalanceByMonth(long categoryId, int month, int year);
}
