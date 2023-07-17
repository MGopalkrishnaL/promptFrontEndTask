package com.Wipro.Student.repository;

import com.Wipro.Student.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
