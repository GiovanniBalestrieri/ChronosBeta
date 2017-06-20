package box.chronos.userk.brain.utils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by francesco on 17/03/2017.
 */

public class AppConstant {

    // Api methods
    public static final String LOGIN_METHOD = "userLogin";
    public static final String LOGOUT_METHOD = "userLogOut";
    public static final String UPDATE_PROFILE_METHOD = "updateProfile";
    public static final String SIGNUP_METHOD = "userSignUp";
    public static final String CODE_VALIDATION_METHOD = "validateCode";
    public static final String CODE_SKIP_METHOD = "skipCode";
    public static final String FORGOT_PSS_METHOD = "forgotPassword";
    public static final String GET_CAT_METHOD = "getAllCategories";
    public static final String GET_CAT_METHOD_ANON = "getAllCategoriesAnon";
    public static final String GET_OFFERS_METHOD = "getNotifications";
    public static final String GET_OFFERS_METHOD_ANON = "getNotificationsAnon";
    public static final String GET_ARTICLES_METHOD = "getArticles";
    public static final String GET_ARTICLES_METHOD_ANON = "getArticlesAnon";
    public static final String GET_PROFILE_INFO_METHOD = "viwProfile";
    public static final String SPENT_MORE_THAN_TEN_METHOD = "spentTimeMoreThanTenSecOnOffer";
    public static final String OFFER_CLICKED_METHOD = "offerClicked";



    // Api responses
    public static final String DATA_RESP = "data";
    public static final String CODE_RESP = "code";
    public static final String ERROR_RESP = "error";
    public static final String MSG_RESP = "message";
    public static final String ZERO_RESP = "0";
    public static final String ONE_RESP = "1";

    // Anonnymous messages

    public static final String ANONYMOUS = "Anon User";
    public static final String EMPTY_STRING = "";

    public static final Boolean SHOW_PROFILE_USER = false;

    // API fields
    public static final String METHOD_PARAM = "method";
    public static final String USERID_PARAM = "userid";
    public static final String USERNAME_PARAM = "username";
    public static final String NAME_PARAM = "name";
    public static final String BIRTHDAY_PARAM = "birthday";
    public static final String GENDER_PARAM = "gender";
    public static final String PHOTO_PARAM = "photo";
    public static final String PICTURE_FB_PARAM = "picture";
    public static final String DISTANCE_PARAM = "distance";
    public static final String BUSINESSNAME_PARAM = "businessname";
    public static final String TIMER_PARAM = "timer";
    public static final String DISCOUNT_PARAM = "discount";
    public static final String PRICE_PARAM = "price";
    public static final String OFF_DESC_PARAM = "offerdescription";
    public static final String BUSINESS_PHONE_PARAM = "businessphone";
    public static final String BUSINESS_ADD_PARAM = "businessaddress";
    public static final String PHONE_PARAM = "phonenumber";
    public static final String ADDRESS_PARAM = "address";
    public static final String SESSION_KEY_PARAM = "sessionkey";
    public static final String OFF_PIC_PARAM = "offerPictures";
    public static final String OFF_NAME_PARAM = "offername";
    public static final String OFF_PIC_PATH_PARAM = "offerPicture";
    public static final String OFF_PIC_ID_PARAM = "offerpicId";
    public static final String WORLD_PARAM = "world";
    public static final String PAGE_PARAM = "page";
    public static final String TEN_KM_BOUND = "1";
    public static final String SIX_KM_BOUND = "2";
    public static final String UID_PARAM = "uid";
    public static final String ID_FB_PARAM = "id";
    public static final String CODE_PARAM = "code";
    public static final String PSS_PARAM = "password";
    public static final String EMAIL_PARAM = "email";
    public static final String PASS_PARAM = "password";
    public static final String OPTION_PARAM = "option";
    public static final String DEV_TYPE_PARAM = "devicetype";
    public static final String CAT_ID_PARAM = "categoryid";
    public static final String TOT_PAGES_PARAM = "totalPages";

    public static final String MAX_OFF_DIST_PARAM = "maxofferdistance";
    public static final String MAX_OFF_VIEW_PARAM = "maxofferview";
    public static final String REPEAT_OFF_PARAM = "repeatoffer";
    public static final String USER_TYPE_PARAM = "usertype";
    public static final String SEL_CAT_PARAM = "selctedcategory";
    public static final String OFF_ID_PARAM = "offerid";
    public static final String URL_PARAM = "url";



/* Statis coordinates */

    public static final String ROME_COORD_LAT = "41.886395";
    public static final String ROME_COORD_LON = "12.516753";



    public static final String APERTO_SHOP = "APERTO";
    public static final String CHIUSO_SHOP = "CHIUSO";



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
    public static final String CAT_COUNT = "count";


    // Utilities
    public static final String PARENTESI_SX = " ( ";
    public static final String PARENTESI_DX = " )";
    public static final String EUR_SIGN = " €";
    public static final String PERC_SIGN = " %";
    public static final String ALL_CATS = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16";
    public static final String ONE_KM = "1";
    public static final String FIVE_KM = "5";
    public static final String TEN_KM = "10";
    public static final int ONE_KM_INT = 1000;
    public static final float ONE_KM_FLOAT = 1000.0f;
    public static final String MORE_THAN_ONE_KM = "1+ km";
    public static final String MORE_THAN_FIVE_KM = "5+ km";
    public static final String MORE_THAN_TEN_KM = "10+ km";
    public static final String METERS = " m";
    public static final String K_METERS = " km";
    public static final String STRING_45_MIN = "45 min";
    public static final String STRING_30_MIN = "30 min";
    public static final String STRING_15_MIN = "15 min";
    public static final String STRING_DUE = "10 min";

    // Number of columns of Grid View
    public static final int NUM_OF_COLUMNS = 3;

    // Gridview image padding
    public static final int GRID_PADDING = 8; // in dp
    public static final long TOAST_DURATION = 2000; // in dp

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

    // Utility Int
    public static int FOURTY_5_MIN = 45;
    public static int FIFTEEN_MIN = 15;
    public static int ZERO_MIN = 0;
    public static int DELAY_TEN_SEC = 10000;
    public static final int SHOP_START_HOUR = 8;
    public static final int SHOP_STOP_HOUR = 20;
}