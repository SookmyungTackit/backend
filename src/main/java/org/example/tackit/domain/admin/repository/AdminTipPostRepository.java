package org.example.tackit.domain.admin.repository;

import org.example.tackit.domain.entity.Status;
import org.example.tackit.domain.entity.TipPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminTipPostRepository extends JpaRepository<TipPost, Long> {
    Page<TipPost> findAllByStatus(Status status, Pageable pageable);
}
