package com.java.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.java.logic.CWInfoLogic;
import com.java.model.CodecowInfo;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;

public class SumMoneyInternalFrame extends JInternalFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnQueryAll;

    // 列：员工账号、姓名、职位、原工资、扣款金额、扣款后工资、缺勤次数
    private final String[] COLUMNS = {"员工账号", "姓名", "职位", "原工资", "扣款金额", "扣款后工资", "缺勤次数"};

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SumMoneyInternalFrame frame = new SumMoneyInternalFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public SumMoneyInternalFrame() {
        setTitle("员工工资单独扣款");
        setBounds(100, 100, 1295, 840);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        getContentPane().setLayout(new BorderLayout());

        // 顶部面板：仅查询按钮
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        btnQueryAll = new JButton("一键查询所有员工");
        btnQueryAll.setForeground(Color.DARK_GRAY);
        btnQueryAll.setBackground(SystemColor.activeCaption);
        btnQueryAll.setFont(new Font("宋体", Font.PLAIN, 58));
        topPanel.add(btnQueryAll);
        getContentPane().add(topPanel, BorderLayout.NORTH);

        // 表格初始化
        tableModel = new DefaultTableModel(COLUMNS, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("宋体", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // 绑定事件
        initEvent();
    }

    private void initEvent() {
        // 查询按钮 传统监听（兼容低版本JDK）
        btnQueryAll.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                queryAllStaff();
            }
        });

        // 监听表格单元格编辑完成（输入缺勤次数后回车/失去焦点触发）
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                // 只监听单元格修改事件
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int col = e.getColumn();
                    // 仅处理 缺勤次数列（下标6）
                    if (col == 6) {
                        singleDeductMoney(row);
                    }
                }
            }
        });
    }

    /**
     * 查询所有员工数据
     */
    private void queryAllStaff() {
        tableModel.setRowCount(0);
        List<Map<String, Object>> dataList = com.java.dao.MySQLDBToolKit.executeQuery(
                "SELECT CWID,Name,job,Money FROM codecowinfo"
        );

        if (dataList == null || dataList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "暂无员工数据！");
            return;
        }

        for (Map<String, Object> map : dataList) {
            String cwid = (String) map.get("CWID");
            String name = (String) map.get("Name");
            String job = (String) map.get("job");
            String oldMoneyStr = (String) map.get("Money");

            double oldMoney = 0;
            try {
                oldMoney = Double.parseDouble(oldMoneyStr);
            } catch (Exception ex) {
                oldMoney = 0;
            }
            // 初始值：扣款0、新工资=原工资、缺勤次数为空
            Object[] row = {cwid, name, job, oldMoney, 0.0, oldMoney, ""};
            tableModel.addRow(row);
        }
    }

    /**
     * 单人扣款逻辑
     */
    private void singleDeductMoney(int row) {
        String absentText = tableModel.getValueAt(row, 6).toString().trim();
        if (absentText.isEmpty()) {
            return;
        }

        int absentCount;
        try {
            absentCount = Integer.parseInt(absentText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入合法整数！");
            tableModel.setValueAt("", row, 6);
            return;
        }

        if (absentCount < 0) {
            JOptionPane.showMessageDialog(this, "缺勤次数不能为负数！");
            tableModel.setValueAt("", row, 6);
            return;
        }

        // 1次缺勤扣100元
        double totalDeduct = absentCount * 100.0;

        String cwid = tableModel.getValueAt(row, 0).toString();
        String name = tableModel.getValueAt(row, 1).toString();
        String job = tableModel.getValueAt(row, 2).toString();
        double oldMoney = (double) tableModel.getValueAt(row, 3);

        double newMoney = oldMoney - totalDeduct;
        if (newMoney < 0) {
            JOptionPane.showMessageDialog(this, "【" + name + "】工资不足，无法扣款！");
            tableModel.setValueAt("", row, 6);
            return;
        }

        // 更新表格
        tableModel.setValueAt(totalDeduct, row, 4);
        tableModel.setValueAt(newMoney, row, 5);

        // 更新数据库
        CodecowInfo info = new CodecowInfo();
        info.setCWID(cwid);
        info.setName(name);
        info.setJob(job);
        info.setMoney(String.valueOf(newMoney));
        boolean updateOk = CWInfoLogic.UpdateAccount(info);

        if (updateOk) {
            JOptionPane.showMessageDialog(this,
                    "扣款成功！\n缺勤次数：" + absentCount +
                    "\n扣款金额：" + totalDeduct + " 元");
        } else {
            JOptionPane.showMessageDialog(this, "数据库更新失败！");
        }
    }
}