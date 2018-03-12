package com.filip.financesrest.repositories;


import com.filip.financesrest.models.EntryCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<EntryCategory, Long>
{
}
