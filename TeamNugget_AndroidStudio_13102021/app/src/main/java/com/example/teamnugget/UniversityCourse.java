package com.example.teamnugget;

import java.util.*;

public class UniversityCourse extends Course {
	
	//Storing the cut-off points for A'Level
	String cutOffPointsAL;
	//Storing the cut-off points for GPA
	double cutOffPointsGPA = 0.0f;
	//Storing the type?
	String type = "";
		
	//Constuctor for the UniversityCourse
	public UniversityCourse(String name, String fullTime, String description, String cutOffPointsAL, double cutOffPointsGPA, String type) {
		super(name, fullTime, description);
		
		this.cutOffPointsAL = cutOffPointsAL;
		this.cutOffPointsGPA = cutOffPointsGPA;
		this.type = type;
		// TODO Auto-generated constructor stub
	}
	
	//Obtain the cut-off points for A'Level
	public String getCutOffPointsAL()
	{
		return cutOffPointsAL;
	}
	//Obtain the cut-off points for GPA
	public double getCutOffPointsGPA ()
	{
		return cutOffPointsGPA;
	}
	//Obtain the type?
	public String getType()
	{
		return type;
	}
	public void setCutOffPointsAL(String cutOffPointsAL)
	{
		this.cutOffPointsAL = cutOffPointsAL;
	}
	public void setCutOffPointsGPA(double cutOffPointsGPA)
	{
		this.cutOffPointsGPA = cutOffPointsGPA;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public void setAttributes(String name, String description, String fullTime, String cutOffPointsAL, double cutOffPointsGPA, String type )
	{
		if (!name.equals("") && this.name.equals(""))
			this.setName(name);
		if (!description.equals("") && this.description.equals(""))
			this.setDescription(description);
		if (!fullTime.equals("") && this.fullTime.equals(""))
			this.setFullTime(fullTime);
		if (this.cutOffPointsAL.isEmpty())
			this.setCutOffPointsAL(cutOffPointsAL);
		if (this.cutOffPointsGPA <= 0.0f)
			this.setCutOffPointsGPA(cutOffPointsGPA);
		if (!type.equals("") && this.type.equals(""))
			this.setType(type);
	}

}
