package com.tunemerge.tunemerge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the TuneMerge application.
 *
 * This class is responsible for bootstrapping the Spring Boot application.
 * It contains the main method which serves as the entry point for the application.
 *
 * To run the application, execute the main method.
 *
 * @author Somendra
 */
@SpringBootApplication
public class TuneMergeApplication {
	/**
	 * The main method which serves as the entry point for the Spring Boot application.
	 *
	 * @param args command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(TuneMergeApplication.class, args);
	}
}