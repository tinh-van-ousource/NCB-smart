package com.tvo.common;

public class AppConstant {
    public static final String LOGIN_FAILURE_CODE = "401";
    public static final String ACCOUNT_DEACTIVATED_CODE = "402";
    public static final String ACCESS_DENIED_CODE = "403";

    public static final String ACCOUNT_IS_ALREADY_EXISTS_CODE = "404";
    public static final String ACCOUNT_IS_ALREADY_EXISTS_MESSAGE = "Account is already exists.";

    public static final String FILE_NOT_FOUND_CODE = "404";
    public static final String FILE_NOT_FOUND_MESSAGE = "File not found.";

    public static final String OLD_PASSWORD_IS_INCORRECT_CODE = "405";
    public static final String OLD_PASSWORD_IS_INCORRECT_MESSAGE = "Old password is incorrect.";

    public static final String PAR_CARD_EXISTED_CODE = "901";
    public static final String PAR_CARD_EXISTED_MESSAGE = "Par card existed.";

    public static final String UPLOAD_FILE_FAILED_CODE = "902";
    public static final String UPLOAD_FILE_FAILED_MESSAGE = "Upload file failed.";

    public static final String PARAM_MANAGER_EXISTED_CODE = "903";
    public static final String PARAM_MANAGER_EXISTED_MESSAGE = "Param is existed.";

    public static final String BANK_TRANSFER_EXISTED_CODE = "904";
    public static final String BANK_TRANSFER_EXISTED_MESSAGE = "Bank transfer is existed.";

    public static final String BRANCH_EXISTED_CODE = "905";
    public static final String BRANCH_EXISTED_MESSAGE = "Branch is existed.";

    public static final String PROVIDER_EXISTED_CODE = "906";
    public static final String PROVIDER_EXISTED_MESSAGE = "Provider is existed.";

    public static final String COMPANY_EXISTED_CODE = "907";
    public static final String COMPANY_EXISTED_MESSAGE = "Company is existed.";

    public static final String BANNER_EXISTED_CODE = "908";
    public static final String BANNER_EXISTED_MESSAGE = "Banner is existed.";

    public static final String ACCESS_DENIED_STATUS = "access denied";
    public static final String LOGIN_SUCCESSFUL_MESSAGE = "Login successful";

    public static final String SYSTEM_SUCCESS_CODE = "00";
    public static final String SYSTEM_SUCCESS_MESSAGE = "Successful";

    public static final String SYSTEM_ERROR_CODE = "99";
    public static final String SYSTEM_ERROR_MESSAGE = "System error";
    
    public static final String SYSTEM_ERROR_CREATE_FUNCTION_CODE = "914";
    public static final String SYSTEM_ERROR_CREATE_FUNCTION_MESSAGE = "Create duplicate error";
    
    public static final String CITY_CREATE_DUPLICATE_ERROR_CODE = "909";
    public static final String CITY_CREATE_DUPLICATE_ERROR_MESSAGE = "Value is duplicate";

    // Custom User exception
    public static final String USER_NOT_EXITS_CODE = "1001";
    // QRCoupons
    public static final String NOT_APPROVE = "Can not approve";
    public static final String APPROVE_SUCCESS = "Approve Successful";

    // Custom Deleted Successfully
    public static final String DELETED_SUCCESS_MESSAGE = "Deleted";

    public static final String SECRET =	 "Unknown";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME_MS = 1000 * 60 * 30; // 30 minutes

    public static final int LIMIT_PAGE = 15;

    public static final String RESOURCE_IMG = "C:\\uploads\\";
    public static final String RESOURCE_BANNER_IMG = "C:\\uploads\\banner\\";

    public static final String fordelWorkingFTP = "CMSBanner";
    public static final String passFTP ="123@123aA";
    public static final String userFTP = "cmsuat";
    public static final String hostFTP="10.1.62.33";

    public enum Status {
        ACTIVATED("A"), DEACTIVATED("D");

        private String value;

        Status(String index) {
            this.value = index;
        }

        public String getValue() {
            return this.value;
        }
    }

    public static Integer getOffset(Integer page, Integer size) {
        return (page - 1) * size;
    }
}
