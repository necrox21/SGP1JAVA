/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgp1java;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BrianT
 */
public class ST extends Thread{
    
    PipedInputStream IF;
    PipedOutputStream IP;
    private int tarace = -1;
    
    ST()
    {
        IF = new PipedInputStream();
        IP = new PipedOutputStream();
    }
    
   /* public void initPipe(PipedOutputStream po)
    {
        IF
    }*/
    
    @Override
    public void run()
    {
        try {
            tarace = IF.read();
        } catch (IOException ex) {
            Logger.getLogger(ST.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int i = 0;i<10;i++)
            tarace +=1;
        try {
            IP.write(tarace);
            IP.close();
        } catch (IOException ex) {
            Logger.getLogger(ST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
