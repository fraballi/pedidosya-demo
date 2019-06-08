package com.pedidosya.domain;

import javax.validation.constraints.NotNull;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Table(value = "opinion_by_store")
public class OpinionByStore {

	@PrimaryKey
	private OpinionByStoreKey key;

	@NotNull
	@Column
	private String comments;

	public OpinionByStore(OpinionByStoreKey key, String comments) {
		this.key = key;
		this.comments = comments;
	}
}
