package com.filip.financesrest;

import com.filip.financesrest.models.FinanceEntry;
import com.filip.financesrest.models.Role;
import com.filip.financesrest.models.User;
import com.filip.financesrest.repositories.EntriesRepository;
import com.filip.financesrest.repositories.RoleRepository;
import com.filip.financesrest.repositories.UserRepository;
import com.filip.financesrest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DatabaseSeeder implements CommandLineRunner
{
    private EntriesRepository entriesRepository;
    private UserService userService;
    private RoleRepository roleRepository;
    @Autowired
    public DatabaseSeeder(EntriesRepository entriesRepository, UserService userService, RoleRepository roleRepository)
    {
        this.entriesRepository = entriesRepository;
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
        entries.add(new FinanceEntry("Bought a new car", -30000, user1));
        entries.add(new FinanceEntry("Received a monthly pay", 13000, user1));
        entries.add(new FinanceEntry("Received a spouse's monthly pay", 40000, user2));
        entries.add(new FinanceEntry("Bought food", 8500, user2));
        entriesRepository.save(entries);
    }
}
