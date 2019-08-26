/**
 *
 */
package com.tvo.common;

/**
 * @author Ace
 *
 */
public class AppConstant {
    public static final String LOGIN_FAILURE_CODE = "401";
    public static final String SUCCSESSFUL_CODE = "200";
	public static final String ACCOUNT_DEACTIVATED_CODE = "402";
    public static final String ACCESS_DENIED_CODE = "403";
    public static final String ACCESS_DENIED_STATUS = "access denied";
    public static final String LOGIN_FAILURE_STATUS = "login failure";
    public static final String LOGIN_SUCCSESSFUL_STATUS = "login successful";

    public static final String SECRET = "Unknow";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATIONTIME = 1000 * 60 * 60 * 24; // 1 day

    public static final String USER_STATUS_STRING_ACTIVE = "Activated";
    public static final String USER_STATUS_STRING_DEACTIVE = "Deactivated";

    public static final String SYSTEM_SUCCESS_CODE = "00";
    public static final String SYSTEM_SUCCESS_MESSAGE = "Successful";

    public static final String SYSTEM_ERROR_CODE = "99";
    public static final String SYSTEM_ERROR_MESSAGE = "System error";

    public static final int LIMIT_PAGE = 15;
    public static final int MAX_PAGE_ITEM_DISPLAY = 5;

    public static final String RESOURCE_IMG = "C:\\upload\\card-img\\";

    public enum Status {
        ACTIVE("A"), CLOSE("C");

        private String value;

        private Status(String index) {
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
