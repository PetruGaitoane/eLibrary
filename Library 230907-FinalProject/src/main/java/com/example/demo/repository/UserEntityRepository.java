package com.example.demo.repository;

import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findUserByUserName(String userName);
    List<UserEntity> findUserByUserContactNumber(int userContactNumber);
    List<UserEntity> findUserByUserEmailIgnoreCase(String userEmail);

}
