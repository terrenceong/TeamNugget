package com.example.teamnugget;

import java.util.*;

public class School {
	
	//Storing name of School
	String name = "";
	//Storing all the course in the School
	List<Course> courses = new ArrayList<Course>();
	//Storing description of School
	String description = "GAY";
	
	//Constructor for the School Object
	public School(String name, List<Course> courses, String description)
	{
		this.name = name;
		this.courses = courses;
		this.description = description;
	}
	//Obtain Name of School
	public String getName()
	{
		return name;
	}
	//Obtain Courses from the School
	public List<Course> getCourses()
	{
		return courses;
	}
	//Obtain Description from the School
	public String getDescription()
	{
		return description;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public void setAttributes(String name, String description)
	{
		if (!name.equals("") && this.name.equals(""))
			this.setName(name);
		if (!description.equals("") && this.description.equals("GAY"))
			this.setDescription(description);
	}
	//Checking if the name is the same as this instance
	public boolean containName(String name)
	{		
		if ((this.name.toLowerCase()).equals(name.toLowerCase()))
		{
			return true;
		}
		return false;
	}
	//Printing the variables of School
	public void print()
	{
		System.out.println("SCHOOL NAME : " + this.name + "\nSCHOOL DESCRIPTION: " + this.description);
		System.out.println("_________________________________________________________________________");
		for (int i = 0; i < courses.size(); i++)
		{
			courses.get(i).print();
		}
		System.out.println("\n");
		System.out.println("_________________________________________________________________________");
	}
}
