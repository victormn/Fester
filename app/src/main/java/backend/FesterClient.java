/*
 * Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package backend;

import java.util.*;

import backend.model.PartyResponse;
import backend.model.IdResponse;
import backend.model.PartyInput;
import backend.model.Permissions;


@com.amazonaws.mobileconnectors.apigateway.annotation.Service(endpoint = "https://1mnceccsw1.execute-api.us-east-1.amazonaws.com/prod")
public interface FesterClient {


    /**
     * A generic invoker to invoke any API Gateway endpoint.
     * @param request
     * @return ApiResponse
     */
    com.amazonaws.mobileconnectors.apigateway.ApiResponse execute(com.amazonaws.mobileconnectors.apigateway.ApiRequest request);
    
    /**
     * 
     * 
     * @param username 
     * @return PartyResponse
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/parties", method = "GET")
    PartyResponse partiesGet(
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "username", location = "query")
                    String username);
    
    /**
     * 
     * 
     * @param body 
     * @return IdResponse
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/parties", method = "POST")
    IdResponse partiesPost(
            PartyInput body);
    
    /**
     * 
     * 
     * @param username 
     * @param party 
     * @return Permissions
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/parties/permissions", method = "GET")
    Permissions partiesPermissionsGet(
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "username", location = "query")
                    String username,
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "party", location = "query")
                    String party);
    
}

