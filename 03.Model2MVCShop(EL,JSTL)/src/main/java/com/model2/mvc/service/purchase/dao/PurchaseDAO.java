package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.user.dao.UserDao;




public class PurchaseDAO {

	public PurchaseDAO() {
	}

	public Purchase addPurchase(Purchase purchase) throws Exception {

		String tCode = "2";

		System.out.println("addPurchase Start...");

		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO transaction values(seq_transaction_tran_no.nextval, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?)";

		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		pstmt.setString(2, purchase.getBuyer().getUserId());
		pstmt.setString(3, purchase.getPaymentOption());
		pstmt.setString(4, purchase.getReceiverName());
		pstmt.setString(5, purchase.getReceiverPhone());
		pstmt.setString(6, purchase.getDlvyAddr());
		pstmt.setString(7, purchase.getDlvyRequest());
		pstmt.setString(8, tCode);
		pstmt.setString(9, purchase.getDlvyDate());

		System.out.println(sql);

		pstmt.executeUpdate();

		con.close();

		return purchase;
	}

	public Purchase getPurchase(int tranNo) throws Exception {
		
		System.out.println(tranNo+" == tran");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM transaction WHERE tran_no = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);
		
		ResultSet rs = stmt.executeQuery();
		
		Purchase purchaseVO = null;
		
		while (rs.next()) {
			purchaseVO = new Purchase();
			UserDao userDAO = new UserDao();
			ProductDAO purchaseDAO = new ProductDAO();
			purchaseVO.setTranNo(rs.getInt("TRAN_NO"));
			purchaseVO.setBuyer(userDAO.findUser(rs.getString("BUYER_ID")));
			System.out.println(rs.getInt("PROD_NO"));
			purchaseVO.setPurchaseProd(purchaseDAO.findProduct(rs.getInt("PROD_NO")));
			purchaseVO.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			purchaseVO.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchaseVO.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchaseVO.setDlvyAddr(rs.getString("DEMAILADDR"));
			purchaseVO.setDlvyRequest(rs.getString("DLVY_REQUEST"));
			purchaseVO.setTranCode(rs.getString("TRAN_STATUS_CODE"));
			purchaseVO.setOrderDate(rs.getDate("ORDER_DATA"));
			purchaseVO.setDlvyDate(rs.getString("DLVY_DATE"));
		}
		
		
		
		con.close();
		
		return purchaseVO;
	}

	public HashMap<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {

		System.out.println("getPurchaseList");

		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM transaction t, users u" 
				+ " WHERE u.user_id =  '"+ buyerId +"'  AND u.user_id = t.buyer_id"
				+ " ORDER BY t.tran_no";

		
		
		PreparedStatement stmt = con.prepareStatement(sql,
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		
		int totalCount = this.getTotalCount(sql);
		ResultSet rs = stmt.executeQuery();

		
		rs.last(); 
		int total = rs.getRow();
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		
		rs.absolute(search.getCurrentPage() * search.getPageSize() - search.getPageSize() + 1);
		
		List<Purchase> list = new ArrayList<Purchase>();
		if (total > 0) {
			for (int i = 0; i < search.getPageSize(); i++) {
				Purchase vo = new Purchase();
				UserDao userDAO = new UserDao();
				ProductDAO purchaseDAO = new ProductDAO();
				vo.setTranNo(rs.getInt("TRAN_NO"));
				vo.setBuyer(userDAO.findUser(rs.getString("USER_ID")));
				vo.setPurchaseProd(purchaseDAO.findProduct(rs.getInt("PROD_NO")));
				vo.setPaymentOption(rs.getString("PAYMENT_OPTION"));
				vo.setReceiverName(rs.getString("RECEIVER_NAME"));
				vo.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
				vo.setDlvyAddr(rs.getString("DEMAILADDR"));
				vo.setDlvyRequest(rs.getString("DLVY_REQUEST"));
				vo.setTranCode(rs.getString("TRAN_STATUS_CODE"));
				vo.setOrderDate(rs.getDate("ORDER_DATA"));
				vo.setDlvyDate(rs.getString("DLVY_DATE"));
				
				list.add(vo);
				if (!rs.next())
					break;
			}
		}
		
		
		map.put("totalCount", new Integer(totalCount));
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());
		System.out.println("list.size() : "+ list.size());
		
		rs.close();
		stmt.close();
		con.close();
		
		return map;
	}

	public HashMap<String, Object> getSaleList(Search search) throws Exception {
		return null;
	}

	public Purchase updatePurchase(Purchase purchaseVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE transaction SET payment_option = ?, receiver_name = ?, receiver_phone = ?, DEMAILADDR = ?, DLVY_REQUEST = ?, DLVY_DATE = ? WHERE tran_no = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchaseVO.getPaymentOption());
		stmt.setString(2, purchaseVO.getReceiverName());
		stmt.setString(3, purchaseVO.getReceiverPhone());
		stmt.setString(4, purchaseVO.getDlvyAddr());
		stmt.setString(5, purchaseVO.getDlvyRequest());
		stmt.setString(6, purchaseVO.getDlvyDate());
		stmt.setInt(7, purchaseVO.getTranNo());
		stmt.executeUpdate();
		
		con.close();
		System.out.println("updatePurchase end...");
		return purchaseVO;
	}

	public void updateTranCode(Purchase purchaseVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		int tranCode = Integer.parseInt(purchaseVO.getTranCode()) + 1;
		
		String sql = "UPDATE transaction SET tran_status_code = " + tranCode + "";
		if (purchaseVO.getPurchaseProd() != null) {
			sql += " WHERE prod_no = "+ purchaseVO.getPurchaseProd().getProdNo() + "";
		}
		if (purchaseVO.getTranNo() != 0) {
			System.out.println(purchaseVO.getTranNo());
			sql += " WHERE tran_no = "+purchaseVO.getTranNo();
		}
		
		PreparedStatement stmt = con.prepareStatement(sql);
		System.out.println("updateTranCode >>> " + sql);
		
		stmt.executeUpdate();
		
		con.close();
		
		
	}
	
private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	
	// 게시판 currentPage Row 만  return 
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("UserDAO :: make SQL :: "+ sql);	
		
		return sql;
	}

}
