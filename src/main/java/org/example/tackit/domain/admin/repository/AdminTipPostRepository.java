package org.example.tackit.domain.admin.repository;

import org.example.tackit.domain.entity.Status;
import org.example.tackit.domain.entity.TipPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminTipPostRepository extends JpaRepository<TipPost, Long> {
    List <TipPost> findAllByStatus(Status status);
}
