package com.szc.fast_express_system.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  正则表达式格式相�?
 * @author ly
 * @version v1.0 2013-5-1 09:30
 * 
 */
public class RegExpUtil {
	/**
	 * 判断是否为纯数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		return isNum.matches();
	}
	
	/**
	 * 判断座机电话
	 * @param str
	 * @return
	 */
    public static boolean isTelephone(String str) {
    	boolean flag = false;
        String phone = "0\\d{2,3}-\\d{7,8}";
        String phone2 = "0\\d{2,3}-\\d{7,8}-\\d{3,5}";
        flag = str.matches(phone) || str.matches(phone2);
        return flag;
    }

	/**
	 * 验证手机号码是否合法
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMobileNO(String str) {
		Pattern pattern = Pattern.compile("1[3,4,5,8]{1}\\d{9}");
		Matcher invalid = pattern.matcher(str);
		return invalid.matches();
	}

	/**
	 * 18位或�?15位身份证验证 18位的�?后一位可以是字母x
	 * 
	 * @param str
	 * @return
	 */
	public static boolean personIdValidation(String str) {
		boolean flag = false;
		String regx = "[0-9]{17}x";
		String regX = "[0-9]{17}X";
		String reg1 = "[0-9]{15}";
		String regex = "[0-9]{18}";
		flag = str.matches(regx) || str.matches(regX) || str.matches(reg1) || str.matches(regex);
		return flag;
	}
	
	 /**
	  * 验证邮箱格式
	  * 
	  * @param email
	  * @return
	  */
	 public static boolean emailValidation(String email) {
		 boolean flag = false;
		 String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		 flag = email.matches(regex);
		 return flag;
	 }
	 
	 /**
	  * 验证是否是全角字�?
	  * 
	  * @param email
	  * @return
	  */
	 public static boolean quanjiaoValidation(String hanzi) {
		 boolean flag = false;
		 String regex = "^[\u0391-\uFFE5]*$";
		 flag = hanzi.matches(regex);
		 return flag;
	 }
	 
	 /**
	  * 验证是否是汉�?
	  * 
	  * @param email
	  * @return
	  */
	 public static boolean hanziValidation(String hanzi) {
		 boolean flag = false;
		 String regex = "^[\u4e00-\u9fa5]*$";
		 flag = hanzi.matches(regex);
		 return flag;
	 }
	 
	 /**
	  * 验证是否是中文名�?
	  * 
	  * @param email
	  * @return
	  */
	 public static boolean xingmingValidation(String hanzi) {
		 boolean flag = false;
		 String regex = "^[\u4e00-\u9fa5·]*$";
		 flag = hanzi.matches(regex);
		 return flag;
	 }
	 
	 /**
	  * 验证是否是中文英文数�?
	  * 
	  * @param email
	  * @return
	  */
	 public static boolean ZYSValidation(String hanzi) {
		 boolean flag = false;
		 String regex = "^([a-zA-Z0-9]|[\u4E00-\u9FA5])*$";
		 flag = hanzi.matches(regex);
		 return flag;
	 }
	 
	 /**
	  * 验证是否是公司名�?
	  * 
	  * @param email
	  * @return
	  */
	 public static boolean company_nameValidation(String hanzi) {
		 boolean flag = false;
//		 String regex = "^([a-zA-Z0-9]|[-_()\\s]|[\u4E00-\u9FA5])*$";
		 String regex = "^([a-zA-Z0-9]|[()]|[\uFF08\uFF09]|[\u4E00-\u9FA5])*$";
		 flag = hanzi.matches(regex);
		 return flag;
	 }
	 
	 /**
	  * 验证是否是地�?
	  * 
	  * @param email
	  * @return
	  */
	 public static boolean addressValidation(String hanzi) {
		 boolean flag = false;
//		 String regex = "^([a-zA-Z0-9]|[-\\s]|[\u4E00-\u9FA5])*$";
		 String regex = "^([a-zA-Z0-9]|[-—�?�]|[\u002d\u2014\u2010]|[\u4E00-\u9FA5])*$";
		 flag = hanzi.matches(regex);
		 return flag;
	 }
	 
	 /**
	  * 验证是否是公司名�?
	  * 
	  * @param email
	  * @return
	  */
	 public static boolean company_sectionValidation(String hanzi) {
		 boolean flag = false;
//		 String regex = "^([a-zA-Z0-9]|[\\s]|[\u4E00-\u9FA5])*$";
		 String regex = "^([a-zA-Z0-9]|[\u4E00-\u9FA5])*$";
		 flag = hanzi.matches(regex);
		 return flag;
	 }
	 
}
