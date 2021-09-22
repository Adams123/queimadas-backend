package com.queimadas.repository;

import com.queimadas.model.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, UUID> {
}
