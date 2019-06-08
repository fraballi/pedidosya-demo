package com.pedidosya.controllers;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedidosya.domain.OpinionByShopping;
import com.pedidosya.domain.OpinionByStore;
import com.pedidosya.services.OpinionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "opinions")
public class OpinionController {

	private final OpinionService opinionService;

	@Autowired
	public OpinionController(OpinionService service) {
		opinionService = service;
	}

	@GetMapping
	public Mono<String> home() {
		return Mono.just("PedidosYa");
	}

	@GetMapping("/{id}")
	public Mono<OpinionByShopping> get(@PathVariable(name = "id") UUID id) {
		return opinionService.findByOpinionId(id);
	}

	@GetMapping("/{id}/shopping")
	public Mono<OpinionByShopping> getByShoppingId(@PathVariable(name = "id") UUID shoppingId) {
		return opinionService.findByShoppingId(shoppingId);
	}

	@GetMapping("/{id}/store/{from}/{to}")
	public Flux<OpinionByStore> getAllByStoreId(@PathVariable(name = "id") UUID storeId,
			@PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
		return opinionService.findAllByStoreIdAndDateRange(storeId, from, to);
	}

	@GetMapping("/{id}/user/{from}/{to}")
	public Flux<OpinionByStore> getAllByUserId(@PathVariable(name = "id") UUID userId,
			@PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
		return opinionService.findAllByStoreIdAndDateRange(userId, from, to);
	}

	@GetMapping("/store/rating-avg")
	public Mono<Map<UUID, Double>> getAvgRatingPerStore() {
		return opinionService.findAvgByStores();
	}

//	@PostMapping(consumes = "application/json")
//	@ResponseStatus(code = HttpStatus.CREATED)
//	public void post(@RequestBody Mono<OpinionByUser> opinion) {
//		opinionService.save(opinion);
//	}

//	@PatchMapping
//	@ResponseStatus(code = HttpStatus.PARTIAL_CONTENT)
//	public void patch(@RequestBody OpinionByUser opinion) {
//		opinionService.edit(opinion);
//	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable(name = "id") UUID id) {
		opinionService.delete(id);
	}
}
