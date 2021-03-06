package com.example.teamnugget;

import android.util.Log;

public class CCA {
	
	//Storing name of CCA
	String name = "";
	//Storing description of CCA
	String description = "";
	
	//Constructor for CCA Object
	public CCA(String name, String description)
	{
		this.name = name;
		this.description = description;
	}
	//Obtain name of CCA
	public String getName()
	{
		return name;
	}
	//Obtain description of CCA
	public String getDescription()
	{
		return description;
	}
	//Checking if the name is the same as this instance
	public boolean containName(String name)
	{
		
		if ((this.name.toLowerCase()).equals(name.toLowerCase()))
		{
			return true;
		}
		return false;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public void setAttributes(String name, String description)
	{
		if (!name.equals("") && this.name.equals(""))
			this.setName(name);
		if (!description.equals("") && this.description.equals("GAY"))
			this.setDescription(description);
	}
	//Printing the variables of CCA
	public void print(String type)
	{
		switch (type)
		{
			case "J":
				Log.i("JCDebug","CCA NAME : " + this.name);
				if (this.description != "")
					Log.i("JCDebug","CCA DSCRIPTION: " + this.description);
				break;
			case "P":
				Log.i("PolyDebug","CCA NAME : " + this.name);
				if (this.description != "")
					Log.i("PolyDebug","CCA DSCRIPTION: " + this.description);
				break;
			case "U":
				Log.i("UniDebug","CCA NAME : " + this.name);
				if (this.description != "")
					Log.i("UniDebug","CCA DSCRIPTION: " + this.description);
			case "I":
				Log.i("ITEDebug", "CCA NAME : " + this.name);
				if (this.description != "")
					Log.i("ITEDebug","CCA DSCRIPTION: " + this.description);

				break;

		}

	}
	public CCA similarCCA(String nameToCheck)
	{
		if (this.getName().contains(nameToCheck))
		{
			return this;
		}
		return null;
	}
}
