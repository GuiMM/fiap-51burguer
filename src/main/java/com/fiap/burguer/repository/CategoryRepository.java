package com.fiap.burguer.repository;

import com.fiap.burguer.entities.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
    Category save(Category category);
    Category findById(int id);
}
