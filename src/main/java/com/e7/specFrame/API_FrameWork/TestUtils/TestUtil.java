package com.e7.specFrame.API_FrameWork.TestUtils;

import java.util.Hashtable;

import com.e7.specFrame.API_FrameWork.setUp.APISetUp;

import io.restassured.specification.RequestSpecification;

public class TestUtil extends APISetUp{

	
	public static RequestSpecification setFormParam(String arguments,RequestSpecification reqSpecs)
	{
	 String [] listOfArguments = arguments.split(",");
	for(String singleArgument:listOfArguments)
	{
		String[] keyValue =singleArgument.split(":");
		{
			String key = keyValue[0];
			String value=keyValue[1];
			reqSpecs.formParam(key, value);
		}
		
	}
	return reqSpecs;

		
	}
}
