package com.model2.mvc.view.product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


public class GetProductAction extends Action{

	@Override
	public String execute(HttpServletRequest request, 
										HttpServletResponse response) throws Exception {
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		ProductService service = new ProductServiceImpl();
		Product vo = service.findProduct(prodNo);
		
		request.setAttribute("vo", vo);
		
		String history = "";
		history +=  prodNo + "_";
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0) {
			for (int i = 0; i< cookies.length; i++) {
				Cookie cookie = cookies[i];
				if(cookie.getName().equals("history")) {
					history += cookie.getValue();
				}
			}
		}
		
		
		System.out.println(history);
		
		Cookie cookie = new Cookie("history", history);
		cookie.setMaxAge(60*5);
		response.addCookie(cookie);
		
		/*Cookie cookie = new Cookie("history", Integer.toString(prodNo));
		cookie.setMaxAge(60*5);
		response.addCookie(cookie);*/
		
		
		
		
		return "forward:/product/getProduct.jsp";
	}

	
	
}
