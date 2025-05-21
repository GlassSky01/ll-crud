package ciallo.glasssky.jdbc.mainWindow;


import ciallo.glasssky.jdbc.Format;
import ciallo.glasssky.jdbc.Tools;
import ciallo.glasssky.jdbc.database.DbOperates;
import ciallo.glasssky.jdbc.mainWindow.pages.*;
import ciallo.glasssky.jdbc.mainWindow.pages.page0.Page0;
import ciallo.glasssky.jdbc.mainWindow.pages.page1.Page1;
import ciallo.glasssky.jdbc.mainWindow.pages.page2.Page2;
import ciallo.glasssky.jdbc.mainWindow.pages.page3.Page3;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;


public class MainWindow extends JFrame implements Format {
    private final double size;
    private final JPanel[] comp = new JPanel[3];
    private final JLabel[] warning = new JLabel[2];
    private final Page[] pages = new Page[4];
    private final JButton[] buttons = new JButton[4];
    private final int[] ratio = {1, 10, 2};
    private final int[] heights = new int[3];
    private final String[] name = {"记账", "账户", "科目", "个人"};
    private final int w, h;
    private Format nowPage;
    private final JPanel emptyPanel = new JPanel();
    private MyMenu menu;

    public MainWindow() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("个人记账管理系统");
        size = Tools.H * 3 / 4;

        int x = (int) ((Tools.W - size) / 2);
        int y = (int) ((Tools.H - size) / 2);
        w = (int) size;
        h = (int) size;
        this.setBounds(x, y, w, h);

        comp[0] = new JPanel();
//        comp[0].setBackground(Color.CYAN);
        comp[1] = new JPanel();
//        comp[1].setBackground(Color.PINK);
        comp[2] = new JPanel();
//        comp[2].setBackground(Color.LIGHT_GRAY);
        for (int i = 0; i < 3; i++) {
            heights[i] = (int) (1.0 * h * ratio[i] / (ratio[0] + ratio[1] + ratio[2]));
            comp[i].setPreferredSize(new Dimension(w, heights[i]));

        }
        this.add(comp[0], BorderLayout.NORTH);
        this.add(comp[1], BorderLayout.CENTER);
        this.add(comp[2], BorderLayout.SOUTH);
        initAll();
    }

    private void initAll() {
        menu = new MyMenu(h);
        this.setJMenuBar(menu);
        initTop();
        initMain();
        initBottom();

    }

    private void initTop() {
        Font font = Tools.getFont(heights[0], 2);
        int dx = w / 7;
        int dy = (int) ((heights[0] - font.getSize()) / 2.0);
        comp[0].setLayout(new FlowLayout(FlowLayout.CENTER, dx, dy));
        warning[0] = new JLabel();
        warning[1] = new JLabel();
        for (int i = 0; i < 2; i++) {
            warning[i].setFont(font);
            comp[0].add(warning[i]);
        }
    }

    private void initMain() {
        comp[1].setLayout(new CardLayout());
        pages[0] = new Page0(comp[1], heights[1]);
        pages[1] = new Page1(comp[1], heights[1]);
        pages[2] = new Page2(comp[1], heights[1]);
        pages[3] = new Page3(comp[1], heights[1]);
        comp[1].add(emptyPanel, "-1");
        for (int i = 0; i < 4; i++)
            comp[1].add(pages[i], Integer.toString(i));
    }

    private void initBottom() {

        int h = heights[2];
        comp[2].setLayout(new GridLayout(1, 4));
        Font font = Tools.getFont(h, 2.5);
        for (int i = 0; i < 4; i++) {
            buttons[i] = new JButton(name[i]);
            buttons[i].setFont(font);
            int finalI = i;
            buttons[i].addActionListener(e -> {
                for(int j = 0 ; j < 4 ; j ++)
                    buttons[j].setText(name[j]);
                buttons[finalI].setText(">" + name[finalI] + "<");
                try {
                    pages[finalI].init();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                switchover(String.valueOf(finalI));
            });
            comp[2].add(buttons[i]);
        }

    }

    public void switchover(String name) {
        CardLayout layout = (CardLayout) comp[1].getLayout();
        layout.show(comp[1], name);
    }

    @Override
    public void init() throws SQLException {
        resetWarning();
        switchover("-1");
        this.setVisible(true);
    }
    @Override
    public void end() {
        this.setVisible(false);
        for(int i = 0 ; i < 4 ; i ++)
            buttons[i].setText(name[i]);
        if (nowPage != null)
            nowPage.end();

    }
    public void resetWarning() throws SQLException {
        LocalDate date = LocalDate.now();
        LocalDate mon = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sun = date.with(TemporalAdjusters.nextOrSame((DayOfWeek.SUNDAY)));
        LocalDate first = date.withDayOfMonth(1);
        LocalDate last = date.with(TemporalAdjusters.lastDayOfMonth());
        double week = DbOperates.getDoubleResults(String.format(
                "select sum(money) from glassskydb.billing \n" +
                        "where user = '%s' and class = '支出' and '%s' <= date and date <= '%s';"
                ,DbOperates.getUser() , mon , sun
        )).get(0);
        double month = DbOperates.getDoubleResults(String.format(
                "select sum(money) from glassskydb.billing \n" +
                        "where user = '%s' and class = '支出' and '%s' <= date and date <= '%s';"
                ,DbOperates.getUser() , first , last
        )).get(0);
        warning[0].setText(String.format("本月支出 : %.2f" , month));
        warning[1].setText(String.format("本周支出 : %.2f" , week));
    }


}

