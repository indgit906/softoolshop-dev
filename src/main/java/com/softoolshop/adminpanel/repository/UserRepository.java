package com.softoolshop.adminpanel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softoolshop.adminpanel.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
}
