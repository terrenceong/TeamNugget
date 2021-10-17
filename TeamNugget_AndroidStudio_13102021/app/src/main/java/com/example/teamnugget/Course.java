package com.example.teamnugget;
//import java.util.*;

import android.util.Log;

public class Course {
	
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
				Log.i("Uni","COURSE NAME : " + this.name + "\tFULL-TIME: " + isFullTimeB());
				if (this.description != "")
					Log.i("Uni","COURSE DESCRIPTION : " + this.description + "\n");
				break;
			case "P":
				Log.i("Poly","COURSE NAME : " + this.name + "\tFULL-TIME: " + isFullTimeB());
				if (this.description != "")
					Log.i("Poly","COURSE DESCRIPTION : " + this.description + "\n");
				break;
			case "I":
				Log.i("ITE","COURSE NAME : " + this.name + "\tFULL-TIME: " + isFullTimeB());
				if (this.description != "")
					Log.i("ITE","COURSE DESCRIPTION : " + this.description + "\n");
				break;

		}

	}
}
