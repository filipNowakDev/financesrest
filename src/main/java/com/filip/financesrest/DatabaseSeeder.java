package com.filip.financesrest;

import com.filip.financesrest.models.FinanceEntry;
import com.filip.financesrest.repositories.EntriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private EntriesRepository entriesRepository;

    @Autowired
    public DatabaseSeeder(EntriesRepository entriesRepository){
        this.entriesRepository = entriesRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        List<FinanceEntry> entries = new ArrayList<>();
        entries.add(new FinanceEntry("Bought a new car", -30000));
        entries.add(new FinanceEntry("Received a monthly pay", 13000));
        entries.add(new FinanceEntry("Received a spouse's monthly pay", 40000));
        entries.add(new FinanceEntry("Bought food", 8500));
        entriesRepository.save(entries);
    }
}
