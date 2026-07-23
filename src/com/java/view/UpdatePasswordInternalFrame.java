package com.java.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.java.logic.CWInfoLogic;
import util.SmsCodeUtil;

public class UpdatePasswordInternalFrame extends JInternalFrame {
    private JTextField textFieldCWID;
    private JTextField textFieldPhone;
    private JTextField textFieldCode;
    private JPasswordField passwordFieldNew;
    private JPasswordField passwordFieldConfirm;
    private JButton btnGetCode;
    private JButton btnUpdate;
    private JButton btnToggleNewPwd;
    private JButton btnToggleConfirmPwd;
    private boolean isNewPwdVisible = false;
    private boolean isConfirmPwdVisible = false;

    public UpdatePasswordInternalFrame() {
        setTitle("短信验证码修改密码");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setBounds(100, 100, 800, 600);
        getContentPane().setLayout(null);

        Font font = new Font("宋体", Font.PLAIN, 24);

        // 1. 员工账号输入
        JLabel lblCWID = new JLabel("员工账号：");
        lblCWID.setFont(font);
        lblCWID.setBounds(50, 50, 120, 40);
        getContentPane().add(lblCWID);

        textFieldCWID = new JTextField();
        textFieldCWID.setFont(font);
        textFieldCWID.setBounds(180, 50, 200, 40);
        getContentPane().add(textFieldCWID);
        textFieldCWID.setColumns(10);

        // 2. 绑定手机号（自动填充，只读）
        JLabel lblPhone = new JLabel("绑定手机号：");
        lblPhone.setFont(font);
        lblPhone.setBounds(50, 120, 120, 40);
        getContentPane().add(lblPhone);

        textFieldPhone = new JTextField();
        textFieldPhone.setFont(font);
        textFieldPhone.setBounds(180, 120, 200, 40);
        textFieldPhone.setEditable(false);
        textFieldPhone.setBackground(new Color(230, 230, 230));
        getContentPane().add(textFieldPhone);
        textFieldPhone.setColumns(10);

        // 3. 短信验证码
        JLabel lblCode = new JLabel("短信验证码：");
        lblCode.setFont(font);
        lblCode.setBounds(50, 190, 120, 40);
        getContentPane().add(lblCode);

        textFieldCode = new JTextField();
        textFieldCode.setFont(font);
        textFieldCode.setBounds(180, 190, 150, 40);
        getContentPane().add(textFieldCode);
        textFieldCode.setColumns(10);

        btnGetCode = new JButton("获取验证码");
        btnGetCode.setFont(new Font("宋体", Font.PLAIN, 20));
        btnGetCode.setBounds(350, 190, 150, 40);
        getContentPane().add(btnGetCode);

        // 4. 新密码 + 显示切换
        JLabel lblNewPwd = new JLabel("新密码：");
        lblNewPwd.setFont(font);
        lblNewPwd.setBounds(50, 260, 120, 40);
        getContentPane().add(lblNewPwd);

        passwordFieldNew = new JPasswordField();
        passwordFieldNew.setFont(font);
        passwordFieldNew.setBounds(180, 260, 200, 40);
        getContentPane().add(passwordFieldNew);

        btnToggleNewPwd = new JButton("显示");
        btnToggleNewPwd.setFont(new Font("宋体", Font.PLAIN, 18));
        btnToggleNewPwd.setBounds(390, 260, 100, 40);
        getContentPane().add(btnToggleNewPwd);

        // 5. 确认密码 + 显示切换
        JLabel lblConfirmPwd = new JLabel("确认密码：");
        lblConfirmPwd.setFont(font);
        lblConfirmPwd.setBounds(50, 330, 120, 40);
        getContentPane().add(lblConfirmPwd);

        passwordFieldConfirm = new JPasswordField();
        passwordFieldConfirm.setFont(font);
        passwordFieldConfirm.setBounds(180, 330, 200, 40);
        getContentPane().add(passwordFieldConfirm);

        btnToggleConfirmPwd = new JButton("显示");
        btnToggleConfirmPwd.setFont(new Font("宋体", Font.PLAIN, 18));
        btnToggleConfirmPwd.setBounds(390, 330, 100, 40);
        getContentPane().add(btnToggleConfirmPwd);

        // 6. 确认修改按钮
        btnUpdate = new JButton("确认修改");
        btnUpdate.setFont(font);
        btnUpdate.setBounds(180, 400, 150, 50);
        getContentPane().add(btnUpdate);

        // 绑定所有事件
        initEvents();
    }

