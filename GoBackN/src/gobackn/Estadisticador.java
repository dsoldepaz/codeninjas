/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gobackn;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author b06171
 */
public class Estadisticador {
    public List<Double> tiempoTransmision;
    public List<Double> tiempoPermanencia;
    public List<Integer> tamanyoLista;
    public double tiempoUltimoAgregado ;
    public List<Double> tiempoTamanyoLista;
    
    public double PromedioTamnyo = 0;
    public double PromedioTiempoPermanencia = 0;
    public double PromedioTransmision = 0;
    public double PromedioDeServicio = 0;
    public double Eficiencia = 0;
    public Estadisticador(){
        tiempoTransmision = new ArrayList<>();
        tiempoPermanencia= new ArrayList<>();
        tamanyoLista= new ArrayList<>();
        tiempoUltimoAgregado = 0;
        tiempoTamanyoLista= new ArrayList<>();
    }
    public double calcularPromedio(List l){
        double sum = 0;
        for(int i = 0; i < l.size() ; ++i){
            sum += Double.parseDouble(l.get(i).toString());
        }
        return sum/l.size();
    }
    
    public double sum(List l){
        double sum = 0;
        for(int i = 0; i < l.size() ; ++i){
            sum += Double.parseDouble(l.get(i).toString());
        }
        return sum;
    }
    public double tiempoPromedioDeServicio(){
        return (calcularPromedio(tiempoPermanencia)-calcularPromedio(tiempoTransmision) );
    }
    
    public double eficiencia(){
        return calcularPromedio(tiempoTransmision)/(calcularPromedio(tiempoPermanencia)-calcularPromedio(tiempoTransmision));
    }
    
    public double tamanyoPromedioDeLaCola(){
        double total = 0;
        for(int i = 0; i < tamanyoLista.size(); ++i){
            total = Double.parseDouble(tamanyoLista.get(i).toString())*tiempoTamanyoLista.get(i);
        }
        return total/sum(tiempoTamanyoLista);
    }
    public String estadisticasDelPrograma(){
        String res = "";
       
        res = "Tamaño promedio de la cola de A: " +PromedioTamnyo + "\n" +
              "Tiempo promedio de Permacia de un mensaje en la cola: " +PromedioTiempoPermanencia + "\n" + 
              "Tiempo promedio de transmision: " + calcularPromedio(tiempoTransmision)+ "\n"+
               "Tiempo promedio de servicio: " + PromedioDeServicio+ "\n"+
                "Eficiencia del sistema: " + Eficiencia;
              
        return res;
    }
    
    public void calcularEstadisticas(){
        PromedioTamnyo = (calcularPromedio(tamanyoLista));
        PromedioTiempoPermanencia = calcularPromedio(tiempoPermanencia);
        PromedioTransmision = calcularPromedio(tiempoTransmision);
        PromedioDeServicio = tiempoPromedioDeServicio();
        Eficiencia = eficiencia();
    }
}
