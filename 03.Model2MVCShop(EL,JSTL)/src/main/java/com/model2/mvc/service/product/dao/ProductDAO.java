package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;

public class ProductDAO {

	public ProductDAO() {}
	
	public Product findProduct(int prodNo) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from PRODUCT where PROD_NO = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		
		ResultSet rs = stmt.executeQuery();
		
		Product productVO = null;
		while (rs.next()) {
			productVO = new Product();
			productVO.setProdNo(rs.getInt("PROD_NO"));
			productVO.setProdName(rs.getString("PROD_NAME"));
			productVO.setProdDetail(rs.getString("PROD_DETAIL"));
			productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
			productVO.setPrice(rs.getInt("PRICE"));
			productVO.setFileName(rs.getString("IMAGE_FILE"));
			productVO.setRegDate(rs.getDate("REG_DATE"));
		}
		
		System.out.println(productVO.toString());
		
		con.close();
		return productVO;
	}
	
public HashMap<String, Object> getProductList(Search searchVO) throws Exception{
		
		System.out.println("productDAO getProductList start");
		HashMap<String,Object> map = new HashMap<String,Object>();
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from PRODUCT ";
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0") && !searchVO.getSearchKeyword().equals("")) {
				sql += " where PROD_NO LIKE '%"+searchVO.getSearchKeyword() + "%'";
			} else if (searchVO.getSearchCondition().equals("1") && !searchVO.getSearchKeyword().equals("")) {
				sql += " where PROD_NAME LIKE '%" + searchVO.getSearchKeyword() + "%'";
			} else if (searchVO.getSearchCondition().equals("2") && !searchVO.getSearchKeyword().equals("")) {
				sql += " where PRICE = " + searchVO.getSearchKeyword() + "";
			}
		}
		sql += " order by PROD_NO";
		
		System.out.println("ProductDAO::Original SQL :: " + sql);
		
		int totalCount = this.getTotalCount(sql);
		
		
		sql = makeCurrentPageSql(sql,searchVO);
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		List<Product> list = new ArrayList<Product>();
		while(rs.next()) {
			Product vo = new Product();
			vo.setProdNo(rs.getInt("PROD_NO"));
			vo.setProdName(rs.getString("PROD_NAME"));
			vo.setProdDetail(rs.getString("PROD_DETAIL"));
			vo.setManuDate(rs.getString("MANUFACTURE_DAY"));
			vo.setPrice(rs.getInt("PRICE"));
			vo.setFileName(rs.getString("IMAGE_FILE"));
			vo.setRegDate(rs.getDate("REG_DATE"));
			vo.setProTranCode(rs.getString("TRAN_STATUS_CODE"));
			
			list.add(vo);
		} 
		
		
		map.put("totalCount", new Integer(totalCount));
		map.put("list",list);
		
		System.out.println(totalCount);
		System.out.println(list);
		
		con.close();
		rs.close();
		stmt.close();
		
		return map;
	}  

	
	
	public Product insertProduct(Product productVO) throws Exception {
		
		System.out.println("insertProduct start...");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "insert into product values (seq_product_prod_no.nextval, ? , ? , ? , ? , ? ,SYSDATE)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.executeUpdate();
		
		con.close();
		
		
		
		return productVO;
	}
	
	public void updateproduct(Product productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "update PRODUCT set PROD_NAME = ?, PROD_DETAIL = ?, MANUFACTURE_DAY = ?, PRICE = ?, IMAGE_FILE = ? where PROD_NO = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1,  productVO.getProdName());
		stmt.setString(2,  productVO.getProdDetail());
		stmt.setString(3,  productVO.getManuDate());
		stmt.setInt(4,  productVO.getPrice());
		stmt.setString(5,  productVO.getFileName());
		stmt.setInt(6, productVO.getProdNo());
		stmt.executeUpdate();
		
		con.close();
	}
	
		// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
		private int getTotalCount(String sql) throws Exception {
			
			System.out.println("getTotalCount Start...");
			
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
		
		
		 //게시판 currentPage Row 만  return 
		private String makeCurrentPageSql(String sql , Search search){
			System.out.println("makeCurrentPageSql Start....");
			sql = 	"SELECT p.* , NVL(t.tran_status_code, 1) tran_status_code "+ 
						"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
										" 	FROM (	"+sql+" ) inner_table "+
										"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) p, transaction t " +
						"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize() +
						" AND t.prod_no(+) = p.prod_no ";
			
			System.out.println("ProductDAO :: make SQL :: "+ sql);	
			
			return sql;
		}

}
