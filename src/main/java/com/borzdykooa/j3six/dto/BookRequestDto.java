package com.borzdykooa.j3six.dto;

import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class BookRequestDto {

    private List<String> authorNames;
    private List<Genre> genres;

    @Valid
    private PeriodDto periodDto;
}
