package com.java.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.java.logic.CWInfoLogic;
import com.java.model.CodecowInfo;

import javax.swing.JButton;

public class UpdateInfoInternalFrame extends JInternalFrame {
	private JTextField textField_CWID;
	private JTextField textField_Name;
	private JTextField textField_Job;
	// 新增余额输入框
	private JTextField textField_Money;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateInfoInternalFrame frame = new UpdateInfoInternalFrame();
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
	public UpdateInfoInternalFrame() {
		setBounds(100, 100, 1191, 671);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("工号：");
		label.setBounds(37, 69, 81, 22);
		getContentPane().add(label);
		
		textField_CWID = new JTextField();
		textField_CWID.setBounds(103, 66, 96, 28);
		getContentPane().add(textField_CWID);
		textField_CWID.setColumns(10);
		
		JLabel label_1 = new JLabel("用户名：");
		label_1.setBounds(15, 173, 81, 22);
		getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("职位：");
		label_2.setBounds(15, 259, 81, 22);
		getContentPane().add(label_2);

		// 新增余额标签
		JLabel label_Money = new JLabel("余额：");
		label_Money.setBounds(15, 330, 81, 22);
		getContentPane().add(label_Money);
		// 新增余额输入框
		textField_Money = new JTextField();
		textField_Money.setBounds(103, 327, 96, 28);
		getContentPane().add(textField_Money);
		textField_Money.setColumns(10);
		
		JButton btn_OK = new JButton("查询");
		btn_OK.setBounds(438, 65, 123, 31);
		getContentPane().add(btn_OK);
		btn_OK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String cwid = textField_CWID.getText().trim();
				if(cwid.isEmpty()){
					JOptionPane.showMessageDialog(null, "请输入工号！");
					return;
				}
				
				CodecowInfo codecowInfo=CWInfoLogic.SelectinfobyID(cwid);
				if(codecowInfo != null){
					textField_Name.setText(codecowInfo.getName());
					textField_Job.setText(codecowInfo.getJob());
					// 回填余额
					textField_Money.setText(codecowInfo.getMoney());
				}else{
					JOptionPane.showMessageDialog(null, "未查询到该工号信息！");
					textField_Name.setText("");
					textField_Job.setText("");
					textField_Money.setText("");
				}
			}
		});

		JButton btn_Update = new JButton("修改");
		btn_Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cwid = textField_CWID.getText().trim();
				String name = textField_Name.getText().trim();
				String job = textField_Job.getText().trim();
				String money = textField_Money.getText().trim();
				
				if(cwid.isEmpty() || name.isEmpty() || job.isEmpty() || money.isEmpty()){
					JOptionPane.showMessageDialog(null, "工号、用户名、职位、余额不能为空！");
					return;
				}
				
				CodecowInfo codecowinfo = new CodecowInfo();
				codecowinfo.setCWID(cwid);
				codecowinfo.setName(name);
				codecowinfo.setJob(job);
				// 封装余额
				codecowinfo.setMoney(money);
				
				boolean success = CWInfoLogic.UpdateAccount(codecowinfo);
				if (success) {
					JOptionPane.showMessageDialog(null, "修改成功！");
				} else {
					JOptionPane.showMessageDialog(null, "修改失败！请检查工号是否存在");
				}
			}
		});
		btn_Update.setBounds(31, 223, 93, 23);
		getContentPane().add(btn_Update);
		
		
		textField_Name = new JTextField();
		textField_Name.setText("");
		textField_Name.setBounds(103, 170, 96, 28);
		getContentPane().add(textField_Name);
		textField_Name.setColumns(10);
		
		textField_Job = new JTextField();
		textField_Job.setBounds(103, 256, 96, 28);
		getContentPane().add(textField_Job);
		textField_Job.setColumns(10);

	}
}