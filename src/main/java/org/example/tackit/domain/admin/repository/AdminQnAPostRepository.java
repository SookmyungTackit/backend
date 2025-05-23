package org.example.tackit.domain.admin.repository;

import org.example.tackit.domain.entity.QnAPost;
import org.example.tackit.domain.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AdminQnAPostRepository extends JpaRepository<QnAPost, Long> {
    List<QnAPost> findAllByStatus(Status status);
}
