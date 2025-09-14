package org.example.tackit.domain.admin.repository;

import org.example.tackit.domain.entity.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogRepository extends JpaRepository<UserLog, Long> {
}
