package org.example.tackit.domain.admin.service;

import org.example.tackit.domain.admin.dto.ReportedPostDTO;

import java.util.List;

public interface ReportedPostService {
    List<ReportedPostDTO> getDeletedPosts();
    void deletePost(Long id);
    void activatePost(Long id);
}
