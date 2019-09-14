package com.borzdykooa.j3six.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDto {

    private String bookName;
    private Integer bookCreationYear;
    private Genre genre;
    private String authorName;
}
