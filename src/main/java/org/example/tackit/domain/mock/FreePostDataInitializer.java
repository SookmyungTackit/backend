package org.example.tackit.domain.mock;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.entity.FreePost;
import org.example.tackit.domain.entity.Post;
import org.example.tackit.domain.entity.Status;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.free_post.repository.FreePostJPARepository;
import org.example.tackit.domain.free_post.repository.MemberJPARepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Order(2)
public class FreePostDataInitializer implements CommandLineRunner {
    private final MemberJPARepository memberRepository;
    private final FreePostJPARepository freePostRepository;

    @Override
    public void run(final String... args) throws Exception {
        if (freePostRepository.count() == 0) {
            Member writer = memberRepository.findTopByOrderByIdAsc();

            if (writer != null) {
                FreePost post = FreePost.builder()
                        .writer(writer)
                        .title("첫 번째 자유 글")
                        .content("첫 번째 자유 게시판 글입니다.")
                        .createdAt(LocalDateTime.now())
                        .type(Post.Free)
                        .status(Status.ACTIVE)
                        .reportCount(0)
                        .build();

                freePostRepository.save(post);
            }
        }
    }
}
