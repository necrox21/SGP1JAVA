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
    PipedInputStream LectureS;
    PipedOutputStream EcritureS;
    PipedInputStream LectureT;
    PipedOutputStream EcritureT;
    private List<Integer> tmp = new ArrayList<Integer>();
    private List<Integer> S = new ArrayList<Integer>();
    private List<Integer> T = new ArrayList<Integer>();
    private char type;
    private String recu;
    
    ST(char type,String recu)
    {
        PFI = new PipedInputStream();
        PPO = new PipedOutputStream();
        Lecture = new PipedInputStream();
        Ecriture = new PipedOutputStream();
        LectureS = new PipedInputStream();
        EcritureS = new PipedOutputStream();
        LectureT = new PipedInputStream();        
        EcritureT = new PipedOutputStream();
        this.type = type;
        this.recu = recu;
    }
    
    public static void Separator(List<Integer> tab,List<Integer> tabS,List<Integer> tabT)
    {
        int size = tab.size()/2+tab.size()%2;
        tabS.addAll(tab.subList(0,size));
        tabT.addAll(tab.subList(size,tab.size()));
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
            System.out.println(recu+" Demarre : "+tmp);
            Exchange();
            ST PS = new ST('S',recu+"S");
            ST PT = new ST('T',recu+"T");
            if(tmp.size()>1)
            try {
                EcritureS.connect(PS.PFI);
                LectureS.connect(PS.PPO);
                EcritureT.connect(PT.PFI);
                LectureT.connect(PT.PPO);
                PS.Ecriture.connect(PT.Lecture);
                PT.Ecriture.connect(PS.Lecture);
                Separator(tmp,S,T);
                Write(S,EcritureS);
                Write(T,EcritureT);
               PS.start();
               PT.start();
               PS.join();
               PT.join();
               Read(S,LectureS);
               Read(T,LectureT);
               tmp.clear();
               tmp.addAll(S);
               tmp.addAll(T);
        } catch (IOException ex) {
            Logger.getLogger(ST.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ST.class.getName()).log(Level.SEVERE, null, ex);
        }
            Write(tmp,PPO);
            System.out.println(recu+" Sors : "+tmp);
    }    
}
