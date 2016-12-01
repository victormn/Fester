package br.usp.fester.fester;

import android.content.Context;
import android.util.Log;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.regions.Regions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adriano on 15/11/16.
 */

public class AmazonAppHelper
{
	private static AmazonAppHelper appHelper;

	private static CognitoUserPool userPool;
	private static String user;
	private static CognitoDevice thisDevice;

	// Creates the maps for attributes and their conversions
	private static List<String> attributeDisplaySeq;
	private static Map<String, String> signUpFieldsC2O;
	private static Map<String, String> signUpFieldsO2C;

	// Amazon Cognito (Identity) Data
	private static final String userPoolId = "us-east-1_SJ9F9R0WH";
	private static final String clientId = "1etpfjplugrfkqv8i0tc5qrabj";
	private static final String clientSecret = "61d35u427e8fp0qb18o25msb3ftkl23mlq86jtqkrbk1mcdqfvr";
	private static final Regions cognitoRegion = Regions.US_EAST_1;

	// User details from the service (like login)
	private static CognitoUserSession currentSession;
	private static CognitoUserDetails userDetails;

	public static CognitoUserPool getUserPool()
	{
		return userPool;
	}

	public static String getUser()
	{
		return user;
	}

	public static CognitoDevice getThisDevice()
	{
		return thisDevice;
	}

	public static void initialize(Context context)
	{
		// First, we initialize our maps
		setAttributeMappingData();

		// Then we check if we haven't initialized yet
		if (appHelper != null && userPool != null) return;

		// Else we instantiate our app helper
		if (appHelper == null)
		{
			appHelper = new AmazonAppHelper();
		}

		// Then we instantiate the user pool
		if (userPool == null)
		{
			userPool = new CognitoUserPool(context, userPoolId, clientId, clientSecret, cognitoRegion);
		}

		thisDevice = null;
	}


	private static void setAttributeMappingData()
	{
		// Set attribute display sequence
		attributeDisplaySeq = new ArrayList<String>();
		attributeDisplaySeq.add("given_name");
		attributeDisplaySeq.add("middle_name");
		attributeDisplaySeq.add("family_name");
		attributeDisplaySeq.add("nickname");
		attributeDisplaySeq.add("phone_number");
		attributeDisplaySeq.add("email");

		signUpFieldsC2O = new HashMap<String, String>();
		signUpFieldsC2O.put("Name", "name");
		signUpFieldsC2O.put("Given name", "given_name");
		signUpFieldsC2O.put("Family name", "family_name");
		signUpFieldsC2O.put("Nick name", "nickname");
		signUpFieldsC2O.put("Phone number", "phone_number");
		signUpFieldsC2O.put("Phone number verified", "phone_number_verified");
		signUpFieldsC2O.put("Email verified", "email_verified");
		signUpFieldsC2O.put("Email","email");
		signUpFieldsC2O.put("Middle name","middle_name");

		signUpFieldsO2C = new HashMap<String, String>();
		signUpFieldsO2C.put("name", "Name");
		signUpFieldsO2C.put("given_name", "Given name");
		signUpFieldsO2C.put("family_name", "Family name");
		signUpFieldsO2C.put("nickname", "Nick name");
		signUpFieldsO2C.put("phone_number", "Phone number");
		signUpFieldsO2C.put("phone_number_verified", "Phone number verified");
		signUpFieldsO2C.put("email_verified", "Email verified");
		signUpFieldsO2C.put("email", "Email");
		signUpFieldsO2C.put("middle_name", "Middle name");
	}

	public static String formatException(Exception exception) {
		String formattedString = "Internal Error";
		Log.e("App Error",exception.toString());
		Log.getStackTraceString(exception);

		String temp = exception.getMessage();

		if(temp != null && temp.length() > 0) {
			formattedString = temp.split("\\(")[0];
			if(temp != null && temp.length() > 0) {
				return formattedString;
			}
		}

		return  formattedString;
	}

	public static Map<String, String> getSignUpFieldsC2O()
	{
		return signUpFieldsC2O;
	}

	public static Map<String, String> getSignUpFieldsO2C()
	{
		return signUpFieldsO2C;
	}

	public static void setThisDevice(CognitoDevice thisDevice)
	{
		AmazonAppHelper.thisDevice = thisDevice;
	}

	public static void setCurrentSession(CognitoUserSession currentSession)
	{
		AmazonAppHelper.currentSession = currentSession;
	}

	public static void setUserDetails(CognitoUserDetails userDetails)
	{
		AmazonAppHelper.userDetails = userDetails;
	}

	public static void setUser(String user)
	{
		AmazonAppHelper.user = user;
	}

	public static String getUsername() { return user; }
}
