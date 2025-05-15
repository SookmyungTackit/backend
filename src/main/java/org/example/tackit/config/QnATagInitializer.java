package org.example.tackit.config;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.QnA_board.QnA_tag.repository.QnATagRepository;
import org.example.tackit.domain.entity.QnATag;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QnATagInitializer implements CommandLineRunner {
    private final QnATagRepository tagRepository;

    @Override
    public void run(String... args) {
        if (tagRepository.count() == 0) {
            tagRepository.saveAll(List.of(
                    QnATag.builder().tagName("태그1").build(),
                    QnATag.builder().tagName("태그2").build(),
                    QnATag.builder().tagName("태그3").build()
            ));
        }
    }

}
