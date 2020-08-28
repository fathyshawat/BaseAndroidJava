package com.example.javabase.MyBase.Uitls;

public class Constant {

    public static final String Bearer = "Bearer ";
    public static final String DEVICE_TYPE = "android";
    public static final int PHOTO_GALLERY = 777;

    public static class UserActiveMode {

        public static String DEACTIVE = "deactive";

        public static String ACTIVE = "active";
    }

    public static class RequestCode {

        public static final int GPS_ENABLING = 300;

        public static final int GET_LOCATION = 500;

    }

    public static final class RequestPermission {

        final public static int REQUEST_GPS_LOCATION = 800;

        final public static int REQUEST_IMAGES = 400;

        final public static int REQUEST_CALL = 300;
    }

    public static class LocationConstant {

        public static String LAT = "lat";

        public static String LNG = "lng";

        public static String LOCATION = "location";
    }

    public static final class InfinitScroll {

        public static final int ITEM = 0;

        public static final int LOADING = 1;

        public static final int SELF_TEXT = 100;

        public static final int OTHER_TEXT = 101;

        public static final int SELF_IMAGE = 103;

        public static final int OTHER_IMAGE = 104;
    }


    public static class ResultData {

        public static final String CHOOSE_COUNTRY = "choose_country";

        public static final String CHOOSE_CITY = "choose_city";

        public static final String CHOOSE_CATEGORIES = "choose_categories";

        public static final String CHOOSE_CATEGORIES_ID = "choose_categories_id";
    }

    public static final class NotificationType {

        public static final int ChatSound = 1;

        public static final int NotificationSound = 0;
    }

    public static final class BundleData {

        public static final String FORGET_PASS_MODEL = "forget_pass";

        public static final String FOOD_MODEL = "food_model";

        public static final String CATEGORY_ID = "category_id";

        public static final String FAMILY_MODEL = "family_model";

        public static final String FAMILY_ID = "familey_id";

        public static String ORDER = "order_details";


    }


    public static class SharedPrefKey {

        public final static String SHARED_PREF_NAME = "makkah_shared_pref";

        public final static String LOGIN_STATUS = "makkah_login_status";

        public final static String USER = "makkah_user_data";

        public final static String USERLAT = "makkah_user_lat";

        public final static String USERLNG = "makkah_user_lng";

        public final static String NOTIFICATION = "makkah_Status";

        public final static String DEVICE_ID = "makkah_Device_Id";

        public final static String CategoryData = "makkah_category";

        public static final String SHARED_PREF_FIREBASE = "ask_makkah_firebase";
    }


    public static class SearchKeys {

        public final static int categories = 1;

        public final static int city = 2;

        public final static int price = 3;


    }
}
