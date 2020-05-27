package dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utility.ConnectionManager;

public class SearchDao {
	ConnectionManager cm = new ConnectionManager();
	final String user_details = "select * from users";
	final String product_details = "select * from products";
//	final String product_based_on_id = "select * from products";
	final String getLastOrderId = "select max(order_id) from orders";
	
	
	
	public boolean searchForProductId(int id) throws SQLException, ClassNotFoundException, IOException {
		boolean result = false;
		PreparedStatement ps = cm.getConnection().prepareStatement(product_details);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			if(rs.getInt(1) == id) {
				result = true;
				break;
			}
		}
		return result;
	}//searchForProductId() ends
	
	
	public void productBasedOnId(int id) throws SQLException, ClassNotFoundException, IOException {

		PreparedStatement ps = cm.getConnection().prepareStatement(product_details);
		
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			if(rs.getInt("product_id") == id) {
				System.out.println("\n\t"+rs.getString(2)+" costs "+rs.getString(3)+".0 per 1 "+rs.getString(5));
			}
		}
	}
	
	
	public int searchForProdQuantity(int productId) throws SQLException, ClassNotFoundException, IOException {
		int result = 0;
		PreparedStatement ps = cm.getConnection().prepareStatement(product_details);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			if((rs.getInt(1) == productId)) {
				result = rs.getInt(4);
				break;
			}
		}
		return result;
	}//searchForProdQuantity() ends
	
	public float searchForProdPrice(int productId) throws SQLException, ClassNotFoundException, IOException {
		float price = 0;
		PreparedStatement ps = cm.getConnection().prepareStatement(product_details);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			if((rs.getInt(1) == productId) ) {
				price = rs.getFloat(3);
				break;
			}
		}
		return price;
	}
	
	public boolean searchForUserEmail(String email) throws ClassNotFoundException, SQLException, IOException {
		boolean result = false;
		PreparedStatement ps = cm.getConnection().prepareStatement(user_details);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			if(rs.getString("email").equals(email)) {
				result = true;
				break;
			}
		}
		return result;
	}//serachForUserEmail() ends
	
	
	
	public boolean searchForUserMobileNo(String mobile_no) throws SQLException, ClassNotFoundException, IOException {
		boolean result = false;
		PreparedStatement ps = cm.getConnection().prepareStatement(user_details);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			if(rs.getString("mobile_no").equals(mobile_no)) {
				result = true;
				break;
			}
		}
		return result;
	}//searchForUserMobileNo() ends
	
	public boolean searchForUserPassword(String password) throws ClassNotFoundException, SQLException, IOException {
		boolean result = false;
		PreparedStatement ps = cm.getConnection().prepareStatement(user_details);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			if(rs.getString("password").equals(password)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	public int searchForUserId(String email,String password) throws SQLException, ClassNotFoundException, IOException {
		int userId=0;
		
		PreparedStatement ps = cm.getConnection().prepareStatement(user_details);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			if(rs.getString("email").equals(email) && rs.getString("password").equals(password)) {
				userId = rs.getInt("user_id");
				break;
			}
		}
		
		return userId;
	}//searchForUserId() ends
	
	public int getLastOrderId() throws ClassNotFoundException, SQLException, IOException {
		int id = 0;
		PreparedStatement ps = cm.getConnection().prepareStatement(getLastOrderId);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			id = rs.getInt(1);
		}
		return id;
		
	}

}
