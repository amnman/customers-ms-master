package com.edureka.customersms;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    @Transactional
    @Modifying
    @Query("update Customer c set c.isbn = :isbn where c.cid = :cid")
    void setIsbn(@Param("isbn") Integer isbn, @Param("cid") Integer cid);
    List<Customer> findByCid(Integer cid);

}
