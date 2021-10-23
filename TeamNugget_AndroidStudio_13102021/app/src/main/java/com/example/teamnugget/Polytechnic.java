package com.example.teamnugget;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Polytechnic extends Institute{
	
	//Storing all school in Polytechnics
	List<School> schools = new ArrayList<School>();
	//Storing all ccas in Polytechnics
	List<CCA> ccas = new ArrayList<CCA>();
	
	//Attributes important for construction
	static List<List<String>> attributes = new ArrayList<List<String>>();
	static List<String> attributeRequired = new ArrayList<String>();
	//IMPORTANT TAKE NOTE:
	//Add variation of the ATTRIBUTE name of the csv file into the list for it to work properly across different csv files
	//Added variation must match the ATTRIBUTE name in the csv exactly
	//Example. If the ATTRIBUTE name in the csv file for Polytechnic name is Poly. Add Poly to the list as shown below
	//for it to recognise as institute name == iName.
	//If is Polytechnic_Name, do Array.asList("Poly", "Polytechnic_Name"); and it will work.
	
	//Thoughts:
	//Considered changing to contain instead of matching exact name but problem might occur if more than one name appear.
	//Example Poly_Name, Course_Name.
	static List<String> i_Name = Arrays.asList("Polytechnic");
	static List<String> i_Description = Arrays.asList("Description");
	static List<String> i_Fees = Arrays.asList("Fee");
	static List<String> cca_Name = Arrays.asList("CCA","cca_name");
	static List<String> cca_Description = Arrays.asList("CCA_Description","cca_description");
	static List<String> s_Name = Arrays.asList("School");
	static List<String> s_Description = Arrays.asList("School_Description");
	static List<String> c_Name = Arrays.asList("Course_Name");
	static List<String> c_Description = Arrays.asList("Course_Description");
	static List<String> c_fullTime = Arrays.asList("Full-Time");
	static List<String> c_COPOL = Arrays.asList("OLevel");
	
	//IMPORTANT WHEN ADDING NEW ATTRIBUTE WITH VARIATION:
	//New Attributes added for variation must contain an underscore _
	//Eg. static List<String> p_Intake = Arrays.asList("Intake");
	//Remember to add it to the enum in csvParse for easy access
	
	//Constructor for Polytechnic Object
	public Polytechnic(String name, String description, float fees, List<School> schools,  List<CCA> ccas)
	{
		super(name, description, fees);
		this.schools = schools;
		this.ccas = ccas;
	}
	//Obtain Schools in Polytechnic
	public List<School> getSchools()
	{
		return schools;
	}
	//Obtain CCAs in Polytechnic
	public List<CCA> getCCAs()
	{
		return ccas;
	}
	//Override Parent Print method
	public void print()
	{
		Log.i("PolyDebug", "INSTITUTE NAME : " + this.name );
		Log.i("PolyDebug", "--------------------------------------------------------------------");
		for (int i = 0; i < schools.size(); i++)
		{
			schools.get(i).print("P");
		}
		if (ccas.size() != 0)
		{
			Log.i("PolyDebug","CCA");
			Log.i("PolyDebug","--------------------------------------------------------------------");
			for (int i = 0; i < ccas.size(); i++)
			{
				ccas.get(i).print("P");
			}
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
		if (attributeRequired.isEmpty())
		{
			boolean copy = false;

			Field[] fld = Polytechnic.class.getDeclaredFields();

			for (int i = 0; i < fld.length; i++)
			{
				if (fld[i].getName().contains("_"))
				{
					attributeRequired.add(fld[i].getName());
					try {
						List<String> array = (List<String>)fld[i].get(Polytechnic.class);
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

		return attributeRequired;
	}
}
