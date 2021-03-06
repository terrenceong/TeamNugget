package com.example.teamnugget;

import android.util.Log;

public class PolyITECourse extends Course {

	//Storing the cut-off points for O'Level
	int cutOffPoints = 0;
	
	//Constructor for PolyITECourse
	public PolyITECourse(String name, String fullTime, String description, int cutOffPoints) {
		super(name, fullTime, description);
		this.cutOffPoints = cutOffPoints;
		// TODO Auto-generated constructor stub
	}
	//Obtain the cut-off points for O'Level
	public int getCutOffPoints()
	{
		return cutOffPoints;
	}
	public void setCutOffPoints(int cutOffPoints)
	{
		this.cutOffPoints = cutOffPoints;
	}
	public void setAttributes(String name, String description, String fullTime, int cutOffPoints)
	{
		if (!name.equals("") && this.name.equals(""))
			this.setName(name);
		if (!description.equals("") && this.description.equals(""))
			this.setDescription(description);
		if (!fullTime.equals("") && this.fullTime.equals(""))
			this.setFullTime(fullTime);
		if (this.cutOffPoints <= 0)
			this.setCutOffPoints(cutOffPoints);
	}

	@Override
	public void print(String type) {
		super.print(type);
		switch (type)
		{
			case "P":
				Log.i("PolyDebug","COURSE CUT-OFF-POINTS (L1R5) : " + this.cutOffPoints);
				break;
			case "I":
				Log.i("ITEDebug","COURSE CUT-OFF-POINTS (L1R5) : " + this.cutOffPoints);
				break;

		}
	}
}
