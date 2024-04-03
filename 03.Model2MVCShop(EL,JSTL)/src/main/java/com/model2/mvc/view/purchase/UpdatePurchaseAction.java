package com.model2.mvc.view.purchase;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdatePurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		System.out.println("updatepurchaseAction" + tranNo);
		PurchaseService service = new PurchaseServiceImpl();
		
		Purchase purchase = new Purchase();
		
		purchase.setTranNo(tranNo);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDlvyAddr(request.getParameter("receiverAddr"));
		purchase.setDlvyRequest(request.getParameter("receiverReuest"));
		purchase.setDlvyDate(request.getParameter("divyDate"));
		
		
		service.updatePurchase(purchase);
		System.out.println(purchase.toString()+"purchase");
		request.setAttribute("vo", purchase);
		return "redirect:/getPurchase.do?tranNo="+tranNo;
	}

}
