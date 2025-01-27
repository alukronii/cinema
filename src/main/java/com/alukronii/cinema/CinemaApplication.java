package com.alukronii.cinema;

import com.alukronii.cinema.entity.Movie;
import com.alukronii.cinema.entity.Ticket;
import com.alukronii.cinema.repository.MovieRepository;
import com.alukronii.cinema.repository.TicketRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CinemaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CinemaApplication.class, args);
//		MovieRepository movieRepository = context.getBean(MovieRepository.class);
//		Movie movie = Movie.builder()
//			.movieName("1+1")
//			.movieDescription("Фильм о богатом человеке и его друге")
//			.build();
//		movieRepository.save(movie);
//		movieRepository.selectAll();
		TicketRepository ticketRepository = context.getBean(TicketRepository.class);
		ticketRepository.sellTicket(1);
	}
}