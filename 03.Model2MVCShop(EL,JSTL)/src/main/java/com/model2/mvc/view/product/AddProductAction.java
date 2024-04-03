package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


public class AddProductAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("AddProductAction execute");
		Product productVO = new Product();
		productVO.setProdName(request.getParameter("prodName"));
		productVO.setProdDetail(request.getParameter("prodDetail"));
		
		String[] md = request.getParameter("manuDate").split("-");
		
		productVO.setManuDate(md[0]+md[1]+md[2]);
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		productVO.setFileName(request.getParameter("fileName"));
		
		System.out.println(productVO);
		
		ProductService service = new ProductServiceImpl();
		Product product = service.insertProduct(productVO);
		
		System.out.println(product.toString());
		
		request.setAttribute("product", product);
		
		return "forward:/product/addProductResultView.jsp";
		
	}

	
	
}
