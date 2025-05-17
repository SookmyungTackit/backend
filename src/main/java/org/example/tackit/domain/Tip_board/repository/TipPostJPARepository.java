package org.example.tackit.domain.Tip_board.repository;


import org.example.tackit.domain.entity.Status;
import org.example.tackit.domain.entity.TipPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TipPostJPARepository extends JpaRepository<TipPost, Long> {
    List<TipPost> findAllByStatus(Status status);

    List<TipPost> findByOrganizationAndStatus(String organization, Status status);

    Optional<TipPost> findByIdAndOrganizationAndStatus(Long id, String organization, Status status);
}
