package com.borzdykooa.j3six.util;

import com.borzdykooa.j3six.dto.BookResponseDto;
import com.borzdykooa.j3six.dto.Genre;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookResponseRowMapper implements RowMapper<BookResponseDto> {

    @Override
    public BookResponseDto mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        return BookResponseDto.builder()
                .bookName(resultSet.getString("book_name"))
                .bookCreationYear(resultSet.getInt("book_creation_year"))
                .genre(Genre.getByValue(resultSet.getString("book_genre")))
                .authorName(resultSet.getString("author_name"))
                .build();
    }
}
