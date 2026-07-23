package com.java.view;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import com.java.model.CodecowInfo;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

	private CodecowInfo codecowInfo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// 测试用：创建一个空用户（登录后会替换成真实用户）
					CodecowInfo info = new CodecowInfo();
					MainFrame frame = new MainFrame(info);
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
	public MainFrame(CodecowInfo codecowInfo) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 725); // 扩大窗口适配薪资系统
		// ========== 新增：窗口居中显示 ==========
		setLocationRelativeTo(null);

		JDesktopPane jDesktop = new JDesktopPane();
		this.codecowInfo = codecowInfo;
		jDesktop.setBackground(SystemColor.activeCaption);
		this.setContentPane(jDesktop);
		jDesktop.setVisible(true);
		jDesktop.setLocation(0, 0);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(UIManager.getColor("InternalFrame.inactiveTitleForeground"));
		menuBar.setBackground(UIManager.getColor("InternalFrame.inactiveTitleGradient"));
		setJMenuBar(menuBar);
		
		// 改为 员工薪资系统 菜单
		JMenu Menu_Business = new JMenu("薪资管理");
		Menu_Business.setBackground(UIManager.getColor("InternalFrame.inactiveTitleGradient"));
		menuBar.add(Menu_Business);
		
		JMenuItem MenuItem_WithDraw = new JMenuItem("薪资查询");
		Menu_Business.add(MenuItem_WithDraw);
		MenuItem_WithDraw.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // 关闭所有已打开的子窗口
		        JInternalFrame[] frames = jDesktop.getAllFrames();
		        for (JInternalFrame f : frames) {
		            f.dispose();
		        }
		        // 打开员工信息查询窗口
		        SelectCWInfoInternalFrame selectFrame = new SelectCWInfoInternalFrame();
		        selectFrame.setVisible(true);
		        selectFrame.setLocation(0, 0);
		        jDesktop.add(selectFrame);
		    }
		});
		
		JMenuItem MenuItem_Trans = new JMenuItem("薪资统计");
		MenuItem_Trans.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	// 薪资统计加CEO权限校验
		    	if (codecowInfo == null || !"CEO".equals(codecowInfo.getJob())) {
		    		JOptionPane.showMessageDialog(null, "您没有权限执行此操作！", "权限不足", JOptionPane.WARNING_MESSAGE);
		    		return;
		    	}
		    	
		        JInternalFrame[] frames = jDesktop.getAllFrames();
		        for (JInternalFrame f : frames) {
		            f.dispose();
		        }
		        SumMoneyInternalFrame sumFrame = new SumMoneyInternalFrame();
		        sumFrame.setVisible(true);
		        sumFrame.setLocation(0, 0);
		        jDesktop.add(sumFrame);
		    }
		});
		Menu_Business.add(MenuItem_Trans);
		
		JMenu menu = new JMenu("\u8D26\u6237\u64CD\u4F5C");
		menuBar.add(menu);
		
		JMenuItem menuItem_1 = new JMenuItem("\u6DFB\u52A0\u8D26\u6237");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 添加账户加CEO权限校验
		    	if (codecowInfo == null || !"CEO".equals(codecowInfo.getJob())) {
		    		JOptionPane.showMessageDialog(null, "您没有权限执行此操作！", "权限不足", JOptionPane.WARNING_MESSAGE);
		    		return;
		    	}
				
				//将原来在Desktop中的小窗口都隐藏掉
				JInternalFrame[] JInter=jDesktop.getAllFrames();
				for(JInternalFrame frame : JInter) {
					frame.dispose();
				}
				//将新的InternalFrame加载
				AddAccountInternalFrame addAccountInternalFrame = new AddAccountInternalFrame();
				addAccountInternalFrame.setVisible(true);
				addAccountInternalFrame.setLocation(0, 0);
				jDesktop.add(addAccountInternalFrame);
			}
		});
		menu.add(menuItem_1);
		
		JMenuItem menuItem = new JMenuItem("\u4FEE\u6539\u8D26\u6237");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 修改账户加CEO权限校验
		    	if (codecowInfo == null || !"CEO".equals(codecowInfo.getJob())) {
		    		JOptionPane.showMessageDialog(null, "您没有权限执行此操作！", "权限不足", JOptionPane.WARNING_MESSAGE);
		    		return;
		    	}
				
				// 关闭所有已有子窗口
				JInternalFrame[] JInter=jDesktop.getAllFrames();
				for(JInternalFrame frame : JInter) {
					frame.dispose();
				}
				// 加载修改信息窗口
				UpdateInfoInternalFrame updateFrame = new UpdateInfoInternalFrame();
				updateFrame.setVisible(true);
				updateFrame.setLocation(0, 0);
				jDesktop.add(updateFrame);
			}
		});
		menu.add(menuItem);
		
		JMenuItem menuItem_2 = new JMenuItem("\u4FEE\u6539\u5BC6\u7801");
		menu.add(menuItem_2);
		menuItem_2.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // 关闭所有已打开的子窗口
		        JInternalFrame[] frames = jDesktop.getAllFrames();
		        for (JInternalFrame f : frames) {
		            f.dispose();
		        }
		        // 打开验证码修改密码窗口
		        UpdatePasswordInternalFrame pwdFrame = new UpdatePasswordInternalFrame();
		        pwdFrame.setVisible(true);
		        pwdFrame.setLocation(0, 0);
		        jDesktop.add(pwdFrame);
		    }
		});
		menu.add(menuItem_2);
		
		JMenu Menu_Auxiliary = new JMenu("系统功能");
		menuBar.add(Menu_Auxiliary);
		
		JMenuItem MenuItem_LogOut = new JMenuItem("切换账号");
		Menu_Auxiliary.add(MenuItem_LogOut);
		MenuItem_LogOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 1. 关闭当前主窗口
				MainFrame.this.dispose();
				// 2. 打开登录窗口
				Login login = new Login();
				login.setVisible(true);
			}
		});
		JMenuItem MenuItem_Exit = new JMenuItem("退出系统");
		Menu_Auxiliary.add(MenuItem_Exit);
		
		// 正确传递用户对象
		WelComeInternalFrame welcome = new WelComeInternalFrame(this.codecowInfo);
		welcome.getContentPane().setFont(new Font("宋体", Font.PLAIN, 50));
		welcome.getContentPane().setLayout(null);
		welcome.setBounds(45,10, 1100, 600);
		welcome.setVisible(true);
		jDesktop.setLayout(null);
		jDesktop.add(welcome);
	}
}