package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class UpdateProductAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		ProductService service = new ProductServiceImpl();
		Product productVO = service.findProduct(prodNo);
		
		productVO.setProdName(request.getParameter("prodName"));
		productVO.setProdDetail(request.getParameter("prodDetail"));
		
		if(request.getParameter("manuDate").contains("-")) {
			String[] md = request.getParameter("manuDate").split("-");
			productVO.setManuDate(md[0]+md[1]+md[2]);
		}else {
			productVO.setManuDate(request.getParameter("manuDate"));
		}
		
		
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		productVO.setFileName(request.getParameter("fileName"));
		
		System.out.println(productVO.toString() + "upa");
		
		service.updateproduct(productVO);
		
		request.setAttribute("productVO", productVO);
		
		
		return "redirect:/getProduct.do?prodNo="+prodNo;
	}

	
	
}
