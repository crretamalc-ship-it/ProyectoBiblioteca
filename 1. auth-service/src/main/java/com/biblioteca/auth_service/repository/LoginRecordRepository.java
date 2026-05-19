package com.biblioteca.auth_service.repository;


import com.biblioteca.auth_service.model.LoginRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRecordRepository extends JpaRepository<LoginRecord, Long> {
    
}