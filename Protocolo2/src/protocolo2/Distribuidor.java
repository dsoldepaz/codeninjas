package protocolo2;


public class Distribuidor {
    
   private static Distribuidor instance = null;
   protected Distribuidor() {
      
   }
   public static Distribuidor getInstance() {
      if(instance == null) {
         instance = new Distribuidor();
      }
      return instance;
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
  
}
