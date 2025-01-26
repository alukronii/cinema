package com.alukronii.cinema.repository;

import com.alukronii.cinema.entity.Session;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Repository
@RequiredArgsConstructor
public class SessionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final MovieRepository movieRepository;

    public void selectAll() {
        String sql = "SELECT * FROM session";
        List<Session> result= jdbcTemplate.query(sql, this::mapToSession);
        System.out.println(result);
    }

    public Optional<Session> findById(Integer id) {
        String sql = "SELECT * FROM session WHERE id = ?";
        try {
            return of(jdbcTemplate.queryForObject(sql, this::mapToSession, id));
        } catch (EmptyResultDataAccessException e) {
            return empty();
        }
    }

    @SneakyThrows
    private Session mapToSession(ResultSet rs, int rowNum) {
        Session session = new Session();
        session.setId(rs.getInt("id"));
        if (rs.getString("movie_id") != null) {
           Integer movieId = Integer.valueOf(rs.getString("movie_id"));
           session.setMovie(movieRepository.findById(movieId).orElse(null));
        }
        session.setTimestamp(rs.getTimestamp("timestamp"));
        session.setTicketPrice(rs.getBigDecimal("ticket_price"));
        return session;
    }
}
