package com.qa.toDoList.data.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.qa.toDoList.data.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {


}