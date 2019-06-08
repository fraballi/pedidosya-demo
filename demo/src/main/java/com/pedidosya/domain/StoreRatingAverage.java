package com.pedidosya.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.driver.core.utils.UUIDs;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Table(value = "store_rating_average")
public class StoreRatingAverage implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
	private LocalDateTime createdAt = LocalDateTime.now();

	@PrimaryKeyColumn(ordinal = 1)
	private String json;

	@PrimaryKeyColumn(ordinal = 2)
	private UUID id = UUIDs.timeBased();

	public StoreRatingAverage(LocalDateTime createdAt, String json) {
		this.createdAt = createdAt;
		this.json = json;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof StoreRatingAverage))
			return false;

		if (this == obj)
			return true;

		final StoreRatingAverage that = (StoreRatingAverage) obj;
		return this.json.equals(that.getJson()) && this.createdAt.equals(that.getCreatedAt())
				&& this.id.equals(that.getId());
	}

	@Override
	public int hashCode() {
		return 37 ^ this.json.hashCode() ^ this.createdAt.hashCode() ^ this.id.hashCode();
	}
}
