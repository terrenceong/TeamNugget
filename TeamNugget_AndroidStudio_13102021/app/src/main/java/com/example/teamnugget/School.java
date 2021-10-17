package com.example.teamnugget;

import android.util.Log;

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
	public void print(String type)
	{
		switch (type)
		{
			case "U":
				Log.i("Uni","SCHOOL NAME : " + this.name + "\nSCHOOL DESCRIPTION: " + this.description);
				Log.i("Uni","_________________________________________________________________________");
				for (int i = 0; i < courses.size(); i++)
				{
					courses.get(i).print("U");
				}
				Log.i("Uni","\n");
				Log.i("Uni","_________________________________________________________________________");
				break;
			case "P":
				Log.i("Poly","SCHOOL NAME : " + this.name + "\nSCHOOL DESCRIPTION: " + this.description);
				Log.i("Poly","_________________________________________________________________________");
				for (int i = 0; i < courses.size(); i++)
				{
					courses.get(i).print("P");
				}
				Log.i("Poly","\n");
				Log.i("Poly","_________________________________________________________________________");
				break;
			case "I":
				Log.i("ITE","SCHOOL NAME : " + this.name + "\nSCHOOL DESCRIPTION: " + this.description);
				Log.i("ITE","_________________________________________________________________________");
				for (int i = 0; i < courses.size(); i++)
				{
					courses.get(i).print("I");
				}
				Log.i("ITE","\n");
				Log.i("ITE","_________________________________________________________________________");
				break;

		}



	}
}
