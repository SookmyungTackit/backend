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
                    QnATag.builder().tagName("회사생활").build(),
                    QnATag.builder().tagName("복지혜택").build(),
                    QnATag.builder().tagName("조언구해요").build(),
                    QnATag.builder().tagName("실수했어요").build(),
                    QnATag.builder().tagName("추천해주세요").build(),
                    QnATag.builder().tagName("도움요청").build(),
                    QnATag.builder().tagName("초보").build(),
                    QnATag.builder().tagName("프로세스").build()
            ));
        }
    }

}
