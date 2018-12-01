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
    PipedInputStream Lecture;
    PipedOutputStream Ecriture;
    private List<Integer> tmp = new ArrayList<Integer>();
    private char type;
    
    ST(char type)
    {
        PFI = new PipedInputStream();
        PPO = new PipedOutputStream();
        Lecture = new PipedInputStream();
        Ecriture = new PipedOutputStream();
        this.type = type;
    }
    
    public static void Read(List<Integer> t,PipedInputStream p)
    {
        t.clear();
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
    
    public static int Read(PipedInputStream p)
    {
        try {
            int r = -1;          
            return r=p.read();    
        } catch (IOException ex) {
            Logger.getLogger(ST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public static void Write(int t,PipedOutputStream p)
    {
        try {
                p.write(t);  
        } catch (IOException ex) {
                Logger.getLogger(SGP1JAVA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void Exchange()
    {
        int pos;
            int e;
            int l;
            while(true)
            {
                if(type=='S'||type=='s')
                {
                    pos = tmp.indexOf(Collections.max(tmp));
                }
                else if(type=='T'||type=='t')
                {
                    pos = tmp.indexOf(Collections.min(tmp));
                }
                else
                    break;
                e = tmp.get(pos);
                Write(e,Ecriture);
                l = Read(Lecture);
            
                if( ((type=='S'||type=='s')&&e>l)||((type=='T'||type=='t'))&&e<l)
                {
                    tmp.remove(pos);
                    tmp.add(l);
                }
                else
                    break;
                //System.out.println(type+" envoi : "+e+" lit : l");
            }
    }
    
    @Override
    public void run()
    {
            Read(tmp,PFI);
            System.out.println(type+" Demarre : "+tmp);
            Exchange();
            Write(tmp,PPO);
            System.out.println(type+" Sors : "+tmp);
    }    
}
