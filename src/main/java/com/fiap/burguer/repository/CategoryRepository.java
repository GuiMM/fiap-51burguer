package com.fiap.burguer.repository;
import com.fiap.burguer.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category save(Category category);
    Category findById(int id);
}
