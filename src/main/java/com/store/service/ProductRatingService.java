package com.store.service;

import com.store.dto.ProductRatingDTO;

public interface ProductRatingService {

	public ProductRatingDTO createRating(Long productId, ProductRatingDTO ratingDTO);

	public Double getRatingsByProductId(Long productId);

	public Boolean IsProductEvaluatedByUser(Long userId, Long productId);

}
