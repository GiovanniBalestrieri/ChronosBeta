package box.chronos.userk.chronos.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.sql.Timestamp;
import java.util.Random;

/**
 * Created by userk on 14/12/16.
 */

public class UserSharedPreference {

    // server key ==> AIzaSyA6i8_l8atDhWHTt4lGq8eQkFZ-TLctPBI

    private static final String TAG = "UserSharedPreference";
    /***
     * ===========User Preference Declaration===========
     **/
    public static final String UserPreference = "UserDetails";
    private static final String UserLoginByKey = "userLoginBy";
    private static final String UserNameKey = "userName";


    private static final String code_status = "codeStatus";
    private static final String GcmId = "Gcm_Id";
    private static final String DeviceToken = "Device_Token";
    private static final String UserId = "userId";
    private static final String VerifyCode = "verifyCode";
    private static final String CountryName = "countryName";
    private static final String CountryCode = "countryCode";
    private static final String UserEmail = "userEmail";
    private static final String UserCity = "userCity";
    private static final String UserDisplayName = "userDispayName";
    private static final String UserImage = "userImage";
    private static final String UserProfile = "userProfile";
    private static final String UserMessageMe = "userMessageMe";
    private static final String FlagImage = "flagImage";
    private static final String UserJoin = "userJoin";
    private static final String UserSetting = "userSetting";
    private static final String UserLogged = "userLogged";
    private static final String UserIsActive = "isActive";
    private static final String GroupIsActive = "isGroupActive";
    private static final String NotificationSeen = "notificationSeen";
    private static final String FirstLogin = "firstLogin";
    private static final String UserType = "userType";
    private static final String IsGeolocation = "isGeolocation";
    private static final String IsPrivate = "isPrivate";
    private static final String IsPublic = "isPublic";
    private static final String IsAllow = "isAllow";
    private static final String IsExist = "isExist";
    private static final String IsPushNotification = "isPushNotification";
    private static final String IsSynchronizeIcal = "IsSynchronizeIcal";
    private static final String UserFirstTime="userFirstTime";
    private static final String UserPhoneNumber="userPhoneNumber";
    private static final String UserPassword="userPassword";
    private static final String UserName="userName";
    private static final String DisplayName="displayName";
    private static final String IS_FIRST_TIME_USER="isfirsttimeuser";

    private static final String Gender = "gender";
    private static final String MAX_OFFER_DISTANCE = "maxofferdistance";
    private static final String MAX_OFFER_VIEW = "maxofferview";
    private static final String REPEAT_OFFER = "repeatoffer";
    private static final String SESSION_KEY = "sessionkey";
    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";
    private static final String BIRTHDAY = "birthday";
    private static final String SELECTED_CATRGORY = "selctedcategory";

    private static final String BUSINESSNAME = "businessname";
    private static final String BUSINESSADDRESS = "businessaddress";
    private static final String BUSINESSPHONE = "businessphone";
    private static final String CATEGORYID = "categoryid";
    private static final String OFFERDESCRIPTION = "offerdescription";
    private static final String RECURRENT = "recurrent";
    private static final String TIMER = "timer";

    private static final String LANGUAGE = "language";
    private static final String DISTANCE = "distance";
    private static final String NOTIFICATION = "notification";
    private static final String SWITCH = "switchButton";

    private static final String OFFER_LONGITUDE = "offerlongitude";
    private static final String OFFER_LATITUDE = "offerlatitude";
    private static final String OFFER_PLACENAME = "offerplacename";

    private static final String DEFAULT_ADDRESS = "default_address";
    private static final String DEFAULT_LONGITUDE = "default_longitude";
    private static final String DEFAULT_LATITUDE = "default_latitude";




    private SharedPreferences userPreference;
    private SharedPreferences.Editor editor;

    public UserSharedPreference(Context current) {
        userPreference = current.getSharedPreferences(UserPreference,
                Context.MODE_PRIVATE);
        editor = userPreference.edit();
    }

    public void clearPrefrence() {
        editor.clear();
        editor.commit();
    }


    public void setBusinessname(String businessname) {
        editor.putString(BUSINESSNAME, businessname);
        editor.commit();
    }
    public String getBusinessname() {
        return userPreference.getString(BUSINESSNAME, "");
    }


