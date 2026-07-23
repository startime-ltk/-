package com.java.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.java.logic.CWInfoLogic;
import com.java.model.CodecowInfo;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;

public class AddAccountInternalFrame extends JFrame {

	private JPanel contentPane;
	private JPasswordField password_PassW;
	private JPasswordField passwordField_1;
	private JTextField textField_CWID;
	private JTextField textField_Name;
	private JTextField textField_Job;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddAccountInternalFrame frame = new AddAccountInternalFrame();
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
	public AddAccountInternalFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100,1200,725);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u8D26\u53F7\uFF1A");
		label.setFont(new Font("宋体", Font.PLAIN, 50));
		label.setBounds(10, 168, 167, 111);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u5BC6\u7801:");
		label_1.setFont(new Font("宋体", Font.PLAIN, 50));
		label_1.setBounds(10, 289, 125, 111);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("\u786E\u8BA4\u5BC6\u7801:");
		label_2.setFont(new Font("宋体", Font.PLAIN, 50));
		label_2.setBounds(498, 289, 230, 111);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("\u7528\u6237:");
		label_3.setFont(new Font("宋体", Font.PLAIN, 50));
		label_3.setBounds(10, 410, 206, 111);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("\u804C\u4F4D:");
		label_4.setFont(new Font("宋体", Font.PLAIN, 50));
		label_4.setBounds(10, 528, 137, 111);
		contentPane.add(label_4);
		
		password_PassW = new JPasswordField();
		password_PassW.setFont(new Font("宋体", Font.PLAIN, 40));
		password_PassW.setBounds(148, 337, 340, 33);
		contentPane.add(password_PassW);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setFont(new Font("宋体", Font.PLAIN, 40));
		passwordField_1.setBounds(738, 337, 340, 33);
		contentPane.add(passwordField_1);
		
		textField_CWID = new JTextField();
		textField_CWID.setBounds(150, 214, 338, 33);
		contentPane.add(textField_CWID);
		textField_CWID.setColumns(10);
		
		textField_Name = new JTextField();
		textField_Name.setColumns(10);
		textField_Name.setBounds(148, 450, 340, 33);
		contentPane.add(textField_Name);
		
		textField_Job = new JTextField();
		textField_Job.setColumns(10);
		textField_Job.setBounds(150, 567, 338, 33);
		contentPane.add(textField_Job);
		
		JButton btnOK = new JButton("\u786E\u5B9A");
		btnOK.setFont(new Font("宋体", Font.PLAIN, 50));
		btnOK.setBounds(218, 610, 167, 66);
		contentPane.add(btnOK);
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String psw1="password_PassW.getPassword()";
				String psw2="passwordField_1.getPassword()";
				/*if(psw1!=psw2) {
					JOptionPane.showMessageDialog(btnOK, "两次密码不一致");
				}else {*/
				CodecowInfo codecowInfo=new CodecowInfo();
				codecowInfo.setCWID(textField_CWID.getText());
				codecowInfo.setPassW(new String(password_PassW.getPassword()));
				codecowInfo.setName(textField_Name.getText());
				codecowInfo.setJob(textField_Job.getText());
				
				boolean sucess=CWInfoLogic.AddAccount(codecowInfo);
				if (sucess) {
						JOptionPane.showMessageDialog(btnOK, "添加成功！");
				}else {
						JOptionPane.showMessageDialog(btnOK, "添加失败！");
				//}
				
			}}
			
		});
		JButton btn_clear = new JButton("\u91CD\u7F6E");
		btn_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_Name.setText("刘庆国");
				textField_CWID.setText("20070431");
				textField_Job.setText("保安");
				password_PassW.setText("123456");
				passwordField_1.setText("123456");
			}
		});
		btn_clear.setFont(new Font("宋体", Font.PLAIN, 50));
		btn_clear.setBounds(756, 610, 167, 66);
		contentPane.add(btn_clear);
	}
}
