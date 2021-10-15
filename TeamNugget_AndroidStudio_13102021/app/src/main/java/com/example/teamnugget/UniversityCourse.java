package com.example.teamnugget;

import java.util.*;

public class UniversityCourse extends Course {
	
	//Storing the cut-off points for A'Level
	List<Character> cutOffPointsAL = new ArrayList<Character>();
	//Storing the cut-off points for GPA
	float cutOffPointsGPA = 0.0f;
	//Storing the type?
	String type = "";
		
	//Constuctor for the UniversityCourse
	public UniversityCourse(String name, String fullTime, String description, String cutOffPointsAL, float cutOffPointsGPA, String type) {
		super(name, fullTime, description);
		
		for (int i = 0; i < cutOffPointsAL.length(); i++ )
		{
			this.cutOffPointsAL.add(cutOffPointsAL.charAt(i));
		}
		
		this.cutOffPointsGPA = cutOffPointsGPA;
		this.type = type;
		// TODO Auto-generated constructor stub
	}
	
	//Obtain the cut-off points for A'Level
	public List<Character> getCutOffPointsAL()
	{
		return cutOffPointsAL;
	}
	//Obtain the cut-off points for GPA
	public float getCutOffPointsGPA ()
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
		for (int i = 0; i < cutOffPointsAL.length(); i++ )
		{
			this.cutOffPointsAL.add(cutOffPointsAL.charAt(i));
		}
	}
	public void setCutOffPointsGPA(float cutOffPointsGPA)
	{
		this.cutOffPointsGPA = cutOffPointsGPA;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public void setAttributes(String name, String description, String fullTime, String cutOffPointsAL, float cutOffPointsGPA, String type )
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
