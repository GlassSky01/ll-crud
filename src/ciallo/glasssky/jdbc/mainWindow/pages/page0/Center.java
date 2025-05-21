package ciallo.glasssky.jdbc.mainWindow.pages.page0;

import ciallo.glasssky.jdbc.Format;
import ciallo.glasssky.jdbc.Tools;
import ciallo.glasssky.jdbc.database.DbOperates;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Center extends JPanel implements Format {
    public DefaultTableModel model;
    public JTable table;

    public Center(int h) {
        this.setLayout(new GridLayout(1, 1));

        model = new DefaultTableModel(new Object[][]{}, new String[]
                {"金额", "收支", "账户", "类别", "日期", "备注"}){
            @Override
            public boolean isCellEditable(int row , int column){
                return false;
            }
        };
        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        Font font = Tools.getFont(h, 27);
        table.getTableHeader().setFont(font);
        table.setFont(font);
        JScrollPane pane = new JScrollPane(table);
        this.add(pane);
    }


    @Override
    public void init() throws SQLException {
        table.setRowHeight(table.getTableHeader().getHeight());
        ArrayList<Object[]> arr = DbOperates.queryAllBilling();
        model.setRowCount(0);
        for(Object[] data : arr)
        {
            model.addRow(data);
        }
    }
    @Override
    public void end() {

    }
}
