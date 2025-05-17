package org.example.tackit.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.Free_board.Free_post.repository.FreeScrapJPARepository;
import org.example.tackit.domain.entity.FreeScrap;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.mypage.dto.response.FreeScrapResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageFreeService {
    private final FreeScrapJPARepository freeScrapJPARepository;

    public List<FreeScrapResponse> getScrapListByMember(Member member) {
        List<FreeScrap> scraps = freeScrapJPARepository.findByMember(member);

        return scraps.stream()
                .map(FreeScrapResponse::from)
                .collect(Collectors.toList());
    }
}
