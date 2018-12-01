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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BrianT
 */
public class SGP1JAVA {

    /**
     * @param args the command line arguments
     */
    
    static final int TailleT = 10;
    static final int MIN = 0;
    static final int MAX = 100;


    
    public static int Aleatoire()
    {
        return ((int)(Math.random()*(MAX-MIN+1) + MIN));
    }
    
    public static void main(String[] args){

        List<Integer> tab = new <Integer>ArrayList();
        //tab.addAll(Arrays.asList(3,2,5,1,9,4,7,6,8,0));
        for(int i = 0 ;i<TailleT;i++)
        {
            tab.add(Aleatoire());tab.add(Aleatoire());
        }
                
        List<Integer> S = new ArrayList();
        List<Integer> T= new ArrayList();
        ST.Separator(tab,S,T);
        System.out.println(tab.toString());
        ST PS = new ST('S',"S");
        ST PT = new ST('T',"T");
        PipedInputStream res=new PipedInputStream();
        PipedOutputStream val=new PipedOutputStream();
        List<Integer> tmp= new ArrayList();
        List<Integer> tmp2= new ArrayList();
        PipedInputStream res2=new PipedInputStream();
        PipedOutputStream val2=new PipedOutputStream();
        try {
            val.connect(PS.PFI);
            res.connect(PS.PPO);
            val2.connect(PT.PFI);
            res2.connect(PT.PPO);
            PS.Ecriture.connect(PT.Lecture);
            PT.Ecriture.connect(PS.Lecture);
            ST.Write(S,val);
            ST.Write(T,val2);
            PS.start();
            PT.start();
            PS.join();
            PT.join();
            ST.Read(tmp, res);
            ST.Read(tmp2, res2);
            tmp.addAll(tmp2);
            System.out.println("Fin : "+tmp);
        } catch (IOException ex) {
            Logger.getLogger(SGP1JAVA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(SGP1JAVA.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        
    }
    
}
