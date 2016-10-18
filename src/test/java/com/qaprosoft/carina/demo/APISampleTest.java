/*
 * Copyright 2013-2016 QAPROSOFT (http://qaprosoft.com/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qaprosoft.carina.demo;

import com.qaprosoft.carina.core.demo.api.*;
import com.qaprosoft.carina.core.demo.util.PO;

import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.APITest;
import com.qaprosoft.carina.core.foundation.http.HttpResponseStatusType;
import com.qaprosoft.carina.core.foundation.performance.Timer;

/**
 * This sample shows how create web test.
 * 
 * @author Alex Khursevich
 */
public class APISampleTest extends APITest
{
	@Test
	public void testCreateUser()
	{
		Timer.start(PO.CREATE_USER);
		PostUserMethod api = new PostUserMethod();
		api.expectResponseStatus(HttpResponseStatusType.CREATED_201);
		api.callAPI();
		api.validateResponse();
		Timer.stop(PO.CREATE_USER);
	}

	@Test
	public void testCreateUserMissingSomeFields()
	{
		PostUserMethod api = new PostUserMethod();
		api.getProperties().remove("name");
		api.getProperties().remove("username");
		api.expectResponseStatus(HttpResponseStatusType.CREATED_201);
		api.callAPI();
		api.validateResponse();
	}

	@Test
	public void testGetUsers()
	{
		Timer.start(PO.GET_USER);
		GetUserMethods getUsersMethods = new GetUserMethods();
		getUsersMethods.expectResponseStatus(HttpResponseStatusType.OK_200);
		getUsersMethods.callAPI();
		getUsersMethods.validateResponse(JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
		getUsersMethods.validateResponseAgainstJSONSchema("api/users/_get/rs.schema");
		Timer.stop(PO.GET_USER);
	}

	@Test
	public void testDeleteUsers()
	{
		Timer.start(PO.DELETE_USER);
		DeleteUserMethod deleteUserMethod = new DeleteUserMethod();
		deleteUserMethod.expectResponseStatus(HttpResponseStatusType.OK_200);
		deleteUserMethod.callAPI();
		deleteUserMethod.validateResponse();
		Timer.stop(PO.DELETE_USER);
	}
}