package com.ufscar.queimadas.repository;

import com.ufscar.queimadas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findById(UUID id);
    Optional<User> findByName(String name);
    Optional<User> findByNameAndPassword(String name, String password);

    <T> Optional<T> findById(UUID id, Class<T> projectionClass);
    <T> Optional<T> findByName(String name, Class<T> projectionClass);
}