    public void setBusinessaddress(String businessaddress) {
        editor.putString(BUSINESSADDRESS, businessaddress);
        editor.commit();
    }
    public String getBusinessaddress() {
        return userPreference.getString(BUSINESSADDRESS, "");
    }

    public void setBusinessphone(String businessphone) {
        editor.putString(BUSINESSPHONE, businessphone);
        editor.commit();
    }
    public String getBusinessphone() {
        return userPreference.getString(BUSINESSPHONE, "");
    }


    public void setCategoryid(String categoryid) {
        editor.putString(CATEGORYID, categoryid);
        editor.commit();
    }
    public String getCategoryid() {
        return userPreference.getString(CATEGORYID, "");
    }


    public void setOfferdescription(String offerdescription) {
        editor.putString(OFFERDESCRIPTION, offerdescription);
        editor.commit();
    }
    public String getOfferdescription() {
        return userPreference.getString(OFFERDESCRIPTION, "");
    }

    public void setRecurrent(String recurrent) {
        editor.putString(RECURRENT, recurrent);
        editor.commit();
    }
    public String getRecurrent() {
        return userPreference.getString(RECURRENT, "");
    }

    public void setTimer(String timer) {
        editor.putString(TIMER, timer);
        editor.commit();
    }
    public String getTimer() {
        return userPreference.getString(TIMER, "");
    }



    public void setLanguage(String language) {
        editor.putString(LANGUAGE, language);
        editor.commit();
    }
    public String getLanguage() {
        return userPreference.getString(LANGUAGE, "");
    }


    public void setDistance(String distance) {
        editor.putString(DISTANCE, distance);
        editor.commit();
    }
    public String getDistance() {
        return userPreference.getString(DISTANCE, "");
    }

    public void setNotification(String notification) {
        editor.putString(NOTIFICATION, notification);
        editor.commit();
    }
    public String getNotification() {
        return userPreference.getString(NOTIFICATION, "");
    }

    public void setSwitch(String switchButton) {
        editor.putString(SWITCH, switchButton);
        editor.commit();
    }
    public String getSwitch() {
        return userPreference.getString(SWITCH, "");
    }

    public void setIsLogged(Boolean isLogged) {
        editor.putBoolean(UserLogged, isLogged);
        editor.commit();
    }
    public Boolean getIsLogged() {
        return userPreference.getBoolean(UserLogged, false);
    }


    public void setIsActive(Boolean isActive) {
        editor.putBoolean(UserIsActive, isActive);
        editor.commit();
    }
    public Boolean getIsActive() {
        return userPreference.getBoolean(UserIsActive, false);
    }

    public void setIsGroupActive(Boolean isGroupActive) {
        editor.putBoolean(GroupIsActive, isGroupActive);
        editor.commit();
    }
    public Boolean getIsGroupActive() {
        return userPreference.getBoolean(GroupIsActive, false);
    }

    public void setNotificationSeen(Boolean notificationSeen) {
        editor.putBoolean(NotificationSeen, notificationSeen);
        editor.commit();
    }
    public Boolean getNotificationSeen() {
        return userPreference.getBoolean(NotificationSeen, false);
    }

    public void setIsFirstTimeUser(Boolean isTrue) {
        editor.putBoolean(IS_FIRST_TIME_USER, isTrue);
        editor.commit();
    }
    public Boolean getIsFirstTimeUser() {
        return userPreference.getBoolean(IS_FIRST_TIME_USER, false);
    }

    public void setUserPhoneNumber(String phoneNumber) {
        editor.putString(UserPhoneNumber, phoneNumber);
        editor.commit();
    }

    public String getUserPhoneNumber() {
        return userPreference.getString(UserPhoneNumber, "");
    }


    public void setRepeatOffer(String repeatoffer) {
        editor.putString(REPEAT_OFFER, repeatoffer);
        editor.commit();
    }
    public String getRepeatOffer() {
        return userPreference.getString(REPEAT_OFFER, "");
    }



    public void setBirthday(String birthday) {
        editor.putString(BIRTHDAY, birthday);
        editor.commit();
    }
    public String getBirthday() {
        return userPreference.getString(BIRTHDAY, "");
    }

