package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


public class AddPurchaseViewAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		HttpSession session = request.getSession();
		
		ProductService pService = new ProductServiceImpl();
		Product product = pService.findProduct(prodNo);
		request.setAttribute("product", product);
		
		User user = (User)session.getAttribute("user");
		Purchase purchase = new Purchase();
		
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		purchase.setDlvyAddr(request.getParameter("receiverAddr"));
		purchase.setDlvyDate(request.getParameter("receiverDate"));
		purchase.setDlvyRequest(request.getParameter("receiverRequest"));
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName("receiverName");
		purchase.setReceiverPhone("receiverPhone");
		
		request.setAttribute("purchase", purchase);
		
		
		return "forward:/purchase/addPurchase.jsp";
	}

}
