package com.p;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Random;
import java.lang.String;

class SMS
{
	private String name;
	private static int stu_id;
	private int YearOfDegree;
	private String courses="";
	private int balance;
	private int tuition_fees=1000;
	
	//Add n number of students into a database
	public SMS()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter student name");
		this.name=sc.nextLine();
		System.out.println("Enter the Year of Graduayion or degree \n 1 - Graduation 1st year\n 2 - Graduation 2nd year\n 3 - Graduation 3rd year\n 4 - Graduation 4th year");
		this.YearOfDegree=sc.nextInt();
		StudentID_Generation();
		System.out.println("Student name : "+this.name+" Student Year of Grad :"+this.YearOfDegree+" of ID "+stu_id );
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Driver myDriver=new com.mysql.cj.jdbc.Driver();
			DriverManager.registerDriver(myDriver);
			String url="jdbc:mysql://localhost:3306/Mini_Project";
			String UserName= "root";
			String Password= "root";
		    Connection con=DriverManager.getConnection(url,UserName,Password);
		    Statement statement=con.createStatement();
		    String MyQuery_Insert = "insert into students values('"+this.name+"', "+SMS.stu_id+" ,"+this.YearOfDegree+",'"+this.courses+"' ,"+this.balance+")";
		    statement.executeUpdate(MyQuery_Insert);
		    System.out.println("entry added to database");	
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	//generate a 5 digit id number for the student
	
	public int StudentID_Generation()
	{
		Random random = new Random();
		stu_id= random.nextInt(99999);
		return stu_id ;
	}
	
	//Enroll in courses
	public void enroll()
	{
		System.out.println("Enter courses you want to enroll in,or enter E to exit \n");
		Scanner sc=new Scanner(System.in);
		while(true)
		{
			String c = sc.nextLine();
	     	if(!c.equals("E"))
	     	{
		    	courses = courses +"\n" +c;
			    this.balance= this.balance + tuition_fees;
		    }
	     	else
	     	{
	     		break;
	     	}
	    }
		System.out.println("Courses enrolled in by the student " +" are " +courses);
		Update_Courses();
	}
	
	
	//view balance
	void view_Balance()
	{
		System.out.println("Student " +this.name +" of ID " +stu_id  +" has a balance of " +this.balance +" to pay for the courses.");
	}
	//pay tuition fees
	void pay_fees()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("How much would you like to pay?");
		int amount=sc.nextInt();
		this.balance-=amount;
		System.out.println("You have paid "+amount);
		Update_Balance();
	}

	@Override
	public String toString() {
		return "SMS [name=" + name + ", \nStudent ID=" +stu_id +", \nYearOfDegree=" + YearOfDegree + ", \ncourses=" + courses + ", \nbalance=" + balance
				+ "]";
	}
	
	public void Update_Balance()
	{
		try
		{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Mini_Project","root","root");
		Statement statement=con.createStatement();
		
		String MyQuery_UpdateBalance= "update students set  balance =  "+this.balance+" where name= '"+this.name+"' AND stu_id="+SMS.stu_id+"";
		statement.executeUpdate(MyQuery_UpdateBalance);
		System.out.println("balance updated in the students table");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public void Update_Courses()
	{
		try
		{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Mini_Project","root","root");
		Statement statement=con.createStatement();
		
		String MyQuery_UpdateCourses = "update students set courses = '"+this.courses+"' where name = '"+this.name+"' and stu_id= "+SMS.stu_id+"";
		statement.executeUpdate(MyQuery_UpdateCourses);
		System.out.println("courses updated in the students table");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}



public class StudentManagementSystem {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the number of students to enroll");
		int n=sc.nextInt();
		SMS[] s=new SMS[n];
		for(int i=0;i<n;i++)
		{
		s[i]=new SMS();
		s[i].enroll();
	    s[i].view_Balance();
	    s[i].pay_fees();
	    System.out.println("Status of Student ");
	    System.out.println(s[i].toString());
		}
	}
}