    public void setSelectedCatrgory(String selectedCatrgory) {
        editor.putString(SELECTED_CATRGORY, selectedCatrgory);
        editor.commit();
    }
    public String getSelectedCatrgory() {
        return userPreference.getString(SELECTED_CATRGORY, "");
    }

    public void setLongitude(double longitude) {
        editor.putString(LONGITUDE, String.valueOf(longitude));
        editor.commit();
    }
    public String getLongitude() {
        return userPreference.getString(LONGITUDE, "");
    }

    public void setLatitude(double latitude) {
        editor.putString(LATITUDE, String.valueOf(latitude));
        editor.commit();
    }
    public String getLatitude() {
        return userPreference.getString(LATITUDE, "");
    }

    public void setOfferLatitude(double offerLatitude) {
        editor.putString(OFFER_LATITUDE, String.valueOf(offerLatitude));
        editor.commit();
    }

    public String getOfferLatitude() {
        return userPreference.getString(OFFER_LATITUDE, "");
    }

    public void setOfferLongitude(double offerLongitude) {
        editor.putString(OFFER_LONGITUDE, String.valueOf(offerLongitude));
        editor.commit();
    }
    public String getOfferLongitude() {
        return userPreference.getString(OFFER_LONGITUDE, "");
    }

    public void setDefaultLongitude(double default_longitude) {
        editor.putString(DEFAULT_LONGITUDE, String.valueOf(default_longitude));
        editor.commit();
    }
    public String getDefaultLongitude() {
        return userPreference.getString(DEFAULT_LONGITUDE, "");
    }

    public void setDefaultLatitude(double default_latitude) {
        editor.putString(DEFAULT_LATITUDE, String.valueOf(default_latitude));
        editor.commit();
    }
    public String getDefaultLatitude() {
        return userPreference.getString(DEFAULT_LATITUDE, "");
    }

    public void setOfferPlacename(String offerPlacename) {
        editor.putString(OFFER_PLACENAME, offerPlacename);
        editor.commit();
    }
    public String getOfferPlacename() {
        return userPreference.getString(OFFER_PLACENAME, "");
    }

    public void setDefaultAddress(String default_address) {
        editor.putString(DEFAULT_ADDRESS, default_address);
        editor.commit();
    }
    public String getDefaultAddress() {
        return userPreference.getString(DEFAULT_ADDRESS, "");
    }

    public void setSessionKey(String sessionKey) {
        editor.putString(SESSION_KEY, sessionKey);
        editor.commit();
    }
    public String getSessionKey() {
        return userPreference.getString(SESSION_KEY, "");
    }


    public void setMaxOfferView(String maxofferview) {
        editor.putString(MAX_OFFER_VIEW, maxofferview);
        editor.commit();
    }
    public String getMaxOfferView() {
        return userPreference.getString(MAX_OFFER_VIEW, "");
    }

    public void setMaxOfferDistance(String maxofferdistance) {
        editor.putString(MAX_OFFER_DISTANCE, maxofferdistance);
        editor.commit();
    }
    public String getMaxOfferDistance() {
        return userPreference.getString(MAX_OFFER_DISTANCE, "");
    }

    public void setGender(String gender) {
        editor.putString(Gender, gender);
        editor.commit();
    }
    public String getGender() {
        return userPreference.getString(Gender, "");
    }

    public void setUserPassword(String userPassword) {
        editor.putString(UserPassword, userPassword);
        editor.commit();
    }

    public String  getUserPassword() {
        return userPreference.getString(UserPassword, "");
    }

    public void hasDoneFirstLogin(boolean isTrue) {
        editor.putBoolean(FirstLogin, isTrue);
        editor.commit();
    }

    public boolean getHasAlreadySignedUp() {
        return userPreference.getBoolean(FirstLogin, false);
    }

    public void setCountryCode(String countryCode) {
        editor.putString(CountryCode, countryCode);
        editor.commit();
    }

    public String  getCountryCode() {
        return userPreference.getString(CountryCode, "");
    }


    public void setCountryName(String countryName) {
        editor.putString(CountryName, countryName);
        editor.commit();
    }

    public String  getCountryName() {
        return userPreference.getString(CountryName, "");
    }


