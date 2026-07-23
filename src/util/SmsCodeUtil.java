package util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 短信验证码工具类（随机6位数字+5分钟有效期）
 */
public class SmsCodeUtil {
    // 存储验证码：key=手机号，value=[验证码, 过期时间戳]
    private static Map<String, String[]> smsCodeCache = new HashMap<>();
    // 验证码有效期：5分钟
    private static final long CODE_EXPIRE_TIME = 5 * 60 * 1000;

    /**
     * 生成6位随机数字验证码
     */
    public static String generateSmsCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    /**
     * 发送并存储验证码
     */
    public static String sendSmsCode(String phone) {
        String code = generateSmsCode();
        smsCodeCache.put(phone, new String[]{code, String.valueOf(System.currentTimeMillis() + CODE_EXPIRE_TIME)});
        System.out.println("【模拟短信】手机号 " + phone + " 的验证码：" + code + "，5分钟内有效");
        return code;
    }

    /**
     * 验证验证码
     */
    public static boolean verifySmsCode(String phone, String inputCode) {
        if (!smsCodeCache.containsKey(phone)) {
            return false;
        }
        String[] cacheData = smsCodeCache.get(phone);
        String realCode = cacheData[0];
        long expireTime = Long.parseLong(cacheData[1]);
        
        if (System.currentTimeMillis() > expireTime) {
            smsCodeCache.remove(phone);
            return false;
        }
        return realCode.equals(inputCode);
    }

    /**
     * 清除已使用的验证码
     */
    public static void clearCode(String phone) {
        smsCodeCache.remove(phone);
    }
}