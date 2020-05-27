package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import dao.AdminDao;
import dao.UsersDao;
//import dao.SearchDao;
import utility.ConnectionManager;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		// creating objects for all required classes
		AdminDao adminDao = new AdminDao();
		UsersDao usersDao = new UsersDao();
		ConnectionManager cm =new ConnectionManager();
		cm.getConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int menu_option;
//		Main Menu starts
		do {
			System.out.println("\n\t~~~ Welcome ~~~");
			System.out.println("\t1.Admin");
			System.out.println("\t2.User");
			System.out.println("\t3.Exit");
			System.out.print("Your Choice: ");
			menu_option = Integer.parseInt(br.readLine());
			switch (menu_option) {
			
			case 1: //for admin menu
//				System.out.println("\tAdmin Menu"); 
				boolean admin_login = false;
				int admin_option;
				do {
					System.out.println("\n\t Admin Menu");
					System.out.println("\t 1.Login");
					System.out.println("\t 2.Add Products");
					System.out.println("\t 3.Update Products");
					System.out.println("\t 4.Delete Products");
					System.out.println("\t 5.View Products");
					System.out.println("\t 6.View Orders");
					System.out.println("\t 7.Logout");
					System.out.println("\t 8.Exit");
					System.out.print("Your choice: ");
					admin_option = Integer.parseInt(br.readLine());
					switch(admin_option) {
					case 1: 
						if(admin_login) {
							System.out.println("\nAlready Logged In - Do Log out First");
						}
						else {
							admin_login = adminDao.adminLogin();
							if(admin_login)
								System.out.println("\n\tAdmin Log In sucess");
							else
								System.out.println("\n\tAdmin Login failed");
						}
						
						break;
						// admin-menu case1 ends
					case 2:
						if(admin_login)
							adminDao.addProducts();
						else {
							System.out.println("\nNo user has been logged in : Do Login First");
							admin_login = adminDao.adminLogin();
							if(admin_login)
								adminDao.addProducts();
						}
//						System.out.println("\t 2.Add Products");
						break;
						//admin-menu case2 ends
					case 3: 
						if(admin_login)
							adminDao.updateProducts();
						else {
							System.out.println("\nNo user has been logged in : Do Login First");
							admin_login = adminDao.adminLogin();
							if(admin_login)
								adminDao.updateProducts();
						}
						break;
					//admin-menu case3 ends
					case 4:
						if(admin_login)
							adminDao.deleteProducts();
						else {
							System.out.println("\nNo user has been logged in : Do Login First");
							admin_login = adminDao.adminLogin();
							if(admin_login)
								adminDao.deleteProducts();						
						}
						break;
						//admin-menu case4 ends
					case 5:
						if(admin_login)
							adminDao.viewProducts();
						else {
							System.out.println("\nNo user has been logged in : Do Login First");
							admin_login = adminDao.adminLogin();
							if(admin_login)
								adminDao.viewProducts();						
						}
						break;
						//admin-menu case5 ends
					case 6: 
//						System.out.println("6");
						adminDao.viewOrders();
						break;
						//admin-menu case6 ends
					case 7:
//						System.out.println("7");
						if(admin_login) {
							admin_login = false;
							System.out.println("\nLog out successfull");
						}
						else
							System.out.println("\nNo user has been logged in LOG OUT FAILED");
						
						break;
						//admin-menu case7 ends
					case 8: 
						System.out.println("\t ..... Thank u .....");
						break;
						//admin-menu case8 ends
					default: System.out.println("\t Wrong Entry");break;
						
					}
					
					
				}while(admin_option != 8);
				break;// main-menu case1 ends
				
				
			case 2: // for user menu
				boolean user_login = false;int userId =0;
				int user_option;
				do {
					System.out.println("\n\t User Menu");
					if(user_login == false) {
					
					System.out.println("\t 1.Sign Up");
					System.out.println("\t 2.Login");
					}
					if(user_login == true) {
					System.out.println("\t 3.View Products");
					System.out.println("\t 4.Sort Products");
					System.out.println("\t 5.Place Order");
					System.out.println("\t 6.Logout");
					}
					System.out.println("\t 7.Exit");
					System.out.print("Your choice: ");
					user_option = Integer.parseInt(br.readLine());
					
					switch(user_option) {
					case 1:
						// option for user sign up
						if(user_login == false) {
						boolean usrSignUp = false;
						
						if(user_login) {
							System.out.println("\nLog out to Register a new account");
						}
						else {
							usrSignUp=usersDao.userSignUp();
							if(usrSignUp) {
								System.out.println("\nYour registration was Sucess");
							}
							else {
								System.out.println("\nRegistration Failed !!!" );
							}
								
							
						}
						}
						else {
							System.out.println("\nWrong Entry");
						}
						
						break;//user-menu case1 ends
					case 2:
						// option for user login
						
						if(!user_login) {//used to display the menu only when user was not logged in ,, if any user was logged in then this case 2 will never be displayed

							if(user_login) {
								System.out.println("\nAlready Logged In - Do Log out First");
							}
							else {

								System.out.print("Enter your E-mail: ");
								String email = br.readLine();
								
								System.out.print("Give your Passsword: ");
								String password = br.readLine();
								
								user_login = usersDao.userLogin(email,password);
								if(user_login) {
									userId = usersDao.getUserIdFromTable(email, password);
									System.out.println("\n\tUser Login Sucess "+userId);
								}
								else
									System.out.println("\n\tUser Login Failed!!!");
							}
						}
						else {
							System.out.println("\nWrong Entry");
						}
						
						break;//user-menu case2 ends
					case 3:
						// option to view products
						if(user_login) {
							usersDao.viewProducts();
						}
						else {
							System.out.println("\n Wrong Entry");
						}
						
						
						break;//user-menu case3 ends
					case 4:
						// option for sorting functionality 
						if(user_login) {
						usersDao.sortProducts();
						}
						else {
							System.out.println("\nWrong Entry");
						}
//						System.out.println("4");
						break;//user-menu case4 ends
					case 5:
						// option for placing orders
						if(user_login) {
							usersDao.placeOrder(userId);
							
							}
							else {
								System.out.println("\nWrong Entry");
							}
						break;//user-menu case5 ends
					case 6:
						if(user_login) {
							user_login = false;
							System.out.println("\nLog out successfull");
						}
						else
							System.out.println("\nWrong Entry");
						break;//user-menu case6 ends
					case 7:
						System.out.println("7");
						break;//user-menu case7 ends
					}
				}while(user_option!=7);
				
				break;
			case 3: //to exit
				System.out.println("\t ..... Thank U .....");break;
			default: System.out.println("\t Wrong Entry");break;
			}
			
			
		}while(menu_option!=3);
		
//		Main Menu ends
	}

}
