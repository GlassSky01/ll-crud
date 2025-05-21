package ciallo.glasssky.jdbc.mainWindow.pages.page0.Bottom;

import ciallo.glasssky.jdbc.Format;
import ciallo.glasssky.jdbc.Tools;
import ciallo.glasssky.jdbc.database.DbOperates;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class Input extends JPanel implements Format {
    public JTextField money;
    public JComboBox<String> inOrOut, account, subject;
    public JSpinner date;
    public JTextField mark;

    public Input(int h) {
        this.setLayout(new GridLayout(1, 6));
        Font font1 = Tools.getFont(h, 30);
        Font font2 = Tools.getFont(h , 20);
        money = new JTextField();
        inOrOut = new JComboBox<>();
        account = new JComboBox<>();
        subject = new JComboBox<>();

        date = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(date, "yyyy-MM-dd");
        date.setEditor(editor);
        mark = new JTextField();
        money.setFont(font1);
        inOrOut.setFont(font2);
        account.setFont(font1);
        subject.setFont(font1);
        date.setFont(font1);
        mark.setFont(font1);

        inOrOut.addItem("收入");
        inOrOut.addItem("支出");
        inOrOut.setRenderer(Tools.getCenter());
        account.setRenderer(Tools.getCenter());
        subject.setRenderer(Tools.getCenter());
        setInOrOut();
        JComponent[] comp = {money, inOrOut, account, subject, date, mark};
        Tools.add(this, comp);
    }

    @Override
    public void init() throws SQLException {
        inOrOut.setSelectedIndex(0);
        account.removeAllItems();
        try {
            ArrayList<String> arr = DbOperates.getStringResults(String.format(
                    "select account from glassskydb.account where user = '%s' and enable = 1;"
                    , DbOperates.getUser()
            ));

            for (String i : arr)
                account.addItem(i);
        } catch (Exception e) {

        }

    }

    @Override
    public void end() {

    }

    private void setInOrOut() {
        inOrOut.addActionListener(e -> {
            if (e.getSource() == inOrOut) {
                subject.removeAllItems();
                try {
                    ArrayList<String> arr = DbOperates.getStringResults(String.format(
                            "select subject from glassskydb.subject where class = '%s' and user = '%s' and enable = 1;"
                            , inOrOut.getSelectedItem() , DbOperates.getUser()
                    ));
                    for(String i : arr)
                    {
                        subject.addItem(i);
                    }
                } catch (SQLException ex) {
                }
            }
        });
    }
}