    public void setIsFirst(Boolean isLogged) {
        editor.putBoolean(UserFirstTime, isLogged);
        editor.commit();
    }


    public Boolean getIsFirst() {
        return userPreference.getBoolean(UserFirstTime, true);
    }


    /* joining date of user*/
    public void setUserJoin(String join) {
        editor.putString(UserJoin, join);
        editor.commit();
    }
    public String getUserJoin() {
        return userPreference.getString(UserJoin, "");
    }

    public void setUserId(String userId) {
        editor.putString(UserId, userId);
        editor.commit();
    }
    public String getUserId() {
        return userPreference.getString(UserId, "");
    }


    public void setCodeStatus(String status) {
        editor.putString(code_status, status);
        editor.commit();
    }
    public String getCode_status() {
        return userPreference.getString(code_status, "");
    }

    public void setDeviceToken(String deviceToken) {
        editor.putString(DeviceToken, deviceToken);
        editor.commit();
    }
    public String getDeviceToken() {
        return userPreference.getString(DeviceToken, "");
    }

    public void setVerifyCode(String verifyCode) {
        editor.putString(VerifyCode, verifyCode);
        editor.commit();
    }
    public String getVerifyCode() {
        return userPreference.getString(VerifyCode, "");
    }

    public void setUserEmail(String email) {
        editor.putString(UserEmail, email);
        editor.commit();
    }

    public String getUserEmail() {
        return userPreference.getString(UserEmail, "");
    }

    public void setUserDispayName(String name) {
        editor.putString(UserDisplayName, name);
        editor.commit();
    }

    public String getUserImage() {
        return userPreference.getString(UserImage, "");
    }

    public void setUserImage(String userImage) {
        editor.putString(UserImage, userImage);
        editor.commit();
    }

    public String getUserProfile() {
        return userPreference.getString(UserProfile, "");
    }

    public void setUserProfile(String userProfile) {
        editor.putString(UserProfile, userProfile);
        editor.commit();
    }

    public String getUserMessageMe() {
        return userPreference.getString(UserMessageMe, "");
    }

    public void setUserMessageMe(String userMessageMe) {
        editor.putString(UserMessageMe, userMessageMe);
        editor.commit();
    }

    public String getFlagImage() {
        return userPreference.getString(FlagImage, "");
    }

    public void setFlagImage(String flagImage) {
        editor.putString(FlagImage, flagImage);
        editor.commit();
    }

    public String getUserDispayName() {
        return userPreference.getString(UserDisplayName, "");
    }

    public void setUserName(String userName) {
        editor.putString(UserNameKey, userName);
        editor.commit();
    }
    public String getUserName() {
        return userPreference.getString(UserNameKey, "");
    }


    public String getUserLoginBy() {
        return userPreference.getString(UserLoginByKey, "");
    }

    public void setUserLoginBy(String loginBy) {
        editor.putString(UserLoginByKey, loginBy);
        editor.commit();
    }


    public void setUserType(String userType) {
        editor.putString(UserType, userType);
        editor.commit();
    }
    public String getUserType() {
        return userPreference.getString(UserType, "");
    }




    /* date for setting section*/

    public void setIsSetting(Boolean isSetting) {
        editor.putBoolean(UserSetting, isSetting);
        editor.commit();
    }

    public void setIsSynchronizeIcal(Boolean isSynchronizeIcal) {
        editor.putBoolean(IsSynchronizeIcal, isSynchronizeIcal);
        editor.commit();
    }

    public void setIsPushNotification(Boolean isPushNotification) {
        editor.putBoolean(IsPushNotification, isPushNotification);
        editor.commit();
    }

    public void setIsGeolocation(Boolean isGeolocation) {
        editor.putBoolean(IsGeolocation, isGeolocation);
        editor.commit();
    }

    public void setIsPrivate(Boolean isPrivate) {
        editor.putBoolean(IsPrivate, isPrivate);
        editor.commit();
    }

    public void setIsPublic(Boolean isPublic) {
        editor.putBoolean(IsPublic, isPublic);
        editor.commit();
    }

    public void setIsAllow(Boolean isAllow) {
        editor.putBoolean(IsAllow, isAllow);
        editor.commit();
    }

