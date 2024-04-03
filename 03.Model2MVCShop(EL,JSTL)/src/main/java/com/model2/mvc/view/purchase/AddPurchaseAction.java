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
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;


public class AddPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("AddPurchaseAction execute...");
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("apa"+prodNo);
		HttpSession session = request.getSession();
		
		ProductService pService = new ProductServiceImpl();
		Product productVO = pService.findProduct(prodNo);
		
		request.setAttribute("product", productVO);
		
		User userVO = (User)session.getAttribute("user");
		Purchase purchaseVO = new Purchase();
		
		purchaseVO.setPurchaseProd(productVO);
		purchaseVO.setBuyer(userVO);
		purchaseVO.setDlvyAddr(request.getParameter("receiverAddr"));
		purchaseVO.setDlvyDate(request.getParameter("receiverDate"));
		purchaseVO.setDlvyRequest(request.getParameter("receiverRequest"));
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));
		
		System.out.println(purchaseVO.toString());
		
		PurchaseService service = new PurchaseServiceImpl();
		purchaseVO = service.addPurchase(purchaseVO);
		
		request.setAttribute("purchase", purchaseVO);
		request.setAttribute("user", userVO);
		
		return "forward:/purchase/addPurchaseView.jsp";
	}

}
