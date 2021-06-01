package com.queimadas.repository;

import com.queimadas.model.Location;
import com.queimadas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {

    List<Location> findAllBySentUsersNotContaining(User user);
}
