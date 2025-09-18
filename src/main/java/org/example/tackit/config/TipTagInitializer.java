package org.example.tackit.config;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.Tip_board.Tip_tag.repository.TipTagRepository;
import org.example.tackit.domain.entity.TipTag;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TipTagInitializer implements CommandLineRunner {
    private final TipTagRepository tagRepository;

    @Override
    public void run(String... args) {
        if (tagRepository.count() == 0) {
            tagRepository.saveAll(List.of(
                    TipTag.builder().tagName("업무팁").build(),
                    TipTag.builder().tagName("인수인계").build(),
                    TipTag.builder().tagName("꼭지켜주세요").build(),
                    TipTag.builder().tagName("조직문화").build(),
                    TipTag.builder().tagName("신입FAQ").build(),
                    TipTag.builder().tagName("업무절차").build(),
                    TipTag.builder().tagName("현장노하우").build(),
                    TipTag.builder().tagName("실무경험담").build()
            ));
        }
    }

}
