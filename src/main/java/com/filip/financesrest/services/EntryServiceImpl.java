package com.filip.financesrest.services;

import com.filip.financesrest.models.FinanceEntry;
import com.filip.financesrest.models.User;
import com.filip.financesrest.repositories.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntryServiceImpl implements EntryService
{
    @Autowired
    private EntryRepository entryRepository;
    @Autowired
    private UserService userService;

    @Override
    public void save(FinanceEntry entry)
    {
        entryRepository.save(entry);
    }

    @Override
    public void delete(Long id)
    {
        entryRepository.delete(id);
    }


    @Override
    public FinanceEntry findOne(Long id)
    {
        return entryRepository.findOne(id);
    }

    @Override
    public List<FinanceEntry> findAll()
    {
        return entryRepository.findAll();
    }

    @Override
    public String getOwnerName(Long id)
    {
        FinanceEntry entry = entryRepository.findOne(id);
        return entry.getUser().getUsername();
    }

    @Override
    public Boolean isOwner(User user, Long id)
    {
        FinanceEntry entry = entryRepository.findOne(id);
        return entry.getUser().getUsername().equals(user.getUsername());

    }

    @Override
    public Boolean isOwner(Authentication authentication, Long id)
    {
        FinanceEntry entry = entryRepository.findOne(id);
        return authentication.getName().equals(entry.getUser().getUsername());
    }

    @Override
    public List<FinanceEntry> findByUser_UsernameOrderByDateAsc(String username)
    {
        return entryRepository.findByUser_UsernameOrderByDateAsc(username);
    }

    @Override
    public List<FinanceEntry> findByUser_UsernameOrderByDateDesc(String username)
    {
        return entryRepository.findByUser_UsernameOrderByDateDesc(username);
    }

    @Override
    public List<FinanceEntry> findByUser_UsernameOrderByValueAsc(String username)
    {
        return entryRepository.findByUser_UsernameOrderByValueAsc(username);
    }

    @Override
    public List<FinanceEntry> findByUser_UsernameOrderByValueDesc(String username)
    {
        return entryRepository.findByUser_UsernameOrderByValueDesc(username);
    }


    @Override
    public Double getBalance(Authentication authentication)
    {
        User user = userService.findByUsername(authentication.getName());
        List<FinanceEntry> entries = user.getEntries();
        Double sum = new Double(0);
        for(FinanceEntry entry : entries)
        {
            sum += entry.getValue();
        }
        return sum;
    }
}
