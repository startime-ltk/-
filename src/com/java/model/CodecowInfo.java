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
    // 全参构造方法
    public CodecowInfo(String CWID, String Name, String PassW, String Job,String Money) {
        this.CWID = CWID;
        this.Name = Name;
        this.PassW = PassW;
        this.Job = Job;
        this.Money=Money;
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
}