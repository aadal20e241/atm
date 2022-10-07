package customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class customer {

	public static Connection con;
	public static ResultSet rs;
	public static Statement st;
	public static PreparedStatement pst;
	public static Scanner s = new Scanner(System.in);

	public static void createatm() throws ClassNotFoundException, SQLException {
		con = dbutil.getConnect();
		PreparedStatement pst = con.prepareStatement("create table atm(Denomination number,Value number,Number number");
		pst.execute();
		System.out.println("atm table created");
	}

	public static void custdet() throws ClassNotFoundException, SQLException {
		con = dbutil.getConnect();
		pst = con.prepareStatement("create table customer(acc_no number,name varchar(60),balance number,pin number");
		pst.execute();
		System.out.println("custdet table created");
	}

	public static void addcust() throws ClassNotFoundException, SQLException {
		con = dbutil.getConnect();
		PreparedStatement pst = con.prepareStatement("insert into customer values(1002201,'aadal',10000,1234)");
		pst.execute();
		PreparedStatement pst2 = con.prepareStatement("insert into customer values(1002202,'arasu',10000,5432)");
		pst2.execute();
		System.out.println("customer values inserted");
	}

	public static void activateatm() throws ClassNotFoundException, SQLException {
		con = dbutil.getConnect();
		PreparedStatement pst1 = con.prepareStatement("insert into atm values(2000,0,0");
		pst1.execute();
		PreparedStatement pst2 = con.prepareStatement("insert into atm values(500,0,0)");
		pst2.execute();
		PreparedStatement pst3 = con.prepareStatement("insert into atm values(100,0,0");
		pst3.execute();
	}

	public static void addnewcust() throws SQLException, ClassNotFoundException {
		int acc_no = 1002203;
		con = dbutil.getConnect();
		PreparedStatement pst = con.prepareStatement("insert into customer values(?,?,?,?)");
		pst.setInt(1, acc_no);
		String name = s.next();
		pst.setString(2, name);
		int balance = s.nextInt();
		pst.setInt(3, balance);
		int pin = s.nextInt();
		pst.setInt(4, pin);
		pst.execute();
		acc_no++;

	}

	public static void loadcash() throws ClassNotFoundException, SQLException {
		con = dbutil.getConnect();
		PreparedStatement pst = con
				.prepareStatement("update table atm set Number=Number+?,value=value+? where Denomination=?");
		System.out.println("enter the denomination:");
		int Denomination = s.nextInt();
		System.out.println("enter the number");
		int number = s.nextInt();
		int value = number * Denomination;
		pst.setInt(1, Denomination);
		pst.setInt(2, number);
		pst.setInt(3, value);
		pst.executeUpdate();

	}

	public static void showdetails() throws ClassNotFoundException, SQLException {
		con = dbutil.getConnect();
		PreparedStatement pst = con.prepareStatement("select*from customer");
		rs = pst.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + " " + rs.getInt(4));
		}

	}

	public static void checkbalance() throws ClassNotFoundException, SQLException {
		con = dbutil.getConnect();
		System.out.println("enter the account no:");
		int acc_no = s.nextInt();
		System.out.println("enter the pin:");
		int pin_no = s.nextInt();
		int pinno = 0, accbal = 0;
		PreparedStatement pst = con.prepareStatement("select pin, balance from customer where acc_no=?");
		pst.setInt(1, acc_no);
		rs = pst.executeQuery();
		while (rs.next()) {
			pinno = rs.getInt("pin");
			accbal = rs.getInt("balance");
		}
		if (pinno == pin_no) {
			System.out.println("balance is:" + " " + accbal);
		} else {
			System.out.println("incorrect pin");
		}
	}

	public static void withdraw() throws ClassNotFoundException, SQLException {
		con = dbutil.getConnect();
		int accbal = 0,pinno=0;
		System.out.println("enter the account no");
		int acc_no = s.nextInt();
		System.out.println("enter the pin:");
		int pin_no = s.nextInt();
		System.out.println("enter the amount to widraw in the multiples of 100:");
		int amt = s.nextInt();
		if (amt % 100 != 0) {
			System.out.println("invalid amount to widraw");
			return;
		} else if (amt<100) {
			System.out.println("amount must be greater than 100");
			return;
		}
		else if(amt>10000) {
			System.out.println("daily limit reached");
		}
		PreparedStatement pst = con.prepareStatement("select pin, balance from customer where acc_no=?");
		rs=pst.executeQuery();
		while(rs.next()) {
			pinno=rs.getInt("pin");
			accbal=rs.getInt("balance");
		}
		if(pinno==pin_no && accbal>=amt ) {
			accbal=accbal-amt;
			System.out.println("your current balance:"+""+accbal);
			PreparedStatement pst1 =con.prepareStatement("update customer set accbal=? where acc_no=?");
	         pst1.setInt(1, accbal);
	         pst1.setInt(2, acc_no);
	         System.out.println("withdraw successfully");
	         if(amt%2000==0) {
	        	 int a=amt/2000;
	        	 PreparedStatement pst2=con.prepareStatement("update atm set number=number-?,value=value-? where denomination=?");
	        	  int value=a*2000;
	        	 pst2.setInt(1, a);
	        	 pst2.setInt(2,value );
	        	 pst2.setInt(3, 2000);
	        	 pst2.execute();
	         }
	         else if(amt%1000==0 && amt>3000) {
	        	 int a=amt/1000;
	        	 PreparedStatement pst2=con.prepareStatement("update atm set number=number-?,value=value-? where denomination=?");
                 int value=a*1000;
                 pst2.setInt(1, a);
	        	 pst2.setInt(2,value );
	        	 pst2.setInt(3, 2000);
	        	 pst2.executeUpdate();
	        	 PreparedStatement pst3=con.prepareStatement("update atm set number=number-?,value=value-?,where denomination=?");
	        	 pst3.setInt(1, 2);
	        	 pst3.setInt(2,1000);
	        	 pst3.setInt(3, 500);
	        	 pst3.executeUpdate();
	         }
	         else
	         {
	        	 int a=amt/2000;
	        	 int value=a*2000;
	        	 PreparedStatement pst2=con.prepareStatement("update atm set number=number-?,value=value-?,where denomination=?");
	        	 pst2.setInt(1,a);
	        	 pst2.setInt(2, value);
	        	 pst2.setInt(3, 2000);
	        	 pst2.executeUpdate();
	        	 if(amt>=1000) {
	        		 amt=1000;
	        		 PreparedStatement pst3 = con.prepareStatement("update atm set number=number-?,value=value-?,where denomination=?");
	        						 pst3.setInt(1, 2);
	        						 pst3.setInt(2, 1000);
	        						 pst3.setInt(3, 500);
	        						 pst3.executeUpdate();
	        	 }
	        	 int s=amt/100;
        		 PreparedStatement pst3 = con.prepareStatement("update atm set number=number-?,value=value-?,where denomination=?");
        		 pst3.setInt(1, s);
				 pst3.setInt(2, amt);
				 pst3.setInt(3, 100);
				 pst3.executeUpdate();
				 pst3.executeUpdate();
	        	 
	         }
	         return;
	        	 
	         }
        else if(Acc_Bal()>amt) {

	         System.out.println("insufficient amount");
	         return;
		}
        else {
        	System.out.println("wrong pin");
        	return;
        }
		
	}
	public static void transfer() throws ClassNotFoundException, SQLException {
		con=dbutil.getConnect();
		System.out.println("enter the account number of the sender:");
		int sendacc=s.nextInt();
		System.out.println("enter the amount:");
		int amt=s.nextInt();
		System.out.println("enter the account number of the reciver:");
		int recacc=s.nextInt();
		System.out.println("enter the pin");
		int pinno=s.nextInt();
		if(amt<1000) {
			System.out.println("min cash to transfer");
			return;
		}
		else if(amt>10000) {
			System.out.println("limit reached");
			return;
		}
		int pin_no=0,accbal=0;
        PreparedStatement pst3=con.prepareStatement("select pin,balance from customer where acc_no=?");
        pst3.setInt(1, sendacc);
        rs = pst.executeQuery();
        while (rs.next()) {
        pinno = rs.getInt("pin");
        accbal = rs.getInt("balance");
        }
        if(pinno==pin_no && accbal>amt) {
        	System.out.println("your account balance"+""+accbal);
        	PreparedStatement pst=con.prepareStatement("update customer set balance=? where acc_no=?");
        	pst.setInt(1,accbal);
        	pst.setInt(2, sendacc);
        	pst.executeUpdate();
        	PreparedStatement pst1=con.prepareStatement("update customer set balance=balance+? where acc_no=?");
        	pst1.setInt(1,amt);
        	pst1.setInt(2,recacc);
        	pst1.executeQuery();
        	System.out.println("transfer successful");
        	
        }
        else {
        	System.out.println("try different pin");
        }
	}

	public static int Acc_Bal() throws ClassNotFoundException, SQLException {
		con=dbutil.getConnect();
	     int bal=0;			
	PreparedStatement pst=con.prepareStatement("select sum(value) as bal from atm");
	rs=pst.executeQuery();
	while(rs.next()) {
		bal=rs.getInt("bal");
	}
	return bal;
		
	}
	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		createatm();
		custdet();
		addcust();
		activateatm();
		loadcash();
		addnewcust();
		System.out.println("1.loadcash\n2.custdetails\3.atmoperation\n4.exit");
		int ch1=s.nextInt();
		switch(ch1) {
		case 1:
			loadcash();
			break;
		case 2:
			showdetails();
			break;
		case 3:
			System.out.println("1.checkbalance\n2.withdraw\n3.transfer\n4.exit process");
			int ch=s.nextInt();
			switch(ch) {
			case 1:
				checkbalance();
				break;
			case 2:
				withdraw();
				break;
			case 3:
				transfer();
				break;
			case 4:
				System.out.println("exited successfully");
				break;
				default:
					System.out.println("terminated");
			}
			break;
		case 4:
			System.out.println("terminatted successfully");
			break;
			default:
				System.out.println("choice will not exceed 4");
			
		}
	}

}
