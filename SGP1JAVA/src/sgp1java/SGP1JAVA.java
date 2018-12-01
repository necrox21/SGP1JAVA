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


    
    public static int Aleatoire()//Fonction generation int aleatoire
    {
        return ((int)(Math.random()*(MAX-MIN+1) + MIN));
    }
    
    public static void TriST(List<Integer> tab)//Tri Parallele ST
    {
        System.out.println("=====Debut TriST=====");
        List<Integer> S = new ArrayList<Integer>();
        List<Integer> T= new ArrayList<Integer>();
        ST.Separator(tab,S,T);//Separation du tableau en sous listes S et T
        System.out.println("Tableau : "+tab.toString()+"\n");
        ST PS = new ST('S',"S");//Creation fils S
        ST PT = new ST('T',"T");//Creation fils T
        /*Creation Pipe Communication avec le Pere*/
        PipedInputStream resS=new PipedInputStream();
        PipedOutputStream valS=new PipedOutputStream();
        PipedInputStream resT=new PipedInputStream();
        PipedOutputStream valT=new PipedOutputStream();
        List<Integer> tmp= new ArrayList<Integer>();
        try {
            /*Initialisation Connexion*/
            valS.connect(PS.PFI);
            resS.connect(PS.PFO);
            valT.connect(PT.PFI);
            resT.connect(PT.PFO);
            PS.Ecriture.connect(PT.Lecture);
            PT.Ecriture.connect(PS.Lecture);
            /*Envoi Des tableaux S et T au Fils*/
            ST.Write(S,valS);
            ST.Write(T,valT);
            /*Demarrage des fils*/
            PS.start();
            PT.start();
            PS.join();
            PT.join();
            /*Recuperation des resultats des fils*/
            ST.Read(S, resS);
            ST.Read(T, resT);
            tmp.addAll(S);
            tmp.addAll(T);
            System.out.println("Fin : "+tmp);
        } catch (IOException ex){
            Logger.getLogger(SGP1JAVA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(SGP1JAVA.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("=====Fin TriST=====\n");
    }
    
    public static void Partition(List<Integer> tab)//Partition ST
    {
        System.out.println("=====Debut Partition=====");
        List<Integer> S = new ArrayList<Integer>();
        List<Integer> T= new ArrayList<Integer>();
        ST.Separator(tab,S,T);//Separation du tableau en sous listes S et T
        System.out.println("Tableau : "+tab.toString()+"\n");
        ST PS = new ST('S',"-1");//Creation fils S
        ST PT = new ST('T',"-1");//Creation fils T
        /*Creation Pipe Communication avec le Pere*/
        PipedInputStream res=new PipedInputStream();
        PipedOutputStream val=new PipedOutputStream();
        PipedInputStream res2=new PipedInputStream();
        PipedOutputStream val2=new PipedOutputStream();
        List<Integer> tmp= new ArrayList<Integer>();
        try {
            /*Initialisation Connexion*/
            val.connect(PS.PFI);
            res.connect(PS.PFO);
            val2.connect(PT.PFI);
            res2.connect(PT.PFO);
            PS.Ecriture.connect(PT.Lecture);
            PT.Ecriture.connect(PS.Lecture);
            /*Envoi Des tableaux S et T au Fils*/
            ST.Write(S,val);
            ST.Write(T,val2);
            /*Demarrage des fils*/
            PS.start();
            PT.start();
            PS.join();
            PT.join();
            /*Recuperation des resultats des fils*/
            ST.Read(S, res);
            ST.Read(T, res2);
            tmp.addAll(S);
            tmp.addAll(T);
            System.out.println("Fin : "+tmp);
        } catch (IOException ex) {
            Logger.getLogger(SGP1JAVA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(SGP1JAVA.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("=====Fin Partition=====\n");
    }
    
    public static void main(String[] args){
        /*Remplisage du tableau*/
        List<Integer> tab = new ArrayList<Integer>();
        for(int i = 0 ;i<TailleT;i++)
        {
            tab.add(Aleatoire());tab.add(Aleatoire());
        }
         
        Partition(tab);
        TriST(tab);

    }
    
}
