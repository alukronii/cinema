package com.alukronii.cinema.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    private Integer id;
    private Movie movie;
    private Timestamp timestamp;
    private BigDecimal ticketPrice;
}
