package dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import model.Orders;
import model.Users;
import utility.ConnectionManager;

public class UsersDao {
	SearchDao searchDao=new SearchDao();
	AdminDao adminDao = new AdminDao();
	Users user = new Users();
	Orders order = new Orders();
	ConnectionManager cm = new ConnectionManager();
	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	final String userSignUp = "insert into users values (id.nextval,?,?,?,?,?)";//id,name,address,email,password,mobile_no
	final String sortByNameAsc = "select * from products order by name";
	final String insertOrders = "insert into orders values (id.nextval,?,?,?,?,?)";
	final String displayProducts = "select * from products where quantity_available>0";
	final String insertIntoDelivery = "insert into delivery (delivery_id,order_id) values (id.nextval,?)";
	final String updateProducts = "update products set quantity_available=? where product_id =?";
	
	//method for user sign up
	public boolean userSignUp() throws IOException, ClassNotFoundException, SQLException {
		boolean result = false;
	
		System.out.print("Enter your e-mail: ");
		String email = br.readLine();
	
		System.out.print("Enter your Mobile No: ");
		String mobile_no = br.readLine();
		
		boolean check_email = searchDao.searchForUserEmail(email);
		boolean check_mobile = searchDao.searchForUserMobileNo(mobile_no);
		
		//This will chech whether the user exits in database or not 
		if(check_email || check_mobile) {
			System.out.println(" \n Warning : The given details already exists Please enter new details");
			result = true; //modified and added this line to display success
			userSignUp();
		}
		else {
			
			System.out.print("Enter your name: ");
			String name = br.readLine();
			
			System.out.print("Enter your address: ");
			String address = br.readLine();
			
			System.out.print("Enter password : ");
			String password = br.readLine();
			
			user.setName(name);
			user.setAddress(address);
			user.setEmail(email);
			user.setPassword(password);
			user.setMobile_no(mobile_no);
			
			PreparedStatement ps = cm.getConnection().prepareStatement(userSignUp);
			ps.setString(1, user.getName());
			ps.setString(2, user.getAddress());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());
			ps.setString(5, user.getMobile_no());
			
			result = ps.executeUpdate() > 0;
//			System.out.println("checking sign up: "+result);
			cm.getConnection().commit();
			
		}
		return result;
	}//userSignUp() ends
	
	//method for user login
	public boolean userLogin(String email,String password) throws IOException, ClassNotFoundException, SQLException {
		boolean result = false;
		
		
		boolean check_email = searchDao.searchForUserEmail(email);
		boolean check_password = searchDao.searchForUserPassword(password);
		
		if(check_email && check_password) {
			result = true;
//			getUserIdFromTable(email,password);
			
		}
		return result;
		
		
	}//userLogin() ends
	
	public int getUserIdFromTable(String email , String password) throws ClassNotFoundException, SQLException, IOException {
		int userId=0;
		userId = searchDao.searchForUserId(email, password);
//		System.out.println(userId);
		return userId;
		
	}
	
	
	public void viewProducts() throws ClassNotFoundException, SQLException, IOException {
//		PreparedStatement ps = cm.getConnection().prepareStatement(displayProducts);
		Statement st = cm.getConnection().createStatement();
		ResultSet rs = st.executeQuery(displayProducts);
		System.out.println("\tId\t\tName\t\t\tPrice Per Unit");
		while(rs.next()) {
			System.out.print("\t"+rs.getInt(1));
			System.out.print("\t\t"+rs.getString(2));
			System.out.print("\t\t\t"+rs.getFloat(3));
//			System.out.print("\t\t\t"+rs.getInt(4));
			System.out.println(" per 1 "+rs.getString(5));
		}
	}
	
	//this method is for sorting products
	public void sortProducts() throws SQLException, ClassNotFoundException, IOException {
//			PreparedStatement ps = cm.getConnection().prepareStatement(displayProducts);
			Statement st = cm.getConnection().createStatement();
			ResultSet rs = st.executeQuery(sortByNameAsc);
			System.out.println("\tId\t\tName\t\tPrice Per Unit");
			while(rs.next()) {
				System.out.print("\t"+rs.getInt(1));
				System.out.print("\t\t"+rs.getString(2));
				System.out.print("\t\t"+rs.getFloat(3));
//				System.out.print("\t\t\t"+rs.getInt(4));
				System.out.println(" per 1 "+rs.getString(5));
			}
		
	}

	public void placeOrder(int userId) throws ClassNotFoundException, SQLException, IOException {
		
		System.out.println("\n Products in stock ");
		viewProducts();
		
		System.out.print("\n Select Product Id to place order: ");
		int productId = Integer.parseInt(br.readLine());
		boolean searchId = searchDao.searchForProductId(productId);
		if(!searchId) {
			System.out.println("\n Wrong Id selected");
			placeOrder(userId);
			
		}
		else {
			searchDao.productBasedOnId(productId);
			System.out.print("\nHow much quantity do u need of : ");
			int quantityRequired = Integer.parseInt(br.readLine());
			int quantityAvailable = searchDao.searchForProdQuantity(productId);
//			System.out.println("\n "+searchQuantity);
			if(quantityRequired <= quantityAvailable) {
				PreparedStatement ps = cm.getConnection().prepareStatement(insertOrders);
				order.setUserId(userId);
				order.setProductId(productId);
//				LocalDate postedOn = LocalDate.now();
				order.setDate(LocalDate.now());
				order.setQuantity(quantityRequired);
				order.setOrderPrice(searchDao.searchForProdPrice(productId)*order.getQuantity());
				ps.setInt(1, order.getUserId());
				ps.setInt(2, order.getProductId());
				ps.setDate(3, java.sql.Date.valueOf(order.getDate()));
				ps.setInt(4, order.getQuantity());
				ps.setFloat(5, order.getOrderPrice());
				
				if(ps.executeUpdate() > 0) // insert values to orders table 
				{
					int lastOrderId = searchDao.getLastOrderId(); 
					if(lastOrderId > 0) {
						PreparedStatement inserIntoDelivery = cm.getConnection().prepareStatement(insertIntoDelivery);
						inserIntoDelivery.setInt(1, lastOrderId);
						if(inserIntoDelivery.executeUpdate() > 0)  //insert last order id into delivery table
							System.out.println("\n Your Order Received - It will be delivered soon");
						updateProducts(quantityRequired,quantityAvailable,order.getProductId());
					}
//					System.out.println("\nOrder placed successfully");	
					
				
				}
				
				cm.getConnection().commit();
			  }
			
			else {
				System.out.println("\n Your required quantity for this product is not available"
						+ "\nSorry for the inconvenience "
						+ "\nWe will inform u soon once the product is available"
						+ "\nThanks for shopping with us");
			}
			
		} //else part after successful searching of id		
	}//placeOrder() ends

	private void updateProducts(int quantityRequired,int quantityAvailable, int id) throws ClassNotFoundException, SQLException, IOException {
		int quantityUpdate = quantityAvailable - quantityRequired;
		PreparedStatement ps = cm.getConnection().prepareStatement(updateProducts);
		ps.setInt(1, quantityUpdate);
		ps.setInt(2, id);
		boolean result = ps.execute();
		cm.getConnection().commit();
		//false indicates that the query returned an int value or void,, hence update query returns integer value and execute() returns boolean value
		if(!result) {
			System.out.println("\nStock Updated");
			
		}
		cm.getConnection().close();
	}

	
}
