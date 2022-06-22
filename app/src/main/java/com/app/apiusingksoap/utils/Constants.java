package com.app.apiusingksoap.utils;/*
 * Created by Sandeep(Techno Learning) on 20,June,2022
 */

public class Constants {
    public static final String API_BASE_URL = "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso?WSDL";
    public static final String METHOD_NAME = "CapitalCity";
    public static final String NAMESPACE = "http://www.oorsprong.org/websamples.countryinfo"; // In front of method name
    public static final String SOAP_ACTION = NAMESPACE +"/"+ METHOD_NAME ;
}
