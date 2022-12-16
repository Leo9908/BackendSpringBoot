package com.store.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.store.dto.ProductDTO;
import com.store.dto.ProductRatingDTO;
import com.store.service.ProductsService;
import com.store.uploadingfiles.storage.StorageService;
import com.store.utils.ConstantsApp;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductsService service;

	@Autowired
	private StorageService storageService;

	/**
	 * Listado de productos con paginación
	 * 
	 * @return {@link List}
	 */
	@GetMapping
	public List<ProductDTO> getAllProducts(
			@RequestParam(value = "pageNum", defaultValue = ConstantsApp.PAGE_NUM_DEFAULT, required = false) int pageNum,
			@RequestParam(value = "pageSize", defaultValue = ConstantsApp.PAGE_SIZE_DEFAUT, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = ConstantsApp.PAGE_ORDER_BY_DEFAULT, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ConstantsApp.PAGE_ORDER_DIR_DEFAULT, required = false) String sortDir) {
		return service.getAllProducts(pageNum, pageSize, sortBy, sortDir);
	}

	@GetMapping("/no-deleted")
	public List<ProductDTO> getNotDeletedProducts() {
		return service.getNotDeletedProducts();
	}

	@GetMapping("/on-sale")
	public List<ProductDTO> getOnSaleProducts() {
		return service.getOnSaleProducts();
	}

	@GetMapping("/search")
	public List<ProductDTO> searchProducts(@Param("keyWord") String keyWord) {
		return service.findAll(keyWord);
	}

	@GetMapping("/favorites/{userId}")
	public List<ProductDTO> findFavorites(@PathVariable(name = "userId") Long userId) {
		return service.findFavorites(userId);
	}

	@GetMapping("/most-sold")
	public List<ProductDTO> findTheMostSold() {
		return service.findTheMostSold();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) throws IOException {
		return new ResponseEntity<>(service.createProduct(productDTO), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO dto, @PathVariable Long id) {
		ProductDTO productResponse = service.updateProduct(dto, id);
		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteProduct(@PathVariable(name = "id") Long id) {
		service.deleteProduct(id);
		return ResponseEntity.ok(true);
	}

	@GetMapping("/{productId}/ratings")
	public Double getProductRating(@PathVariable(value = "productId") Long productId) {
		return service.getRatingsByProductId(productId);
	}

	@PostMapping("/{userId}/{productId}/ratings")
	public ResponseEntity<ProductRatingDTO> saveRating(@PathVariable(value = "userId") Long userId,
			@PathVariable(value = "productId") Long productId, @Valid @RequestBody ProductRatingDTO ratingDTO) {
		return new ResponseEntity<>(service.createRating(userId, productId, ratingDTO), HttpStatus.CREATED);
	}

	@GetMapping("/{productId}/{userId}/ratings")
	public Boolean IsProductRatedByUser(@PathVariable(value = "userId", required = true) Long userId,
			@PathVariable(value = "productId", required = true) Long productId) {
		return service.IsProductEvaluatedByUser(userId, productId);
	}
}
