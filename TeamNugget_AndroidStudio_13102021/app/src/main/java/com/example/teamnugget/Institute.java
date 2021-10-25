package com.example.teamnugget;
import java.util.*;

public abstract class Institute implements Comparable<Object> {
	
	//Storing the description of the Institute
	String description = "";
	//Storing the name of the Institute
	String name = "";
	//Storing the fee of the Institute
	float fees = 0.0f;
	
	//Add variation of names here to work with any csv
	
	//DIFFERENT FROM CHILD CLASSES, PLEASE READ
	
	//IMPORTANT TAKE NOTE (SPECIFIC INSTITUTE/ WITH NAME):
	//Add variation of the INSTITUTE name from the CSV FILE name into the list for it to work properly across different csv files
	//Example. If the name of the CSV file is TP_Courses. Add TP into the list as shown below
	//for it to recognise as a polytechnic.
	//If is SIM_Courses for the csv file name, do Array.asList("NUS", "NTU", "SIM"); and it will work.
	//it is for it to recognise it as a University
	//Name does not have to correspond to the file name exactly as it uses Contain instead of equals
	static List<String> uniSpecificName = Arrays.asList("NUS", "NTU");
	static List<String> polySpecificName = Arrays.asList("TP", "NP", "NYP", "RP", "SP");
	static List<String> iteSpecificName = Arrays.asList("Chicken");
	static List<String> jcSpecificName = Arrays.asList("CJC");
	
	//AFTER CHECKING FOR SPECIFIC FILE NAME, if is unable to find any specific Name, it will labeled the csv as a general file.
	//Now it will search for the institute type by checking the attributes
	
	//IMPORTANT TAKE NOTE (GENERAL CATERGORY / WITHOUT NAME):
	//Add variation of the ATTRIBUTE name that distinguishes the institute into the list for it to work properly across different csv files
	//Example. If the ATTRIBUTE name for determining the institute type is poly. Add Poly to the list as shown below
	//for it to recognise it as a Polytechnic
	//If is Poly_Name, do not have to add to the list and it will work.
	//Only if is completely different, then add it to the list (Think of how string.contain works)
	//Name does not have to correspond to the attribute name exactly as it uses Contain instead of equals
	static List<String> uniSynonym = Arrays.asList("University");
	static List<String> polySynonym = Arrays.asList("Poly");
	static List<String> iteSynonym = Arrays.asList("ITE", "College");
	static List<String> jcSynonym = Arrays.asList("JC");
	
	//Constructor for Institute Object
	public Institute(String name, String description, float fees)
	{
		this.name = name;
		this.description = description;
		this.fees = fees;
	}
	public Institute instituteCopy(List<School> schools) {return null;}
	//Obtain name of Institute
	public String getName()
	{
		return name;
	}
	//Obtain description of Institute
	public String getDescription()
	{
		return description;
	}
	//Obtain fees of Institute
	public float getFees()
	{
		return fees;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public void setFees(float fees)
	{
		this.fees = fees;
	}
	public void setAttributes(String name, String description, float fees)
	{
		if (!name.equals("") && this.name.equals(""))
			this.setName(name);
		if (!description.equals("") && this.description.equals(""))
			this.setDescription(description);
		if (fees <= 0.0f)
			this.setFees(fees);
	}
	//Checking if the name is the same as this instance
	public boolean containName(String name)
	{
		//System.out.println("Name: " + this.name + "CheckName: " + name);
		if ((this.name.toLowerCase()).equals(name.toLowerCase()))
		{
			return true;
		}
		return false;
	}
	//Print that can be overwritten by child objects
	public void print()
	{
		System.out.println("NAME : " + name + " DESCRIPTION: " + description);
	}
	public void printSpecific(boolean school, boolean course, boolean cca) { };
	//Obtain school that can be overwritten by child objects
	public List<School> getSchools()
	{
		return null;
	}
	//Obtain CCA that can be overwritten by child objects
	public List<CCA> getCCAs()
	{
		return null;
	}
	//Obtain subset of school that can be overwritten by child objects
	public List<School> similarSchools(String nameToCheck) { return null; }
	//Obtain CCA that can be overwritten by child objects
	public List<CCA> similarCCAs(String nameToCheck) { return null; }
	//A static function used for converting String in a List to lower case
	public static List<String> listToLowerCase(List<String> listToConvert)
	{
		
		for (int i = 0; i < listToConvert.size(); i++)
		{
			listToConvert.set(i, listToConvert.get(i).toLowerCase());
		}
		return listToConvert;
	}
	//Obtaining the Institute Type according to csv attributes
	public static String getInstituteType(String[] row)
	{		
		uniSynonym = listToLowerCase(uniSynonym);
		polySynonym = listToLowerCase(polySynonym);
		iteSynonym = listToLowerCase(iteSynonym);
		jcSynonym = listToLowerCase(jcSynonym);
		
		for (int i = 0; i < row.length; i++)
		{
			if (containName(uniSynonym, row[i].toLowerCase()))
			{
				return "U";
			}
			else if (containName(polySynonym, row[i].toLowerCase()))
			{
				return "P";
			}
			else if (containName(iteSynonym, row[i].toLowerCase()))
			{
				return "I";
			}
			else if (containName(jcSynonym, row[i].toLowerCase()))
			{
				return "J";
			}
		}
		
		
		return "NOTHING FOUND";
	}
	//Obtaining the Institute Type according to file Name
	public static String getInstituteType(String row)
	{		
		uniSpecificName = listToLowerCase(uniSpecificName);
		polySpecificName = listToLowerCase(polySpecificName);
		iteSpecificName = listToLowerCase(iteSpecificName);
		jcSpecificName = listToLowerCase(jcSpecificName);
		
		if (containName(uniSpecificName, row.toLowerCase()))
		{
			return "U";
		}
		else if (containName(polySpecificName, row.toLowerCase()))
		{
			return "P";
		}
		else if (containName(iteSpecificName, row.toLowerCase()))
		{
			return "I";
		}
		else if (containName(jcSpecificName, row.toLowerCase()))
		{
			return "J";
		}
		
		
		return "";
	}
	//Check if the List contain the nameToCheck using the String.Contain
	public static boolean containName(List<String> listToCheck, String nameToCheck)
	{
		for (String s : listToCheck)
		{
			if (nameToCheck.contains(s))
			{
				return true;
			}
		}
		return false;
	}
	public Institute similarName(String nameToCheck)
	{
		if (this.getName().contains(nameToCheck))
		{
			return this;
		}
		return null;
	}

}
