package com.borzdykooa.j3six.service;

import com.borzdykooa.j3six.dto.BookRequestDto;
import com.borzdykooa.j3six.dto.BookResponseDto;
import com.borzdykooa.j3six.exception.ApplicationException;
import com.borzdykooa.j3six.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public void exportBooks(BookRequestDto bookRequestDto, PrintWriter writer) {
        List<BookResponseDto> dtos = bookRepository.getBooks(bookRequestDto);
        String[] headers = {"name", "create year", "genre", "author"};
        try (CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headers))) {
            for (BookResponseDto dto : dtos) {
                printer.printRecord(
                        dto.getBookName(),
                        dto.getBookCreationYear(),
                        dto.getGenre().getValue(),
                        dto.getAuthorName()
                );
            }
        } catch (IOException e) {
            throw new ApplicationException("IOException occurred in BookService::exportBooks");
        }
    }
}
