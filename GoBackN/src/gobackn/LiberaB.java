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
public class LiberaB extends Evento {

   private static LiberaB instance = null;
   protected LiberaB() {
      
   }
   public static LiberaB getInstance() {
      if(instance == null) {
         instance = new LiberaB();
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
        return "Se libera B";
    }
    
}