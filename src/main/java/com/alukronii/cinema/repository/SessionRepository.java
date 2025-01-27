package com.alukronii.cinema.repository;

import com.alukronii.cinema.entity.Session;
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

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SessionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final MovieRepository movieRepository;

    public void selectAll() {
        String sql = "SELECT * FROM session";
        List<Session> result= jdbcTemplate.query(sql, this::mapToSession);
        log.info(result.toString());
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

    public void save(Session session) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO session (movie_id, timestamp, ticket_price) VALUES (?, ?, ?) RETURNING id";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, session.getMovie().getId());
            ps.setTimestamp(2, session.getTimestamp());
            ps.setBigDecimal(3, session.getTicketPrice());
            return ps;
        }, generatedKeyHolder);
        Integer id = generatedKeyHolder.getKey().intValue();
        log.info(session.toString());
    }
}
