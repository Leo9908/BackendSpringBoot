package com.store.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
	public ProductDTO createProduct(ProductDTO productDTO) {
		Product product = this.mapProductEntity(productDTO);
		Product newProduct = repo.save(product);
		ProductDTO productResponse = this.mapProductDTO(newProduct);

		return productResponse;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductDTO> getAllProducts(int pageNum, int pageSize, String sortBy, String sortDir, String filter) {
		Pageable pageable = PageRequest.of(pageNum, pageSize, Direction.fromString(sortDir), sortBy);
		Product product = new Product();
		product.setDeleted(false);
		product.setName(filter);
		product.setDescription(filter);
		try {
			product.setPrice(Integer.parseInt(filter));
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExampleMatcher matcher = ExampleMatcher.matchingAll()
				.withMatcher("deleted", new GenericPropertyMatcher().exact())
				.withMatcher("name", new GenericPropertyMatcher().contains())
				.withMatcher("price", new GenericPropertyMatcher().startsWith())
				.withMatcher("description", new GenericPropertyMatcher().contains())
				.withIgnorePaths("id", "imgUrl", "onSale", "imgName", "ratings");
		Page<Product> products = repo.findAll(Example.of(product, matcher), pageable);
		List<Product> listProducts = products.getContent();
		return listProducts.stream().map(prod -> mapProductDTO(prod)).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductDTO> getOnSaleProducts() {
		return repo.findByOnSale(true).stream().map(prod -> mapProductDTO(prod)).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductDTO> getNotDeletedProducts() {
		return repo.findByDeleted(false).stream().map(prod -> mapProductDTO(prod)).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Product product = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
		return mapProductDTO(product);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductDTO> findAll(String keyWord) {
		if (keyWord != null) {
			return repo.findAll(keyWord).stream().map(prod -> mapProductDTO(prod))
					.collect(Collectors.toList());
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductDTO> findFavorites(Long userId) {
		User user = repository3.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		return repo.findAll(user).stream().map(prod -> mapProductDTO(prod)).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductDTO> findTheMostSold() {
		return repo.findAllMostSold().stream().map(dto -> mapProductDTO(dto)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public ProductDTO updateProduct(ProductDTO productDTO, Long id) {
		Product product = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());
		product.setOnSale(productDTO.getOnSale());
		product.setDescription(productDTO.getDescription());
		product.setImgUrl(productDTO.getImgUrl());
		product.setImgName(productDTO.getImgName());

		Product updatedProduct = repo.save(product);

		return mapProductDTO(updatedProduct);
	}

	@Override
	@Transactional
	public void deleteProduct(Long id) {
		Product product = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
		product.setDeleted(true);
		product.setOnSale(false);
		repo.save(product);
	}

	/**
	 * Servicios relacionados con los ratings
	 */

	@Override
	@Transactional
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
	@Transactional(readOnly = true)
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
	@Transactional(readOnly = true)
	public Boolean IsProductEvaluatedByUser(Long userId, Long productId) {
		List<ProductRating> ratings = repository.findByUserIdAndProductId(userId, productId);
		return ratings.size() == 0;
	}

	@Override
	@Transactional(readOnly = true)
	public Integer getImgNames(String name) {
		return repo.findByImgNameAndDeleted(name, false).size();
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
