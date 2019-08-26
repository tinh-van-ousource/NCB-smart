package com.tvo.common;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {
    /** Declare halfWidthEnglish */
    private static final String[] halfWidthEnglish = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

    /** Declare halfAlphabetNumberSymbol */
    private static final String[] halfAlphabetNumberSymbol = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "=", "+", ":", ";", "'", "\"", ",", "<", "/", "?", "`", "~", "." };

    public static boolean isMixALl(final String input) {
        if (StringUtils.isEmpty(input)) {
            return true;
        }
        Pattern p = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*+=])(?=\\S+$).{8,}$");
        Matcher m = p.matcher(input);

        return m.find();
    }

    /**
     * Input valid : Katakana/Numbers/Letters
     *
     * @param input
     *            String
     * @return boolean
     */
    public static boolean isFullSize(final String input) {
        if (StringUtils.isEmpty(input)) {
            return true;
        }
        Pattern p = Pattern.compile("^[ァ-ン,０-９,Ａ-ｚ]+$");
        Matcher m = p.matcher(input);

        return m.find();
    }

    /**
     * <p>説明 : Check if String is empty</p>
     *
     * @param s
     *            String
     * @return boolean
     */
    public static boolean isEmpty(final String s) {
        return s == null || s.trim().equals("");
    }

    /**
     *
     * <p>
     * 説明 : Check if String is not empty
     * </p>
     *
     * @param strs
     *            Array of String
     * @return boolean
     */
    public static boolean isNotEmpty(final String... strs) {
        if (strs != null) {
            for (String s : strs) {
                if (isEmpty(s)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Check HaftSizeAlphabet
     *
     * @param input
     *            String
     * @return boolean
     */
    public static boolean isHaftSizeAlphabet(final String input) {
        if (StringUtils.isEmpty(input)) {
            return true;
        }
        Pattern p = Pattern.compile("^[A-Z,a-z, ]+$");
        Matcher m = p.matcher(input);

        return m.find();
    }

    /**
     * チェック文字列が数値である
     *
     * @param input
     *            String
     * @return 数値は、trueの場合はnullまたは空白である
     */
    public static boolean isHaftsizeNumeric(final String input) {
        if (StringUtils.isEmpty(input)) {
            return true;
        }
        Pattern p = Pattern.compile("^[0-9]+$");
        Matcher m = p.matcher(input);
        return m.matches();
    }

    /**
     * チェック文字列が数値である
     *
     * @param input
     *            String
     * @return 数値は、trueの場合はnullまたは空白である
     */
    public static boolean isHaftsizeNumericWithSignAndDecimal(final String input) {
        if (StringUtils.isEmpty(input)) {
            return true;
        }
        Pattern p = Pattern.compile("^(-|)(\\d+\\.?\\d*|\\.\\d+)$");
        Matcher m = p.matcher(input);
        return m.matches();
    }

    /**
     * チェック文字列が数値である
     *
     * @param input
     *            String
     * @return 数値は、trueの場合はnullまたは空白である
     */
    public static boolean isHaftsizeNumericWithSign(final String input) {
        if (StringUtils.isEmpty(input)) {
            return true;
        }
        Pattern p = Pattern.compile("^(-|)[0-9]+$");
        Matcher m = p.matcher(input);
        return m.matches();
    }

    /**
     * Check string is haft size alphabet and is haft size number
     *
     * @param input
     *            String
     * @return true if string is haft size engligh
     */
    public static boolean isHalfSizeAlphabetAndNumber(final String input) {
        if (StringUtils.isEmpty(input)) {
            return true;
        }

        Pattern p = Pattern.compile("^[A-Z,a-z,0-9]+$");
        Matcher m = p.matcher(input);

        return m.find();
    }

    /**
     * Check HalfSizeAlphabetAndNumber
     *
     * @param val
     *            String
     * @return boolean
     */
    public static boolean isHalfSizeAlphabetAndNumber2(final String val) {
        if (StringUtils.isEmpty(val)) {
            return false;
        }
        char[] part = val.toCharArray();
        boolean isEnglish = false;
        for (char c : part) {
            isEnglish = false;
            for (String str : halfWidthEnglish) {
                if (str.equals(String.valueOf(c))) {
                    isEnglish = true;
                    break;
                }
            }
            if (!isEnglish) {
                return false;
            }
        }
        return true;
    }

    /**
     * check HalfAlphabet or Number or Symbol
     *
     * @param input
     *            String
     * @return boolean
     */
    public static boolean isHalfAlphabetNumberSymbol(final String input) {
        if (StringUtils.isEmpty(input)) {
            return false;
        }
        char[] part = input.toCharArray();
        boolean isHalfAlphabetNumberSymbol = false;
        for (char c : part) {
            isHalfAlphabetNumberSymbol = false;
            for (String str : halfAlphabetNumberSymbol) {
                if (str.equals(String.valueOf(c))) {
                    isHalfAlphabetNumberSymbol = true;
                    break;
                }
            }
            if (!isHalfAlphabetNumberSymbol) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check format of date string.
     *
     * @param dateString
     *            Date string value
     * @param formatString
     *            Format date
     * @return boolean true : string is valid date format false : string is invalid date format
     */
    public static boolean isDateFormat(final String dateString, final String formatString) {
        if (StringUtils.isEmpty(dateString)) {
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatString);
        try {
            LocalDate.parse(dateString, formatter);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    /**
     * Check format of date string.
     *
     * @param dateString
     *            Date string value
     * @param formatString
     *            Format date
     * @return boolean true : string is valid date format false : string is invalid date format
     */
    public static boolean isTimeFormat(final String dateString, final String formatString) {
        if (StringUtils.isEmpty(dateString)) {
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatString);
        try {
            LocalTime.parse(dateString, formatter);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    /**
     *
     * <p>
     * 説明 : valid exist date
     * </p>
     *
     * @author : minh.ls
     * @param dateString
     *            String date
     * @param formatString
     *            date parten
     * @return true if exist
     */
    public static boolean isDateExist(final String dateString, final String formatString) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatString);
            format.setLenient(false);
            format.parse(dateString);
        } catch (ParseException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    /**
     * Validate email address.
     *
     * @param email
     *            Eメール
     * @return Eメールチェック結果
     */
    public static boolean isValidEmailAddress(final String email) {
        if (StringUtils.isEmpty(email)) {
            return true;
        }
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\" +
                          ".[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * integer pattern
     */
    private static final Pattern INT_PATTERN = Pattern.compile("^\\d+$");

    /**
     *
     * <p>
     * 説明 : Check String is Integer
     * </p>
     *
     * @author : hung.nq
     * @since : 2018/03/14
     * @param str
     *            String
     * @return boolean
     */
    public static boolean isInteger(final String str) {
        return !(str == null || str.length() == 0) && INT_PATTERN.matcher(str).matches();
    }

    /**
     * FIXME Check char input is number
     *
     * @param c
     *            char
     * @return true: char is number. false: char not number
     */
    public static boolean isDigit(final char c) {
        int x = c;
        if ((x >= 48 && x <= 57) || x == 45) {
            return true;
        }
        return false;
    }

    /**
     * <p>
     * 説明 : FIXME Check giá nhập có phải integer không
     * </p>
     *
     * @author : truong.pq
     * @since : 2018/03/07
     * @param input
     *            value String
     * @return true : FIXME nếu giá trị nhập vào là số nguyên
     */
    public static boolean isIntegerNumber(final String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    /**
     *
     * <p>
     * 説明 : compareDates
     * </p>
     *
     * @author hung.pd
     * @since 2018/5/31
     * @since 2018/03/08
     * @param date1
     *            Start date
     * @param date2
     *            End date
     * @return True end >= start
     */
    public static boolean compareDates(final String date1, final String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate start = LocalDate.parse(date1, formatter);
        LocalDate stop = LocalDate.parse(date2, formatter);
        if (stop.isAfter(start) || stop.isEqual(start)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * <p>
     * 説明 : compareDates
     * </p>
     *
     * @author hung.pd
     * @since 2018/5/31
     * @since 2018/03/08
     * @param date1
     *            Start date
     * @param date2
     *            End date
     * @return True end >= start
     */
    public static boolean compareDates(final LocalDate date1, final LocalDate date2) {
        if (date2.isAfter(date1) || date1.isEqual(date2)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <p>
     * 説明 : FIXME Check giá trị nhập vào có phải numberic hay ko
     * </p>
     *
     * @since : 2018/03/20
     * @param input
     *            inputValue
     * @return FIXME True : giá trị rỗng hoặc hợp lệ. False : không hợp lệ
     */
    public static boolean isNumeric(final String input) {
        if (StringUtils.isEmpty(input)) {
            return true;
        }

        try {
            Double.parseDouble(input.replaceAll(",", ""));
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public static boolean checkIsPhoneNumber(final String strData) {
        boolean bRet = true;

        try {
            for (int i = 0; i < strData.length(); i++) {
                if (!((strData.charAt(i) >= '0' && strData.charAt(i) <= '9') ||
                      strData.charAt(i) == '-' ||
                      strData.charAt(i) == '+' ||
                      strData.charAt(i) == '#' ||
                      strData.charAt(i) == ' ')) {
                    bRet = false;
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("error is " + e.getMessage());
            bRet = false;
        }
        return bRet;
    }
}