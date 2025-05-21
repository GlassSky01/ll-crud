package ciallo.glasssky.jdbc.mainWindow.pages.page1;

import ciallo.glasssky.jdbc.Format;
import ciallo.glasssky.jdbc.Tools;
import ciallo.glasssky.jdbc.database.DbOperates;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Center extends JPanel implements Format {
    private DefaultTableModel model1, model2;
    String[] accounts = {"现金", "储蓄卡", "信用卡", "虚拟账户", "投资账户", "负债", "债权"};
    private JTable table1, table2;

    public Center(Font font) {
        model1 = new DefaultTableModel(new Object[][]{}, new String[]{"未添加"});
        model2 = new DefaultTableModel(new Object[][]{}, new String[]{"已添加" , "金额"});
        table1 = new JTable(model1);
        table2 = new JTable(model2);
        table1.getTableHeader().setFont(font);
        table2.getTableHeader().setFont(font);
        Font font2 = Tools.getFont(font.getSize() , 2);
        table1.setFont(font);
        table2.setFont(font2);
        JScrollPane pane1 = new JScrollPane(table1);
        JScrollPane pane2 = new JScrollPane(table2);
        this.setLayout(new GridLayout(1, 2));
        this.add(pane1);
        this.add(pane2);
    }

    @Override
    public void init() throws SQLException {
        table1.setRowHeight(table1.getTableHeader().getHeight());
        table2.setRowHeight(table2.getTableHeader().getHeight());
        for (String account : accounts) {
            try {
                DbOperates.execute(String.format(
                        "insert into glassskydb.account(account, user, money, enable) value('%s' , '%s' , 0 , 0);"
                        , account, DbOperates.getUser()
                ));
            } catch (SQLException e) {

            }
        }
        query();

    }

    @Override
    public void end() {

    }

    public JTable getTable(int x) {
        if (x == 1)
            return table1;
        return table2;
    }

    public void query() throws SQLException {
        ArrayList<String> arr1 = DbOperates.getStringResults(String.format(
                "select account from glassskydb.account where user = '%s' and enable = 0;"
                , DbOperates.getUser()
        ));
        model1.setRowCount(0);
        for (String i : arr1)
            model1.addRow(new Object[]{i});

        ArrayList<Object[]> arr2 = DbOperates.queryAllAccount();
        model2.setRowCount(0);
        for (Object[] i : arr2)
            model2.addRow(i);
    }
}
