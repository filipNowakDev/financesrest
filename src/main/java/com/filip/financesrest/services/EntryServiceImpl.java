package com.filip.financesrest.services;

import com.filip.financesrest.models.FinanceEntry;
import com.filip.financesrest.models.User;
import com.filip.financesrest.repositories.EntriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntryServiceImpl implements EntryService
{
    @Autowired
    private EntriesRepository entriesRepository;
    @Autowired
    private UserService userService;

    @Override
    public void save(FinanceEntry entry)
    {
        entriesRepository.save(entry);
    }

    @Override
    public void delete(Long id)
    {
        entriesRepository.delete(id);
    }


    @Override
    public FinanceEntry findOne(Long id)
    {
        return entriesRepository.findOne(id);
    }

    @Override
    public List<FinanceEntry> findAll()
    {
        return entriesRepository.findAll();
    }

    @Override
    public String getOwnerName(Long id)
    {
        FinanceEntry entry = entriesRepository.findOne(id);
        return entry.getUser().getUsername();
    }

    @Override
    public Boolean isOwner(User user, Long id)
    {
        FinanceEntry entry = entriesRepository.findOne(id);
        return entry.getUser().getUsername().equals(user.getUsername());

    }

    @Override
    public Boolean isOwner(Authentication authentication, Long id)
    {
        FinanceEntry entry = entriesRepository.findOne(id);
        return authentication.getName().equals(entry.getUser().getUsername());
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
