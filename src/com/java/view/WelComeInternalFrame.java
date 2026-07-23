package com.java.view;
import java.awt.Font;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import com.java.model.CodecowInfo;
import javax.swing.ImageIcon;

public class WelComeInternalFrame extends JInternalFrame {

	private CodecowInfo codecowInfo;

	public WelComeInternalFrame(CodecowInfo codecowInfo) {
		setFrameIcon(new ImageIcon(WelComeInternalFrame.class.getResource("/icon/ltk.png")));
		setTitle("\u6B22\u8FCE\u9875\u9762");
		
		// 空指针安全处理（核心修复）
		if (codecowInfo == null) {
			codecowInfo = new CodecowInfo();
			codecowInfo.setCWID("00000000");
			codecowInfo.setName("未登录用户");
			codecowInfo.setJob("访客");
			codecowInfo.setPassW("");
		}
		this.codecowInfo = codecowInfo;

		// 去掉标题栏
		((javax.swing.plaf.basic.BasicInternalFrameUI) getUI()).setNorthPane(null);
		putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);

		setBounds(100, 100, 1200, 725);
		getContentPane().setLayout(null);

		// 标题：员工薪资管理系统
		JLabel lblTitle = new JLabel("欢迎使用员工薪资管理系统");
		lblTitle.setFont(new Font("宋体", Font.BOLD, 69));
		lblTitle.setBounds(119, 81, 927, 216);
		getContentPane().add(lblTitle);

		// 欢迎信息
		JLabel lblWelcome = new JLabel();
		lblWelcome.setFont(new Font("宋体", Font.PLAIN, 50));
		lblWelcome.setBounds(242, 254, 927, 401);

		// 安全拼接信息（适配你的数据库字段：job + name）
		try {
			String job = codecowInfo.getJob();
			String name = codecowInfo.getName();
			lblWelcome.setBounds(242, 254, 1050, 430);
			lblWelcome.setText("您好！" + job + "：" + name);
		} catch (Exception e) {
			lblWelcome.setText("您好！欢迎您使用本系统");
		}

		getContentPane().add(lblWelcome);
	}
}