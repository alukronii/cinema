package com.alukronii.cinema.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    private Integer id;
    private Place place;
    private Session session;
    private Boolean isSold;
}
