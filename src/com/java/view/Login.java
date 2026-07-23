package com.java.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.java.exception.CodecowException;
import com.java.logic.CWInfoLogic;
import com.java.model.CodecowInfo;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField IDcard;
	private JPasswordField passwordField_pass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public Login() {
		setForeground(Color.WHITE);
		setFont(new Font("Dialog", Font.PLAIN, 50));
		setTitle("员工薪资管理系统");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 725);
		// 窗口居中显示
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(224, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		IDcard = new JTextField();
		IDcard.setFont(new Font("宋体", Font.PLAIN, 50));
		IDcard.setText("12345678"); // 正确数据库账号
		IDcard.setBackground(new Color(192, 192, 192));
		IDcard.setBounds(211, 155, 637, 137);
		contentPane.add(IDcard);
		IDcard.setColumns(10);

		passwordField_pass = new JPasswordField();
		passwordField_pass.setFont(new Font("宋体", Font.PLAIN, 50));
		passwordField_pass.setBackground(new Color(192, 192, 192));
		passwordField_pass.setBounds(211, 317, 637, 137);
		contentPane.add(passwordField_pass);

		JLabel label = new JLabel("账号：");
		label.setFont(new Font("宋体", Font.PLAIN, 50));
		label.setBounds(15, 171, 186, 100);
		contentPane.add(label);

		JLabel label_1 = new JLabel("密码：");
		label_1.setFont(new Font("宋体", Font.PLAIN, 50));
		label_1.setBounds(15, 317, 181, 137);
		contentPane.add(label_1);

		JButton btnOK = new JButton("确定");
		btnOK.setBackground(new Color(211, 211, 211));
		btnOK.setForeground(new Color(0, 204, 0));
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String CWID, PassW;
				CWID = IDcard.getText().trim();
				PassW = new String(passwordField_pass.getPassword()).trim();
				CWInfoLogic cwInfoLogic = new CWInfoLogic();
				try {
					// 查询用户
					CodecowInfo cwInfo = cwInfoLogic.CWInfobyIDandPass(CWID, PassW);
					if (cwInfo != null) {
						// ========== 改动：弹窗居中+放大 ==========
						// 用JLabel包装文字，设置大字体，弹窗自动放大
						JLabel successMsg = new JLabel("登录成功！欢迎：" + cwInfo.getJob() + " " + cwInfo.getName());
						successMsg.setFont(new Font("宋体", Font.PLAIN, 32));
						// 父组件传null，弹窗屏幕居中
						JOptionPane.showMessageDialog(null, successMsg, "登录提示", JOptionPane.INFORMATION_MESSAGE);
						
						MainFrame mainFrame = new MainFrame(cwInfo);
						mainFrame.setVisible(true);
						// 关闭登录窗口
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "账号或密码错误", "登录失败", JOptionPane.WARNING_MESSAGE);
					}
				} catch (CodecowException e1) {
					JOptionPane.showMessageDialog(null, "系统异常：" + e1.getMessage(), "系统错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnOK.setBounds(213, 506, 215, 73);
		contentPane.add(btnOK);

		JButton btnClear = new JButton("清空");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IDcard.setText("12345678");
				passwordField_pass.setText("123456");
			}
		});
		btnClear.setBackground(new Color(192, 192, 192));
		btnClear.setForeground(new Color(204, 51, 51));
		btnClear.setBounds(641, 493, 215, 73);
		contentPane.add(btnClear);

		JLabel label_2 = new JLabel("欢迎使用员工薪资管理系统");
		label_2.setFont(new Font("宋体", Font.PLAIN, 52));
		label_2.setBounds(276, 15, 1061, 100);
		contentPane.add(label_2);
		
		//设置窗口图标
		ImageIcon logo=new ImageIcon(getClass().getResource("/icon/ltk.png"));//导入图标
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/icon/ltk.png")));
		
	}
}