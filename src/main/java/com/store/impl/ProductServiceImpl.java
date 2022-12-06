package com.store.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.store.dto.ProductDTO;
import com.store.dto.ProductRatingDTO;
import com.store.entitys.Product;
import com.store.entitys.ProductRating;
import com.store.entitys.User;
import com.store.exceptions.ResourceNotFoundException;
import com.store.repository.ProductRatingRepository;
import com.store.repository.ProductsRepository;
import com.store.repository.UsersRepository;
import com.store.service.ProductsService;

@Service
public class ProductServiceImpl implements ProductsService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ProductsRepository repo;

	@Autowired
	private ProductRatingRepository repository;

	@Autowired
	private UsersRepository repository3;

	@Override
	public ProductDTO createProduct(ProductDTO productDTO) {

		Product product = this.mapProductEntity(productDTO);
		Product newProduct = repo.save(product);
		ProductDTO productResponse = this.mapProductDTO(newProduct);

		return productResponse;
	}

	@Override
	public List<ProductDTO> getAllProducts(int pageNum, int pageSize, String sortBy, String sortDir) {
		Pageable pageable = PageRequest.of(pageNum, pageSize, Direction.fromString(sortDir), sortBy);
		Page<Product> products = repo.findAll(pageable);

		List<Product> listProducts = products.getContent();
		return listProducts.stream().map(prod -> mapProductDTO(prod)).collect(Collectors.toList());
	}

	@Override
	public ProductDTO findById(Long id) {
		Product product = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
		return mapProductDTO(product);
	}

	@Override
	public List<ProductDTO> findAll(String keyWord) {
		return repo.findAll(keyWord).stream().map(prod -> mapProductDTO(prod))
				.collect(Collectors.toList());
	}

	@Override
	public List<ProductDTO> findFavorites(Long userId) {
		User user = repository3.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		return repo.findAll(user).stream().map(prod -> mapProductDTO(prod)).collect(Collectors.toList());
	}

	@Override
	public List<ProductDTO> findTheMostSold() {
		return repo.findAllMostSold().stream().map(dto -> mapProductDTO(dto)).collect(Collectors.toList());
	}

	@Override
	public ProductDTO updateProduct(ProductDTO productDTO, Long id) {
		Product product = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

		product.setName(productDTO.getName());
		product.setPrecio(productDTO.getPrecio());
		product.setOnSale(productDTO.getOnSale());
		product.setType(productDTO.getType());
		product.setDescription(productDTO.getDescription());
		product.setImgUrl(productDTO.getImgUrl());

		Product updatedProduct = repo.save(product);

		return mapProductDTO(updatedProduct);
	}

	@Override
	public void deleteProduct(Long id) {
		repo.deleteById(id);
	}

	/**
	 * Servicios relacionados con los ratings
	 */

	@Override
	public ProductRatingDTO createRating(Long userId, Long productId, ProductRatingDTO ratingDTO) {

		if (IsProductEvaluatedByUser(userId, productId)) {
			ProductRating rating = mapProductRatingEntity(ratingDTO);
			Product product = repo.findById(productId)
					.orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
			rating.setProduct(product);
			User user = repository3.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
			rating.setUser(user);
			ProductRating newraRating = repository.save(rating);
			return mapProductRatingDTO(newraRating);
		} else {
			ProductRating rating = repository.findByUserIdAndProductId(userId, productId).get(0);
			rating.setStars(ratingDTO.getStars());
			ProductRating updateRating = repository.save(rating);
			return mapProductRatingDTO(updateRating);
		}
	}

	@Override
	public Double getRatingsByProductId(Long productId) {
		List<ProductRating> ratings = repository.findByProductId(productId);
		if (ratings.size() > 0) {
			double promedie = 0.0;
			for (ProductRating rating : ratings) {
				promedie += rating.getStars();
			}
			return promedie / ratings.size();
		} else {
			return 3.5;
		}
	}

	@Override
	public Boolean IsProductEvaluatedByUser(Long userId, Long productId) {
		List<ProductRating> ratings = repository.findByUserIdAndProductId(userId, productId);
		return ratings.size() == 0;
	}

	/**
	 * Convierte entidad a DTO
	 */
	private ProductDTO mapProductDTO(Product product) {
		ProductDTO productDTO = mapper.map(product, ProductDTO.class);
		productDTO.setRating(getRatingsByProductId(product.getId()));
		return productDTO;
	}

	/**
	 * Convierte DTO a entidad
	 */
	private Product mapProductEntity(ProductDTO productDTO) {
		Product product = mapper.map(productDTO, Product.class);
		return product;
	}

	private ProductRatingDTO mapProductRatingDTO(ProductRating product) {
		ProductRatingDTO ratingDTO = mapper.map(product, ProductRatingDTO.class);
		return ratingDTO;
	}

	private ProductRating mapProductRatingEntity(ProductRatingDTO rating) {
		ProductRating ratingResponse = mapper.map(rating, ProductRating.class);
		return ratingResponse;
	}

}
