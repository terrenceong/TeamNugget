package com.example.teamnugget;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

import java.io.InputStream;
import java.io.InputStreamReader;

public class csvParse {
	
	//Storing every Institute from the csv(s)
	static List<Institute> universities = new ArrayList<Institute>();
	static List<Institute> polytechnics = new ArrayList<Institute>();
	static List<Institute> ites = new ArrayList<Institute>();
	static List<Institute> juniorcolleges = new ArrayList<Institute>();
	
	boolean general = false;

	//IMPORTANT NOTE: Any new attributes added in the classes should also be added into the enum for convenience when retrieving information
	//All possible variables
	static enum DesiredAttributes
	{
		INSTITUTENAME ("i_Name"),
		INSTITUTEDESCRIPTION ("i_Description"),
		INSTITUTEFEES ("i_Fees"),
		UNIVERSITYRANK ("u_Rank"),
		JCSUBJECTS ("j_Subject"),
		SCHOOLNAME ("s_Name"),
		SCHOOLDESCRIPTION ("s_Description"),
		COURSENAME ("c_Name"),
		COURSEDESCRIPTION ("c_Description"),
		COURSEFULLTIME ("c_fullTime"),
		COURSEOL ("c_COPOL"),
		COURSEAL ("c_COPAL"),
		COURSEGPA ("c_COPGPA"),
		COURSETYPE ("c_Type"),
		CCANAME ("cca_Name"),
		CCADESCRIPTION ("cca_Description");

		private String name;
		public String getName(){ return this.name; }
		DesiredAttributes(String name)
		{
			this.name = name;
		}
	}

	//Pass the folder path of the folder that contain all the csv and watch the magic happen

