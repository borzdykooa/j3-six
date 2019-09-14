package com.borzdykooa.j3six.repository;

import com.borzdykooa.j3six.dto.BookRequestDto;
import com.borzdykooa.j3six.dto.BookResponseDto;
import com.borzdykooa.j3six.dto.Genre;
import com.borzdykooa.j3six.util.BookResponseRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private static final String AND = " AND ";
    private static final String BASE_QUERY = "SELECT " +
            "  book_storage.book.name          AS book_name, " +
            "  book_storage.book.creation_year AS book_creation_year, " +
            "  book_storage.genre.name         AS book_genre, " +
            "  book_storage.author.name        AS author_name " +
            "FROM book_storage.book " +
            "  LEFT JOIN book_storage.author ON book_storage.book.author_id = book_storage.author.id " +
            "  LEFT JOIN book_storage.genre ON book_storage.book.genre_id = book_storage.genre.id WHERE ";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<BookResponseDto> getBooks(BookRequestDto bookRequestDto) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        StringBuilder stringBuilder = new StringBuilder(BASE_QUERY);
        addAuthorsIfPresent(bookRequestDto, namedParameters, stringBuilder);
        addGenresIfPresent(bookRequestDto, namedParameters, stringBuilder);
        addPeriodsIfPresent(bookRequestDto, namedParameters, stringBuilder);
        correctQueryEnding(stringBuilder);

        return jdbcTemplate.query(stringBuilder.toString(), namedParameters, new BookResponseRowMapper());
    }

    private void addAuthorsIfPresent(BookRequestDto bookRequestDto, MapSqlParameterSource namedParameters, StringBuilder stringBuilder) {
        if (bookRequestDto.getAuthorNames() != null) {
            namedParameters.addValue("author", bookRequestDto.getAuthorNames());
            stringBuilder.append("book_storage.author.name IN (:author)" + AND);
        }
    }

    private void addGenresIfPresent(BookRequestDto bookRequestDto, MapSqlParameterSource namedParameters, StringBuilder stringBuilder) {
        if (bookRequestDto.getGenres() != null) {
            List<String> values = bookRequestDto.getGenres().stream().map(Genre::getValue).collect(Collectors.toList());
            namedParameters.addValue("genre", values);
            stringBuilder.append("book_storage.genre.name IN (:genre)" + AND);
        }
    }

    private void addPeriodsIfPresent(BookRequestDto bookRequestDto, MapSqlParameterSource namedParameters, StringBuilder stringBuilder) {
        if (bookRequestDto.getPeriodDto() != null) {
            namedParameters.addValue("from", bookRequestDto.getPeriodDto().getFrom());
            namedParameters.addValue("to", bookRequestDto.getPeriodDto().getTo());
            stringBuilder.append("book_storage.book.creation_year BETWEEN :from AND :to" + AND);
        }
    }

    private void correctQueryEnding(StringBuilder stringBuilder) {
        stringBuilder.delete(stringBuilder.length() - AND.length(), stringBuilder.length());
        stringBuilder.append(";");
    }
}
