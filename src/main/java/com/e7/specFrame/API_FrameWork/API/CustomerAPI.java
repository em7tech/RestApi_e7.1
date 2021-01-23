package com.e7.specFrame.API_FrameWork.API;

import java.util.Hashtable;

import com.e7.specFrame.API_FrameWork.TestUtils.TestUtil;
import com.e7.specFrame.API_FrameWork.setUp.APISetUp;

import io.restassured.response.Response;

public class CustomerAPI extends APISetUp {

	public static Response sendPostRequestWithValidDataWithArguments(Hashtable<String, String> data) {

		Response response = TestUtil.setFormParam(data.get("arguments"), setRequestSpecification())
				.post(data.get("endpoint"));
		return response;
	}
}
