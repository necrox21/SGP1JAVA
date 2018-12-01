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
    public static void main(String[] args){

        try {
            List<Integer> tab = new <Integer>ArrayList();
            tab.addAll(Arrays.asList(3,2,5,1,9,4,7,6,8,0,8));
            int size = tab.size()/2+tab.size()%2;
            List<Integer> S =  new ArrayList<Integer>(tab.subList(0,size));
            List<Integer> T = new ArrayList<Integer>(tab.subList(size,tab.size()));
            System.out.println(tab.toString());
            System.out.println(S.toString());
            System.out.println(T.toString());
            int minIndex = S.indexOf(Collections.min(S));
            int maxIndex = T.indexOf(Collections.max(T));
            
            /*System.out.println(S.get(minIndex)+" "+minIndex);
            System.out.println(T.get(maxIndex)+" "+maxIndex);
            S.remove(minIndex);
            System.out.println(S.toString());*/
            
            int envoieS;
            int envoieT;
            /*while(true)
            {
            int maxS=S.indexOf(Collections.max(S));
            int minT=T.indexOf(Collections.min(T));
            envoieS = S.get(maxS);
            envoieT = T.get(minT);
            if(envoieS>envoieT)
            {
            S.remove(maxS);
            S.add(envoieT);sfsdfsdfsdfsdfsdfsdfsdfsdfsdfsdf
            
            T.remove(minT);
            T.add(envoieS);
            }
            else
            break;
            }
            System.out.println();
            System.out.println(S.toString());
            System.out.println(T.toString());   */
            
            ST o = new ST();
            PipedInputStream res=new PipedInputStream();
            PipedOutputStream val=new PipedOutputStream();
            //o.initPipe(val);
            res.connect(o.IP);
            val.connect(o.IF);
            val.write(10);
            o.start();
            try {
                o.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(SGP1JAVA.class.getName()).log(Level.SEVERE, null, ex);
            }
            int r =0;
            while((r=res.read())!=-1)
            {
                System.out.println(r);
            }
        } catch (IOException ex) {
            Logger.getLogger(SGP1JAVA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
