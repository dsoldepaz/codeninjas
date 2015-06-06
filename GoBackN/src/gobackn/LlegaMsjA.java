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
public class LlegaMsjA extends Evento {

   private static LlegaMsjA instance = null;
   private GoBackN master;
   
   protected LlegaMsjA() {
      
   }
   public static LlegaMsjA getInstance() {
      if(instance == null) {
         instance = new LlegaMsjA();
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
        master= GoBackN.getInstance();
        master.reloj=horaOcurrencia;
        horaOcurrencia= master.reloj+1;
        
    }
        @Override
    public String getNombre() {
        return "Llega Mensaje a A";
    }
    
}
