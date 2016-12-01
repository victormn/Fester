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

import backend.model.PartyInputDate;

public class PartyInput {
    @com.google.gson.annotations.SerializedName("name")
    private String name = null;
    @com.google.gson.annotations.SerializedName("description")
    private String description = null;
    @com.google.gson.annotations.SerializedName("address")
    private String address = null;
    @com.google.gson.annotations.SerializedName("date")
    private PartyInputDate date = null;
    @com.google.gson.annotations.SerializedName("owner")
    private String owner = null;

    /**
     * Gets name
     *
     * @return name
     **/
    public String getName() {
        return name;
    }

    /**
     * Sets the value of name.
     *
     * @param name the new value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets description
     *
     * @return description
     **/
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of description.
     *
     * @param description the new value
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets address
     *
     * @return address
     **/
    public String getAddress() {
        return address;
    }

    /**
     * Sets the value of address.
     *
     * @param address the new value
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets date
     *
     * @return date
     **/
    public PartyInputDate getDate() {
        return date;
    }

    /**
     * Sets the value of date.
     *
     * @param date the new value
     */
    public void setDate(PartyInputDate date) {
        this.date = date;
    }

    /**
     * Gets owner
     *
     * @return owner
     **/
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the value of owner.
     *
     * @param owner the new value
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

}
