package com.borzdykooa.j3six.controller;

import com.borzdykooa.j3six.dto.BookRequestDto;
import com.borzdykooa.j3six.exception.ApplicationException;
import com.borzdykooa.j3six.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping(value = "/get-csv", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void getCsv(HttpServletResponse response, @NotNull @Valid @RequestBody BookRequestDto dto) {
        if (dto.getAuthorNames() == null && dto.getGenres() == null && dto.getPeriodDto() == null) {
            throw new ApplicationException("BookRequestDto is invalid");
        }
        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=books.csv");
            bookService.exportBooks(dto, response.getWriter());
        } catch (IOException e) {
            throw new ApplicationException("IOException occurred in BookController::getCsv");
        }
    }
}
