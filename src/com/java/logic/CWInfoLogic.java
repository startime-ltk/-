package com.java.logic;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.java.dao.MySQLDBToolKit;
import com.java.exception.CodecowException;
import com.java.model.CodecowInfo;

public class CWInfoLogic {
    // ✅ 已确认表名：codecowinfo（完全匹配你的数据库）
    private static final String TABLE_NAME = "codecowinfo";

	public static boolean AddAccount(CodecowInfo codecowInfo) {
        // 字段名完全匹配建表语句：CWID、PassW、Name、job(小写)、Money
		String sql = "insert into " + TABLE_NAME + "(CWID, PassW, Name, job, Money) values(?,?,?,?,?)";
		Object[] params = new Object[5];
		params[0] = codecowInfo.getCWID();
		params[1] = codecowInfo.getPassW();
		params[2] = codecowInfo.getName();
		params[3] = codecowInfo.getJob();
		params[4] = codecowInfo.getMoney();
		int n = MySQLDBToolKit.executeUpdate(sql, params);
		return n == 1;
	}
    
    public CodecowInfo CWInfobyIDandPass(String CWID, String passW) throws CodecowException {
        if (CWID == null || CWID.length() != 8 || !CWID.matches("[0-9]+")) {
            throw new CodecowException("账号格式错误！必须是8位纯数字");
        }
        if (passW == null || passW.trim().isEmpty()) {
            throw new CodecowException("密码不能为空！");
        }
        
        try {
            // 表名+字段名完全匹配
            String sql = "SELECT CWID, PassW, Name, job, Money FROM " + TABLE_NAME + " WHERE CWID=? AND PassW=?";
            Object[] params = {CWID, passW};
            List<Map<String, Object>> list = MySQLDBToolKit.executeQuery(sql, params);
            
            if (list == null || list.isEmpty()) {
                return null; // 账号或密码错误
            }
            
            Map<String, Object> map = list.get(0);
            CodecowInfo codecowInfo = new CodecowInfo();
            codecowInfo.setCWID((String) map.get("CWID"));
            codecowInfo.setPassW((String) map.get("PassW"));
            codecowInfo.setName((String) map.get("Name"));
            codecowInfo.setJob((String) map.get("job")); // 数据库字段是小写job
            codecowInfo.setMoney((String) map.get("Money"));
            
            return codecowInfo;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new CodecowException("数据库查询失败：" + e.getMessage());
        }
    }
    
    public static CodecowInfo SelectinfobyID(String CWID) {
        // 表名+字段名完全匹配
		String sql = "select CWID, PassW, Name, job, Money from " + TABLE_NAME + " where CWID=?";
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
			codecowinfo.setJob((String) map.get("job")); // 修复大写Job→小写job
			codecowinfo.setMoney((String) map.get("Money"));
		    return codecowinfo;
		}
		return null;
	}

    public static boolean UpdateAccount(CodecowInfo codecowinfo) {
        // 表名+字段名完全匹配
		String sql = "Update " + TABLE_NAME + " set Name=?, job=?, Money=? where CWID=? ";
		Object[] params = new Object[4];
		params[0] = codecowinfo.getName();
		params[1] = codecowinfo.getJob(); // 修复大写Job→小写job
		params[2] = codecowinfo.getMoney();
		params[3] = codecowinfo.getCWID();
		
	    int n = MySQLDBToolKit.executeUpdate(sql, params);
	    return n == 1;
	}
}