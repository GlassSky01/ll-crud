package ciallo.glasssky.jdbc.mainWindow.pages.page0.Bottom;

import ciallo.glasssky.jdbc.Main;
import ciallo.glasssky.jdbc.Tools;
import ciallo.glasssky.jdbc.database.DbOperates;
import ciallo.glasssky.jdbc.mainWindow.MainWindow;
import ciallo.glasssky.jdbc.mainWindow.pages.page0.Page0;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class Buttons extends JPanel {
    public Buttons(int h) {
        JButton add = new JButton("添加");
        JButton remove = new JButton("删除");
        this.setLayout(new GridLayout(1, 2));
        Font font = Tools.getFont(h, 15);
        add.setFont(font);
        remove.setFont(font);
        setAdd(add);
        setRemove(remove);
        this.add(add);
        this.add(remove);
    }

    public void setAdd(JButton button) {
        button.addActionListener(e -> {
            Input input = ((Bottom) this.getParent()).input;
            double money;
            try {
                money = Double.parseDouble(input.money.getText());
                if (money < 0) {
                    Tools.setWarning((Component) Main.window, "金额要大于0" , "Warning");
                    return;
                }
            } catch (Exception xx) {
                Tools.setWarning((Component) Main.window,"金额必须是数字" , "Warning");
                return;
            }
            String inOrOut = (String) input.inOrOut.getSelectedItem();
            String account = (String) input.account.getSelectedItem();
            String subject = (String) input.subject.getSelectedItem();
            if(account == null)
            {
                Tools.setWarning((Component) Main.window,"请先添加账户" , "Warning");
                return;
            }
            if(subject == null)
            {
                Tools.setWarning((Component) Main.window,"请先添加科目" , "Warning");
                return;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dateFormat.format(input.date.getValue());
            String mark = input.mark.getText();
            try {
                DbOperates.execute(String.format(
                        "insert into glassskydb.billing(money, class, account, subject, date, remark, user)\n" +
                                "value(%f , '%s' , '%s' , '%s' , '%s' , '%s' , '%s');"
                        , money, inOrOut, account, subject, date, mark, DbOperates.getUser()
                ));
                double nowMoney = DbOperates.getDoubleResults(String.format(
                        "select money from glassskydb.account where user = '%s' and account = '%s' ;"
                        , DbOperates.getUser(), account
                )).get(0);
                DbOperates.execute(String.format(
                        "update glassskydb.account set money = %f %s %f where user = '%s' and account = '%s';"
                        , nowMoney, "收入".equals(inOrOut) ? "+" : "-", money, DbOperates.getUser(), account
                ));
                DefaultTableModel model = ((Page0) this.getParent().getParent()).getCenter().model;
                model.addRow(new Object[]{money, inOrOut, account, subject, date, mark});

                ((MainWindow)Main.window).resetWarning();

            } catch (Exception xx) {

            }

        });
    }

    public void setRemove(JButton button) {
        button.addActionListener(e -> {
            JTable table = ((Page0) this.getParent().getParent()).getCenter().table;
            int[] rows = table.getSelectedRows();
            for (int i = rows.length - 1; i >= 0; i--) {
                int row = rows[i];
                Object[] data = new Object[6];
                for (int j = 0; j < data.length; j++) {
                    data[j] = table.getModel().getValueAt(row, j);
                }
                try {
                    double nowMoney = DbOperates.getDoubleResults(String.format(
                            "select money from glassskydb.account where user = '%s' and account = '%s' ;"
                            , DbOperates.getUser(), data[2]
                    )).get(0);
                    DbOperates.execute(String.format(
                            "delete from glassskydb.billing where money = %f and class = '%s' and account = '%s' \n" +
                                    "and subject = '%s' and date = '%s' and remark = '%s' and user = '%s' limit 1;"
                            , data[0], data[1], data[2], data[3], data[4], data[5], DbOperates.getUser()
                    ));
                    DbOperates.execute(String.format(
                            "update glassskydb.account set money = %f %s %f where account = '%s' and user = '%s';"
                            ,nowMoney , "支出".equals(data[1]) ? "+" : "-" ,  data[0]
                            ,data[2] , DbOperates.getUser()
                    ));
                    ((DefaultTableModel)table.getModel()).removeRow(row);
                    ((MainWindow)Main.window).resetWarning();
                } catch (SQLException ex) {
                }
            }
        });
    }

}
