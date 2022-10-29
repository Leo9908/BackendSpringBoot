package com.store.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.store.dto.ProductDTO;
import com.store.entitys.Product;
import com.store.exceptions.ResourceNotFoundException;
import com.store.repository.ProductsRepository;
import com.store.service.ProductsService;

@Service
public class ProductServiceImpl implements ProductsService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ProductsRepository repo;

	@Override
	public ProductDTO createProduct(ProductDTO productDTO) {

		Product product = this.mapEntity(productDTO);

		Product newProduct = repo.save(product);

		ProductDTO productResponse = this.mapDTO(newProduct);

		return productResponse;
	}

	@Override
	public List<ProductDTO> getAllProducts(int pageNum, int pageSize, String sortBy, String sortDir) {
		Pageable pageable = PageRequest.of(pageNum, pageSize, Direction.fromString(sortDir), sortBy);
		Page<Product> products = repo.findAll(pageable);

		List<Product> listProducts = products.getContent();
		return listProducts.stream().map(prod -> mapDTO(prod)).collect(Collectors.toList());
	}

	@Override
	public ProductDTO findById(Long id) {
		Product product = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
		return mapDTO(product);
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

		return mapDTO(updatedProduct);
	}

	@Override
	public void deleteProduct(Long id) {
		repo.deleteById(id);
	}

	/**
	 * Convierte entidad a DTO
	 */
	private ProductDTO mapDTO(Product product) {
		ProductDTO productDTO = mapper.map(product, ProductDTO.class);
		return productDTO;
	}

	/**
	 * Convierte DTO a entidad
	 */
	private Product mapEntity(ProductDTO productDTO) {
		Product product = mapper.map(productDTO, Product.class);
		return product;
	}
}
