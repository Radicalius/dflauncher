import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Zachary Cotton on 3/27/2017.
 */
public class Listener implements ActionListener{
    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals("JCBCHANGE")) {
            main.sampler.changeImage(main.byName.get(main.jcb.getSelectedItem()).image);
        }
        if (e.getActionCommand().equals("LAUNCH")){
            LaunchThread lt = new LaunchThread();
            lt.start();
        }if (e.getActionCommand().equals("INSTALLATION")){
            JFileChooser jf = new JFileChooser();
            jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (!main.install.equals("")) {
                jf.setSelectedFile(new File(main.install));
            }
            if (jf.showOpenDialog(main.frame) == JFileChooser.APPROVE_OPTION){
                main.install = jf.getSelectedFile().getPath();
                main.dir.setText("Select Installation: "+main.install);
                try {
                    PrintStream ps = new PrintStream(new FileOutputStream("config/installation"));
                    ps.println(main.install);
                } catch (IOException i){
                    System.out.println("Unable to cache installation directory");
                }
            }
        }
    }
}
