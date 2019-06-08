package com.pedidosya.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import com.datastax.driver.core.utils.UUIDs;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@PrimaryKeyClass
public class OpinionByShoppingKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
	private UUID id = UUIDs.timeBased();

	@PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
	private UUID shoppingId;

	@Range(min = 1, max = 5, message = "A valid range is between 1 and 5")
	@PrimaryKeyColumn(ordinal = 0)
	private Integer rating;

	@PrimaryKeyColumn(ordinal = 1)
	private LocalDate createdAt = LocalDate.now();

	public OpinionByShoppingKey(UUID shoppingId, Integer rating) {
		this.shoppingId = shoppingId;
		this.rating = rating;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof OpinionByShoppingKey))
			return false;

		if (this == obj)
			return true;

		final OpinionByShoppingKey that = (OpinionByShoppingKey) obj;
		return this.rating.equals(that.getRating()) && this.createdAt.equals(that.getCreatedAt())
				&& this.id.equals(that.getId());
	}

	@Override
	public int hashCode() {
		return 37 ^ this.rating.hashCode() ^ this.createdAt.hashCode() ^ this.id.hashCode();
	}
}
