package org.example.tackit.domain.QnA_board.QnA_post.controller;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.QnA_board.QnA_post.dto.response.QnAScrapResponseDto;
import org.example.tackit.domain.QnA_board.QnA_post.service.QnAScrapService;
import org.example.tackit.domain.auth.login.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna_post")
public class QnAPostScrapController {
    private final QnAScrapService qnAScrapService;

    @PostMapping("/{postId}/scrap")
    public ResponseEntity<QnAScrapResponseDto> toggleScrap(@PathVariable long postId, @AuthenticationPrincipal CustomUserDetails userDetails){
        String org = userDetails.getOrganization();
        String email = userDetails.getUsername();
        QnAScrapResponseDto response = qnAScrapService.toggleScrap(postId, email, org);
        return ResponseEntity.ok(response);
    }
}
