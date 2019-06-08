package com.pedidosya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

@SpringBootApplication
@EnableReactiveCassandraRepositories(basePackages = "com.pedidosya.repositories")
public class PedidosYaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PedidosYaApplication.class, args);
	}
}
