package com.pedidosya.batch;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pedidosya.domain.StoreRatingAverage;
import com.pedidosya.repositories.OpinionByStoreReactiveRepository;
import com.pedidosya.repositories.StoreRatingAverageReactiveRepository;

@Component
public class StoresRatingAverageScheduler {

	private final static Logger LOGGER = LoggerFactory.getLogger(StoresRatingAverageScheduler.class);
	private final static ObjectMapper MAPPER = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,
			false);

	private final static String JOB_NAME = String.format("Stores_Rating_Avg_Job: [%s]",
			String.valueOf(System.currentTimeMillis()));

	@Autowired
	private StoreRatingAverageReactiveRepository storeRatingAvgRepository;

	@Autowired
	private OpinionByStoreReactiveRepository opinionByStoreRepository;

	@Scheduled(cron = "${scheduling.jobs.rating}")
	public void scheduleRatingAverage() {

		LOGGER.info("{0}: Started", JOB_NAME);

		opinionByStoreRepository.findAvgRatingByStores().subscribe(result -> {
			try {
				final String json = MAPPER.writeValueAsString(result);

				LOGGER.info("{0}: {1}", JOB_NAME, json);
				storeRatingAvgRepository.save(new StoreRatingAverage(LocalDateTime.now(), json));

			} catch (JsonProcessingException e) {
				LOGGER.error("{0}: {1}", JOB_NAME, e.getLocalizedMessage());
			}
		});

		LOGGER.info("{0}: Finished", JOB_NAME);
	}
}
