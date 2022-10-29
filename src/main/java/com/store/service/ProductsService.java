package com.store.service;

import java.util.List;

import com.store.dto.ProductDTO;

public interface ProductsService {
	public ProductDTO createProduct(ProductDTO productDTO);

	public List<ProductDTO> getAllProducts(int pageNum, int pageSize, String sortBy, String sortDir);

	public ProductDTO findById(Long id);

	public ProductDTO updateProduct(ProductDTO productDTO, Long id);

	public void deleteProduct(Long id);
}
