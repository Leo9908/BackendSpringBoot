package com.store.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dto.ProductRatingDTO;
import com.store.entitys.Product;
import com.store.entitys.ProductRating;
import com.store.entitys.User;
import com.store.exceptions.ResourceNotFoundException;
import com.store.repository.ProductRatingRepository;
import com.store.repository.ProductsRepository;
import com.store.repository.UsersRepository;
import com.store.service.ProductRatingService;

@Service
public class ProductRatingImpl implements ProductRatingService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ProductRatingRepository repository;

	@Autowired
	private ProductsRepository repository2;

	@Autowired
	private UsersRepository repository3;

	@Override
	public ProductRatingDTO createRating(Long productId, ProductRatingDTO ratingDTO) {
		ProductRating rating = mapEntity(ratingDTO);
		Product product = repository2.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
		rating.setProduct(product);
		ProductRating newraRating = repository.save(rating);
		return mapDTO(newraRating);
	}

	@Override
	public Double getRatingsByProductId(Long productId) {
		List<ProductRating> ratings = repository.findByProductId(productId);
		double promedie = 0.0;
		for (ProductRating rating : ratings) {
			promedie += rating.getStars();
		}
		return promedie / ratings.size();
	}

	@Override
	public Boolean IsProductEvaluatedByUser(Long userId, Long productId) {
		User user = repository3.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		String email = user.getEmail();
		List<ProductRating> ratings = repository.findByEmailUserAndProductId(email, productId);
		return ratings.size() == 0;
	}

	private ProductRatingDTO mapDTO(ProductRating product) {
		ProductRatingDTO ratingDTO = mapper.map(product, ProductRatingDTO.class);
		return ratingDTO;
	}

	private ProductRating mapEntity(ProductRatingDTO rating) {
		ProductRating ratingResponse = mapper.map(rating, ProductRating.class);
		return ratingResponse;
	}

}
