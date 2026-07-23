package com.java.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import com.java.logic.CWInfoLogic;
import com.java.model.CodecowInfo;

public class SelectCWInfoInternalFrame extends JInternalFrame {
	private JTextField textField_ID;
	private JTextField textField_Name;
	private JTextField textField_Job;
	private JTextField textField_Money;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectCWInfoInternalFrame frame = new SelectCWInfoInternalFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SelectCWInfoInternalFrame() {
		setTitle("员工信息查询");
		setClosable(true);
		setResizable(true);
		setBounds(100, 100, 1066, 735);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\u5DE5\u53F7\uFF1A");
		label.setBounds(44, 48, 81, 22);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("\u7528\u6237\u540D\uFF1A");
		label_1.setBounds(44, 191, 81, 22);
		getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("\u804C\u4F4D\uFF1A");
		label_2.setBounds(44, 365, 81, 22);
		getContentPane().add(label_2);
		
		JButton button_find = new JButton("\u67E5\u8BE2");
		button_find.setBounds(484, 44, 123, 31);
		getContentPane().add(button_find);
		button_find.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearFields();
				String cwId = textField_ID.getText().trim();
				if (cwId.isEmpty()) {
					JOptionPane.showMessageDialog(null, "\u8BF7\u8F93\u5165\u5DE5\u53F7\uff01", "\u8F93\u5165\u9519\u8BEF", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (!cwId.matches("\\d{8}")) {
					JOptionPane.showMessageDialog(null, "\u5DE5\u53F7\u5FC5\u987B\u4E3A8\u4F4D\u6570\u5B57\uff01", "\u683C\u5F0F\u9519\u8BEF", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				try {
					CodecowInfo codecowinfo = CWInfoLogic.SelectinfobyID(cwId);
					if (codecowinfo == null) {
						JOptionPane.showMessageDialog(null, "\u672A\u627E\u5230\u8BE5\u5DE5\u53F7\u7684\u5458\u5DE5\u4FE1\u606F\uff01", "\u67E5\u8BE2\u7ED3\u679C", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					textField_Name.setText(codecowinfo.getName() == null ? "" : codecowinfo.getName());
					textField_Job.setText(codecowinfo.getJob() == null ? "" : codecowinfo.getJob());
					// 展示余额
					textField_Money.setText(codecowinfo.getMoney() == null ? "" : codecowinfo.getMoney());
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "\u67E5\u8BE2\u5931\u8D25\uff1A" + e.getMessage(), "\u7CFB\u7EDF\u9519\u8BEF", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});
		
		JLabel label_3 = new JLabel("\u4F59\u989D\uFF1A");
		label_3.setBounds(44, 498, 81, 22);
		getContentPane().add(label_3);
		
		JButton button_reset = new JButton("\u91CD\u7F6E");
		button_reset.setBounds(620, 44, 123, 31);
		getContentPane().add(button_reset);
		button_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFields();
			}
		});
		
		textField_ID = new JTextField();
		textField_ID.setBounds(112, 45, 230, 28);
		getContentPane().add(textField_ID);
		textField_ID.setColumns(10);
		
		textField_Name = new JTextField();
		textField_Name.setBounds(122, 188, 220, 28);
		textField_Name.setColumns(10);
		textField_Name.setEditable(false);
		
		textField_Job = new JTextField();
		textField_Job.setText("");
		textField_Job.setBounds(140, 365, 202, 28);
		textField_Job.setColumns(10);
		textField_Job.setEditable(false);
		
		textField_Money = new JTextField();
		textField_Money.setText("");
		textField_Money.setBounds(183, 495, 96, 28);
		textField_Money.setColumns(10);
		textField_Money.setEditable(false);
		
		getContentPane().add(textField_Name);
		getContentPane().add(textField_Job);
		getContentPane().add(textField_Money);
	}
	
	private void clearFields() {
		textField_Name.setText("");
		textField_Job.setText("");
		textField_Money.setText("");
	}
}