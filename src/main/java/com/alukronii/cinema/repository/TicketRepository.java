package com.alukronii.cinema.repository;

import com.alukronii.cinema.entity.Place;
import com.alukronii.cinema.entity.Ticket;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Repository
@RequiredArgsConstructor
public class TicketRepository {
    private final JdbcTemplate jdbcTemplate;
    private final PlaceRepository placeRepository;
    private final SessionRepository sessionRepository;

    public void selectAll() {
        String sql = "SELECT * FROM ticket";
        List<Ticket> result= jdbcTemplate.query(sql, this::mapToTicket);
        System.out.println(result);
    }

    public Optional<Ticket> findById(Integer id) {
        String sql = "SELECT * FROM ticket WHERE id = ?";
        try {
            return of(jdbcTemplate.queryForObject(sql, this::mapToTicket, id));
        } catch (EmptyResultDataAccessException e) {
            return empty();
        }
    }

    @SneakyThrows
    private Ticket mapToTicket(ResultSet rs, int rowNum) {
        Ticket ticket = new Ticket();
        ticket.setId(rs.getInt("id"));
        if (rs.getString("place_id") != null) {
            Integer placeId = Integer.valueOf(rs.getString("place_id"));
            ticket.setPlace(placeRepository.findById(placeId).orElse(null));
        }
        if (rs.getString("session_id") != null) {
            Integer sessionId = Integer.valueOf(rs.getString("session_id"));
            ticket.setSession(sessionRepository.findById(sessionId).orElse(null));
        }
        ticket.setIsSold(rs.getBoolean("is_sold"));
        return ticket;
    }
}
