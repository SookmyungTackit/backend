package org.example.tackit.config;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.Free_board.Free_tag.repository.FreeTagRepository;
import org.example.tackit.domain.entity.FreeTag;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FreeTagInitializer implements CommandLineRunner {
    private final FreeTagRepository tagRepository;

    @Override
    public void run(String... args) {
        if (tagRepository.count() == 0) {
            tagRepository.saveAll(List.of(
                    FreeTag.builder().tagName("회고").build(),
                    FreeTag.builder().tagName("내가배운것").build(),
                    FreeTag.builder().tagName("일상").build(),
                    FreeTag.builder().tagName("공유").build(),
                    FreeTag.builder().tagName("추천").build(),
                    FreeTag.builder().tagName("맛집").build(),
                    FreeTag.builder().tagName("사내편의").build(),
                    FreeTag.builder().tagName("근처편의").build()
            ));
        }
    }

}
