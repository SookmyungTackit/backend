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
                    FreeTag.builder().tagName("태그1").build(),
                    FreeTag.builder().tagName("태그2").build(),
                    FreeTag.builder().tagName("태그3").build()
            ));
        }
    }

}
