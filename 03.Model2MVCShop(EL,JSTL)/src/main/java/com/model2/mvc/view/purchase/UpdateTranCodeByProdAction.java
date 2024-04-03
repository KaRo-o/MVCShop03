package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;


public class UpdateTranCodeByProdAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		PurchaseService service = new PurchaseServiceImpl();
		Purchase vo = new Purchase();
		
		String tranCode = request.getParameter("tranCode");
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		ProductService pService = new ProductServiceImpl();
		Product product = pService.findProduct(prodNo);
		vo.setPurchaseProd(product);
		vo.setTranCode(tranCode);
		service.updateTranCode(vo);
		
		
		return "redirect:/listProduct.do?menu=manage";
		
	}

}
