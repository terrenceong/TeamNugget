package com.example.teamnugget;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JuniorCollege extends Institute {
	
	//Storing the cutOffPoint of the JC in L1R5
	int pointsArts = 0;
	int pointsScience = 0;
	//Storing the subjects in the JC
	List<String> subjects = new ArrayList<String>();
	//Storing the DSA in the JC
	List<String> dsa = new ArrayList<String>();
	//Storing the Electives in the JC
	List<String> electives = new ArrayList<String>();
	//Storing all ccas in JC
	List<CCA> ccas = new ArrayList<CCA>();
	
	//Attributes important for construction
	static List<List<String>> attributes = new ArrayList<List<String>>();
	static List<String> attributeRequired = new ArrayList<String>();
	//IMPORTANT TAKE NOTE:
	//Add variation of the ATTRIBUTE name of the csv file into the list for it to work properly across different csv files
	//Added variation must match the ATTRIBUTE name in the csv exactly
	//Example. If the ATTRIBUTE name in the csv file for JC name is JC. Add JC to the list as shown below
	//for it to recognise as institute name == iName.
	//If is JC_Name, do Array.asList("JC", "JC_Name"); and it will work.
	
	//Thoughts:
	//Considered changing to contain instead of matching exact name but problem might occur if more than one name appear.
	//Example Poly_Name, Course_Name.
	static List<String> i_Name = Arrays.asList("JC");
	static List<String> i_Description = Arrays.asList("Description");
	static List<String> i_Fees = Arrays.asList("Fee");
	static List<String> cca_Name = Arrays.asList("CCA");
	static List<String> cca_Description = Arrays.asList("CCA_Description");
	static List<String> j_PointArts = Arrays.asList("Arts");
	static List<String> j_PointScience = Arrays.asList("Science / IB");
	static List<String> j_Subject = Arrays.asList("Subjects");
	static List<String> j_DSA = Arrays.asList("DSA talent areas");
	static List<String> j_Electives = Arrays.asList("Electives and programmes");
	
	//IMPORTANT WHEN ADDING NEW ATTRIBUTE WITH VARIATION:
	//New Attributes added for variation must contain an underscore _
	//Eg. static List<String> jc_Intake = Arrays.asList("Intake");
	//Remember to add it to the enum in csvParse for easy access
	
	//Constructor for JuniorCollege Object
	public JuniorCollege(String name, String description, float fees, List<String> subjects,  List<String> dsa, List<String> electives, List<CCA> ccas,
						 int pointsArts, int pointsScience)
	{
		super(name, description, fees);
		this.subjects = subjects;
		this.ccas = ccas;
		this.dsa = dsa;
		this.electives = electives;
		this.pointsArts = pointsArts;
		this.pointsScience = pointsScience;
	}
	//Obtain Subjects in JC
	public List<String> getSubjects()
	{
		return subjects;
	}
	//Obtain DSAs in JC
	public List<String> getDSA() { return dsa; }
	//Obtain Electives in JC
	public List<String> getElectives()
	{
		return electives;
	}
	//Obtain CCAs in JC
	public List<CCA> getCCAs()
	{
		return ccas;
	}
	public void setPointsArts(int pointsArts)
	{
		this.pointsArts = pointsArts;
	}
	public void setPointsScience(int pointsScience)
	{
		this.pointsScience = pointsScience;
	}
	public void setAttributes(String name, String description, float fees, int pointsArts, int pointsScience)
	{
		if (!name.equals("") && this.name.equals(""))
			this.setName(name);
		if (!description.equals("") && this.description.equals(""))
			this.setDescription(description);
		if (this.fees <= 0.0f)
			this.setFees(fees);
		if (this.pointsArts <= 0)
			this.setPointsArts(pointsArts);
		if (this.pointsScience <= 0)
			this.setPointsScience(pointsScience);
	}
	//Override Parent Print method
	public void print()
	{
		Log.i("JC","INSTITUTE NAME : " + this.name);
		Log.i("JC","--------------------------------------------------------------------");
		Log.i("JC", "POINTS ART: " + this.pointsArts + " POINTS SCIENCE/IB: " + this.pointsScience);
		Log.i("JC","Subjects");
		Log.i("JC","--------------------------------------------------------------------");
		for (int i = 0; i < subjects.size(); i++)
		{
			Log.i("JC", subjects.get(i));
		}
		Log.i("JC","--------------------------------------------------------------------");
		Log.i("JC","\nDSA");
		Log.i("JC","--------------------------------------------------------------------");
		for (int i = 0; i < dsa.size(); i++)
		{
			Log.i("JC", dsa.get(i));
		}
		Log.i("JC","--------------------------------------------------------------------");
		Log.i("JC","\nElectives");
		Log.i("JC","--------------------------------------------------------------------");
		for (int i = 0; i < electives.size(); i++)
		{
			Log.i("JC", electives.get(i));
		}
		Log.i("JC","--------------------------------------------------------------------");
		if (ccas.size() != 0)
		{
			Log.i("JC","CCA");
			Log.i("JC","--------------------------------------------------------------------");
			for (int i = 0; i < ccas.size(); i++)
			{
				ccas.get(i).print("J");
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

			Field[] fld = JuniorCollege.class.getDeclaredFields();

			for (int i = 0; i < fld.length; i++)
			{
				if (fld[i].getName().contains("_"))
				{
					attributeRequired.add(fld[i].getName());
					try {
						List<String> array = (List<String>)fld[i].get(JuniorCollege.class);
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
