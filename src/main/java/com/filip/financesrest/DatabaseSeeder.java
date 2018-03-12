package com.filip.financesrest;

import com.filip.financesrest.models.FinanceEntry;
import com.filip.financesrest.models.User;
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
    @Autowired
    public DatabaseSeeder(EntryRepository entryRepository, UserService userService, RoleRepository roleRepository)
    {
        this.entryRepository = entryRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
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



        List<FinanceEntry> entries = new ArrayList<>();
        entries.add(new FinanceEntry("Bought a new car", -30000, LocalDate.of(2017, 10, 5), user1));
        entries.add(new FinanceEntry("Received a monthly pay", 13000,LocalDate.of(2017, 9, 13), user1));
        entries.add(new FinanceEntry("Received a spouse's monthly pay", 40000,LocalDate.of(2016, 5, 27), user2));
        entries.add(new FinanceEntry("Bought food", 8500,LocalDate.of(2017, 12, 13), user2));
        entryRepository.save(entries);
    }
}
