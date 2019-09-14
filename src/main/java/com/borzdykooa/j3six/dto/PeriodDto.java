package com.borzdykooa.j3six.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodDto {

    @NotNull
    private Integer from;

    @NotNull
    private Integer to;
}
