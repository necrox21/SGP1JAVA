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
    
    PipedInputStream PFI; //Pipe d'entree
    PipedOutputStream PFO; //Pipe de sortie
    PipedInputStream Lecture; //Pipe de lecture avec le frere
    PipedOutputStream Ecriture; //Pipe d'ecriture avec le frere
    PipedInputStream LectureS; //Pipe de lecture avec le fils S
    PipedOutputStream EcritureS; //Pipe d'ecriture avec le fils S
    PipedInputStream LectureT; //Pipe de lecture avec le fils T
    PipedOutputStream EcritureT; //Pipe d'ecriture avec le fils T
    private List<Integer> tmp = new ArrayList<Integer>(); // Liste tampon
    private List<Integer> S = new ArrayList<Integer>(); // Liste S
    private List<Integer> T = new ArrayList<Integer>(); // Liste T
    private char type; // Type de fils S ou T
    private String recu; // Niveau de récursion dans l'arbre (ex : SSTSS)
    
    ST(char type,String recu)//Constructeur avec initialisation des pipes
    {
        PFI = new PipedInputStream();
        PFO = new PipedOutputStream();
        Lecture = new PipedInputStream();
        Ecriture = new PipedOutputStream();
        LectureS = new PipedInputStream();
        EcritureS = new PipedOutputStream();
        LectureT = new PipedInputStream();        
        EcritureT = new PipedOutputStream();
        this.type = type;
        this.recu = recu;
    }
    
    public static void Separator(List<Integer> tab,List<Integer> tabS,List<Integer> tabT)//Fonction de Separation du tableau en deux sous tableaux le premier est le plus grand en cas de taille impair
    {
        int size = tab.size()/2+tab.size()%2;
        tabS.addAll(tab.subList(0,size));
        tabT.addAll(tab.subList(size,tab.size()));
    }
        
    public static void Read(List<Integer> t,PipedInputStream p)// Fonction de lecture d'une list entière dans un Pipe Input
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
    
    public static void Write(List<Integer> t,PipedOutputStream p)// Fonction d'ecriture d'une list entière dans un Pipe Output
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
    
    public static int Read(PipedInputStream p)// Fonction de lecture d'un Int dans un Pipe Input
    {
        try {
            int r = -1;          
            return r=p.read();    
        } catch (IOException ex) {
            Logger.getLogger(ST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public static void Write(int t,PipedOutputStream p)// Fonction d'ecriture d'un Int dans un Pipe Output
    {
        try {
                p.write(t);  
        } catch (IOException ex) {
                Logger.getLogger(SGP1JAVA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void Exchange()// méthode d'echange entre vers le deuxieme frere via les pipes
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
    public void run()// Méthode principale du Thread
    {
            boolean mode = true; //Mode d'utilisation (true : Tri Parallele || false: Partition ST)
            Read(tmp,PFI);//Lecture du tableau envoyer par le pere
            if(recu=="-1")
            {
                recu=""+type;
                mode = false;
            }
            System.out.println(recu+" Demarre : "+tmp+"\n");
            Exchange();//Appel a la methode d'echange avec le frere
            ST PS = new ST('S',recu+"S");//Fils S
            ST PT = new ST('T',recu+"T");//Fils T
            if(tmp.size()>1&&mode!=false)
            try {
                /*Connexions des Pipes pour les fils*/
                EcritureS.connect(PS.PFI);
                LectureS.connect(PS.PFO);
                EcritureT.connect(PT.PFI);
                LectureT.connect(PT.PFO);
                PS.Ecriture.connect(PT.Lecture);
                PT.Ecriture.connect(PS.Lecture);
                
                Separator(tmp,S,T);//Separation en deux listes
                /*Envoi des deux listes au nouveau fils*/
                Write(S,EcritureS);
                Write(T,EcritureT);
                /*Demarrage des fils*/
                PS.start();
                PT.start();
                PS.join();
                PT.join();
                /*Lecture du resultat du traitement fait par le fils*/
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
            /*Envoi du resultat au Pere*/
            Write(tmp,PFO);
            System.out.println(recu+" Sors : "+tmp+"\n");
    }    
}
