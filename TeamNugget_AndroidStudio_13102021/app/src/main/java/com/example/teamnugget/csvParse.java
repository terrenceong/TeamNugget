package com.example.teamnugget;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.*;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.*;

import java.io.InputStream;
import java.io.InputStreamReader;

public class csvParse extends Application {

	private static final DecimalFormat df = new DecimalFormat("0.00");
	//Storing every Institute from the csv(s)
	static List<Institute> universities = new ArrayList<Institute>();
	static List<Institute> polytechnics = new ArrayList<Institute>();
	static List<Institute> ites = new ArrayList<Institute>();
	static List<Institute> juniorcolleges = new ArrayList<Institute>();
	
	boolean general = false;

	//IMPORTANT NOTE: Any new attributes added in the classes should also be added into the enum for convenience when retrieving information
	//All possible variables
	enum DesiredAttributes
	{
		INSTITUTENAME ("i_Name"),
		INSTITUTEDESCRIPTION ("i_Description"),
		INSTITUTEFEES ("i_Fees"),
		UNIVERSITYRANK ("u_Rank"),
		JCSUBJECTS ("j_Subject"),
		JCPOINTARTS ("j_PointArts"),
		JCPOINTSCIENCE ("j_PointScience"),
		JCDSA("j_DSA"),
		JCELECTIVES("j_Electives"),
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

	@Override
	public void onCreate() {
		super.onCreate();
		parseCSV();
	}


