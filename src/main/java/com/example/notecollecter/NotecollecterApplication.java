package com.example.notecollecter;

import com.example.notecollecter.util.Mapping;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NotecollecterApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotecollecterApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
