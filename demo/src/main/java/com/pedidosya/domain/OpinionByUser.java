package com.pedidosya.domain;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Table(value = "opinion_by_user")
public class OpinionByUser {

	@PrimaryKey
	private OpinionByUserKey key;

	@NotNull
	private UUID shoppingId;

	@NotNull
	@Column
	private String comments;

	public OpinionByUser(OpinionByUserKey key, UUID shoppingId, String comments) {
		this.key = key;
		this.shoppingId = shoppingId;
		this.comments = comments;
	}
}
