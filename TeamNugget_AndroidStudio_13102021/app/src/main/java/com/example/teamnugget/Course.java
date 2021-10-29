package com.example.teamnugget;
//import java.util.*;

import android.util.Log;

public class Course implements Comparable<Object>{
	
	//Storing name of Course
	String name = "";
	//Storing type of Course
	String fullTime = "";
	//Storing description of Course
	String description = "";
	
	//Constructor for the Course Object
	public Course(String name, String fullTime, String description)
	{
		this.name = name;
		this.fullTime = fullTime;
		this.description = description;
	}
	//Obtain the name of the Course
	public String getName()
	{
		return name;
	}
	//Obtain the type of the Course
	public String isFullTime()
	{
		return fullTime;
	}
	//Obtain the type of the Course in boolean according to full-time
	public boolean isFullTimeB()
	{
		if (this.fullTime.toLowerCase().equals("FULL-TIME".toLowerCase()))
			return true;
		
		return false;
	}
	//Obtaining the description of the course
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
	public void setFullTime(String fullTime)
	{
		this.fullTime = fullTime;
	}
	public void setAttributes(String name, String description, String fullTime)
	{
		if (!name.equals("") && this.name.equals(""))
			this.setName(name);
		if (!description.equals("") && this.description.equals(""))
			this.setDescription(description);
		if (!fullTime.equals("") && this.fullTime.equals(""))
			this.setFullTime(fullTime);
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
	//Printing the variables of Course
	public void print(String type)
	{
		switch (type)
		{
			case "U":
				Log.i("UniDebug","COURSE NAME : " + this.name + "\tFULL-TIME: " + isFullTimeB());
				if (this.description != "")
					Log.i("UniDebug","COURSE DESCRIPTION : " + this.description + "\n");
				break;
			case "P":
				Log.i("PolyDebug","COURSE NAME : " + this.name + "\tFULL-TIME: " + isFullTimeB());
				if (this.description != "")
					Log.i("PolyDebug","COURSE DESCRIPTION : " + this.description + "\n");
				break;
			case "I":
				Log.i("ITEDebug","COURSE NAME : " + this.name + "\tFULL-TIME: " + isFullTimeB());
				if (this.description != "")
					Log.i("ITEDebug","COURSE DESCRIPTION : " + this.description + "\n");
				break;

		}

	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Course)
		{
			if (((Course)o).getName().compareTo(this.getName()) > 0)
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
	public Course similarName(String nameToCheck)
	{
		if (this.getName().toLowerCase().contains(nameToCheck.toLowerCase()))
		{
			return this;
		}
		return null;
	}
	public Course similarGPA(float gpa, boolean equal)
	{
		if (this instanceof PolyITECourse)
		{
			if (((PolyITECourse)this).getCutOffPoints() <= gpa && !equal)
			{
				return this;
			}
			else if (((PolyITECourse)this).getCutOffPoints() == gpa && equal)
			{
				return this;
			}
		}
		else
		{
			if (((UniversityCourse)this).getCutOffPointsGPA() <= gpa && !equal)
			{
				return this;
			}
			else if (((UniversityCourse)this).getCutOffPointsGPA() == gpa && equal)
			{
				return this;
			}
		}
		return null;
	}
	public Course similarALevel (String ALevel)
	{
		if (((UniversityCourse)this).getCutOffPointsAL().equals(ALevel))
		{
			return this;
		}
		return null;
	}


}
