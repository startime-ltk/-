# 员工薪资管理系统 (codecowmoneysystem)（码牛）

## 项目简介

本系统是一个基于 Java Swing 的桌面端员工薪资管理系统，支持员工登录、薪资查询、缺勤扣款统计、账户管理等功能，通过短信验证码方式实现密码修改。

## 技术栈

| 层级 | 技术 |
|------|------|
| 语言 | Java 8 |
| UI 框架 | Java Swing + JGoodies Forms 1.8.0 |
| 数据库 | MySQL 8.0 |
| JDBC 驱动 | mysql-connector-java 8.0.21 |
| 架构模式 | DAO / Model / Logic / View 四层架构 |
| IDE | Eclipse |

## 项目结构

```
src/
├── icon/
│   └── ltk.png                          # 应用图标
├── util/
│   └── SmsCodeUtil.java                 # 短信验证码工具（模拟）
└── com/java/
    ├── dao/
    │   ├── DBTest.java                  # 数据库连接测试
    │   └── MySQLDBToolKit.java          # 数据库 CRUD 封装
    ├── exception/
    │   └── CodecowException.java        # 自定义异常类
    ├── logic/
    │   └── CWInfoLogic.java             # 业务逻辑层
    ├── model/
    │   ├── CodecowInfo.java             # 员工信息模型
    │   └── DetailInfo.java              # 简易账户模型
    └── view/
        ├── Login.java                   # 登录窗口（启动入口）
        ├── MainFrame.java               # 主窗口（菜单 + 子窗口容器）
        ├── WelComeInternalFrame.java    # 欢迎页
        ├── AddAccountInternalFrame.java # 添加账户（CEO 专属）
        ├── SelectCWInfoInternalFrame.java   # 员工薪资查询
        ├── SumMoneyInternalFrame.java       # 薪资统计与缺勤扣款（CEO 专属）
        ├── UpdateInfoInternalFrame.java     # 修改员工信息（CEO 专属）
        └── UpdatePasswordInternalFrame.java # 短信验证码修改密码
```

## 功能模块

| 模块 | 说明 | 权限 |
|------|------|------|
| 登录 | 输入 8 位工号 + 密码登录 | 所有员工 |
| 欢迎页 | 显示当前登录员工的姓名和职位 | 所有员工 |
| 薪资查询 | 按工号查询姓名、职位、当前余额 | 所有员工 |
| 薪资统计 | 表格展示全员薪资，录入缺勤次数自动扣款（100元/次） | 仅 CEO |
| 添加账户 | 新建员工账户（工号、密码、姓名、职位） | 仅 CEO |
| 修改账户 | 按工号查询后修改姓名、职位、余额 | 仅 CEO |
| 修改密码 | 通过短信验证码验证身份后修改密码 | 所有员工 |
| 切换账号 / 退出 | 注销当前登录或退出系统 | 所有员工 |

## 数据库设计

数据库名称：`codecow`

### codecowinfo（员工信息表）

| 字段 | 类型 | 说明 |
|------|------|------|
| CWID | CHAR(8) PK | 员工工号（8位数字） |
| PassW | VARCHAR(10) | 登录密码 |
| Name | VARCHAR(20) | 员工姓名 |
| job | VARCHAR(20) | 职位 |
| Money | DECIMAL(9,2) | 薪资余额 |
| Phone | CHAR(11) | 手机号 |

### checkInfo（考勤工资记录表）

| 字段 | 类型 | 说明 |
|------|------|------|
| ID | INT AUTO_INCREMENT PK | 记录 ID |
| checkin | DATE | 考勤日期 |
| CWID | CHAR(8) FK | 员工工号 |
| money | DECIMAL(9,2) | 基础工资 |
| moneyadd | DECIMAL(9,2) | 奖金/补贴 |
| moneyout | DECIMAL(9,2) | 罚款/扣款 |
| Currentmoney | DECIMAL(9,2) | 实发工资 |
| Remark | VARCHAR(50) | 备注 |

## 运行方式

1. 安装 MySQL 8.0，创建数据库 `codecow`
2. 执行项目根目录下的 SQL 建表脚本 `Untitled 2`，并确保 `codecowinfo` 表包含 `Money` 和 `Phone` 字段
3. 在 Eclipse 中导入项目
4. 将 lib 目录下的三个 JAR 包加入 Build Path：
   - `mysql-connector-java-8.0.21 - jar`
   - `jgoodies-common-1.8.0.jar`
   - `jgoodies-forms-1.8.0.jar`
5. 修改 `MySQLDBToolKit.java` 中的数据库连接信息（URL、用户名、密码）
6. 运行 `com.java.view.Login` 的 `main()` 方法

## 测试账号

| 工号 | 密码 | 姓名 | 职位 |
|------|------|------|------|
| 12345678 | 123456 | 李图康 | CEO |
| 01314520 | 123456 | 杨凯然 | 保洁队长 |

## 已知问题

- 密码以明文形式存储，未做加密处理
- 数据库连接信息（URL / 用户名 / 密码）硬编码在代码中
- 短信验证码为模拟实现，验证码直接弹窗显示，无实际短信发送能力
- SQL 建表脚本与代码模型存在差异，需手动补充 `Money` / `Phone` 字段
- `AddAccountInternalFrame` 中的两次密码确认逻辑已被注释
- `AddAccountInternalFrame` 继承 `JFrame`，与其他子窗口（`JInternalFrame`）不一致，会独立弹出
