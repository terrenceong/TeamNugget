package com.example.teamnugget;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ITE extends Institute {
	
	//Storing all school in ITE
	List<School> schools = new ArrayList<School>();
	//Storing all ccas in ITE
	List<CCA> ccas = new ArrayList<CCA>();
	
	//Attributes important for construction
	static List<List<String>> attributes = new ArrayList<List<String>>();
	static List<String> attributeRequired = new ArrayList<String>();
	
	//IMPORTANT TAKE NOTE:
	//Add variation of the ATTRIBUTE name of the csv file into the list for it to work properly across different csv files
	//Added variation must match the ATTRIBUTE name in the csv exactly
	//Example. If the ATTRIBUTE name in the csv file for ITE name is ITE. Add ITE to the list as shown below
	//for it to recognise as institute name == iName.
	//If is ITE_Name, do Array.asList("ITE", "ITE_Name"); and it will work.
	
	//Thoughts:
	//Considered changing to contain instead of matching exact name but problem might occur if more than one name appear.
	//Example Poly_Name, Course_Name.

	static List<String> i_Name = Arrays.asList("ITE", "College");
	static List<String> i_Description = Arrays.asList("Description");
	static List<String> i_Fees = Arrays.asList("Fee");
	static List<String> cca_Name = Arrays.asList("CCA","cca_name");
	static List<String> cca_Description = Arrays.asList("CCA_Description","cca_description");
	static List<String> s_Name = Arrays.asList("School");
	static List<String> s_Description = Arrays.asList("School_Description");
	static List<String> c_Name = Arrays.asList("course_name");
	static List<String> c_Description = Arrays.asList("Course_Description");
	static List<String> c_fullTime = Arrays.asList("course_type");
	static List<String> c_COPOL = Arrays.asList("OLevel");

	
	//IMPORTANT WHEN ADDING NEW ATTRIBUTE WITH VARIATION:
	//New Attributes added for variation must contain an underscore _
	//Eg. static List<String> ite_Intake = Arrays.asList("Intake");
	//Remember to add it to the enum in csvParse for easy access
	
	//Constructor for ITE Object
	public ITE(String name, String description, float fees, List<School> schools,  List<CCA> ccas)
	{

		super(name, description, fees);

		this.schools = schools;
		this.ccas = ccas;
	}
	public Institute instituteCopy(List<School> schools, List<CCA> ccas) {
		ITE i = new ITE(this.name, this.description, this.fees, schools, ccas);
		return i;
	}
	//Obtain Schools in ITE
	public List<School> getSchools()
	{
		return schools;
	}
	//Obtain CCAs in ITE
	public List<CCA> getCCAs()
	{
		return ccas;
	}
	public void sortSchool()
	{
		schools = (List<School>) SearchSortAlgorithm.sortList(schools, false);

		for(School s : schools)
		{
			s.sortCourses();
		}
	}
	//Override Parent Print method
	public void print()
	{
		Log.i("ITEDebug", "INSTITUTE NAME : " + this.name);
		Log.i("ITEDebug", "--------------------------------------------------------------------");
		for (int i = 0; i < schools.size(); i++)
		{
			schools.get(i).print("I", true);
		}
		if (ccas != null && ccas.size() != 0)
		{
			Log.i("ITEDebug", "CCA: " + ccas.size());
			Log.i("ITEDebug", "--------------------------------------------------------------------");
			for (int i = 0; i < ccas.size(); i++)
			{
				ccas.get(i).print("I");
			}
		}
		Log.i("ITEDebug", "==============================================");

	}
	@Override
	public void printSpecific(boolean school, boolean course, boolean cca) {

		Log.i("ITEDebug", "INSTITUTE NAME : " + this.name);
		Log.i("ITEDebug", "--------------------------------------------------------------------");
		if (school)
		{
			for (int i = 0; i < schools.size(); i++)
			{
				schools.get(i).print("I", course);
			}
		}
		if (cca)
		{
			Log.i("ITEDebug", "CCA");
			Log.i("ITEDebug", "--------------------------------------------------------------------");
			for (int i = 0; i < ccas.size(); i++)
			{
				ccas.get(i).print("I");
			}
		}

		Log.i("ITEDebug", "==============================================");

	}
	//Obtain all the attributes variation to check if they exist in csv
	public static List<List<String>> getAttributesRequired()
	{
		return attributes;		
	}
	//Obtain the variables in this Class
	public static List<String> getVariables()
	{
		if (attributeRequired.isEmpty()) {

			boolean copy = false;

			Field[] fld = ITE.class.getDeclaredFields();

			for (int i = 0; i < fld.length; i++) {

				if (fld[i].getName().contains("_"))
				{
					attributeRequired.add(fld[i].getName());
					try {
						List<String> array = (List<String>)fld[i].get(ITE.class);
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
	@Override
	public int compareTo(Object o) {

		if (o instanceof ITE)
		{
			if (((ITE)o).getName().compareTo(this.getName()) > 0)
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
	@Override
	public List<School> similarSchools(String nameToCheck) {

		List<School> similarSchool = new ArrayList<School>();

		for (School s : schools)
		{
			if (s.similarName(nameToCheck) != null)
			{
				similarSchool.add(s);
			}
		}
		return similarSchool;
	}

}
