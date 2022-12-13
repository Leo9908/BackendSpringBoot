package com.store.service;

import java.util.List;

import com.store.dto.ProductDTO;
import com.store.dto.ProductRatingDTO;

public interface ProductsService {
	public ProductDTO createProduct(ProductDTO productDTO);

	public List<ProductDTO> getAllProducts(int pageNum, int pageSize, String sortBy, String sortDir);

	public List<ProductDTO> getOnSaleProducts();
	
	public List<ProductDTO> getNotDeletedProducts();

	public ProductDTO findById(Long id);

	public ProductDTO updateProduct(ProductDTO productDTO, Long id);

	public void deleteProduct(Long id);

	public Double getRatingsByProductId(Long productId);

	public Boolean IsProductEvaluatedByUser(Long userId, Long productId);

	public ProductRatingDTO createRating(Long userId, Long productId, ProductRatingDTO ratingDTO);

	public List<ProductDTO> findAll(String keyWord);

	public List<ProductDTO> findFavorites(Long userId);

	public List<ProductDTO> findTheMostSold();
}
