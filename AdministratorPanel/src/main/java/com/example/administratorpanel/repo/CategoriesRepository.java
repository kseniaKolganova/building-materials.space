package com.example.administratorpanel.repo;

import com.example.administratorpanel.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoriesRepository extends JpaRepository<Categories, Long> {

    Categories findByName(String stringCellValue);
}
