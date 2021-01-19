package com.e7.specFrame.API_FrameWork.TestUtils;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({"file:src/test/resources/propertyFiles/config.properties"})
public interface ConfigProperty extends Config {
	
	@Key("baseURI")
	String getBaseURI();
	
	@Key("basePath")
	String getBasePath();
	
	@Key("secretKey")
	String getSceretKey();
	
	@Key("testReportPath")
	String getTestFilePath();
	
	@Key("testReportName")
	String getTestReportName();

}
