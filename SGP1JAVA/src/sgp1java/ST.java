/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgp1java;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BrianT
 */
public class ST extends Thread{
    
    PipedInputStream PFI;
    PipedOutputStream PPO;
    private List<Integer> tmp = new ArrayList<Integer>();
        private List<Integer> tmp2 = new ArrayList<Integer>();
    private char type;
    
    ST(char type)
    {
        PFI = new PipedInputStream();
        PPO = new PipedOutputStream();
        this.type = type;
    }
    
    public static void Read(List<Integer> t,PipedInputStream p)
    {
        try {
            int size=p.read();

            int r = -1;
            for(int i =0;i<size;i++)
            {               
                r=p.read();
                 t.add(r);    
            }
        } catch (IOException ex) {
            Logger.getLogger(ST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void Write(List<Integer> t,PipedOutputStream p)
    {
        try {
            p.write(t.size());
                        
            for(int i=0; i< t.size();i++)
            {
                p.write(t.get(i));  
            }
        } catch (IOException ex) {
                Logger.getLogger(SGP1JAVA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        
    @Override
    public void run()
    {
            Read(tmp,PFI);
            Read(tmp2,PFI);
            Write(tmp,PPO);
            Write(tmp2,PPO);
            System.out.println(type+" "+tmp);
            System.out.println(type+" "+tmp2);
    }    
}
