package com.filip.financesrest.repositories;


import com.filip.financesrest.models.EntryCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<EntryCategory, Long>
{
    List<EntryCategory> findByUser_Username(String username);
    List<EntryCategory> findByUser_UsernameOrderByName(String username);
}
