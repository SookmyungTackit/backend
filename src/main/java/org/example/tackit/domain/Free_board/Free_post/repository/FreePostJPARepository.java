package org.example.tackit.domain.Free_board.Free_post.repository;

import org.example.tackit.domain.entity.FreePost;
import org.example.tackit.domain.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FreePostJPARepository extends JpaRepository<FreePost, Long> {
    List<FreePost> findAllByStatus(Status status);

    List<FreePost> findByOrganizationAndStatus(String organization, Status status);

    Optional<FreePost> findByIdAndOrganizationAndStatus(Long id, String organization, Status status);
}
