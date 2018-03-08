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
}
