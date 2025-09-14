package org.example.tackit.domain.admin.service;

import org.example.tackit.domain.admin.dto.ReportedPostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ReportedPostService {
    Page<ReportedPostDTO> getDeletedPosts(Pageable pageable);
    void deletePost(Long id);
    void activatePost(Long id);

}