    public void setIsExist(Boolean isExist) {
        editor.putBoolean(IsExist, isExist);
        editor.commit();
    }

    public Boolean getIsSynchronizeIcal() {
        return userPreference.getBoolean(IsSynchronizeIcal, false);
    }

    public Boolean getIsPushNotification() {
        return userPreference.getBoolean(IsPushNotification, false);
    }

    public Boolean getIsGeolocation() {
        return userPreference.getBoolean(IsGeolocation, false);
    }

    public Boolean getIsPrivate() {
        return userPreference.getBoolean(IsPrivate, false);
    }
    public Boolean getIsPublic() {
        return userPreference.getBoolean(IsPublic, false);
    }

    public Boolean getIsAllow() {
        return userPreference.getBoolean(IsAllow, false);
    }
    public Boolean getIsExist() {
        return userPreference.getBoolean(IsExist, false);
    }



    public Boolean getIsSetting() {
        return userPreference.getBoolean(UserSetting, true);
    }

    public void setGcmId(String gcmId) {
        editor.putString(GcmId, gcmId);
        editor.commit();
    }
    public String getGcmId() {
        return userPreference.getString(GcmId, "");
    }

    /***********************
     * Gcm
     *******************/
    public static final String REG_ID = "regId";
    private static final String APP_VERSION = "appVersion";
    public static final String PROPERTY_ON_SERVER_EXPIRATION_TIME = "onServerExpirationTimeMs";
    /**
     * Default lifespan (7 days) of a reservation until it is considered
     * expired.
     */
    public static final long REGISTRATION_EXPIRY_TIME_MS = 1000 * 3600 * 24 * 7;
    private static final int MAX_ATTEMPTS = 5;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final Random random = new Random();

    public static String getRegistrationId(Context context) {

        final SharedPreferences prefs = context.getSharedPreferences(
                "GCM_CREDENTIALS", Context.MODE_PRIVATE);
        String registrationId = prefs.getString(REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        int registeredVersion = prefs.getInt(APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion
                || isRegistrationExpired(context)) {

            Log.v(TAG, "App version changed or registration expired.");
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    public static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("RegisterActivity",
                    "I never expected this! Going down, going down!" + e);
            throw new RuntimeException(e);
        }
    }

	/*
     * Settings.Secure.ANDROID_ID returns the unique DeviceID Works for Android
	 * 2.2 and above
	 */

    // public static String getUniqueDeviceId(Context con) {
    //
    // String deviceId = Secure.getString(con.getContentResolver(),
    // Secure.ANDROID_ID);
    // return deviceId;
    //
    // }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private static SharedPreferences getGCMPreferences(Context context) {
        return context.getSharedPreferences("GCM_CREDENTIALS",
                Context.MODE_PRIVATE);
    }

    /**
     * Checks if the registration has expired.
     * <p/>
     * <p/>
     * To avoid the scenario where the device sends the registration to the
     * server but the server loses it, the app developer may choose to
     * re-register after REGISTRATION_EXPIRY_TIME_MS.
     *
     * @return true if the registration has expired.
     */
    public static boolean isRegistrationExpired(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        // checks if the information is not stale
        long expirationTime = prefs.getLong(PROPERTY_ON_SERVER_EXPIRATION_TIME,
                -1);
        return System.currentTimeMillis() > expirationTime;
    }

    public static void storeRegistrationId(Context context, String regId) {

        final SharedPreferences prefs = context.getSharedPreferences(
                "GCM_CREDENTIALS", Context.MODE_PRIVATE);
        UserSharedPreference userPref = new UserSharedPreference(context);
        userPref.setGcmId(regId);

        int appVersion = getAppVersion(context);
        Log.d("registerInBackground", "Saving regId on app version "
                + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REG_ID, regId);
        editor.putInt(APP_VERSION, appVersion);

        long expirationTime = System.currentTimeMillis()
                + REGISTRATION_EXPIRY_TIME_MS;

        Log.v(TAG, "Setting registration expiry time to "
                + new Timestamp(expirationTime));
        editor.putLong(PROPERTY_ON_SERVER_EXPIRATION_TIME, expirationTime);

        editor.commit();
    }
}

