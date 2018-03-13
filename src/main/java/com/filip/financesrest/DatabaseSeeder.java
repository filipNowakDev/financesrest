package com.filip.financesrest;

import com.filip.financesrest.models.EntryCategory;
import com.filip.financesrest.models.FinanceEntry;
import com.filip.financesrest.models.User;
import com.filip.financesrest.repositories.CategoryRepository;
import com.filip.financesrest.repositories.EntryRepository;
import com.filip.financesrest.repositories.RoleRepository;
import com.filip.financesrest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner
{
    private EntryRepository entryRepository;
    private UserService userService;
    private RoleRepository roleRepository;
    private CategoryRepository categoryRepository;
    @Autowired
    public DatabaseSeeder(EntryRepository entryRepository, UserService userService, RoleRepository roleRepository, CategoryRepository categoryRepository)
    {
        this.entryRepository = entryRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... strings) throws Exception
    {
        //Role role1 = new Role();
        //role1.setName("USER");
        //Set<Role> roles1 = new HashSet<Role>();
        //roles1.add(role1);

        User user1 = new User();
        user1.setUsername("username1");
        user1.setPassword("password");
        //user1.setRoles(roles1);

        //roleRepository.save(roles1);
        userService.save(user1);


        User user2 = new User();
        user2.setUsername("username2");
        user2.setPassword("password");
        //user1.setRoles(roles1);

        //roleRepository.save(roles1);
        userService.save(user2);

        EntryCategory category1 = new EntryCategory();
        category1.setUser(user1);
        category1.setName("Expense");

        categoryRepository.save(category1);

        EntryCategory category2 = new EntryCategory();
        category2.setUser(user2);
        category2.setName("Fun");

        categoryRepository.save(category2);

        List<FinanceEntry> entries = new ArrayList<>();
        entries.add(new FinanceEntry("Bought a new car", -30000, LocalDate.of(2017, 10, 5), user1, category1));
        entries.add(new FinanceEntry("Received a monthly pay", 13000,LocalDate.of(2017, 9, 13), user1, category1));
        entries.add(new FinanceEntry("Received a spouse's monthly pay", 40000,LocalDate.of(2016, 5, 27), user2, category2));
        entries.add(new FinanceEntry("Bought food", 8500,LocalDate.of(2017, 12, 13), user2, category2));
        entryRepository.save(entries);
    }
}
