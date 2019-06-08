package com.pedidosya.tests;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.datastax.driver.core.utils.UUIDs;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedidosya.PedidosYaApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PedidosYaApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class OpinionControllerTests {

	@Autowired
	private WebTestClient webClient;

	private final static ObjectMapper MAPPER = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

	@Test
	public void checkRoot() {
		webClient.get().uri("/opinions").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk()
				.expectBody(String.class).consumeWith(body -> assertEquals(body.getResponseBody(), "PedidosYa"));
	}

//	@Test
//	public void checkGetAll() {
//		webClient.get().uri("list").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk().expectBody()
//				.jsonPath("$").isNotEmpty();
//	}

//	@Test
//	public void checkGet() {
//		webClient.get().uri("/{id}", 1).accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk()
//				.expectBody(String.class).consumeWith(body -> assertEquals(body.getResponseBody(), "1"));
//	}

	@Test
	public void checkPost() {

		final UUID userId = UUIDs.timeBased();
		final UUID storeId = UUIDs.timeBased();
		final UUID shoppingId = UUIDs.timeBased();
		final int rating = 3;
//
//		final String comments = "These are my words on the product";
//		final Opinion opinion = new Opinion(userId, storeId, shoppingId, rating, comments);
//
//		webClient.post().uri("/").body(Mono.just(opinion), Opinion.class).exchange().expectStatus().isCreated()
//				.expectBody().isEmpty();
	}

	@Test
	public void checkPatch() {

		final UUID userId = UUIDs.timeBased();
		final UUID storeId = UUIDs.timeBased();
		final UUID shoppingId = UUIDs.timeBased();
		final int rating = 3;
//
//		final String comments = "These are my words on the product";
//		final Opinion opinion = new Opinion(userId, storeId, shoppingId, rating, comments);
//
//		webClient.patch().uri("/").body(Mono.just(opinion), Opinion.class).exchange().expectStatus()
//				.isEqualTo(HttpStatus.PARTIAL_CONTENT).expectBody().isEmpty();
	}

}