    private void initEvents() {
        // 工号输入完成自动填充手机号
        textFieldCWID.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String cwid = textFieldCWID.getText().trim();
                if (cwid.isEmpty()) {
                    textFieldPhone.setText("");
                    return;
                }
                if (!cwid.matches("\\d{8}")) {
                    textFieldPhone.setText("");
                    return;
                }
                String phone = CWInfoLogic.getPhoneByCWID(cwid);
                if (phone != null) {
                    textFieldPhone.setText(phone);
                } else {
                    textFieldPhone.setText("");
                    JOptionPane.showMessageDialog(null, "该工号不存在或未绑定手机号！", "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // 新密码显示/隐藏切换
        btnToggleNewPwd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isNewPwdVisible) {
                    passwordFieldNew.setEchoChar('●');
                    btnToggleNewPwd.setText("显示");
                    isNewPwdVisible = false;
                } else {
                    passwordFieldNew.setEchoChar((char) 0);
                    btnToggleNewPwd.setText("隐藏");
                    isNewPwdVisible = true;
                }
            }
        });

        // 确认密码显示/隐藏切换
        btnToggleConfirmPwd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isConfirmPwdVisible) {
                    passwordFieldConfirm.setEchoChar('●');
                    btnToggleConfirmPwd.setText("显示");
                    isConfirmPwdVisible = false;
                } else {
                    passwordFieldConfirm.setEchoChar((char) 0);
                    btnToggleConfirmPwd.setText("隐藏");
                    isConfirmPwdVisible = true;
                }
            }
        });

        // 获取验证码
        btnGetCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cwid = textFieldCWID.getText().trim();
                String phone = textFieldPhone.getText().trim();

                if (cwid.isEmpty() || !cwid.matches("\\d{8}")) {
                    JOptionPane.showMessageDialog(null, "请输入8位数字的员工账号！", "输入错误", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (phone.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "该工号未绑定手机号，无法获取验证码！", "错误", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String realCode = CWInfoLogic.sendSmsCode(phone);
                JOptionPane.showMessageDialog(null, 
                    "验证码已发送！\n验证码：" + realCode + "\n（5分钟内有效）", 
                    "发送成功", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 确认修改密码
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cwid = textFieldCWID.getText().trim();
                String phone = textFieldPhone.getText().trim();
                String code = textFieldCode.getText().trim();
                String newPwd = new String(passwordFieldNew.getPassword()).trim();
                String confirmPwd = new String(passwordFieldConfirm.getPassword()).trim();

                if (cwid.isEmpty() || phone.isEmpty() || code.isEmpty() || newPwd.isEmpty() || confirmPwd.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "请填写所有必填项！", "输入错误", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!newPwd.equals(confirmPwd)) {
                    JOptionPane.showMessageDialog(null, "两次密码输入不一致！", "输入错误", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (newPwd.length() < 6) {
                    JOptionPane.showMessageDialog(null, "密码长度不能少于6位！", "输入错误", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean codeValid = CWInfoLogic.verifySmsCode(phone, code);
                if (!codeValid) {
                    JOptionPane.showMessageDialog(null, "验证码无效或已过期！", "验证失败", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean updateSuccess = CWInfoLogic.updatePassword(cwid, newPwd);
                if (updateSuccess) {
                    JOptionPane.showMessageDialog(null, "密码修改成功！请重新登录", "修改成功", JOptionPane.INFORMATION_MESSAGE);
                    SmsCodeUtil.clearCode(phone);
                    // 清空输入框
                    textFieldCWID.setText("");
                    textFieldPhone.setText("");
                    textFieldCode.setText("");
                    passwordFieldNew.setText("");
                    passwordFieldConfirm.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "密码修改失败！", "修改失败", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UpdatePasswordInternalFrame frame = new UpdatePasswordInternalFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}