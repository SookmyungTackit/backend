package org.example.tackit.domain.admin.repository;

import org.example.tackit.domain.entity.FreePost;
import org.example.tackit.domain.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminFreePostRepository extends JpaRepository<FreePost, Long> {

    Page<FreePost> findAllByStatusAndReportCountGreaterThanEqual(Status status, int reportCount, Pageable pageable);




}
