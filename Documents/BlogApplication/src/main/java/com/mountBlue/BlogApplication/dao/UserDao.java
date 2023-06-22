package com.mountBlue.BlogApplication.dao;

import com.mountBlue.BlogApplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    List<User> findAllByName(String name);

    Optional<User> findByName(String name);

    boolean existsByName(String author);
}
