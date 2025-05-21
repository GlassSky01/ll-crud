package ciallo.glasssky.jdbc.mainWindow;

import ciallo.glasssky.jdbc.Main;
import ciallo.glasssky.jdbc.Tools;
import ciallo.glasssky.jdbc.database.DbOperates;
import test.FileChooser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class MyMenu extends JMenuBar {
    public MyMenu(int h) {
        Font font = Tools.getFont(h, 20);
        JMenu file = new JMenu("文件");
        JMenu helpMenu = new JMenu("帮助");
        file.setFont(font);
        helpMenu.setFont(font);
        this.add(file);
        this.add(helpMenu);

        JMenuItem save = new JMenuItem("保存");
        JMenuItem imp = new JMenuItem("导入");
        save.setFont(font);
        imp.setFont(font);
        file.add(save);
        file.add(imp);

        JMenuItem help = new JMenuItem("使用说明");
        help.setFont(font);
        helpMenu.add(help);

        JMenuItem fileHelp = new JMenuItem("文件说明");
        fileHelp.setFont(font);
        helpMenu.add(fileHelp);

        help.addActionListener(e -> {
            System.out.println("使用说明");
        });

        fileHelp.addActionListener(e -> {
            System.out.println("文件说明");
        });

        save.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            fileChooser.setDialogTitle("保存文件");
            fileChooser.setSelectedFile(new File("未命名"));
            int value = fileChooser.showSaveDialog(null);
            if (value == JFileChooser.APPROVE_OPTION) {
                File f;
                String path = fileChooser.getSelectedFile().toString();
                if (path.endsWith(".txt"))
                    f = new File(path);
                else
                    f = new File(path + ".txt");
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {


                    ArrayList<Object[]> arr = DbOperates.query(String.format(
                            "select account , money from glassskydb.account where user = 'a' and enable = 1;"
                            , DbOperates.getUser()
                    ), new String[]{"string", "double"});
                    bw.write(Integer.toString(arr.size()));
                    bw.newLine();
                    for (Object[] obj : arr) {
                        bw.write(String.format("%s %s", obj[0], obj[1]));
                        bw.newLine();
                    }
                    arr = DbOperates.query(String.format(
                            "select subject , class from glassskydb.subject where user = '%s' and enable = 1;"
                            , DbOperates.getUser()
                    ), new String[]{"string", "string"});
                    bw.write(Integer.toString(arr.size()));
                    bw.newLine();
                    for (Object[] obj : arr) {
                        bw.write(String.format("%s %s", obj[0], obj[1]));
                        bw.newLine();
                    }
                    arr = DbOperates.query(String.format(
                            "select money , class , account , subject , date , remark from glassskydb.billing\n" +
                                    "where user = '%s';"
                            , DbOperates.getUser()
                    ), new String[]{"double", "string", "string", "string", "string", "string"});
                    bw.write(Integer.toString(arr.size()));
                    bw.newLine();
                    for (Object[] obj : arr) {
                        bw.write(String.format("%f %s %s %s %s %s", obj));
                        bw.newLine();
                    }


                    Tools.setWarning(this.getParent(), "保存成功", "notice");
                } catch (Exception ex) {
                    Tools.setWarning(this.getParent(), "保存失败", "warning");
                }


            }
        });

        imp.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            fileChooser.setDialogTitle("导入文件");
            fileChooser.setFileFilter(new FileNameExtensionFilter("文本文件", "txt"));
            fileChooser.setSelectedFile(new File("未命名"));
            int value = fileChooser.showOpenDialog(null);
            if (value == JFileChooser.APPROVE_OPTION) {
                File f = fileChooser.getSelectedFile();
                try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                    int a = Integer.parseInt(br.readLine());
                    ArrayList<Object[]> arr = new ArrayList<>();
                    for (int i = 0; i < a; i++) {
                        arr.add(br.readLine().split(" "));
                        if (arr.get(arr.size() - 1).length != 2)
                            throw new Exception();
                    }
                    int b = Integer.parseInt(br.readLine());
                    ArrayList<Object[]> brr = new ArrayList<>();
                    for (int i = 0; i < b; i++) {
                        brr.add(br.readLine().split(" "));
                        if (brr.get(brr.size() - 1).length != 2)
                            throw new Exception();
                    }
                    int c = Integer.parseInt(br.readLine());
                    ArrayList<Object[]> crr = new ArrayList<>();
                    for (int i = 0; i < c; i++) {
                        crr.add(br.readLine().split(" "));
                        if (crr.get(crr.size() - 1).length != 5 && crr.get(crr.size() - 1).length != 6)
                            throw new Exception();
                    }
                    DbOperates.execute(String.format(
                            "delete from glassskydb.account where user = '%s';"
                            , DbOperates.getUser()
                    ));
                    DbOperates.execute(String.format(
                            "delete from glassskydb.subject where user = '%s';"
                            , DbOperates.getUser()
                    ));
                    DbOperates.execute(String.format(
                            "delete from glassskydb.billing where user = '%s';"
                            , DbOperates.getUser()
                    ));
                    for (int i = 0; i < a; i++) {
                        Object[] obj = arr.get(i);
                        DbOperates.execute(String.format(
                                "insert into glassskydb.account value('%s' , '%s' , '%s' , 1);"
                                , obj[0], DbOperates.getUser(), obj[1]
                        ));
                    }
                    for (int i = 0; i < b; i++) {
                        Object[] obj = brr.get(i);
                        DbOperates.execute(String.format(
                                "insert into glassskydb.subject value('%s' , '%s' , '%s' , 1);"
                                , obj[0], obj[1], DbOperates.getUser()
                        ));
                    }
                    for (int i = 0; i < c; i++) {
                        Object[] obj = crr.get(i);
                        String sql = String.format(
                                "insert into glassskydb.billing" +
                                        " value(%s , '%s' , '%s' , '%s' , '%s' , '%s' , '%s') ;"
                                , obj[0] , obj[1] , obj[2] , obj[3] , obj[4] , obj.length == 6 ? obj[5]:"",DbOperates.getUser()
                        );
                        System.out.println(sql);
                        DbOperates.execute(sql);
                    }

                    Main.window.init();
                    Tools.setWarning(this.getParent(), "导入成功", "notice");
                } catch (Exception ex) {
                    Tools.setWarning(this.getParent(), "导入失败", "warning");
                }


            }
        });

    }
}
