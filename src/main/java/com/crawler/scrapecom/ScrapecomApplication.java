package com.crawler.scrapecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScrapecomApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScrapecomApplication.class, args);
	}

}
