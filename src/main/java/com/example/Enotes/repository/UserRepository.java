package com.example.Enotes.repository;

import com.example.Enotes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    Boolean existsByEmail(String email);
}
