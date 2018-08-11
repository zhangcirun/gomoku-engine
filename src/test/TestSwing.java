package test;

import javax.swing.*;



public class TestSwing extends JFrame {
    public TestSwing() {
        /*Interuption*/
        JPanel j = new JPanel();
        /**/
        JMenuBar jmb = new JMenuBar();

        JMenu jm1 = new JMenu("文件");
        JMenuItem jmi11 = new JMenuItem("读取文件");
        JMenuItem jmi12 = new JMenuItem("保存文件");
        jm1.add(jmi11);jm1.add(jmi12);

        JMenu jm2 = new JMenu("设置");
        JMenuItem jmi21 = new JMenuItem("设置颜色");
        JMenuItem jmi22 = new JMenuItem("设置字体");
        jm2.add(jmi21);jm2.add(jmi22);

        jmb.add(jm1);
        jmb.add(jm2);
        setJMenuBar(jmb);
        setTitle("测试Demo");// 标题
        setSize(1024, 576);// 窗口大小
        setLocationRelativeTo(null);// 窗口居中
        setDefaultCloseOperation(EXIT_ON_CLOSE);// 窗口点击关闭时,退出程序
        this.add(new JPanel());
        this.add(j);
        setVisible(true);// 窗口可见
    }

    public static void main(String[] args) {
        new TestSwing();
    }

}

