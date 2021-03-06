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

public class Distribuidor {
    public enum EstadoMensaje {
    LLEGO,PERDIDO,ERROR 
    }
   private static Distribuidor instance = null;
   protected Distribuidor() {
      
   }
   public static Distribuidor getInstance() {
      if(instance == null) {
         instance = new Distribuidor();
      }
      return instance;
   }
   public EstadoMensaje distribucionLlegaMensajeB(){
       double r1 = Math.random();
       if(r1 < 0.15){
           return EstadoMensaje.PERDIDO;
       }
       if(r1 < 1){
           return EstadoMensaje.LLEGO;
       }
       return null;
   }
   public EstadoMensaje distribucionLlegaACKaA(){
        double r1 = Math.random();
       if(r1 < 0.05){
           return EstadoMensaje.PERDIDO;
       }
       if(r1 < 0.15){
           return EstadoMensaje.ERROR;
       }
       if(r1 < 1){
           return EstadoMensaje.LLEGO;
       }
       return null;
    }
   public double distribucionLlegaMensajeA(){
       return Zn() + 25;
   }
   private double Zn(){
       return Math.pow((-2.0*Math.log(Math.random())),(1.0/2.0))*Math.sin(2.0*Math.PI*Math.random());
   }
   public double distribucionConvertirMensaje(){
       return (-1.0/2.0)*Math.log(Math.random());
   }
   public double revisaFrame(){
       return Math.sqrt(5*Math.random()+4);
   }
   public EstadoMensaje perdidoACK(){
       if(Math.random()<0.15){
           return EstadoMensaje.PERDIDO;
       }
       return EstadoMensaje.LLEGO;
   }
}
