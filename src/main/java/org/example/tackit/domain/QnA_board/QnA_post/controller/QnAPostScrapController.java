package org.example.tackit.domain.QnA_board.QnA_post.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.example.tackit.domain.QnA_board.QnA_post.dto.response.QnACheckScrapResponseDto;
import org.example.tackit.domain.QnA_board.QnA_post.dto.response.QnAScrapResponseDto;
import org.example.tackit.domain.QnA_board.QnA_post.service.QnAScrapService;
import org.example.tackit.domain.auth.login.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/scrap")
    public ResponseEntity<List<QnACheckScrapResponseDto>> getMyScrapList(@AuthenticationPrincipal CustomUserDetails userDetails){
        String email = userDetails.getUsername();
        List<QnACheckScrapResponseDto> scraps = qnAScrapService.getMyQnAScraps(email);
        return ResponseEntity.ok(scraps);
    }

}
