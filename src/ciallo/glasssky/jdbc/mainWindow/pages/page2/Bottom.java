package ciallo.glasssky.jdbc.mainWindow.pages.page2;

import ciallo.glasssky.jdbc.Tools;
import ciallo.glasssky.jdbc.database.DbOperates;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

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
            JTable table1 = ((Page2)this.getParent()).getCenter().getTable(1);
            JTable table2 = ((Page2)this.getParent()).getCenter().getTable(2);
            int[] rows = table1.getSelectedRows();
            for(int i = rows.length - 1 ; i >= 0 ; i--)
            {
                int row = rows[i];
                try {
                    DbOperates.execute(String.format(
                            "update glassskydb.subject set enable = 1 where user = '%s' and subject = '%s';"
                            , DbOperates.getUser() , table1.getValueAt(row , 0)
                    ));
                    ((DefaultTableModel)table2.getModel()).addRow(new Object[]{table1.getValueAt(row , 0)});
                    ((DefaultTableModel)table1.getModel()).removeRow(row);
                } catch (SQLException ex) {

                }
            }
        });
    }
    private void setRemove(JButton button){
        button.addActionListener(e->{
            JTable table1 = ((Page2)this.getParent()).getCenter().getTable(1);
            JTable table2 = ((Page2)this.getParent()).getCenter().getTable(2);
            int[] rows = table2.getSelectedRows();
            for(int i = rows.length - 1 ; i >= 0 ; i--)
            {
                int row = rows[i];
                try {
                    String subject = (String) table2.getValueAt(row , 0);
                    ArrayList<Double> money = DbOperates.getDoubleResults(String.format(
                            "select money from glassskydb.billing where subject = '%s';"
                            , subject
                    ));
                    ArrayList<String> account = DbOperates.getStringResults(String.format(
                            "select account from glassskydb.billing where subject = '%s';"
                            , subject
                    ));
                    ArrayList<String> classes = DbOperates.getStringResults(String.format(
                            "select class from glassskydb.billing where subject = '%s';"
                            , subject
                    ));
                    for(int j = 0 ; j < money.size() ; j ++)
                    {
                        double nowMoney = DbOperates.getDoubleResults(String.format(
                                "select money from glassskydb.account where user = '%s' and account = '%s';"
                                , DbOperates.getUser(), account.get(j)
                        )).get(0);
                        DbOperates.execute(String.format(
                                "update glassskydb.account set money = %f %s %f where user = '%s' and account = '%s';"
                                , nowMoney , "支出".equals(classes.get(j)) ? "+" : "-" , money.get(j) , DbOperates.getUser() , account.get(j)
                        ));
                    }
                    String class_ = DbOperates.getStringResults(String.format(
                            "select class from glassskydb.subject where subject = '%s';",
                            subject
                    )).get(0);
                    DbOperates.execute(String.format(
                            "delete from glassskydb.subject where user = '%s' and subject = '%s';"
                            , DbOperates.getUser() , subject
                    ));
                    DbOperates.execute(String.format(
                            "insert into glassskydb.subject(subject, class, user, enable) value('%s' , '%s' , '%s' , 0);"
                            ,subject , class_ , DbOperates.getUser() , 0
                    ));
                    ((DefaultTableModel)table1.getModel()).addRow(new Object[]{table2.getValueAt(row , 0)});
                    ((DefaultTableModel)table2.getModel()).removeRow(row);
                } catch (SQLException ex) {

                }
            }
        });
    }
}