	//Converting CSV to Classes in Java
	public void parseData(InputStream inputStream, String path)
	{
			//File path
			String file = path;	
			
			//Get the type of institute that the csv is focusing on
			String instituteType = Institute.getInstituteType(file);
			
			//Dictionary to keep track of the required attributes from the csv
			Hashtable<String, Integer> attributeDict = new Hashtable<String, Integer>();
			
			//Used for reading file
			BufferedReader reader = null;
			
			//Current Line traversed
			String line = "";
			
			//Try to read the file
			try 
			{
				reader = new BufferedReader(new InputStreamReader(inputStream));
				
				//Reading the first line to obtain attribute from csv
				line = reader.readLine();
				String[] csvAttributes = line.split(",");
				
				//Determine Attributes that is required for constructing the class
				if (instituteType == "")
				{
					instituteType = Institute.getInstituteType(csvAttributes);
					//If institute type is determined here, the csv is in-general instead of specific to a certain Institute
					general = true;
				}
				else
				{
					general = false;
				}
					
				//Debugging Purposes
				System.out.println("Institute Type: " + instituteType);
				getAttributes(csvAttributes, instituteType, attributeDict );
				System.out.println("Dictionary: " + attributeDict);
				
				System.out.println("General: " + general);
				
				
				//Loading Data based of CSV focused institute
				switch (instituteType)
				{
					//University
					case "U":
						
						//Traversing the entire files row by row
						while ((line = reader.readLine()) != null)
						{

							String[] row = line.split(",");
							row = fixRow(row);

							Institute currentI = null;

							if (general)
								currentI = (Institute)Contains(universities, row[attributeDict.get(DesiredAttributes.INSTITUTENAME.getName())]);
							else
								currentI = (Institute)Contains(universities, obtainInstituteName(file));


							//New University
							if (currentI == null)
							{

								List<School> schoolToAdd = new ArrayList<School>();
								List<Course> courseToAdd = new ArrayList<Course>();
								
								courseToAdd.add(InitialiseUniversityCourse(attributeDict, row, null));							
								
								schoolToAdd.add(InitialiseSchool(attributeDict, row, courseToAdd, null));
															
								universities.add(InitialiseUniversity(attributeDict, row, schoolToAdd, obtainInstituteName(file), null));
							}
							//Exist University
							else
							{
								//Update University Attributes if needed
								InitialiseUniversity(attributeDict, row, null, obtainInstituteName(file), (University)currentI);
								
								School currentS = null;
								
								if (attributeDict.get(DesiredAttributes.SCHOOLNAME.getName()) != null)
									currentS = (School)Contains(currentI.getSchools(), row[attributeDict.get(DesiredAttributes.SCHOOLNAME.getName())]);
								else
									currentS = (School)Contains(currentI.getSchools(), "School");
								
								//New School
								if (currentS == null)
									
								{	List<Course> courseToAdd = new ArrayList<Course>();									
									//Initialising Course								
									courseToAdd.add(InitialiseUniversityCourse(attributeDict, row, null));
									
									currentI.getSchools().add(InitialiseSchool(attributeDict, row, courseToAdd, null));
								}
								//Exist School
								else
								{
									//Update School Attributes if needed
									InitialiseSchool(attributeDict, row, null, currentS);

									if (attributeDict.get(DesiredAttributes.COURSENAME.getName()) != null)
									{
										Course currentC = (Course)Contains(currentS.getCourses(), row[attributeDict.get(DesiredAttributes.COURSENAME.getName())]);
										
										//New Course
										if (currentC == null)
										{
											currentS.getCourses().add(InitialiseUniversityCourse(attributeDict, row, null));
										}
										//Exist Course
										else
										{
											//Update Course Attributes if needed
											InitialiseUniversityCourse(attributeDict, row, (UniversityCourse) currentC);
										}
									}		
									
								}
							}						
						}
						
						break;
					//=========================================================================================================================================	
					//Polytechnic
					case "P":
						
						//Traversing the entire files row by row
						while ((line = reader.readLine()) != null)
						{
							String[] row = line.split(",");
							row = fixRow(row);
							
							Institute currentI = null;
																						
							if (general)
								currentI = (Institute)Contains(polytechnics, row[attributeDict.get(DesiredAttributes.INSTITUTENAME.getName())]);
							else
								currentI = (Institute)Contains(polytechnics, obtainInstituteName(file));
							
							//New Polytechnic
							if (currentI == null)
							{
								
								List<School> schoolToAdd = new ArrayList<School>();
								List<CCA> ccaToAdd = new ArrayList<CCA>();
								List<Course> courseToAdd = new ArrayList<Course>();
								
								courseToAdd.add(InitialisePolyITECourse(attributeDict, row, null));							
								ccaToAdd.add(InitialiseCCA(attributeDict, row, null));
								schoolToAdd.add(InitialiseSchool(attributeDict, row, courseToAdd, null));
															
								polytechnics.add(InitialisePolytechnics(attributeDict, row, schoolToAdd, ccaToAdd, obtainInstituteName(file), null));
							}
							//Exist Polytechnic
							else
							{
								//Update Polytechnic Attributes if needed
								InitialisePolytechnics(attributeDict, row, null, null, obtainInstituteName(file), (Polytechnic) currentI);

								School currentS = null;
								
								if (attributeDict.get(DesiredAttributes.SCHOOLNAME.getName()) != null)
									currentS = (School)Contains(currentI.getSchools(), row[attributeDict.get(DesiredAttributes.SCHOOLNAME.getName())]);
								else
									currentS = (School)Contains(currentI.getSchools(), "School");
								
								//New School
								if (currentS == null)
									
								{	List<Course> courseToAdd = new ArrayList<Course>();									
								
									courseToAdd.add(InitialisePolyITECourse(attributeDict, row, null));
									
									currentI.getSchools().add(InitialiseSchool(attributeDict, row, courseToAdd, null));
								}
								//Exist School
								else
								{
									//Update School Attributes if needed
									InitialiseSchool(attributeDict, row, null, currentS);

									if (attributeDict.get(DesiredAttributes.COURSENAME.getName()) != null)
									{
										Course currentC = (Course)Contains(currentS.getCourses(), row[attributeDict.get(DesiredAttributes.COURSENAME.getName())]);
										
										//New Course
										if (currentC == null)
										{
											currentS.getCourses().add(InitialisePolyITECourse(attributeDict, row, null));
										}
										//Exist Course
										else
										{
											//Update Course Attributes if needed
											InitialisePolyITECourse(attributeDict, row, (PolyITECourse) currentC);
										}
									}										
								}
								
								if (attributeDict.get(DesiredAttributes.CCANAME.getName()) != null)
								{
									CCA currentCCA = (CCA)Contains(currentI.getSchools(), row[attributeDict.get(DesiredAttributes.CCANAME.getName())]);
									
									//New CCA
									if (currentCCA == null)
									{
										currentI.getCCAs().add(InitialiseCCA(attributeDict, row, null));
									}
									//Exist CCA
									else
									{
										//Update CCA Attributes if needed
										InitialiseCCA(attributeDict, row, currentCCA);
									}
								}						
								
							}							
						}
						
						break;
					//=========================================================================================================================================	
					// ITE
					case "I":
						
						//Traversing the entire files row by row
						while ((line = reader.readLine()) != null)
						{
							String[] row = line.split(",");
							row = fixRow(row);
							
							Institute currentI = null;
																				
							if (general)
								currentI = (Institute)Contains(ites, row[attributeDict.get(DesiredAttributes.INSTITUTENAME.getName())]);
							else
								currentI = (Institute)Contains(ites, obtainInstituteName(file));
							
							//New ITE
							if (currentI == null)
							{

								List<School> schoolToAdd = new ArrayList<School>();
								List<CCA> ccaToAdd = new ArrayList<CCA>();
								List<Course> courseToAdd = new ArrayList<Course>();
								
								courseToAdd.add(InitialisePolyITECourse(attributeDict, row, null));							
								ccaToAdd.add(InitialiseCCA(attributeDict, row, null));
								schoolToAdd.add(InitialiseSchool(attributeDict, row, courseToAdd, null));

								ites.add(InitialiseITE(attributeDict, row, schoolToAdd, ccaToAdd, obtainInstituteName(file), null));

							}
							//Exist ITE
							else
							{
								//Update ITE Attributes if needed
								InitialiseITE(attributeDict, row, null, null, obtainInstituteName(file), (ITE) currentI);

								School currentS = null;
								
								if (attributeDict.get(DesiredAttributes.SCHOOLNAME.getName()) != null)
									currentS = (School)Contains(currentI.getSchools(), row[attributeDict.get(DesiredAttributes.SCHOOLNAME.getName())]);
								else
									currentS = (School)Contains(currentI.getSchools(), "School");
								
								//New School
								if (currentS == null)
									
								{	List<Course> courseToAdd = new ArrayList<Course>();									
									//Initialising Course								
									courseToAdd.add(InitialisePolyITECourse(attributeDict, row, null));
									
									currentI.getSchools().add(InitialiseSchool(attributeDict, row, courseToAdd, null));
								}
								//Exist School
								else
								{
									//Update School Attributes if needed
									InitialiseSchool(attributeDict, row, null, currentS);

									if (attributeDict.get(DesiredAttributes.COURSENAME.getName()) != null)
									{
										Course currentC = (Course)Contains(currentS.getCourses(), row[attributeDict.get(DesiredAttributes.COURSENAME.getName())]);
										
										//New Course
										if (currentC == null)
										{
											currentS.getCourses().add(InitialisePolyITECourse(attributeDict, row, null));
										}
										//Exist Course
										else
										{
											//Update Course Attributes if needed
											InitialisePolyITECourse(attributeDict, row, (PolyITECourse) currentC);
										}
									}										
								}
																
								if (attributeDict.get(DesiredAttributes.CCANAME.getName()) != null)
								{
									CCA currentCCA = (CCA)Contains(currentI.getSchools(), row[attributeDict.get(DesiredAttributes.CCANAME.getName())]);
									
									//New CCA
									if (currentCCA == null)
									{
										currentI.getCCAs().add(InitialiseCCA(attributeDict, row, null));
									}
									//Exist CCA
									else
									{
										//Update CCA Attributes if needed
										InitialiseCCA(attributeDict, row, currentCCA);
									}
								}						
								
							}							
						}
						break;
					//=========================================================================================================================================	
					//Junior College
					case "J":
						
						//Traversing the entire files row by row
						while ((line = reader.readLine()) != null)
						{
							String[] row = line.split(",");
							row = fixRow(row);
							
							Institute currentI = null;
																						
							if (general)
								currentI = (Institute)Contains(juniorcolleges, row[attributeDict.get(DesiredAttributes.INSTITUTENAME.getName())]);
							else
								currentI = (Institute)Contains(juniorcolleges, obtainInstituteName(file));
							
							//New Junior College
							if (currentI == null)
							{
								
								List<CCA> ccaToAdd = new ArrayList<CCA>();
								List<String> subjects = new ArrayList<String>();
														
								ccaToAdd.add(InitialiseCCA(attributeDict, row, null));
								subjects.add(InitialiseSubject(attributeDict,row));
								juniorcolleges.add(InitialiseJC(attributeDict, row, subjects, ccaToAdd, obtainInstituteName(file), null));
							}
							//Exist Junior College
							else
							{
								//Update JC Attributes if needed
								InitialiseJC(attributeDict, row, null, null, obtainInstituteName(file), (JuniorCollege) currentI);

								if (attributeDict.get(DesiredAttributes.CCANAME.getName()) != null)
								{
									CCA currentCCA = (CCA)Contains(currentI.getSchools(), row[attributeDict.get(DesiredAttributes.CCANAME.getName())]);
									
									//New CCA
									if (currentCCA == null)
									{
										currentI.getCCAs().add(InitialiseCCA(attributeDict, row, null));
									}
									//Exist CCA
									else
									{
										//Update CCA Attributes if needed
										InitialiseCCA(attributeDict, row, currentCCA);
									}
								}						
								
							}							
						}
						break;
						//=========================================================================================================================================	
					default:
						break;
				
				}
				
			}
			//Display Error
			catch(Exception e)
			{
				e.printStackTrace();
			}
			//Close the reader after finished
			finally
			{
				try {
					reader.close();
				} catch (IOException e) {
					//Display Error is unable to close Reader
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("================DEBUG ABOVE==============================");
	}
	//Constructing a new course based on row values
	public PolyITECourse InitialisePolyITECourse(Hashtable<String, Integer> dict, String[] row, PolyITECourse pc )
	{
		String cName = ((dict.get(DesiredAttributes.COURSENAME.getName()) == null) ? "" : row[dict.get(DesiredAttributes.COURSENAME.getName())]);
		String cDescription = ((dict.get(DesiredAttributes.COURSEDESCRIPTION.getName()) == null) ? "" : row[dict.get(DesiredAttributes.COURSEDESCRIPTION.getName())]);
		String cfullTime = ((dict.get(DesiredAttributes.COURSEFULLTIME.getName()) == null) ? "" : row[dict.get(DesiredAttributes.COURSEFULLTIME.getName())]);
		String cCOPOL = ((dict.get(DesiredAttributes.COURSEOL.getName()) == null) ? "-1" : row[dict.get(DesiredAttributes.COURSEOL.getName())]);

		if (pc == null)
		{	
			//Initialising PolyITECourse
			PolyITECourse c = new PolyITECourse(cName, cfullTime, cDescription, Integer.parseInt(cCOPOL));
		
			return c;
		}
		//Update PolyITECourse
		pc.setAttributes(cName, cDescription, cfullTime, Integer.parseInt(cCOPOL));
		return pc;
	}
	public UniversityCourse InitialiseUniversityCourse(Hashtable<String, Integer> dict, String[] row, UniversityCourse uc)
	{
		String cName = ((dict.get(DesiredAttributes.COURSENAME.getName()) == null) ? "" : row[dict.get(DesiredAttributes.COURSENAME.getName())]);
		String cDescription = ((dict.get(DesiredAttributes.COURSEDESCRIPTION.getName()) == null) ? "" : row[dict.get(DesiredAttributes.COURSEDESCRIPTION.getName())]);
		String cfullTime = ((dict.get(DesiredAttributes.COURSEFULLTIME.getName()) == null) ? "" : row[dict.get(DesiredAttributes.COURSEFULLTIME.getName())]);
		String cCOPAL = ((dict.get(DesiredAttributes.COURSEAL.getName()) == null) ? "" : row[dict.get(DesiredAttributes.COURSEAL.getName())]);
		String cCOPGPA = ((dict.get(DesiredAttributes.COURSEGPA.getName()) == null) ? "-1.0" : row[dict.get(DesiredAttributes.COURSEGPA.getName())]);
		String cType = ((dict.get(DesiredAttributes.COURSETYPE.getName()) == null) ? "" : row[dict.get(DesiredAttributes.COURSETYPE.getName())]);
		
		if (uc == null)
		{
			//Initialising UniversityCourse
			UniversityCourse c = new UniversityCourse(cName, cfullTime, cDescription, cCOPAL, Float.parseFloat(cCOPGPA), cType);
			
			return c;
		}
		//Update UniversityCourse
		uc.setAttributes(cName, cDescription, cfullTime, cCOPAL, Float.parseFloat(cCOPGPA), cType);
		return uc;
	}
	//Constructing a new subject based on row values
	public String InitialiseSubject(Hashtable<String, Integer> dict, String[] row)
	{
		//Initialising subject
		String jSubject = ((dict.get(DesiredAttributes.JCSUBJECTS.getName()) == null) ? "" : row[dict.get(DesiredAttributes.JCSUBJECTS.getName())]);
		//String subDescription = ((dict.get("ccaDescription") == null) ? "" : row[dict.get("ccaDescription")]);
		//CCA cca = new CCA(ccaName, ccaDescription);
		
		return jSubject;
	}
	//Constructing a new CCA based on row values
	public CCA InitialiseCCA(Hashtable<String, Integer> dict, String[] row, CCA cc)
	{
		String ccaName = ((dict.get(DesiredAttributes.CCANAME.getName()) == null) ? "" : row[dict.get(DesiredAttributes.CCANAME.getName())]);
		String ccaDescription = ((dict.get(DesiredAttributes.CCADESCRIPTION.getName()) == null) ? "" : row[dict.get(DesiredAttributes.CCADESCRIPTION.getName())]);
		//Initialising CCA
		if (cc == null)
		{			
			CCA cca = new CCA(ccaName, ccaDescription);	
			return cca;
		}
		//UpdateCCA
		cc.setAttributes(ccaName, ccaDescription);
		return cc;
	}
	//Constructing a new School based on row 
	public School InitialiseSchool(Hashtable<String, Integer> dict, String[] row, List<Course> courses, School sc)
	{
		String sName = ((dict.get(DesiredAttributes.SCHOOLNAME.getName()) == null) ? "School" : row[dict.get(DesiredAttributes.SCHOOLNAME.getName())]);
		String sDescription = ((dict.get(DesiredAttributes.SCHOOLDESCRIPTION.getName()) == null) ? "" : row[dict.get(DesiredAttributes.SCHOOLDESCRIPTION.getName())]);
		if (sc == null)
		{
			//Initialising School		
			School s = new School(sName, courses, sDescription);
			return s;
		}
		//Update School
		sc.setAttributes(sName, sDescription);
		return sc;
		
	}
	//Constructing a new University based on row values
	public University InitialiseUniversity(Hashtable<String, Integer> dict, String[] row, List<School> schools, String convert, University uni)
	{
		String iName = ((dict.get(DesiredAttributes.INSTITUTENAME.getName()) == null) ? "" : row[dict.get(DesiredAttributes.INSTITUTENAME.getName())]);
		String iDescription = ((dict.get(DesiredAttributes.INSTITUTEDESCRIPTION.getName()) == null) ? "" : row[dict.get(DesiredAttributes.INSTITUTEDESCRIPTION.getName())]);
		String iFees = ((dict.get(DesiredAttributes.INSTITUTEFEES.getName()) == null) ? "-1.0" : row[dict.get(DesiredAttributes.INSTITUTEFEES.getName())]);
		String uRank = ((dict.get(DesiredAttributes.UNIVERSITYRANK.getName()) == null) ? "-1" : row[dict.get(DesiredAttributes.UNIVERSITYRANK.getName())]);
		if (uni == null)
		{
			//Inititalising University
			
			if (iName.equals(""))
				iName = convert;
			University u = new University(iName, iDescription, Float.parseFloat(iFees) , schools, Integer.parseInt(uRank));
			return u;
		}
		//Update University
		uni.setAttributes(iName, iDescription, Float.parseFloat(iFees), Integer.parseInt(uRank));
		return uni;
				
	}
	//Constructing a new Polytechnics based on row 
	public Polytechnic InitialisePolytechnics(Hashtable<String, Integer> dict, String[] row, List<School> schools, List<CCA> ccas, String convert, Polytechnic poly)
	{
		String iName = ((dict.get(DesiredAttributes.INSTITUTENAME.getName()) == null) ? "" : row[dict.get(DesiredAttributes.INSTITUTENAME.getName())]);
		String iDescription = ((dict.get(DesiredAttributes.INSTITUTEDESCRIPTION.getName()) == null) ? "" : row[dict.get(DesiredAttributes.INSTITUTEDESCRIPTION.getName())]);
		String iFees = ((dict.get(DesiredAttributes.INSTITUTEFEES.getName()) == null) ? "-1.0" : row[dict.get(DesiredAttributes.INSTITUTEFEES.getName())]);
		
		if (poly == null)
		{
			//Inititalising Polytechnic
			
			if (iName.equals(""))
				iName = convert;
			Polytechnic p = new Polytechnic(iName, iDescription, Float.parseFloat(iFees) , schools, ccas);
			
			return p;
		}
		//Update Polytechnics
		poly.setAttributes(iName, iDescription, Float.parseFloat(iFees));
		return poly;
	}
	//Constructing a new ITE based on row values
	public ITE InitialiseITE(Hashtable<String, Integer> dict, String[] row, List<School> schools, List<CCA> ccas, String convert, ITE ite)
	{

		String iName = ((dict.get(DesiredAttributes.INSTITUTENAME.getName()) == null) ? "" : row[dict.get(DesiredAttributes.INSTITUTENAME.getName())]);
		String iDescription = ((dict.get(DesiredAttributes.INSTITUTEDESCRIPTION.getName()) == null) ? "" : row[dict.get(DesiredAttributes.INSTITUTEDESCRIPTION.getName())]);
		String iFees = ((dict.get(DesiredAttributes.INSTITUTEFEES.getName()) == null) ? "-1.0" : row[dict.get(DesiredAttributes.INSTITUTEFEES.getName())]);

		if (ite == null)
		{

			//Inititalising ITE
			if (iName.equals(""))
				iName = convert;

			ITE i = new ITE(iName, iDescription, Float.parseFloat(iFees) , schools, ccas);
			return i;
		}
		//Update ITE
		ite.setAttributes(iName, iDescription, Float.parseFloat(iFees));
		return ite;
	}
	//Constructing a new JuniorCollege based on row values
	public JuniorCollege InitialiseJC(Hashtable<String, Integer> dict, String[] row, List<String> subjects, List<CCA> ccas, String convert, JuniorCollege jc)
	{
		String iName = ((dict.get(DesiredAttributes.INSTITUTENAME.getName()) == null) ? "" : row[dict.get(DesiredAttributes.INSTITUTENAME.getName())]);
		String iDescription = ((dict.get(DesiredAttributes.INSTITUTEDESCRIPTION.getName()) == null) ? "" : row[dict.get(DesiredAttributes.INSTITUTEDESCRIPTION.getName())]);
		String iFees = ((dict.get(DesiredAttributes.INSTITUTEFEES.getName()) == null) ? "-1.0" : row[dict.get(DesiredAttributes.INSTITUTEFEES.getName())]);
		String cCOPOL = ((dict.get(DesiredAttributes.COURSEOL.getName()) == null) ? "-1" : row[dict.get(DesiredAttributes.COURSEOL.getName())]);
		
		if (jc == null)
		{
			//Inititalising JC			
			if (iName.equals(""))
				iName = convert;
			JuniorCollege j = new JuniorCollege(iName, iDescription, Float.parseFloat(iFees) , subjects, ccas, Integer.parseInt(cCOPOL) );
			
			return j;
		}
		//Update JC
		jc.setAttributes(iName, iDescription, Float.parseFloat(iFees), Integer.parseInt(cCOPOL));
		return jc;
		
	}
	//Check if Institute/School/Course already exist based on their name
	public Object Contains(List<?> listToCheck, String name)
	{

		if (listToCheck != null && !listToCheck.isEmpty())
		{
				
				if (listToCheck.get(0) instanceof Institute)
				{	
					
					for (int i = 0; i < listToCheck.size(); i++)
					{
						Institute institute = (Institute)listToCheck.get(i);
						if (institute.containName(name))
						{
							return institute;
						}
					}
				}
				else if (listToCheck.get(0) instanceof School)
				{
					for (int i = 0; i < listToCheck.size(); i++)
					{
						School school = (School)listToCheck.get(i);
						if (school.containName(name))
						{
							return school;
						}
					}
				}
				else if (listToCheck.get(0) instanceof Course)
				{
					for (int i = 0; i < listToCheck.size(); i++)
					{
						Course course = (Course)listToCheck.get(i);
						if (course.containName(name))
						{
							return course;
						}
					}
				}
				else if (listToCheck.get(0) instanceof CCA)
				{
					for (int i = 0; i < listToCheck.size(); i++)
					{
						CCA cca = (CCA)listToCheck.get(i);
						if (cca.containName(name))
						{
							return cca;
						}
					}
				}
		}
		return null;
	}
	//Fixes the problem where there are comma in the Row Value from the splitting
	public String[] fixRow(String[] stringArrToFix)
	{
		List<String> fixedString = new ArrayList<String>();
		
		String fixing = "";
		
		for(String s : stringArrToFix)
		{
			if (s.contains("\""))
			{
				if (fixing.equals(""))
					fixing = s.replace("\"", "");
				else
				{
					fixing = fixing + ","+  s.replace("\"", "");
					fixedString.add(fixing);
					fixing = "";
				}
					
					
			}
			else
			{
				fixedString.add(s);
			}
		}		
		
		String[] fixed = new String[fixedString.size()];
		fixedString.toArray(fixed);				
				
		return fixed;
	}
	//Obtaining the desired attributes to construct the classes according to their institution type 
	public void getAttributes(String[] row, String type, Hashtable<String, Integer> attributeDict)
	{
		
		if (type.equals("U"))
		{
			List<String> variables = University.getVariables();
			List<List<String>> classAttributes = University.getAttributesRequired();
			
			for (int i = 0; i < row.length; i++)
			{
				for (int j = 0; j < classAttributes.size(); j++)
				{
					if (classAttributes.get(j).contains(row[i].toLowerCase()))
					{
						attributeDict.put(variables.get(j), i);
					}
				}
			}
		}
		else if (type.equals("P"))
		{
			List<String> variables = Polytechnic.getVariables();
			List<List<String>> classAttributes = Polytechnic.getAttributesRequired();
			
			for (int i = 0; i < row.length; i++)
			{
				for (int j = 0; j < classAttributes.size(); j++)
				{
					//System.out.println(variables.get(j) + " " + classAttributes.get(j));
					if (classAttributes.get(j).contains(row[i].toLowerCase()))
					{						
						attributeDict.put(variables.get(j), i);
						break;
					}
				}
			}
		}
		else if (type.equals("I"))
		{
			List<String> variables = ITE.getVariables();
			List<List<String>> classAttributes = ITE.getAttributesRequired();
			
			for (int i = 0; i < row.length; i++)
			{
				for (int j = 0; j < classAttributes.size(); j++)
				{
					//System.out.println(variables.get(j) + " " + classAttributes.get(j));
					if (classAttributes.get(j).contains(row[i].toLowerCase()))
					{						
						attributeDict.put(variables.get(j), i);
						break;
					}
				}
			}
		}
		else if (type.equals("J"))
		{
			List<String> variables = JuniorCollege.getVariables();
			List<List<String>> classAttributes = JuniorCollege.getAttributesRequired();
			
			for (int i = 0; i < row.length; i++)
			{
				for (int j = 0; j < classAttributes.size(); j++)
				{
					//System.out.println(variables.get(j) + " " + classAttributes.get(j));
					if (classAttributes.get(j).contains(row[i].toLowerCase()))
					{						
						attributeDict.put(variables.get(j), i);
						break;
					}
				}
			}
		}	
	}
	//Obtain the institute name from the csv file name
	public String obtainInstituteName (String s)
	{
		String name = s.replace("D:\\EclipseWorkshop\\SchoolApp\\src\\", "").replace(".csv", "");
		int iend = name.length();
		if (name.contains("_"))
			iend = name.indexOf("_");
		name = name.substring(0, iend);
		name = convertInstituteName(name.toUpperCase());
		
		return name;
	}
	//Convert the Abbreviation to the respective Names (might need to add more if required, or convert to csv/json)
	public String convertInstituteName(String s)
	{
		switch(s)
		{
			case "TP":
				return "Temasek Polytechnic";
			case "NP":
				return "Ngee Ann Polytechnic";
			case "NYP":
				return "Nanyang Polytechnic";
			case "RP":
				return "Republic Polytechnic";
			case "SP":
				return "Singapore Polytechnic";
			case "ITE":
				return "Institute of Technical Education";
			case "NUS":
				return "National University of Singapore";
			case "NTU":
				return "Nanyang Technological University";
			case "SIT":
				return "Singapore Institute of Technology";
			case "SMU":
				return "Singapore Management University";
			case "SUTD":
				return "Singapore University of Technology and Design";
			case "SUSS":
				return "Singapore University of Social Sciences";
		}
		return s;
	}
	//Printing the institute by their type
	public static void printInstitute(String type)
	{

		switch (type)
		{
			case "U":

				Log.d("MyActivity", Integer.toString(universities.size()));
				for (int i = 0; i < universities.size(); i++)
				{
					//System.out.println(universities.isEmpty());
					//System.out.println(universities.get(i).getName());						
					universities.get(i).print();
					Log.i("Uni", "==============================================");
				}
				break;
			case "P":

				for (int i = 0; i < polytechnics.size(); i++)
				{

					//System.out.println(universities.isEmpty());
					//System.out.println(universities.get(i).getName());						
					polytechnics.get(i).print();
					Log.i("Poly", "==============================================");
				}
				break;
				
			case "I":

				for (int i = 0; i < ites.size(); i++)
				{
					//System.out.println(universities.isEmpty());
					//System.out.println(universities.get(i).getName());						
					ites.get(i).print();
					Log.i("ITE", "==============================================");
				}
				break;
			case "J":
				
				for (int i = 0; i < juniorcolleges.size(); i++)
				{
					System.out.println("YOES");
					//System.out.println(universities.isEmpty());
					//System.out.println(universities.get(i).getName());						
					juniorcolleges.get(i).print();
					Log.i("JC", "==============================================");
				}
				break;
		
		}
	}
	//Print all the institutes collected from the csv(s)
	public static void printInstitutes()
	{
		Log.i("UniPolyITEJC","LISTING OF ALL INSTITUTES BY TYPE:");
		Log.i("UniPolyITEJC","\n\n\n=========================================================================");
		//Print University
		printInstitute("U");
		//Print Polytechnic
		printInstitute("P");
		//Print ITE
		printInstitute("I");
		//Print JC
		printInstitute("J");
		
	}
}

