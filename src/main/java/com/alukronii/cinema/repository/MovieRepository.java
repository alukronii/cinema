package com.alukronii.cinema.repository;

import com.alukronii.cinema.entity.Movie;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MovieRepository {
    private final JdbcTemplate jdbcTemplate;

    public void selectAll() {
        String sql = "SELECT * FROM movie";
        List<Movie> result= jdbcTemplate.query(sql, this::mapToMovie);
        log.info(result.toString());
    }

    @SneakyThrows
    private Movie mapToMovie(ResultSet rs, int rowNum) {
        Movie movie = new Movie();
        movie.setId(rs.getInt("id"));
        movie.setMovieName(rs.getString("movie_name"));
        movie.setMovieDescription(rs.getString("movie_description"));
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
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO movie (movie_name, movie_description) VALUES (? ,?) RETURNING id";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, movie.getMovieName());
            ps.setString(2, movie.getMovieDescription());
            return ps;
        }, generatedKeyHolder);
        Integer id = generatedKeyHolder.getKey().intValue();
        log.info(movie.toString());
    }
}
