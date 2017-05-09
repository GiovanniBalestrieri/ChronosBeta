package box.chronos.userk.chronos.utils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by francesco on 17/03/2017.
 */

public class AppConstant {

    // Api methods
    public static final String LOGIN_METHOD = "userLogin";
    public static final String UPDATE_PROFILE_METHOD = "updateProfile";
    public static final String SIGNUP_METHOD = "userSignUp";
    public static final String CODE_VALIDATION_METHOD = "validateCode";
    public static final String CODE_SKIP_METHOD = "skipCode";
    public static final String FORGOT_PSS_METHOD = "forgotPassword";
    public static final String GET_CAT_METHOD = "getAllCategories";
    public static final String GET_OFFERS_METHOD = "getNotifications";
    public static final String GET_PROFILE_INFO_METHOD = "viwProfile";

    // Api responses
    public static final String DATA_RESP = "data";
    public static final String CODE_RESP = "code";
    public static final String ERROR_RESP = "error";
    public static final String MSG_RESP = "message";
    public static final String ZERO_RESP = "0";
    public static final String ONE_RESP = "1";

    // API fields
    public static final String METHOD_PARAM = "method";
    public static final String USERID_PARAM = "userid";
    public static final String USERNAME_PARAM = "username";
    public static final String BIRTHDAY_PARAM = "birthday";
    public static final String GENDER_PARAM = "gender";
    public static final String PHOTO_PARAM = "photo";
    public static final String BUSINESSNAME_PARAM = "businessname";
    public static final String PHONE_PARAM = "phonenumber";
    public static final String ADDRESS_PARAM = "address";
    public static final String SESSION_KEY_PARAM = "sessionkey";
    public static final String UID_PARAM = "uid";
    public static final String CODE_PARAM = "code";
    public static final String PSS_PARAM = "password";
    public static final String EMAIL_PARAM = "email";
    public static final String DEV_TYPE_PARAM = "devicetype";
    public static final String MAX_OFF_DIST_PARAM = "maxofferdistance";
    public static final String MAX_OFF_VIEW_PARAM = "maxofferview";
    public static final String REPEAT_OFF_PARAM = "repeatoffer";
    public static final String USER_TYPE_PARAM = "usertype";
    public static final String SEL_CAT_PARAM = "selctedcategory";










    public static final String DEV_TOKEN_PARAM = "devicetoken";
    public static final String LAT_PARAM = "latitude";
    public static final String LON_PARAM = "longitude";
    public static final String SUCCESS_PARAM = "success";
    public static final String ERROR_PARAM = "error";
    public static final String MSG_PARAM = "message";

    // Categories
    public static final String CAT_ID = "categoryid";
    public static final String CAT_NAME = "category";
    public static final String CAT_PHOTO_DEF = "categoryphoto";
    public static final String CAT_PHOTO_ACTIVE = "photoactive";
    public static final String CAT_SELECTED = "selected";


    // Utilities

    public static final String EUR_SIGN = " €";
    public static final String PERC_SIGN = " %";
    public static final String ALL_CATS = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16";
    public static final String ONE_KM = "1000";
    public static final String MORE_THAN_ONE_KM = "1+ km";
    public static final String METERS = " m";


    // Number of columns of Grid View
    public static final int NUM_OF_COLUMNS = 3;

    // Gridview image padding
    public static final int GRID_PADDING = 8; // in dp

    // SD card image directory
    public static final boolean DEBUG = true;
    public static final String PACKAGE_NAME = "com.chronos";
    public static final String TESTING_PACKAGE_NAME = "com.developer.con5ort";

    // supported file formats
    public static final List<String> FILE_EXTN = Arrays.asList("jpg", "jpeg", "png", "mp4");

    public static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static String MESSAGE_KEY = "message";
    /* project id register with jaswinder.techwin@gmail.com  on firebase*/

    //public static final String GOOGLE_PROJ_ID = "1007413403673";
    public static final String GOOGLE_PROJ_ID = "526978631385";
    public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static String GCM_ID;
    public static String DEVICE_TYPE = "2";

    public static final String DISTANCE = "Distance";
    public static final String NOTIFICATION = "Notification";
    public static final String SWITCH = "Switch";
    public static final String ON = "1";
    public static final String OFF = "2";
    public static final String FACEBOOK = "1";
    public static final String GOOGLE_PLUS = "2";
    public static final String PHOTO_ALBUM = "Chronos";
    public static final String PHOTO_VIDEO = "Chronos";
    public static final String ENGLISH = "English";
    public static final String FRENCH = "French";
    public static final String ITALIAN = "Italian";
    public static final String EURO = "\u20ac";


    public static final String MALE_STRING = "male";
    public static final String FEMALE_STRING = "female";
    public static final String MALE_STRING_IT = "uomo";
    public static final String FEMALE_STRING_IT = "donna";



}