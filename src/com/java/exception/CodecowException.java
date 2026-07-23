package com.java.exception;


public class CodecowException extends Exception{
	String message;
	public CodecowException(String CWID) {
		if (CWID.length()!=8) {
			message="账号必须是8位且只能由数字组成！ ";
		}
		else if (!CWID.matches("[0-9]+ ")){
			message="账号必须是8位且只能由数字组成！";
		}
		else {
            message="账号格式验证失败！";
        }
	}
	public String toString() {
        return message;
    }
}