	public void parseCSV()
	{
		csvParse cp = new csvParse();
		Field[] fields=R.raw.class.getFields();
		for(int count=0; count < fields.length; count++){

			int resourceID = 0;
			try
			{
				resourceID = fields[count].getInt(fields[count]);
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			System.out.println("Raw Asset: " + fields[count].getName() + "ID: " + resourceID);
			InputStream is = getResources().openRawResource(resourceID);
			cp.parseData(is, fields[count].getName() );
		}
		csvParse.sortAllInstitutes();
		csvParse.printInstitutes();
		SearchSortAlgorithm.searchTest();

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
					System.out.println(Arrays.asList(csvAttributes));
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
							row = fixRow(row, csvAttributes.length);


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
								List<CCA> ccaToAdd = new ArrayList<CCA>();
								
								courseToAdd.add(InitialiseUniversityCourse(attributeDict, row, null));
								ccaToAdd.add(InitialiseCCA(attributeDict, row, null));
								schoolToAdd.add(InitialiseSchool(attributeDict, row, courseToAdd, null));
															
								universities.add(InitialiseUniversity(attributeDict, row, schoolToAdd, ccaToAdd, obtainInstituteName(file), null));
							}
							//Exist University

							else
							{
								//Update University Attributes if needed
								InitialiseUniversity(attributeDict, row, null, null, obtainInstituteName(file), (University)currentI);
								
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
								if (attributeDict.get(DesiredAttributes.CCANAME.getName()) != null)
								{
									CCA currentCCA = (CCA)Contains(currentI.getCCAs(), row[attributeDict.get(DesiredAttributes.CCANAME.getName())]);

									//New CCA
									if (currentCCA == null)
									{
										if (!row[attributeDict.get(DesiredAttributes.CCANAME.getName())].equals(""))
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
					//Polytechnic
					case "P":
						
						//Traversing the entire files row by row
						while ((line = reader.readLine()) != null)
						{
							String[] row = line.split(",");
							/*for (int i = 0; i < row.length; i++)
							{
								System.out.println(row[i]);
							}*/

							row = fixRow(row, csvAttributes.length);


							
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
									CCA currentCCA = (CCA)Contains(currentI.getCCAs(), row[attributeDict.get(DesiredAttributes.CCANAME.getName())]);

									//New CCA
									if (currentCCA == null && !row[attributeDict.get(DesiredAttributes.CCANAME.getName())].equals("") )
									{
										if (!row[attributeDict.get(DesiredAttributes.CCANAME.getName())].equals(""))
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
							row = fixRow(row, csvAttributes.length);
							System.out.println(Arrays.asList(row));


							Institute currentI = null;

							if (general && !row[attributeDict.get(DesiredAttributes.INSTITUTENAME.getName())].equals(""))
							{
								currentI = (Institute)Contains(ites, row[attributeDict.get(DesiredAttributes.INSTITUTENAME.getName())]);
							}
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
									CCA currentCCA = (CCA)Contains(currentI.getCCAs(), row[attributeDict.get(DesiredAttributes.CCANAME.getName())]);

									//New CCA
									if (currentCCA == null)
									{
										if (!row[attributeDict.get(DesiredAttributes.CCANAME.getName())].equals(""))
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
							row = fixRow(row, csvAttributes.length);
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
								List<String> dsas = new ArrayList<String>();
								List<String> electives = new ArrayList<String>();


								ccaToAdd.add(InitialiseCCA(attributeDict, row, null));
								subjects.add(InitialiseSubject(attributeDict,row));
								dsas.add(InitialiseGeneral(attributeDict, row, DesiredAttributes.JCDSA));
								electives.add(InitialiseGeneral(attributeDict, row, DesiredAttributes.JCELECTIVES));
								juniorcolleges.add(InitialiseJC(attributeDict, row, subjects, dsas, electives, ccaToAdd, obtainInstituteName(file), null));
							}
							//Exist Junior College
							else
							{
								//Update JC Attributes if needed
								InitialiseJC(attributeDict, row, null, null, null, null, obtainInstituteName(file), (JuniorCollege) currentI);

								if (attributeDict.get(DesiredAttributes.CCANAME.getName()) != null)
								{
									CCA currentCCA = (CCA)Contains(currentI.getCCAs(), row[attributeDict.get(DesiredAttributes.CCANAME.getName())]);

									//New CCA
									if (currentCCA == null)
									{
										if (!row[attributeDict.get(DesiredAttributes.CCANAME.getName())].equals(""))
											currentI.getCCAs().add(InitialiseCCA(attributeDict, row, null));
									}
									//Exist CCA
									else
									{
										//Update CCA Attributes if needed
										InitialiseCCA(attributeDict, row, currentCCA);
									}
								}
								//Add Subjects
								if (attributeDict.get(DesiredAttributes.JCSUBJECTS.getName()) != null)
								{
									List<String> subjects = ((JuniorCollege) currentI).getSubjects();

									if (!subjects.contains(row[attributeDict.get(DesiredAttributes.JCSUBJECTS.getName())]))
									{
										subjects.add(InitialiseSubject(attributeDict,row));
									}
								}
								//Add DSAs
								if (attributeDict.get(DesiredAttributes.JCDSA.getName()) != null)
								{
									List<String> dsas = ((JuniorCollege) currentI).getDSA();

									if (!dsas.contains(row[attributeDict.get(DesiredAttributes.JCDSA.getName())]))
									{
										dsas.add(InitialiseGeneral(attributeDict, row, DesiredAttributes.JCDSA));
									}
								}
								//Add Electives
								if (attributeDict.get(DesiredAttributes.JCELECTIVES.getName()) != null)
								{
									List<String> electives = ((JuniorCollege) currentI).getElectives();

									if (!electives.contains(row[attributeDict.get(DesiredAttributes.JCELECTIVES.getName())]))
									{
										electives.add(InitialiseGeneral(attributeDict, row, DesiredAttributes.JCELECTIVES));
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
			int olevel = 0;
			//Initialising UniversityCourse
			try{
				olevel = Integer.parseInt(cCOPOL);
			}catch(NumberFormatException e){

				olevel = -1;
			}
			//Initialising PolyITECourse
			PolyITECourse c = new PolyITECourse(cName, cfullTime, cDescription, olevel);

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
			float gpa = 0.0f;
			//Initialising UniversityCourse
			try{
				gpa = Float.parseFloat(cCOPGPA);

			}catch(NumberFormatException e){

				gpa = -1;
			}
			System.out.println("HERE");
			UniversityCourse c = new UniversityCourse(cName, cfullTime, cDescription, cCOPAL, gpa, cType);

			return c;
		}
		//Update UniversityCourse
		uc.setAttributes(cName, cDescription, cfullTime, cCOPAL, Float.parseFloat(cCOPGPA), cType);
		return uc;
	}
	//Constructing a new subject based on row values
	public String InitialiseSubject(Hashtable<String, Integer> dict, String[] row)
	{
		//Log.i("DebugJC", "ROW" + Arrays.asList(row));
		//Initialising subject
		String jSubject = ((dict.get(DesiredAttributes.JCSUBJECTS.getName()) == null) ? "" : row[dict.get(DesiredAttributes.JCSUBJECTS.getName())]);
		//String subDescription = ((dict.get("ccaDescription") == null) ? "" : row[dict.get("ccaDescription")]);
		//CCA cca = new CCA(ccaName, ccaDescription);

		return jSubject;
	}
	public String InitialiseGeneral(Hashtable<String, Integer> dict, String[] row, DesiredAttributes attributesToFind )
	{
		String generalObject = ((dict.get(attributesToFind.getName()) == null) ? "" : row[dict.get(attributesToFind.getName())]);

		return generalObject;
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
	public University InitialiseUniversity(Hashtable<String, Integer> dict, String[] row, List<School> schools, List<CCA> ccas, String convert, University uni)
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
			University u = new University(iName, iDescription, Float.parseFloat(iFees), schools, ccas, Integer.parseInt(uRank));
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
	public JuniorCollege InitialiseJC(Hashtable<String, Integer> dict, String[] row, List<String> subjects, List<String> dsas, List<String> electives, List<CCA> ccas, String convert, JuniorCollege jc)
	{
		String iName = ((dict.get(DesiredAttributes.INSTITUTENAME.getName()) == null) ? "" : row[dict.get(DesiredAttributes.INSTITUTENAME.getName())]);
		String iDescription = ((dict.get(DesiredAttributes.INSTITUTEDESCRIPTION.getName()) == null) ? "" : row[dict.get(DesiredAttributes.INSTITUTEDESCRIPTION.getName())]);
		String iFees = ((dict.get(DesiredAttributes.INSTITUTEFEES.getName()) == null) ? "-1.0" : row[dict.get(DesiredAttributes.INSTITUTEFEES.getName())]);
		String cPOINTART = ((dict.get(DesiredAttributes.JCPOINTARTS.getName()) == null) ? "-1" : row[dict.get(DesiredAttributes.JCPOINTARTS.getName())]);
		String cPOINTSCIENCE = ((dict.get(DesiredAttributes.JCPOINTSCIENCE.getName()) == null) ? "-1" : row[dict.get(DesiredAttributes.JCPOINTSCIENCE.getName())]);

		if (jc == null)
		{
			//Inititalising JC
			if (iName.equals(""))
				iName = convert;
			JuniorCollege j = new JuniorCollege(iName, iDescription, Float.parseFloat(iFees) , subjects, dsas, electives, ccas,
					Integer.parseInt(cPOINTART), Integer.parseInt(cPOINTSCIENCE));

			return j;
		}
		//Update JC
		jc.setAttributes(iName, iDescription, Float.parseFloat(iFees), Integer.parseInt(cPOINTART), Integer.parseInt(cPOINTSCIENCE));
		return jc;

	}
	//Check if Institute/School/Course already exist based on their name
	public static Object Contains(List<?> listToCheck, String name)
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
	public static int originalIndex(Object objectToCheck, List<?> listToCheck)
	{
		if (listToCheck != null)
		{
			if (objectToCheck instanceof University)
			{
				for (int i =0 ; i < universities.size(); i++)
				{
					Institute institute = universities.get(i);
					if (institute.containName(((University) objectToCheck).getName()))
					{
						return i;
					}
				}
			}
			else if (objectToCheck instanceof Polytechnic)
			{
				for (int i =0 ; i < polytechnics.size(); i++)
				{
					Institute institute = polytechnics.get(i);
					if (institute.containName(((Polytechnic) objectToCheck).getName()))
					{
						return i;
					}
				}
			}
			else if (objectToCheck instanceof ITE)
			{
				for (int i =0 ; i < ites.size(); i++)
				{
					Institute institute = ites.get(i);
					if (institute.containName(((ITE) objectToCheck).getName()))
					{
						return i;
					}
				}
			}
			else if (objectToCheck instanceof JuniorCollege)
			{
				for (int i =0 ; i < juniorcolleges.size(); i++)
				{
					Institute institute = juniorcolleges.get(i);
					if (institute.containName(((JuniorCollege) objectToCheck).getName()))
					{
						return i;
					}
				}
			}
			else if (objectToCheck instanceof School)
			{
				for (int i =0 ; i < listToCheck.size(); i++)
				{
					School school = (School) listToCheck.get(i);
					if (school.containName(((School) objectToCheck).getName()))
					{
						return i;
					}
				}
			}
			else if (objectToCheck instanceof Course)
			{
				for (int i =0 ; i < listToCheck.size(); i++)
				{
					Course course = (Course) listToCheck.get(i);
					if (course.containName(((Course) objectToCheck).getName()))
					{
						return i;
					}
				}
			}
		}



		return -1;
	}
	public boolean Exist(List<String> listToCheck, String strToCheck)
	{
		for (int i = 0; i < listToCheck.size(); i++)
		{
			//System.out.println("StringTC: " + strToCheck + " ListTC: " + listToCheck.get(i));
			//System.out.println("STRSize: " + strToCheck.toCharArray().length +" LISTTSize: " + strToCheck.toCharArray().length);
			if (strToCheck.replace(Character.toString((char)65279), "").equals(listToCheck.get(i)) /*listToCheck.get(i).equals("jc")*/)
			{
				return true;
			}
		}
		return false;
	}

	//Fixes the problem where there are comma in the Row Value from the splitting
	public String[] fixRow(String[] stringArrToFix, int dictSize)
	{
		List<String> fixedString = new ArrayList<String>();
		
		String fixing = "";
		boolean isFixing = false;
		
		for(String s : stringArrToFix)
		{
			if (s.contains("\"") || isFixing)
			{
				int numOfApros = s.length() - s.replace("\"", "").length();
				if (!isFixing)
				{
					fixing = s.replace("\"", "");
					isFixing = true;
				}
				else if (s.contains("\"") && numOfApros % 2 == 1 && isFixing)
				{
					fixing = fixing + "," +  s.replace("\"", "");
					fixedString.add(fixing);
					fixing = "";
					isFixing = false;
				}
				else
				{
					fixing = fixing + "," +  s.replace("\"\"", "\"");
				}

			}
			else
			{
				fixedString.add(s);
			}
		}
		System.out.println("FIXSIZE: "+ fixedString.size() + "DICTSIZE: " + dictSize);
		if (fixedString.size() < dictSize)
		{

			while (fixedString.size() < dictSize)
			{

				fixedString.add("");
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
					if (Exist(classAttributes.get(j), row[i].toLowerCase()))
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
					if (Exist(classAttributes.get(j), row[i].toLowerCase()))
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
					if (Exist(classAttributes.get(j), row[i].toLowerCase()))
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
					if (Exist(classAttributes.get(j), row[i].toLowerCase()))
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
		//System.out.println(s);
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
	public static void printSelectedInstitute(List<Institute> institutes)
	{
		for (Institute i : institutes)
		{
			i.print();
		}
	}
	public static void printSelectedInstitute(List<Institute> institutes, boolean school, boolean course, boolean cca)
	{
		for (Institute i : institutes)
		{
			i.printSpecific(school, course, cca);
		}
	}
	//Print all the institutes collected from the csv(s)
	public static void printInstitutes()
	{
		Log.i("UniDebugPolyDebugITEDebugJCDebug","LISTING OF ALL INSTITUTES BY TYPE:");
		Log.i("UniDebugPolyDebugITEDebugJCDebug","\n\n\n=========================================================================");
		//Print University
		printSelectedInstitute(universities);
		//Print Polytechnic
		printSelectedInstitute(polytechnics);
		//Print ITE
		printSelectedInstitute(ites);
		//Print JC
		printSelectedInstitute(juniorcolleges);
	}
	public static void sortAllInstitutes()
    {
        universities = (List<Institute>)SearchSortAlgorithm.sortList(universities, false);
        polytechnics = (List<Institute>)SearchSortAlgorithm.sortList(polytechnics, false);
        ites = (List<Institute>)SearchSortAlgorithm.sortList(ites, false);
        juniorcolleges = (List<Institute>)SearchSortAlgorithm.sortList(juniorcolleges, false);

        for (Institute i : universities)
        {
			((University)i).sortSchool();
        }
		for (Institute i : polytechnics)
		{
			((Polytechnic)i).sortSchool();
		}
		for (Institute i : ites)
		{
			((ITE)i).sortSchool();
		}

    }
    public static List<String> instituteNames(List<Institute> institutes)
	{
		List<String> names = new ArrayList<String>();
		for (Institute i : institutes)
		{
			names.add(i.getName());
		}
		return names;
	}
	public static Character instituteDeterminator(Institute i)
	{
		if (i instanceof University)
		{
			return 'U';
		}
		else if (i instanceof  Polytechnic)
		{
			return 'P';
		}
		else if (i instanceof  ITE)
		{
			return 'I';
		}

		return 'J';
	}



}

