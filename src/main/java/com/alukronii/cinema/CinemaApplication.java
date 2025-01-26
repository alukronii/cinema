package com.alukronii.cinema;

import com.alukronii.cinema.entity.Movie;
import com.alukronii.cinema.repository.MovieRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CinemaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CinemaApplication.class, args);
		MovieRepository movieRepository = context.getBean(MovieRepository.class);
		Movie movie = Movie.builder().id(10).movieName("Jumbo").movieDescription("Jumbo").build();
		movieRepository.save(movie);
	}

}
