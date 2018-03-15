package com.filip.financesrest.services;

import com.filip.financesrest.models.FinanceEntry;
import com.filip.financesrest.models.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface EntryService
{
    void save(FinanceEntry entry);
    void delete(Long id);
    FinanceEntry findOne(Long id);
    List<FinanceEntry> findAll();
    String getOwnerName(Long id);
    public Boolean isOwner(User user, Long id);
    public Boolean isOwner(Authentication authentication, Long id);
    Double getBalance(Authentication authentication);
    Double getBalanceByCategory(Long categoryId);
    List<FinanceEntry> findByUser_UsernameOrderByDateAsc(String username);
    List<FinanceEntry> findByUser_UsernameOrderByDateDesc(String username);
    List<FinanceEntry> findByUser_UsernameOrderByValueAsc(String username);
    List<FinanceEntry> findByUser_UsernameOrderByValueDesc(String username);
    List<FinanceEntry> findByUser_UsernameAndCategory_Id(String username, Long id);
}
