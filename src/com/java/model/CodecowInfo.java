package com.java.model;

public class CodecowInfo {
    // 账户ID
    private  String CWID;
    // 用户姓名
    private String Name;
    // 账户密码
    private  String PassW;
    // 职位（原注释"用于性别"可能有误，根据字段名修正）
    private String Job;
    private String Money;
    private String Phone;
    // 全参构造方法
    public CodecowInfo(String CWID, String Name, String PassW, String Job,String Money,String Phone) {
        this.CWID = CWID;
        this.Name = Name;
        this.PassW = PassW;
        this.Job = Job;
        this.Money=Money;
        this.Phone=Phone;
    }
    
    // 无参构造方法
    public CodecowInfo() {
    }
    
    // Getter和Setter方法（全部修正）
    public String getCWID() {
        return CWID;
    }
    
    public void setCWID(String CWID) {
        this.CWID = CWID;
    }
    
    public String getName() {
        return Name;
    }
    
    public void setName(String Name) {
       this.Name = Name;
    }
    
    public String getPassW() {
        return PassW;
    }
    
    public void setPassW(String passW) {
       this.PassW = passW;
    }
    
    public String getJob() {
        return Job;
    }
    
    public void setJob(String Job) {
        this.Job = Job;
    }

    public String getMoney() {
        return Money;
    }
    
 

	public void setMoney(String Money) {
        this.Money = Money;
    }
	 public String getPhone() {
	        return Phone;
	    }
	    
	    public void setPhone(String Phone) {
	        this.Phone = Phone;
	    }
	    
}