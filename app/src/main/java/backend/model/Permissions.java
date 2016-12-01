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

package backend.model;


public class Permissions {
    @com.google.gson.annotations.SerializedName("administrator")
    private Boolean administrator = null;
    @com.google.gson.annotations.SerializedName("dj")
    private Boolean dj = null;
    @com.google.gson.annotations.SerializedName("agent")
    private Boolean agent = null;
    @com.google.gson.annotations.SerializedName("security")
    private Boolean security = null;

    /**
     * Gets administrator
     *
     * @return administrator
     **/
    public Boolean getAdministrator() {
        return administrator;
    }

    /**
     * Sets the value of administrator.
     *
     * @param administrator the new value
     */
    public void setAdministrator(Boolean administrator) {
        this.administrator = administrator;
    }

    /**
     * Gets dj
     *
     * @return dj
     **/
    public Boolean getDj() {
        return dj;
    }

    /**
     * Sets the value of dj.
     *
     * @param dj the new value
     */
    public void setDj(Boolean dj) {
        this.dj = dj;
    }

    /**
     * Gets agent
     *
     * @return agent
     **/
    public Boolean getAgent() {
        return agent;
    }

    /**
     * Sets the value of agent.
     *
     * @param agent the new value
     */
    public void setAgent(Boolean agent) {
        this.agent = agent;
    }

    /**
     * Gets security
     *
     * @return security
     **/
    public Boolean getSecurity() {
        return security;
    }

    /**
     * Sets the value of security.
     *
     * @param security the new value
     */
    public void setSecurity(Boolean security) {
        this.security = security;
    }

}
