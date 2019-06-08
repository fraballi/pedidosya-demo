package com.pedidosya.repositories;

import java.util.UUID;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import com.pedidosya.domain.OpinionByShopping;

import reactor.core.publisher.Mono;

@Repository
public interface OpinionByShoppingReactiveRepository extends ReactiveCassandraRepository<OpinionByShopping, UUID> {

	@Query("SELECT * FROM opinion_by_shopping WHERE id = ?0 ")
	Mono<OpinionByShopping> findByKeyId(UUID id);

	@Query("SELECT * FROM opinion_by_shopping WHERE shoppingId = ?0 ")
	Mono<OpinionByShopping> findByKeyShoppingId(UUID shoppingId);
}
