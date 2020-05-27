package dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Products;
import utility.ConnectionManager;

public class AdminDao {

	final String login = "select * from admin";
	final String addProductsToTable = "insert into products values (id.nextval,?,?,?,?)";
	final String updateProducts = "update products set name = ? , price_per_unit = ?, quantity_available = ? , measures = ? where product_id= ?";
	
	final String displayProducts = "select * from products";
	final String deleteProducts = "delete from products where product_id = ?";
	final String displayOrders = "select * from orders";
	
	final String displayOrdersModified = "select orders.order_id as order_id,users.name as username,products.name as product_name,products.price_per_unit as Unit_Price,orders.order_date as Date_of_order,\r\n" + 
			"orders.quantity as ordered_Quantity,orders.price as total from users users, orders orders,products products where \r\n" + 
			"orders.user_id = users.user_id and products.product_id = orders.product_id";
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	ConnectionManager cm = new ConnectionManager();
	SearchDao searchDao = new SearchDao();
	Products products;
	
	//admin login functionality
	public boolean adminLogin() throws IOException, ClassNotFoundException, SQLException {
		boolean result = false;
		
//		getting user inputs
		System.out.print("Enter e-mail: ");
		String email_entry = br.readLine();
		
		System.out.print("Enter Pasword: ");
		String password_entry = br.readLine();
		
		Statement stmt = null;
		stmt = cm.getConnection().createStatement();
		
		ResultSet rs1 = stmt.executeQuery(login);
		while(rs1.next()) {
			
			String email_result = rs1.getString("email");
			String password_result = rs1.getString("password");
			if(email_result.equals(email_entry) && password_result.equals(password_entry)) {
				result = true;
				break;
			}
			
//			System.out.println(rs1.getString(3)+" "+rs1.getString(4));
			
		}
		return result;
		
	} // adminLogin() ends
	
	//adding products to database
	public void addProducts() throws NumberFormatException, IOException, ClassNotFoundException, SQLException {
		System.out.println("Adding Products");
		
		int count = 0;
		int add = 1;
		while(add == 1) {
			products = new Products();
			//reading product details from admin
			System.out.print("Enter Product name: ");
			String name = br.readLine();
			System.out.print("Enter unit price of product: ");
			float pricePerUnit = Float.parseFloat(br.readLine());
			System.out.print("Enter available quantity of product: ");
			int quantityAvailable = Integer.parseInt(br.readLine());
			System.out.print("Enter measures of products (like kg,liter,cm,meter,pieces etc.,) : ");
			String measures=br.readLine();
			
			//using setters
			products.setName(name);
			products.setPricePerUnit(pricePerUnit);
			products.setQuantityAvailable(quantityAvailable);
			products.setMeasures(measures);
			
			PreparedStatement ps = cm.getConnection().prepareStatement(addProductsToTable);
			ps.setString(1, products.getName());
			ps.setFloat(2, products.getPricePerUnit());
			ps.setInt(3, products.getQuantityAvailable());
			ps.setString(4, products.getMeasures());
			boolean result = ps.execute(); //
			cm.getConnection().commit();
//			System.out.println(result);
			//false indicates that the query returned an int value or void,, hence insert query returns integer value
			if(!result) {
				System.out.println("\nProduct added");
				count++;
			}
//			count--;
			System.out.print("\tSelect 1 to add another product\n\tSelect any number to stop adding products\\n\tYour choice: ");
			try {
			int choice = Integer.parseInt(br.readLine());
			if(choice == 1) {
				add =1;
			}
			else {
				add = choice;
				System.out.println(count+" Products are added now");
			}
			}
			catch (Exception e) {
				System.out.println("\nYour option is wrong");
			}
		}
		
	}//addProducts() ends

