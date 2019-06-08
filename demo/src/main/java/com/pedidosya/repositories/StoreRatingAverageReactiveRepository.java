package com.pedidosya.repositories;

import java.util.UUID;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import com.pedidosya.domain.StoreRatingAverage;

@Repository
public interface StoreRatingAverageReactiveRepository extends ReactiveCassandraRepository<StoreRatingAverage, UUID> {

}
