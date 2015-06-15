/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gobackn;
import java.util.List;
/**
 *
 * @author b06171
 */
public class Estadisticador {
    public List<Double> tiempoTransmision;
    public List<Double> tiempoPermanencia;
    public List<Integer> tamanyoLista;
    
    public double calcularPromedio(List l){
        double sum = 0;
        for(int i = 0; i < l.size() ; ++i){
            sum += Double.parseDouble(l.get(i).toString());
        }
        return sum/l.size();
    }
 
    
    public double tiempoPromedioDeServicio(){
        return (calcularPromedio(tiempoPermanencia)-calcularPromedio(tiempoTransmision) );
    }
    
    public double eficiencia(){
        return calcularPromedio(tiempoTransmision)/(calcularPromedio(tiempoPermanencia)-calcularPromedio(tiempoTransmision));
    }
    public String estadisticasDelPrograma(){
        String res = "";
        res = "Tamaño promedio de la cola de A: " + calcularPromedio(tamanyoLista)+ "\n" +
              "Tiempo promedio de Permacia de un mensaje en la cola: " + calcularPromedio(tiempoPermanencia)+ "\n" + 
              "Tiempo promedio de transmision: " + calcularPromedio(tiempoTransmision)+ "\n"+
               "Tiempo promedio de servicio: " + tiempoPromedioDeServicio()+ "\n"+
                "Eficiencia del sistema: " + eficiencia()+ "\n";
              
        return res;
    }
}
