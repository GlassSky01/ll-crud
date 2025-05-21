package ciallo.glasssky.jdbc.mainWindow.pages.page2;

import ciallo.glasssky.jdbc.Format;
import ciallo.glasssky.jdbc.database.DbOperates;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Center extends JPanel implements Format {
    private DefaultTableModel model1 , model2;
    String[] classes = {"收入", "支出"};
    String[][] subjects = {{"工资收入", "利息收入", "加班收入", "经营所得", "奖金收入"}, {"食品酒水", "衣服饰品", "行车交通", "交流通讯", "休闲娱乐",
            "学习进修", "人情往来", "医疗保健", "居家物业", "金融保险", "其他"}};
    private JTable table1 , table2;
    public Center(Font font ){
        model1 = new DefaultTableModel(new Object[][]{} , new String[]{"未添加"});
        model2 = new DefaultTableModel(new Object[][]{} , new String[]{"已添加"});
        table1 = new JTable(model1);
        table2 = new JTable(model2);
        table1.getTableHeader().setFont(font);
        table2.getTableHeader().setFont(font);
        table1.setFont(font);
        table2.setFont(font);

        JScrollPane pane1 = new JScrollPane(table1);
        JScrollPane pane2 = new JScrollPane(table2);
        this.setLayout(new GridLayout(1 , 2));
        this.add(pane1);
        this.add(pane2);
    }

    @Override
    public void init() {
        table1.setRowHeight(table1.getTableHeader().getHeight());
        table2.setRowHeight(table2.getTableHeader().getHeight());
        for(int i = 0 ; i < 2 ; i ++)
        {
            String class_ = classes[i];
            for(String subject : subjects[i])
            {
                try {
                    DbOperates.execute(String.format(
                            "insert into glassskydb.subject(subject, class, user, enable) value('%s' , '%s' , '%s' , %d);"
                            ,subject , class_ , DbOperates.getUser() , 0
                    ));
                } catch (SQLException e) {

                }
            }
        }
    }

    @Override
    public void end() {

    }
    public JTable getTable(int x){
        if(x == 1)
            return table1;
        return table2;
    }
    public void query(String class_) throws SQLException {
        ArrayList<String> arr1 = DbOperates.getStringResults(String.format(
                "select subject from glassskydb.subject where class = '%s' and user = '%s' and enable = 0"
                , class_ , DbOperates.getUser()
        ));
        model1.setRowCount(0);
        for(String i : arr1)
            model1.addRow(new Object[]{i});

        ArrayList<String> arr2 = DbOperates.getStringResults(String.format(
                "select subject from glassskydb.subject where class = '%s' and user = '%s' and enable = 1"
                , class_ , DbOperates.getUser()
        ));
        model2.setRowCount(0);
        for(String i : arr2)
            model2.addRow(new Object[]{i});
    }
}
