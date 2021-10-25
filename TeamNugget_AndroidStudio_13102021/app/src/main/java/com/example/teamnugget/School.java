package com.example.teamnugget;

import android.util.Log;

import java.util.*;

public class School implements Comparable<Object> {
	
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
	public School schoolCopy(List<Course> courses)
	{
		School s = new School(this.name, courses, this.description);

		return s;
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
	public void sortCourses()
	{
		courses = (List<Course>) SearchSortAlgorithm.sortList(courses, false);
	}
	//Printing the variables of School
	public void print(String type, boolean course)
	{
		switch (type)
		{
			case "U":
				Log.i("UniDebug","SCHOOL NAME : " + this.name);
				if (!this.description.equals(""))
					Log.i("UniDebug","SCHOOL DESCRIPTION: " + this.description);
				Log.i("UniDebug","_________________________________________________________________________");
				if (course)
				{
					for (int i = 0; i < courses.size(); i++)
					{
						courses.get(i).print("U");
					}
					Log.i("UniDebug","\n");
					Log.i("UniDebug","_________________________________________________________________________");
				}
				break;
			case "P":
				Log.i("PolyDebug","SCHOOL NAME : " + this.name);
				if (!this.description.equals(""))
					Log.i("PolyDebug","SCHOOL DESCRIPTION: " + this.description);
				Log.i("PolyDebug","_________________________________________________________________________");
				if (course)
				{
					for (int i = 0; i < courses.size(); i++)
					{
						courses.get(i).print("P");
					}
					Log.i("PolyDebug","\n");
					Log.i("PolyDebug","_________________________________________________________________________");
				}

				break;
			case "I":
				Log.i("ITEDebug","SCHOOL NAME : " + this.name);
				if (!this.description.equals(""))
					Log.i("ITEDebug","SCHOOL DESCRIPTION: " + this.description);
				Log.i("ITEDebug","_________________________________________________________________________");
				if (course)
				{
					for (int i = 0; i < courses.size(); i++)
					{
						courses.get(i).print("I");
					}
					Log.i("ITEDebug","\n");
					Log.i("ITEDebug","_________________________________________________________________________");
				}

				break;

		}



	}
	public int compareTo(Object o) {
		if (o instanceof School)
		{
			if (((School)o).getName().compareTo(this.getName()) > 0)
			{
				return -1;
			}
			else
			{
				return 1;
			}
		}
		return 0;
	}
	public School similarName(String nameToCheck)
	{
		if (this.getName().contains(nameToCheck))
		{
			return this;
		}
		return null;
	}
	public List<Course> similarCourses(String nameToCheck) {

		List<Course> similarCourses = new ArrayList<Course>();

		for (Course c : courses)
		{
			if (c.similarName(nameToCheck) != null)
			{
				similarCourses.add(c);
			}
		}
		return similarCourses;
	}
}
