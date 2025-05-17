package org.example.tackit.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.TipScrap;
import org.example.tackit.domain.mypage.dto.response.TipScrapResponse;
import org.example.tackit.domain.Tip_board.repository.TipScrapRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MypageTipService {
    private final TipScrapRepository tipScrapRepository;

    public List<TipScrapResponse> getScrapListByMember(Member member) {
        List<TipScrap> scraps = tipScrapRepository.findByMember(member);
        return scraps.stream()
                .map(TipScrapResponse::from)
                .collect(Collectors.toList());
    }
}
