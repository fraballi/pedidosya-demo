package com.pedidosya.repositories;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import com.pedidosya.domain.OpinionByUser;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface OpinionByUserReactiveRepository extends ReactiveCassandraRepository<OpinionByUser, UUID> {

	@Query(value = "SELECT * FROM opinion_by_user WHERE userId = ?0 ")
	Flux<OpinionByUser> findByKeyUserId(UUID id);

	@Query(value = "SELECT * FROM opinion_by_user WHERE userId = ?0 AND createdAt >= '?1' AND createdAt < '?2' ")
	Flux<OpinionByUser> findByUserIdAndCreatedAtBetween(UUID userId, LocalDate from, LocalDate to);
}
