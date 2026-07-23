package com.java.logic;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.java.dao.MySQLDBToolKit;
import com.java.exception.CodecowException;
import com.java.model.CodecowInfo;
import util.SmsCodeUtil;

public class CWInfoLogic {
    private static final String TABLE_NAME = "codecowinfo";

    // 新增账号
	public static boolean AddAccount(CodecowInfo codecowInfo) {
		String sql = "insert into " + TABLE_NAME + "(CWID, PassW, Name, job, Money, Phone) values(?,?,?,?,?,?)";
		Object[] params = new Object[6];
		params[0] = codecowInfo.getCWID();
		params[1] = codecowInfo.getPassW();
		params[2] = codecowInfo.getName();
		params[3] = codecowInfo.getJob();
		params[4] = codecowInfo.getMoney();
		params[5] = codecowInfo.getPhone();
		int n = MySQLDBToolKit.executeUpdate(sql, params);
		return n == 1;
	}
    
    // 登录校验
    public CodecowInfo CWInfobyIDandPass(String CWID, String passW) throws CodecowException {
        if (CWID == null || CWID.length() != 8 || !CWID.matches("[0-9]+")) {
            throw new CodecowException("账号格式错误！必须是8位纯数字");
        }
        if (passW == null || passW.trim().isEmpty()) {
            throw new CodecowException("密码不能为空！");
        }
        
        try {
            String sql = "SELECT CWID, PassW, Name, job, Money, Phone FROM " + TABLE_NAME + " WHERE CWID=? AND PassW=?";
            Object[] params = {CWID, passW};
            List<Map<String, Object>> list = MySQLDBToolKit.executeQuery(sql, params);
            
            if (list == null || list.isEmpty()) {
                return null;
            }
            
            Map<String, Object> map = list.get(0);
            CodecowInfo codecowInfo = new CodecowInfo();
            codecowInfo.setCWID((String) map.get("CWID"));
            codecowInfo.setPassW((String) map.get("PassW"));
            codecowInfo.setName((String) map.get("Name"));
            codecowInfo.setJob((String) map.get("job"));
            codecowInfo.setMoney((String) map.get("Money"));
            codecowInfo.setPhone((String) map.get("Phone"));
            
            return codecowInfo;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new CodecowException("数据库查询失败：" + e.getMessage());
        }
    }
    
    // 按工号查询信息
    public static CodecowInfo SelectinfobyID(String CWID) {
		String sql = "select CWID, PassW, Name, job, Money, Phone from " + TABLE_NAME + " where CWID=?";
		Object[] params = new Object[1];
		params[0] = CWID;
		
		List list = MySQLDBToolKit.executeQuery(sql, params);
		Iterator<Object> listIt = list.iterator();
		CodecowInfo codecowinfo = new CodecowInfo();
		if (listIt.hasNext()) {
			Map<String,Object> map = (Map<String,Object>) listIt.next();
			codecowinfo.setCWID(CWID);
			codecowinfo.setPassW((String) map.get("PassW"));
			codecowinfo.setName((String) map.get("Name"));
			codecowinfo.setJob((String) map.get("job"));
			codecowinfo.setMoney((String) map.get("Money"));
			codecowinfo.setPhone((String) map.get("Phone"));
		    return codecowinfo;
		}
		return null;
	}

    // 更新账号信息
    public static boolean UpdateAccount(CodecowInfo codecowinfo) {
		String sql = "Update " + TABLE_NAME + " set Name=?, job=?, Money=? where CWID=? ";
		Object[] params = new Object[4];
		params[0] = codecowinfo.getName();
		params[1] = codecowinfo.getJob();
		params[2] = codecowinfo.getMoney();
		params[3] = codecowinfo.getCWID();
		
	    int n = MySQLDBToolKit.executeUpdate(sql, params);
	    return n == 1;
	}

    // ========== 短信修改密码相关方法 ==========
    // 根据工号查询手机号
    public static String getPhoneByCWID(String CWID) {
        String sql = "SELECT Phone FROM " + TABLE_NAME + " WHERE CWID=?";
        Object[] params = {CWID};
        List<Map<String, Object>> list = MySQLDBToolKit.executeQuery(sql, params);
        if (list != null && !list.isEmpty()) {
            return (String) list.get(0).get("Phone");
        }
        return null;
    }

    // 校验手机号是否绑定该账号
    public static boolean checkPhoneByCWID(String CWID, String phone) {
        String sql = "SELECT CWID FROM " + TABLE_NAME + " WHERE CWID=? AND Phone=?";
        Object[] params = {CWID, phone};
        List<Map<String, Object>> list = MySQLDBToolKit.executeQuery(sql, params);
        return list != null && !list.isEmpty();
    }

    // 发送验证码
    public static String sendSmsCode(String phone) {
        return SmsCodeUtil.sendSmsCode(phone);
    }

    // 校验验证码
    public static boolean verifySmsCode(String phone, String code) {
        return SmsCodeUtil.verifySmsCode(phone, code);
    }

    // 修改密码
    public static boolean updatePassword(String CWID, String newPassword) {
        String sql = "UPDATE " + TABLE_NAME + " SET PassW=? WHERE CWID=?";
        Object[] params = {newPassword, CWID};
        int n = MySQLDBToolKit.executeUpdate(sql, params);
        return n == 1;
    }
}