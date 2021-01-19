package com.e7.specFrame.API_FrameWork.setUp;

import static io.restassured.RestAssured.given;

import java.lang.reflect.Method;

import org.aeonbits.owner.ConfigFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.e7.specFrame.API_FrameWork.TestUtils.ConfigProperty;
import com.e7.specFrame.API_FrameWork.TestUtils.ExcelReader;
import com.e7.specFrame.API_FrameWork.TestUtils.ExtentManager;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;


public class APISetUp {
	
	protected static ConfigProperty configProperty;
	
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir")+"/src/test/resources/testData/TestData.xlsx");
	public static ExtentReports extentReport;
	public static ThreadLocal<ExtentTest> classLevelLog = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> testLevelLog = new ThreadLocal<ExtentTest>();
	
	@BeforeSuite
	public void beforeSuite() {
	
		configProperty = ConfigFactory.create(ConfigProperty.class);
		
		RestAssured.baseURI=configProperty.getBaseURI();
		RestAssured.basePath=configProperty.getBasePath();
		
		extentReport =ExtentManager.GetExtent(configProperty.getTestFilePath()+configProperty.getTestReportName());
		

	}
	@BeforeClass
	public void beforeClass() {
		
		//ExtentTest test = new ExtentTest(getClass().getSimpleName());
		
		ExtentTest classLevelTest=extentReport.createTest(getClass().getSimpleName());
		classLevelLog.set(classLevelTest);
	}
	
	@BeforeMethod
	public void beforeMethod(Method method) {
		
		ExtentTest test = classLevelLog.get().createNode(method.getName());
		testLevelLog.set(test);
		testLevelLog.get().info("Test Case:- " +method.getName()+ " execution started ");
		//System.out.println("Test Case :-" + method.getName()+" execution started ");
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result) {
		
		if(result.getStatus()==ITestResult.SUCCESS){
			testLevelLog.get().pass("Test case is pass");
			System.out.println("Test case is passed");
		}else if (result.getStatus()==ITestResult.FAILURE) {
			testLevelLog.get().fail("This test case is failed");
			System.out.println("This test case is failled");
		}else if (result.getStatus()==ITestResult.SKIP) {
			testLevelLog.get().skip("Test case is skipped");
			System.out.println("This test case is skipped");
		}
		
		extentReport.flush();
		
	}
	
	@AfterSuite
	public void afterSuite() {
	
		configProperty = ConfigFactory.create(ConfigProperty.class);
		
		RestAssured.baseURI=configProperty.getBaseURI();
		RestAssured.basePath=configProperty.getBasePath();

	}
	
	public static RequestSpecification setRequestSpecification() {
		
		RequestSpecification spec = given().auth().basic(configProperty.getSceretKey(), "");
		testLevelLog.get().info("Request Specification set");
		
		return spec; 
	}
	

}
