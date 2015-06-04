/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gobackn;

/**
 *
 * @author b06171
 */
public class LiberaA extends Evento {

   private static LiberaA instance = null;
   protected LiberaA() {
      
   }
   public static LiberaA getInstance() {
      if(instance == null) {
         instance = new LiberaA();
      }
      return instance;
   }
    @Override
    public double getHoraOcurrencia() {
        return horaOcurrencia;
    }

    @Override
    public double setHoraOcurrencia(int val) {
        return horaOcurrencia=val;
    }

    @Override
    public void ejecutar() {
    }
    
}
