package com.pedidosya.tests;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.datastax.driver.core.utils.UUIDs;
import com.pedidosya.PedidosYaApplication;
import com.pedidosya.batch.StoresRatingAverageScheduler;
import com.pedidosya.domain.OpinionByShopping;
import com.pedidosya.domain.OpinionByShoppingKey;
import com.pedidosya.domain.OpinionByStore;
import com.pedidosya.domain.OpinionByStoreKey;
import com.pedidosya.domain.OpinionByUser;
import com.pedidosya.domain.OpinionByUserKey;
import com.pedidosya.repositories.OpinionByShoppingReactiveRepository;
import com.pedidosya.repositories.OpinionByStoreReactiveRepository;
import com.pedidosya.repositories.OpinionByUserReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PedidosYaApplication.class)
public class OpinionRepositoryTests {

	@Autowired
	private OpinionByUserReactiveRepository opinionByUserRepository;
	@Autowired
	private OpinionByStoreReactiveRepository opinionByStoreRepository;
	@Autowired
	private OpinionByShoppingReactiveRepository opinionByShoppingRepository;

	private final static UUID USER_ID = UUIDs.timeBased();
	private final static UUID STORE_ID = UUIDs.timeBased();

	@MockBean
	private StoresRatingAverageScheduler scheduler;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void checkBatchOperations() {

		final List<OpinionByUser> userOpinions = Stream.of(
				new OpinionByUser(new OpinionByUserKey(USER_ID, 1, UUIDs.timeBased()), UUIDs.timeBased(), "Sucks"),
				new OpinionByUser(new OpinionByUserKey(UUIDs.timeBased(), 5, STORE_ID), UUIDs.timeBased(), "Excellent"),
				new OpinionByUser(new OpinionByUserKey(USER_ID, 2, UUIDs.timeBased()), UUIDs.timeBased(),
						"Could be worst"),
				new OpinionByUser(new OpinionByUserKey(USER_ID, 5, STORE_ID), UUIDs.timeBased(), "Excellent"),
				new OpinionByUser(new OpinionByUserKey(UUIDs.timeBased(), 4, UUIDs.timeBased()), UUIDs.timeBased(),
						"Could be great"),
				new OpinionByUser(new OpinionByUserKey(USER_ID, 3, STORE_ID), UUIDs.timeBased(), "Not so good"),
				new OpinionByUser(new OpinionByUserKey(UUIDs.timeBased(), 3, STORE_ID), UUIDs.timeBased(),
						"Not so good"),
				new OpinionByUser(new OpinionByUserKey(UUIDs.timeBased(), 2, UUIDs.timeBased()), UUIDs.timeBased(),
						"Could be worst"),
				new OpinionByUser(new OpinionByUserKey(USER_ID, 5, STORE_ID), UUIDs.timeBased(), "Excellent"),
				new OpinionByUser(new OpinionByUserKey(UUIDs.timeBased(), 1, UUIDs.timeBased()), UUIDs.timeBased(),
						"Sucks"))
				.collect(toList());

		Flux<OpinionByUser> deleteAndInsert = opinionByUserRepository.deleteAll()
				.thenMany(opinionByUserRepository.saveAll(userOpinions));
		StepVerifier.create(deleteAndInsert).expectNextCount(10).verifyComplete();

		final List<OpinionByStore> storeOpinions = userOpinions.stream().map(item -> new OpinionByStore(
				new OpinionByStoreKey(item.getKey().getStoreId(), item.getShoppingId(), item.getKey().getRating()),
				item.getComments())).collect(toList());

		Flux<OpinionByStore> deleteAndInsert2 = opinionByStoreRepository.deleteAll()
				.thenMany(opinionByStoreRepository.saveAll(storeOpinions));
		StepVerifier.create(deleteAndInsert2).expectNextCount(10).verifyComplete();

		final List<OpinionByShopping> shoppingOpinions = userOpinions.stream()
				.map(item -> new OpinionByShopping(
						new OpinionByShoppingKey(item.getShoppingId(), item.getKey().getRating()), item.getComments()))
				.collect(toList());

		Flux<OpinionByShopping> deleteAndInsert3 = opinionByShoppingRepository.deleteAll()
				.thenMany(opinionByShoppingRepository.saveAll(shoppingOpinions));
		StepVerifier.create(deleteAndInsert3).expectNextCount(10).verifyComplete();
	}

	@Test
	public void checkUserOpinionSave() {
		final OpinionByUser record = new OpinionByUser(new OpinionByUserKey(USER_ID, 5, STORE_ID), UUIDs.timeBased(),
				"Excellent");

		StepVerifier.create(opinionByUserRepository.save(record))
				.expectNextMatches(entity -> record.getShoppingId().equals(entity.getShoppingId())).verifyComplete();

		StepVerifier.create(opinionByUserRepository.findByKeyUserId(USER_ID))
				.assertNext(entity -> record.getKey().getUserId().equals(entity.getKey().getUserId())).expectComplete();
	}

	@Test
	public void checkFindAll() {
		StepVerifier.create(opinionByUserRepository.findAll()).expectNextCount(10).expectComplete();
	}

	@Test
	public void checkFindUser() {
		StepVerifier.create(opinionByUserRepository.findByKeyUserId(USER_ID))
				.assertNext(entity -> entity.getKey().getUserId().equals(USER_ID)).expectComplete();
	}

	@Test
	public void checkFindUserBetweenDates() {
		StepVerifier
				.create(opinionByUserRepository.findByUserIdAndCreatedAtBetween(USER_ID, LocalDate.now().minusDays(1L),
						LocalDate.now().plusDays(1L)))
				.assertNext(entity -> entity.getKey().getUserId().equals(USER_ID)).expectComplete();
	}

	@Test
	public void checkFindStoreBetweenDates() {
		StepVerifier
				.create(opinionByStoreRepository.findByKeyStoreIdAndCreatedAtBetween(STORE_ID,
						LocalDate.now().minusDays(1L), LocalDate.now().plusDays(1L)))
				.assertNext(entity -> entity.getKey().getStoreId().equals(STORE_ID)).expectComplete();
	}

	@Test
	public void checkFindOneStoreRatingAvg() {
		
		Map<UUID, Double> l = opinionByStoreRepository.findByKeyStoreIdAvgRating(STORE_ID).block();
		System.out.println(l);
		
		StepVerifier.create(opinionByStoreRepository.findByKeyStoreIdAvgRating(STORE_ID))
				.assertNext(map -> assertTrue(!map.isEmpty())).expectComplete();
	}

	@Test
	public void checkFindStoresRatingAvg() {
		StepVerifier.create(opinionByStoreRepository.findAvgRatingByStores())
				.assertNext(map -> assertTrue(!map.isEmpty())).expectComplete();
	}

	@Test
	public void checkDeleteAll() {
		StepVerifier.create(opinionByUserRepository.deleteAll()).expectComplete();
	}
}