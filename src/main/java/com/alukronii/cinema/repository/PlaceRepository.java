package com.alukronii.cinema.repository;

import com.alukronii.cinema.entity.Place;
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
public class PlaceRepository {
    private final JdbcTemplate jdbcTemplate;

    public void selectAll() {
        String sql = "SELECT * FROM place";
        List<Place> result= jdbcTemplate.query(sql, this::mapToPlace);
        System.out.println(result);
    }

    @SneakyThrows
    private Place mapToPlace(ResultSet rs, int rowNum) {
        Place place = new Place();
        place.setId(rs.getInt("id"));
        place.setPlaceNumber(rs.getString("place_number"));
        return place;
    }

    public Optional<Place> findById(Integer id) {
        String sql = "SELECT * FROM place WHERE id = ?";
        try {
            return of(jdbcTemplate.queryForObject(sql, this::mapToPlace, id));
        } catch (EmptyResultDataAccessException e) {
            return empty();
        }
    }
}
