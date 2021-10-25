package com.example.teamnugget;

import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.*;

public class SearchSortAlgorithm {

	public static void quickSort( List<Comparable> listToSort, int n, int m, boolean ranking)
	{
		int pivotPos = 0;
		
		if (n >= m)
		{
			return;
		}
		pivotPos = partition(listToSort, n, m, ranking);
		quickSort(listToSort, n, pivotPos - 1, ranking);
		quickSort(listToSort, pivotPos + 1, m, ranking);
		
	}
	private static int partition (List<Comparable> listToSort, int n, int m, boolean ranking)
	{
		int pivot = (n + m)/ 2;
			
		//Swap pivot with first element
		Comparable temp = listToSort.get(pivot);
		listToSort.set(pivot, listToSort.get(n));
		listToSort.set(n, temp);
		int last_small = n;

		if (!(listToSort.get(0) instanceof  University) && ranking)
		{
			ranking = false;
		}
		
		for (int i = n + 1; i < m + 1; i++)
		{
			if ((listToSort.get(i).compareTo(listToSort.get(n)) < 0 && !ranking) || ((listToSort.get(i) instanceof  University) && ((University)listToSort.get(i)).compareTo((University)listToSort.get(n)) < 0 && ranking))
			{
				temp = listToSort.get(last_small + 1);
				listToSort.set(last_small + 1, listToSort.get(i));
				listToSort.set(i, temp);
				last_small++;
			}
		}
		
		temp = listToSort.get(last_small);
		listToSort.set(last_small, listToSort.get(n));
		listToSort.set(n, temp);
		
		return last_small;
	}
	//Printing the institute by their type
	public static List<Comparable> convertListComparable(List<?> listToConvert)
	{
		if (listToConvert != null && !listToConvert.isEmpty()) {

			List<Comparable> comparables = new ArrayList<Comparable>();

			if (listToConvert.get(0) instanceof Comparable)
			{
				for (int i =0; i < listToConvert.size(); i++)
				{
					comparables.add((Comparable) listToConvert.get(i));

				}
				System.out.println("LIST IS COMPARABLE");
				return comparables;
			}
			else
			{
				//System.out.println("LIST IS NOT COMPARABLE");
			}
		}
		return null;
	}
	public static List<?> convertListBack(List<Comparable> comparables)
	{
		if (comparables != null && !comparables.isEmpty())
		{
			if (comparables.get(0) instanceof Institute)
			{
				List<Institute> institutes = new ArrayList<Institute>();
				for (int i =0; i < comparables.size(); i++)
				{
					institutes.add((Institute)comparables.get(i));
				}
				return institutes;
			}
			else if (comparables.get(0) instanceof School)
			{
				List<School> schools = new ArrayList<School>();
				for (int i =0; i < comparables.size(); i++)
				{
					schools.add((School) comparables.get(i));
				}
				return schools;
			}
			else if (comparables.get(0) instanceof Course)
			{
				List<Course> courses = new ArrayList<Course>();
				for (int i =0; i < comparables.size(); i++)
				{
					courses.add((Course) comparables.get(i));
				}
				return courses;
			}
		}
		return null;
	}
	public static List<?> sortList(List<?> listToSort, boolean ranking)
	{
		List<Comparable> comparableUni = convertListComparable(listToSort);
		SearchSortAlgorithm.quickSort(comparableUni, 0, listToSort.size()-1, ranking);
		listToSort = (List<Institute>)convertListBack(comparableUni);

		return listToSort;
	}
	public static List<?> searchList(List<?> listToCheck, String nameToCheck)
	{
		if (listToCheck != null && !listToCheck.isEmpty())
		{
			if (listToCheck.get(0) instanceof Institute)
			{
				List<Institute> institutes = new ArrayList<Institute>();

				for(int i =0; i < listToCheck.size(); i++)
				{
					if ((((Institute) listToCheck.get(i)).similarName(nameToCheck)) != null)
					{
						institutes.add(((Institute) listToCheck.get(i)));
					}
				}
				return institutes;
			}
			else if (listToCheck.get(0) instanceof  School)
			{
				List<School> schools = new ArrayList<School>();

				for(int i =0; i < listToCheck.size(); i++)
				{
					if ((((School) listToCheck.get(i)).similarName(nameToCheck)) != null)
					{
						schools.add(((School) listToCheck.get(i)));
					}
				}
				return schools;
			}
			else if (listToCheck.get(0) instanceof Course)
			{
				List<Course> courses = new ArrayList<Course>();

				for(int i =0; i < listToCheck.size(); i++)
				{
					if ((((Course) listToCheck.get(i)).similarName(nameToCheck)) != null)
					{
						courses.add(((Course) listToCheck.get(i)));
					}
				}
				return courses;
			}
		}
		return null;
	}
	public static List<Institute> searchByCourses(List<Institute> institutes, String nameOfCourse)
	{
		List<Institute> instituteWithCourse = new ArrayList<Institute>();
		for (Institute i : institutes)
		{
			List<School> similarSchool = new ArrayList<School>();
			for (School s : i.getSchools())
			{
				List<Course> similarCourses = s.similarCourses(nameOfCourse);
				if ( similarCourses != null && !similarCourses.isEmpty())
				{
					similarSchool.add(s.schoolCopy(similarCourses));
				}
			}
			if (similarSchool != null && !similarSchool.isEmpty())
			{
				instituteWithCourse.add(i.instituteCopy(similarSchool));
			}
		}
		return instituteWithCourse;
	}
	public static void searchTest()
	{
		Log.i("SearchingTest","======================SEARCHING TEST===================================================================================================================");
		csvParse.printSelectedInstitute(SearchSortAlgorithm.searchByCourses(csvParse.polytechnics, "Science"));
		Log.i("SearchingTest","======================SEARCHING TEST=================================================================================================================");
		csvParse.printSelectedInstitute((List<Institute>) searchList(csvParse.universities, "University"), false, false, false);
	}




}
