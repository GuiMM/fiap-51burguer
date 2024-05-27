package com.fiap.burguer.repository;

import com.fiap.burguer.entities.CheckOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutRepository extends JpaRepository<CheckOut, Integer> {
//Método para salvar o checkout
CheckOut save(int id);
//Método para consultar o checkout pelo id do Pedido
CheckOut findById(int id);


}
