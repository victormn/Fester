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


public class PartyInputDate {
    @com.google.gson.annotations.SerializedName("day")
    private Integer day = null;
    @com.google.gson.annotations.SerializedName("month")
    private Integer month = null;
    @com.google.gson.annotations.SerializedName("year")
    private Integer year = null;
    @com.google.gson.annotations.SerializedName("hour")
    private Integer hour = null;
    @com.google.gson.annotations.SerializedName("minute")
    private Integer minute = null;

    /**
     * Gets day
     *
     * @return day
     **/
    public Integer getDay() {
        return day;
    }

    /**
     * Sets the value of day.
     *
     * @param day the new value
     */
    public void setDay(Integer day) {
        this.day = day;
    }

    /**
     * Gets month
     *
     * @return month
     **/
    public Integer getMonth() {
        return month;
    }

    /**
     * Sets the value of month.
     *
     * @param month the new value
     */
    public void setMonth(Integer month) {
        this.month = month;
    }

    /**
     * Gets year
     *
     * @return year
     **/
    public Integer getYear() {
        return year;
    }

    /**
     * Sets the value of year.
     *
     * @param year the new value
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * Gets hour
     *
     * @return hour
     **/
    public Integer getHour() {
        return hour;
    }

    /**
     * Sets the value of hour.
     *
     * @param hour the new value
     */
    public void setHour(Integer hour) {
        this.hour = hour;
    }

    /**
     * Gets minute
     *
     * @return minute
     **/
    public Integer getMinute() {
        return minute;
    }

    /**
     * Sets the value of minute.
     *
     * @param minute the new value
     */
    public void setMinute(Integer minute) {
        this.minute = minute;
    }

}
