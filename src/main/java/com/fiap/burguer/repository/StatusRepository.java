package com.fiap.burguer.repository;

import com.fiap.burguer.entities.Category;
import com.fiap.burguer.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {
    Status save(Status status);
    Status findById(int id);
}
