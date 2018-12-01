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

    public static void Separator(List<Integer> tab,List<Integer> tabS,List<Integer> tabT)
    {
        int size = tab.size()/2+tab.size()%2;
        tabS.addAll(tab.subList(0,size));
        tabT.addAll(tab.subList(size,tab.size()));
    }
    public static void main(String[] args){

        try {
            List<Integer> tab = new <Integer>ArrayList();
            tab.addAll(Arrays.asList(3,2,5,1,9,4,7,6,8,0,8));
            List<Integer> S = new ArrayList();
            List<Integer> T= new ArrayList();
            Separator(tab,S,T);
            System.out.println(tab.toString());
            System.out.println("S : "+S.toString());
            System.out.println("T : "+T.toString());

            /*while(true)
            {
            int maxS=S.indexOf(Collections.max(S));
            int minT=T.indexOf(Collections.min(T));
            envoieS = S.get(maxS);
            envoieT = T.get(minT);
            if(envoieS>envoieT)
            {
            S.remove(maxS);
            S.add(envoieT);
            
            T.remove(minT);
            T.add(envoieS);
            }
            else
            break;
            }
            System.out.println();
            System.out.println(S.toString());
            System.out.println(T.toString());   */
            
            ST PS = new ST('S');
            ST PT = new ST('T');
            PipedInputStream resS=new PipedInputStream();
            PipedOutputStream valS=new PipedOutputStream();
            PipedInputStream resT=new PipedInputStream();
            PipedOutputStream valT=new PipedOutputStream();
            
            resS.connect(PS.PPO);
            valS.connect(PS.PFI);
            
            resT.connect(PT.PPO);
            valT.connect(PT.PFI);
            
            PS.ecriture.connect(PT.lecture);
            PT.ecriture.connect(PS.lecture);

            

            ST.Write(S,valS);
            ST.Write(T,valT);
            PS.start();
            PT.start();
            try {

                PS.join();
                PT.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(SGP1JAVA.class.getName()).log(Level.SEVERE, null, ex);
            }
            int r;
            List<Integer> tmp = new ArrayList<Integer>();
            ST.Read(tmp,resS);
            tmp.clear();
            ST.Read(tmp,resT);

        } catch (IOException ex) {
            Logger.getLogger(SGP1JAVA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
