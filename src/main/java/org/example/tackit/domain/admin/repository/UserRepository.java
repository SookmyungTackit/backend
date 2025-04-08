package org.example.tackit.domain.admin.repository;

import org.example.tackit.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u ORDER BY " +
            "CASE WHEN u.status = 0 THEN 0 ELSE 1 END")
    List<User> findAllOrderByStatus();
}
