package org.example.tackit.global.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

@Getter
public class PageResponseDTO<T> {
    private final List<T> content;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    private PageResponseDTO(Page<T> page) {
        this.content = page.getContent();
        this.page = page.getNumber();
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }

    public static <T, R> PageResponseDTO<R> from(Page<T> page, Function<T, R> mapper) {
        Page<R> mapped = page.map(mapper);
        return new PageResponseDTO<>(mapped);
    }

}
