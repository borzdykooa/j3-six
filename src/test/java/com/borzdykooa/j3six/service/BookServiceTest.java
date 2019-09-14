package com.borzdykooa.j3six.service;

import com.borzdykooa.j3six.dto.BookRequestDto;
import com.borzdykooa.j3six.dto.Genre;
import com.borzdykooa.j3six.dto.PeriodDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    private StringWriter stringWriter;
    private PrintWriter printWriter;
    private BookRequestDto bookRequestDto;

    @Before
    public void init() {
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        bookRequestDto = new BookRequestDto();
    }

    @Test
    public void testExportBooksCheckAuthors() {
        bookRequestDto.setAuthorNames(Arrays.asList("Agatha Christie", "George Orwell"));

        bookService.exportBooks(bookRequestDto, printWriter);
        String result = stringWriter.getBuffer().toString().trim();
        String[] lines = result.split(System.lineSeparator());

        assertEquals(4, lines.length);
        assertTrue(lines[0].contains("name,create year,genre,author"));
        assertTrue(lines[1].contains("Murder on The Orient Express,1934,Detective,Agatha Christie"));
        assertTrue(lines[2].contains("Mysterious Affair at Styles,1920,Detective,Agatha Christie"));
        assertTrue(lines[3].contains("1984,1948,Science fiction,George Orwell"));
    }

    @Test
    public void testExportBooksCheckAuthorsAndGenres() {
        bookRequestDto.setAuthorNames(Arrays.asList("Agatha Christie", "George Orwell"));
        bookRequestDto.setGenres(Arrays.asList(Genre.DETECTIVE, Genre.POETRY));

        bookService.exportBooks(bookRequestDto, printWriter);
        String result = stringWriter.getBuffer().toString().trim();
        String[] lines = result.split(System.lineSeparator());

        assertEquals(3, lines.length);
        assertTrue(lines[0].contains("name,create year,genre,author"));
        assertTrue(lines[1].contains("Murder on The Orient Express,1934,Detective,Agatha Christie"));
        assertTrue(lines[2].contains("Mysterious Affair at Styles,1920,Detective,Agatha Christie"));
    }

    @Test
    public void testExportBooksCheckGenresAndPeriod() {
        bookRequestDto.setGenres(Arrays.asList(Genre.DETECTIVE, Genre.POETRY));
        bookRequestDto.setPeriodDto(new PeriodDto(1900, 2000));

        bookService.exportBooks(bookRequestDto, printWriter);
        String result = stringWriter.getBuffer().toString().trim();
        String[] lines = result.split(System.lineSeparator());

        assertEquals(3, lines.length);
        assertTrue(lines[0].contains("name,create year,genre,author"));
        assertTrue(lines[1].contains("Murder on The Orient Express,1934,Detective,Agatha Christie"));
        assertTrue(lines[2].contains("Mysterious Affair at Styles,1920,Detective,Agatha Christie"));
    }

    @Test
    public void testExportBooksCheckAllWithPeriod() {
        bookRequestDto.setGenres(Arrays.asList(Genre.DETECTIVE, Genre.POETRY, Genre.SCIENCE_FICTION));
        bookRequestDto.setAuthorNames(Arrays.asList("Agatha Christie", "Arthur Conan Doyle", "Frank Herbert",
                "George Orwell", "Shakespeare"));
        bookRequestDto.setPeriodDto(new PeriodDto(1600, 2000));

        bookService.exportBooks(bookRequestDto, printWriter);
        String result = stringWriter.getBuffer().toString().trim();
        String[] lines = result.split(System.lineSeparator());

        assertEquals(7, lines.length);
        assertTrue(lines[0].contains("name,create year,genre,author"));
        assertTrue(lines[1].contains("Murder on The Orient Express,1934,Detective,Agatha Christie"));
        assertTrue(lines[2].contains("Mysterious Affair at Styles,1920,Detective,Agatha Christie"));
        assertTrue(lines[3].contains("Sherlock Holmes,1892,Detective,Arthur Conan Doyle"));
        assertTrue(lines[4].contains("Dune,1965,Science fiction,Frank Herbert"));
        assertTrue(lines[5].contains("1984,1948,Science fiction,George Orwell"));
        assertTrue(lines[6].contains("The Tragedy of Othello,1604,Poetry,Shakespeare"));
    }
}
