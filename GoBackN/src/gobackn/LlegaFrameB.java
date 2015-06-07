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
public class LlegaFrameB extends Evento {

   private static LlegaFrameB instance = null;
   protected LlegaFrameB() {
   }
   public static LlegaFrameB getInstance() {
      if(instance == null) {
         instance = new LlegaFrameB();
      }
      return instance;
   }
    @Override
    public double getHoraOcurrencia() {
        return horaOcurrencia;
    }

    @Override
    public double setHoraOcurrencia(double val) {
        return horaOcurrencia=val;
    }

    @Override
    public void ejecutar() {
        
    }
        @Override
    public String getNombre() {
        return "Llega frame a B";
    }
}
