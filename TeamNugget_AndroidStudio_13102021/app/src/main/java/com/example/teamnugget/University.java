package com.example.teamnugget;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.*;

public class University extends Institute  {
	
	//Storing all school in University
	List<School> schools = new ArrayList<School>();
	//Store the ranking of the University
	int ranking = 0; 
	
	//Attributes important for construction
	static List<List<String>> attributes = new ArrayList<List<String>>();
	static List<String> attributesRequired = new ArrayList<String>();

	//IMPORTANT TAKE NOTE:
	//Add variation of the ATTRIBUTE name of the csv file into the list for it to work properly across different csv files
	//Added variation must match the ATTRIBUTE name in the csv exactly
	//Example. If the ATTRIBUTE name in the csv file for University name is University. Add University to the list as shown below
	//for it to recognise as institute name == iName.
	//If is Uni_Name, do Array.asList("University", "Uni_Name"); and it will work.
	
	//Thoughts:
	//Considered changing to contain instead of matching exact name but problem might occur if more than one name appear.
	//Example Poly_Name, Course_Name.
	static List<String> i_Name = Arrays.asList("University");
	static List<String> i_Description = Arrays.asList("Description");
	static List<String> i_Fees = Arrays.asList("Fee");
	static List<String> u_Rank = Arrays.asList("Ranking");
	static List<String> s_Name = Arrays.asList("School");
	static List<String> s_Description = Arrays.asList("School_Description");
	static List<String> c_Name = Arrays.asList("Degree");
	static List<String> c_Description = Arrays.asList("Course_Description");
	static List<String> c_fullTime = Arrays.asList("Full-Time");
	static List<String> c_COPAL = Arrays.asList("ALevel");
	static List<String> c_COPGPA = Arrays.asList("GPA");
	
	//IMPORTANT WHEN ADDING NEW ATTRIBUTE WITH VARIATION:
	//New Attributes added for variation must contain an underscore _
	//Eg. static List<String> u_Intake = Arrays.asList("Intake");
	//Remember to add it to the enum in csvParse for easy access
	static List<String> c_Type = Arrays.asList("Type");
	
	//Constructor for University Object
	public University(String name, String description, float fees, List<School> schools, int ranking)
	{
		super(name, description, fees);
		this.schools = schools;
		this.ranking = ranking;
	}
	//Obtain Schools in University
	public List<School> getSchools()
	{
		return schools;
	}
	//Obtain Ranking of the University
	public int getRanking()
	{
		return ranking;
	}
	public void setRanking(int ranking)
	{
		this.ranking = ranking;
	}
	public void setAttributes(String name, String description, float fees, int ranking)
	{
		if (!name.equals("") && this.name.equals(""))
			this.setName(name);
		if (!description.equals("") && this.description.equals(""))
			this.setDescription(description);
		if (this.fees <= 0.0f)
			this.setFees(fees);
		if (this.ranking <= 0)
			this.setRanking(ranking);
	}
	//Override Parent Print method
	public void print()
	{
		Log.i("Uni", "INSTITUTE NAME : " + this.name + "\tINSTITUTE RANK: " + this.ranking);
		Log.i("Uni", "--------------------------------------------------------------------");
		for (int i = 0; i < schools.size(); i++)
		{
			schools.get(i).print("U");
		}
	}
	//Obtain all the attributes variation to check if they exist in csv
	public static List<List<String>> getAttributesRequired()
	{
		return attributes;		
	}
	//Obtain the variables in this Class
	public static List<String> getVariables()
	{
		if (attributesRequired.isEmpty())
		{

			boolean copy = false;

			Field[] fld = University.class.getDeclaredFields();

			for (int i = 0; i < fld.length; i++)
			{
				if (fld[i].getName().contains("_"))
				{
					attributesRequired.add(fld[i].getName());
					try {
						List<String> array = (List<String>)fld[i].get(University.class);
						attributes.add(listToLowerCase(array));
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		}

		return attributesRequired;
	}

	

}
