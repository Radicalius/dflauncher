import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static java.nio.file.StandardCopyOption.*;

/**
 * Created by Zachary Cotton on 3/27/2017.
 */
public class LaunchThread extends Thread {
    public void run(){
        Pack pack = main.byName.get(main.jcb.getSelectedItem());
        if (!(new File("packs/"+pack.filename).exists())){
            try {
                URL url = new URL(pack.url);
                URLConnection conn = url.openConnection();
                int total = conn.getContentLength();
                InputStream in = conn.getInputStream();
                FileOutputStream out = new FileOutputStream("packs/"+pack.filename);
                int bytesRead = 0;
                byte[] buff = new byte[10240];
                int nb;
                while ((nb = in.read(buff))>0){
                    out.write(buff,0,nb);
                    bytesRead += nb;
                    main.jpb.setValue(Math.round((float)bytesRead/total*100f));
                    main.frame.repaint();
                }
                out.flush();
                out.close();
                in.close();
            } catch (IOException f){ System.out.println(f);}
        }
        try{
            main.jpb.setValue(0);
            //deleteDirectory(new File(main.install+"\\raw"));
            //deleteDirectory(new File(main.install+"\\data"));
            System.out.print(main.install+"\\data");
            ZipFile zf = new ZipFile("packs/"+pack.filename);
            ZipEntry raw = zf.getEntry(pack.path+"/raw");
            ZipEntry data = zf.getEntry(pack.path+"/data");
            ZipInputStream zis = new ZipInputStream(new FileInputStream("packs/"+pack.filename));
            ZipEntry ze;
            int total = zis.available();
            while ((ze = zis.getNextEntry())!= null){
                if (ze.toString().startsWith(pack.path) && !ze.toString().equals(pack.path)) {
                    String fname = main.install+"\\"+ze.toString().replace(pack.path,"").replace("/","\\");
                    if (ze.isDirectory()){
                        new File(fname).mkdir();
                    }else{
                        copy(zf.getInputStream(ze),new FileOutputStream(fname));
                    }
                }
            }
        } catch (IOException e){ System.out.println(e);}
        try {
            Runtime.getRuntime().exec(new String[] {main.install+"\\Dwarf Fortress.exe"},null, new File(main.install));
            System.exit(0);
        } catch (Exception f){
            System.out.println(f);
        }
    }

    public void deleteDirectory(File f){
        for (File fi: f.listFiles()){
            if (fi.isDirectory()){
                deleteDirectory(fi);
            }else{
                fi.delete();
            }
        }
        f.delete();
    }

    public void copy(InputStream in, FileOutputStream out){
        byte[] buff = new byte[10240];
        int nb;
        try {
            while ((nb = in.read(buff)) > 0) {
                out.write(buff,0, nb);
            }
            in.close();
            out.flush();
            out.close();
        }catch (IOException b){System.out.println(b);}
    }
}
