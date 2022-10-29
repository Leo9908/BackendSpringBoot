package com.store.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.dto.ProductRatingDTO;
import com.store.service.ProductRatingService;

@RestController
@RequestMapping("/api/")
public class ProductRatingController {

	@Autowired
	private ProductRatingService service;

	@GetMapping("/products/{productId}/ratings")
	public Double getProductRating(@PathVariable(value = "productId") Long productId) {
		return service.getRatingsByProductId(productId);
	}

	@PostMapping("/products/{productId}/ratings")
	public ResponseEntity<ProductRatingDTO> saveRating(@PathVariable(value = "productId") Long productId,
			@Valid @RequestBody ProductRatingDTO ratingDTO) {
		return new ResponseEntity<>(service.createRating(productId, ratingDTO), HttpStatus.CREATED);
	}

	@GetMapping("/products/{productId}/{userId}")
	public Boolean IsProductRatedByUser(@PathVariable(value = "productId", required = true) Long productId,
			@PathVariable(value = "userId", required = true) Long userId) {
		return service.IsProductEvaluatedByUser(userId, productId);
	}

}
