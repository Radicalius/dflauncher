/**
 * Created by Zachary Cotton on 3/27/2017.
 */

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.Border;

public class main {

    static ArrayList<Pack> packs = new ArrayList<Pack>();
    static HashMap<String, Pack> byName = new HashMap<>();
    static JComboBox<String> jcb;
    static Sampler sampler;
    static String install = "";
    static JFrame frame;
    static JButton dir;
    static JProgressBar jpb;

    public static void loadPacks(){
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream("config/packs")));
            while (bf.ready()){
                String[] cmds = bf.readLine().split("   ");
                Pack p = new Pack(cmds[0]);
                packs.add(p);
                p.image = cmds[1].trim();
                p.filename = cmds[2].trim();
                p.url = cmds[3].trim();
                p.path = cmds[4].trim().replace("|","");
                byName.put(cmds[0], p);
            }
        } catch (Exception e){
            System.out.println("Pack file not found");
        }
    }

    public static void loadInstallation(){
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream("config/installation")));
            while (bf.ready()) {
                install = bf.readLine();
            }
        } catch (IOException e){}
    }

    public static void main(String[] args) {
        loadPacks();
        loadInstallation();

        Listener l = new Listener();

        frame = new JFrame("DF Launcher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(860,680);
        frame.setResizable(false);
        frame.setIconImage(new ImageIcon("samples/dwarfsx0.jpg").getImage());

        BorderLayout bl = new BorderLayout();
        frame.setLayout(bl);

        sampler = new Sampler();
        frame.add(sampler, BorderLayout.CENTER);

        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());

        jcb = new JComboBox<>();
        for (Pack p: packs){
            jcb.addItem(p.name);
        }
        jcb.setActionCommand("JCBCHANGE");
        jcb.addActionListener(l);
        jp.add(jcb,BorderLayout.LINE_START);

        JButton run = new JButton("Launch");
        run.setPreferredSize(new Dimension(100,25));
        run.setActionCommand("LAUNCH");
        run.addActionListener(l);
        jp.add(run,BorderLayout.CENTER);

        dir = new JButton("Select Installation: "+main.install);
        dir.setActionCommand("INSTALLATION");
        dir.addActionListener(l);
        jp.add(dir, BorderLayout.LINE_END);

        jpb = new JProgressBar();
        jpb.setMaximum(100);
        jp.add(jpb, BorderLayout.PAGE_START);

        frame.add(jp,BorderLayout.PAGE_END);

        frame.setVisible(true);
    }
}
