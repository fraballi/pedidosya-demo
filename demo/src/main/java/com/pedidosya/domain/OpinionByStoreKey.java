package com.pedidosya.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.cassandra.core.cql.Ordering;
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
public class OpinionByStoreKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
	private UUID storeId;

	@PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 0, ordering = Ordering.DESCENDING)
	private LocalDate createdAt = LocalDate.now();

	@Range(min = 1, max = 5, message = "A valid range is between 1 and 5")
	@PrimaryKeyColumn(ordinal = 1)
	private Integer rating;

	@PrimaryKeyColumn(ordinal = 2)
	private UUID shoppingId;

	@PrimaryKeyColumn(ordinal = 3, ordering = Ordering.DESCENDING)
	private UUID id = UUIDs.timeBased();

	public OpinionByStoreKey(UUID storeId, UUID shoppingId, Integer rating) {
		this.storeId = storeId;
		this.shoppingId = shoppingId;
		this.rating = rating;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof OpinionByStoreKey))
			return false;

		if (this == obj)
			return true;

		final OpinionByStoreKey that = (OpinionByStoreKey) obj;
		return this.storeId.equals(that.getStoreId()) && this.rating.equals(that.getRating())
				&& this.shoppingId.equals(that.getShoppingId()) && this.createdAt.equals(that.getCreatedAt())
				&& this.id.equals(that.getId());
	}

	@Override
	public int hashCode() {
		return 37 ^ this.storeId.hashCode() ^ this.shoppingId.hashCode() ^ this.rating.hashCode()
				^ this.createdAt.hashCode() ^ this.id.hashCode();
	}
}