	// method for updating products
	public void updateProducts() throws ClassNotFoundException, SQLException, IOException {
		products = new Products();
		System.out.println("\n\t Available products are");
		viewProducts();
		System.out.print("\nEnter Product Id to modify the item: ");
		int product_id = Integer.parseInt(br.readLine());
//		name = ? , price_per_unit = ?, quantity_available = ? , measures = ?
		boolean search = searchDao.searchForProductId(product_id);
		if(search) {
			
			System.out.print("Enter Product name: ");
			String name = br.readLine();
			System.out.print("Enter unit price of product: ");
			float pricePerUnit = Float.parseFloat(br.readLine());
			System.out.print("Enter available quantity of product: ");
			int quantityAvailable = Integer.parseInt(br.readLine());
			System.out.print("Enter measures of products (like kg,liter,cm,meter,pieces etc.,) : ");
			String measures=br.readLine();
			PreparedStatement ps = cm.getConnection().prepareStatement(updateProducts);
			
			
			//using setters
			products.setName(name);
			products.setPricePerUnit(pricePerUnit);
			products.setQuantityAvailable(quantityAvailable);
			products.setMeasures(measures);
			
			ps.setString(1, products.getName());
			ps.setFloat(2, products.getPricePerUnit());
			ps.setInt(3, products.getQuantityAvailable());
			ps.setString(4, products.getMeasures());
			ps.setInt(5, product_id);
			
			boolean result = ps.execute();
			cm.getConnection().commit();
//			System.out.println(result);
			//false indicates that the query returned an int value or void,, hence update query returns integer value and execute() returns boolean value
			if(!result) {
				System.out.println("\nUpdation Success");
				viewProducts();
			}
		}
		else {
			System.out.println("\n Wrong Id selected");
			updateProducts();
		}

		

		
	}//updateProducts() ends
	
	public void deleteProducts() throws ClassNotFoundException, SQLException, IOException {
		products = new Products();
		System.out.println("\n\t Available products are");
		viewProducts();
		System.out.print("\nEnter Product Id to DELETE the items: ");
		int product_id = Integer.parseInt(br.readLine());
		PreparedStatement ps = cm.getConnection().prepareStatement(deleteProducts);
		ps.setInt(1, product_id);

		boolean result = ps.execute();
		cm.getConnection().commit();
//		System.out.println(result);
		//false indicates that the query returned an int value or void,, hence update query returns integer value and execute() returns boolean value
		if(!result) {
			System.out.println("\nDeletion Success");
			viewProducts();
		}
		
	}
	
	public void viewProducts() throws ClassNotFoundException, SQLException, IOException {
//		PreparedStatement ps = cm.getConnection().prepareStatement(displayProducts);
		Statement st = cm.getConnection().createStatement();
		ResultSet rs = st.executeQuery(displayProducts);
		System.out.println("\tId\t\tName\t\tPrice Per Unit\t\tAvailable Quantity");
		while(rs.next()) {
			System.out.print("\t"+rs.getInt(1));
			System.out.print("\t\t"+rs.getString(2));
			System.out.print("\t\t"+rs.getFloat(3));
			System.out.print("\t\t\t"+rs.getInt(4));
			System.out.println(" "+rs.getString(5));
		}
	}
	
	public void viewOrders() throws ClassNotFoundException, SQLException, IOException {
		int option = 0;
		System.out.print("\n\tSelect\n\t1. For Beautified Data\n\t2. For Original Data\n\t3. For Exit\n\tYour Option: ");
		try {
			option = Integer.parseInt(br.readLine());
		}
		
		catch (Exception e){
			System.out.println("\nCaution !!! Please Input Any Number");
			viewOrders();
		}
		
		switch(option) {
		case 1:
			
			System.out.println("\n Modified view of data ");
			PreparedStatement pst = cm.getConnection().prepareStatement(displayOrdersModified);
			ResultSet rst = pst.executeQuery();
			System.out.println("\n\t   Order Id\t   User name\t\tProduct Name\tUnit Price\tOrder Date\tOrder Quantity\t  Order Total");

			while(rst.next()) {
				System.out.print("\t\t"+rst.getInt(1));
				System.out.print("\t\t"+rst.getString(2));
				System.out.print("\t\t"+rst.getString(3));
				System.out.print("\t\t"+rst.getFloat(4));
				System.out.print("\t\t"+rst.getDate(5));
				System.out.print("\t\t"+rst.getInt(6));
				System.out.println("\t\t"+rst.getFloat(7));
			}
			viewOrders();

			break;
		
		case 2:
			PreparedStatement ps = cm.getConnection().prepareStatement(displayOrders);
			ResultSet rs = ps.executeQuery();
			System.out.println("\n The actual data stored in database");
			System.out.println("\n\tOrder Id\tUser Id\t\tProduct Id\tOrder Date\tQuantity\tOrder Total");
			while(rs.next()) {
				System.out.print("\t"+rs.getInt(1));
				System.out.print("\t\t"+rs.getInt(2));
				System.out.print("\t\t"+rs.getInt(3));
				System.out.print("\t\t"+rs.getDate(4));
				System.out.print("\t\t"+rs.getInt(5));
				System.out.println("\t"+rs.getFloat(6));
			}
			viewOrders();
			break;
			
		case 3:
			System.out.println("\nThank u");
			break;
		default : System.out.println("\nWrong Entry"); viewOrders();break;
		
		}
		

	}
}
