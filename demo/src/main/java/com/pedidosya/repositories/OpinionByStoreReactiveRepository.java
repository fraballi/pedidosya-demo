package com.pedidosya.repositories;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import com.pedidosya.domain.OpinionByStore;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface OpinionByStoreReactiveRepository extends ReactiveCassandraRepository<OpinionByStore, UUID> {

	@Query(value = "SELECT * FROM opinion_by_store WHERE storeId = ?0 AND createdAt >= '?1' AND createdAt <= '?2' ")
	Flux<OpinionByStore> findByKeyStoreIdAndCreatedAtBetween(UUID storeId, LocalDate from, LocalDate to);

	@Query(value = "SELECT storeId, AVG(rating) FROM opinion_by_store WHERE storeId = ?0 ")
	Mono<Map<UUID, Double>> findByKeyStoreIdAvgRating(UUID id);

	@Query(value = "SELECT storeId, AVG(rating) FROM opinion_by_store GROUP BY storeId")
	Mono<Map<UUID, Double>> findAvgRatingByStores();
}
