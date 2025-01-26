package com.alukronii.cinema.repository;

import com.alukronii.cinema.entity.Movie;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.*;

@Repository
@RequiredArgsConstructor
public class MovieRepository {
    private final JdbcTemplate jdbcTemplate;

    public void selectAll() {
        String sql = "SELECT * FROM movie";
        List<Movie> result= jdbcTemplate.query(sql, this::mapToMovie);
        System.out.println(result);
    }

    @SneakyThrows
    private Movie mapToMovie(ResultSet rs, int rowNum) {
        Movie movie = new Movie();
        movie.setId(rs.getInt("id"));
        movie.setMovieName(rs.getNString("movie_name"));
        movie.setMovieDescription(rs.getNString("movie_description"));
        return movie;
    }

    public Optional<Movie> findById(Integer id) {
        String sql = "SELECT * FROM movie WHERE id = ?";
        try {
            return of(jdbcTemplate.queryForObject(sql, this::mapToMovie, id));
        } catch (EmptyResultDataAccessException e) {
            return empty();
        }
    }

    public void save(Movie movie) {
        String sql = "INSERT INTO movie (id, movie_name, movie_description) values(?, ? ,?)";
        jdbcTemplate.update(sql, ps -> {
            ps.setInt(1, movie.getId());
            ps.setString(2, movie.getMovieName());
            ps.setString(3, movie.getMovieDescription());
        });
    }
}
