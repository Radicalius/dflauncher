/**
 * Created by Zachary Cotton on 3/27/2017.
 */

import javax.swing.*;
import java.awt.*;

public class Sampler extends JPanel{
    String imageName = "ascii.png";
    ImageIcon icon = new ImageIcon("samples/"+imageName);

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        g2d.fillRect(0,0,860,680);
        icon.paintIcon(this,g,0,0);
        g2d.setColor(new Color(0,0,0,150));
        g2d.fillRect(0,0,860,75);
        g2d.setColor(new Color(255,255,255));
        g2d.setFont(new Font("Times",Font.PLAIN,40));
        g2d.drawString("Dwarf Fortress",300,50);
    }

    public void changeImage(String image){
        System.out.println(image);
        imageName = image;
        icon = new ImageIcon("samples/"+imageName);
        repaint();
    }
}
