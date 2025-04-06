package org.example.tackit.domain.free_post.repository;

import org.example.tackit.domain.entity.FreePost;
import org.example.tackit.domain.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreePostJPARepository extends JpaRepository<FreePost, Long> {
    List<FreePost> findAllByStatus(Status status);
}
