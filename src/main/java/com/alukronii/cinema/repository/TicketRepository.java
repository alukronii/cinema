package com.alukronii.cinema.repository;

import com.alukronii.cinema.entity.Ticket;
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
public class TicketRepository {
    private final JdbcTemplate jdbcTemplate;
    private final PlaceRepository placeRepository;
    private final SessionRepository sessionRepository;

    public void selectAll() {
        String sql = """
            SELECT *
            FROM ticket
            """;
        List<Ticket> result= jdbcTemplate.query(sql, this::mapToTicket);
        System.out.println(result);
    }

    public Optional<Ticket> findById(Integer id) {
        String sql = """
            SELECT *
            FROM ticket
            WHERE id = ?
            """;
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

    public void save(Ticket ticket) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        String sql = """
            INSERT INTO ticket (place_id, session_id, is_sold)
            VALUES (?, ?, ?)
            RETURNING id
            """;
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, ticket.getPlace().getId());
            ps.setInt(2, ticket.getSession().getId());
            ps.setBoolean(3, ticket.getIsSold());
            return ps;
        }, generatedKeyHolder);
        Integer id = generatedKeyHolder.getKey().intValue();
        log.info(ticket.toString());
    }

    public void sellTicket(Integer id) {
        TicketRepository ticketRepository;
        String sql = """
            UPDATE ticket SET is_sold = true
             WHERE id = ?
            """;
        int countRows = jdbcTemplate.update(sql,
            ps -> ps.setInt(1, id)
        );
        if (countRows < 1) {
            throw new RuntimeException("Билет уже куплен");
        }
        log.info("Вы купили билет %s", findById(id));
    }
}
