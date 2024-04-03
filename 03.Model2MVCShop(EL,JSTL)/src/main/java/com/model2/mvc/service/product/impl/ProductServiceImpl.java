package com.model2.mvc.service.product.impl;


import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;

public class ProductServiceImpl implements ProductService {

	private ProductDAO productDAO;
	
	public ProductServiceImpl() {
		productDAO = new ProductDAO();
	}

	public Product findProduct(int prodNo) throws Exception {
		return productDAO.findProduct(prodNo);
	}

	public Map<String, Object> getProductList(Search searchVO) throws Exception {
		return productDAO.getProductList(searchVO);
	}

	public Product insertProduct(Product productVO) throws Exception {
		return productDAO.insertProduct(productVO);
		
	}

	public void updateproduct(Product productVO) throws Exception {
		productDAO.updateproduct(productVO);
	}

}
