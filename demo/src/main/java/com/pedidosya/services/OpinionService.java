package com.pedidosya.services;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedidosya.domain.OpinionByShopping;
import com.pedidosya.domain.OpinionByShoppingKey;
import com.pedidosya.domain.OpinionByStore;
import com.pedidosya.domain.OpinionByStoreKey;
import com.pedidosya.domain.OpinionByUser;
import com.pedidosya.repositories.OpinionByShoppingReactiveRepository;
import com.pedidosya.repositories.OpinionByStoreReactiveRepository;
import com.pedidosya.repositories.OpinionByUserReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OpinionService {

	private final OpinionByUserReactiveRepository opinionByUserRepository;
	private final OpinionByStoreReactiveRepository opinionByStoreRepository;
	private final OpinionByShoppingReactiveRepository opinionByShoppingRepository;

	@Autowired
	public OpinionService(OpinionByUserReactiveRepository opinionByUserRepository,
			OpinionByStoreReactiveRepository opinionByStoreRepository,
			OpinionByShoppingReactiveRepository opinionByShoppingRepository) {

		this.opinionByUserRepository = opinionByUserRepository;
		this.opinionByStoreRepository = opinionByStoreRepository;
		this.opinionByShoppingRepository = opinionByShoppingRepository;
	}

	public Flux<OpinionByUser> findByUserId(final UUID id) {
		return opinionByUserRepository.findByKeyUserId(id);
	}

	public Mono<OpinionByShopping> findByOpinionId(final UUID id) {
		return opinionByShoppingRepository.findByKeyId(id);
	}

	public Mono<OpinionByShopping> findByShoppingId(final UUID id) {
		return opinionByShoppingRepository.findByKeyShoppingId(id);
	}

	public Flux<OpinionByUser> findAllByUserIdAndDateRange(final UUID userId, LocalDate from, LocalDate to) {
		return opinionByUserRepository.findByUserIdAndCreatedAtBetween(userId, from, to);
	}

	public Mono<Map<UUID, Double>> findAvgByStoreId(final UUID storeId) {
		return opinionByStoreRepository.findByKeyStoreIdAvgRating(storeId);
	}

	public Mono<Map<UUID, Double>> findAvgByStores() {
		return opinionByStoreRepository.findAvgRatingByStores();
	}

	public Flux<OpinionByStore> findAllByStoreIdAndDateRange(final UUID storeId, LocalDate from, LocalDate to) {
		return opinionByStoreRepository.findByKeyStoreIdAndCreatedAtBetween(storeId, from, to);
	}

	public void delete(final UUID id) {
		Mono<OpinionByUser> record = opinionByUserRepository.findById(id);
		record.subscribe(item -> opinionByUserRepository.delete(item));
	}

//	public void save(Mono<OpinionByUser> opinion) {
//		opinion.subscribe(item -> {
//
//			opinionByUserRepository.save(item);
//
//			opinionByStoreRepository.save(new OpinionByStore(
//					new OpinionByStoreKey(item.getKey().getStoreId(), item.getKey().getRating()), item.getComments()));
//
//			opinionByShoppingRepository.save(new OpinionByShopping(
//					new OpinionByShoppingKey(item.getShoppingId(), item.getKey().getRating()), item.getComments()));
//		});
//	}

//	public void edit(Mono<OpinionByUser> opinion) {
//
//		findByUserId(opinion.block().getKey().getId()).subscribe(entity -> {
//			
//			
//			if (entity.getComments() != opinion.getComments()) {
//				entity.setComments(opinion.getComments());
//			}
//			if (entity.getRating() != opinion.getRating()) {
//				entity.setRating(opinion.getRating());
//			}
//
//			save(Mono.just(entity));
//		});
//	}
}
