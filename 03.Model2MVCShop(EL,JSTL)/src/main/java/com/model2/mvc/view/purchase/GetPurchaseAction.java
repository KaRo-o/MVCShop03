package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;


public class GetPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		System.out.println(tranNo);
		
		PurchaseService service = new PurchaseServiceImpl();
		Purchase purchase = service.getPurchase(tranNo);
		
		System.out.println("getPurchase execute" + purchase);
		request.setAttribute("purchase", purchase);
		
		
		return "forward:/purchase/getPurchase.jsp";
	}

}
