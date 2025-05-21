package ciallo.glasssky.jdbc.mainWindow.pages.page1;

import ciallo.glasssky.jdbc.database.DbOperates;
import ciallo.glasssky.jdbc.mainWindow.pages.page2.Page2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class Bottom extends JPanel {
    public Bottom(Font font ){
        this.setLayout(new GridLayout(1 , 2));
        JButton add = new JButton("添加");
        JButton remove = new JButton("删除");
        this.add(add);
        this.add(remove);
        add.setFont(font);
        remove.setFont(font);
        setAdd(add);
        setRemove(remove);
    }
    private void setAdd(JButton button){
        button.addActionListener(e->{
            JTable table1 = ((Page1)this.getParent()).getCenter().getTable(1);
            JTable table2 = ((Page1)this.getParent()).getCenter().getTable(2);
            int[] rows = table1.getSelectedRows();
            for(int i = rows.length - 1 ; i >= 0 ; i--)
            {
                int row = rows[i];
                try {
                    DbOperates.execute(String.format(
                            "update glassskydb.account set enable = 1 where user = '%s' and account = '%s';"
                            , DbOperates.getUser() , table1.getValueAt(row , 0)
                    ));
                    ((DefaultTableModel)table2.getModel()).addRow(new Object[]{table1.getValueAt(row , 0) , 0.0});
                    ((DefaultTableModel)table1.getModel()).removeRow(row);
                } catch (SQLException ex) {

                }
            }
        });
    }
    private void setRemove(JButton button){
        button.addActionListener(e->{
            JTable table1 = ((Page1)this.getParent()).getCenter().getTable(1);
            JTable table2 = ((Page1)this.getParent()).getCenter().getTable(2);
            int[] rows = table2.getSelectedRows();
            for(int i = rows.length - 1 ; i >= 0 ; i--)
            {
                int row = rows[i];
                try {
                    String account = (String) table2.getValueAt(row , 0);
                    DbOperates.execute(String.format(
                            "delete from glassskydb.account where user = '%s' and account = '%s';"
                            , DbOperates.getUser() , account
                    ));
                    DbOperates.execute(String.format(
                            "insert into glassskydb.account(account, user, money, enable) value('%s' , '%s' , 0 , 0);"
                            ,account , DbOperates.getUser()
                    ));
                    ((DefaultTableModel)table1.getModel()).addRow(new Object[]{table2.getValueAt(row , 0)});
                    ((DefaultTableModel)table2.getModel()).removeRow(row);
                } catch (SQLException ex) {

                }
            }
        });
    }
}
