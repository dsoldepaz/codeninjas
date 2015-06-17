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

    //Locales

    public List<Double> tiempoTransmision;
    public List<Double> tiempoPermanencia;
    public List<Double> tamanyoLista;
    public double tiempoUltimoAgregado;
    public List<Double> tiempoTamanyoLista;

    public double PromedioTamanyo = 0;
    public double PromedioTiempoPermanencia = 0;
    public double PromedioTransmision = 0;
    public double PromedioDeServicio = 0;
    public double Eficiencia = 0;

    //Globales
    public List<Double> tamanyoListaGlobal;
    public List<Double> tiempoTransmisionGlobal;
    public List<Double> tiempoPermanenciaGlobal;
    public List<Double> tiempoServicioGlobal;
    public List<Double> eficienciaGlobal;
    
    public double PromedioTamanyoGlobal = 0;
    public double PromedioTiempoPermanenciaGlobal = 0;
    public double PromedioTransmisionGlobal = 0;
    public double PromedioDeServicioGlobal = 0;
    public double EficienciaGlobal = 0;

    public Estadisticador() {
        //locales
        tiempoTransmision = new ArrayList<>();
        tiempoPermanencia = new ArrayList<>();
        tamanyoLista = new ArrayList<>();
        tiempoUltimoAgregado = 0;
        tiempoTamanyoLista = new ArrayList<>();
        
        //globales
        tamanyoListaGlobal = new ArrayList<>();
        tiempoTransmisionGlobal = new ArrayList<>();
        tiempoPermanenciaGlobal = new ArrayList<>();
        tiempoServicioGlobal = new ArrayList<>();
        eficienciaGlobal = new ArrayList<>();
    }

    public double calcularPromedio(List l) {
        double sum = 0;
        for (int i = 0; i < l.size(); ++i) {
            sum += Double.parseDouble(l.get(i).toString());
        }
        return sum / l.size();
    }

    public double sum(List l) {
        double sum = 0;
        for (int i = 0; i < l.size(); ++i) {
            sum += Double.parseDouble(l.get(i).toString());
        }
        return sum;
    }

    public double tiempoPromedioDeServicio() {
        return (calcularPromedio(tiempoPermanencia) - calcularPromedio(tiempoTransmision));
    }

    public double eficiencia() {
        return calcularPromedio(tiempoTransmision) / (calcularPromedio(tiempoPermanencia) - calcularPromedio(tiempoTransmision));
    }

    public double tamanyoPromedioDeLaCola() {
        double total = 0;
        for (int i = 0; i < tamanyoLista.size(); ++i) {
            total = Double.parseDouble(tamanyoLista.get(i).toString()) * tiempoTamanyoLista.get(i);
        }
        return total / sum(tiempoTamanyoLista);
    }

    public String estadisticasLocales() {
        String res = "Tamaño promedio de la cola de A: " + PromedioTamanyo + "\n"
                + "Tiempo promedio de permanencia de un mensaje en la cola: " + PromedioTiempoPermanencia + "\n"
                + "Tiempo promedio de transmision: " + calcularPromedio(tiempoTransmision) + "\n"
                + "Tiempo promedio de servicio: " + PromedioDeServicio + "\n"
                + "Eficiencia del sistema: " + Eficiencia;

        return res;
    }

    public void calcularEstadisticas() {
        PromedioTamanyo = (calcularPromedio(tamanyoLista));
        PromedioTiempoPermanencia = calcularPromedio(tiempoPermanencia);
        PromedioTransmision = calcularPromedio(tiempoTransmision);
        PromedioDeServicio = tiempoPromedioDeServicio();
        Eficiencia = eficiencia();
        
        //guardar para globales
        tamanyoListaGlobal.add(PromedioTamanyo);
        tiempoPermanenciaGlobal.add(PromedioTiempoPermanencia);
        tiempoTransmisionGlobal.add(PromedioTransmision);       
        tiempoServicioGlobal.add(PromedioDeServicio);
        eficienciaGlobal.add(Eficiencia);

    }
    void calcularEstadisticasGlobales() {
        PromedioTamanyoGlobal = calcularPromedio(tamanyoListaGlobal);
        PromedioTiempoPermanenciaGlobal = calcularPromedio(tiempoPermanenciaGlobal);
        PromedioTransmisionGlobal = calcularPromedio(tiempoTransmisionGlobal);
        PromedioDeServicioGlobal = calcularPromedio(tiempoServicioGlobal);
        EficienciaGlobal = calcularPromedio(eficienciaGlobal);
    }
    public String estadisticasGlobales() {
        String res = "Tamaño promedio de la cola de A: " + PromedioTamanyoGlobal + "\n"
                + "Tiempo promedio de permanencia de un mensaje en la cola: " + PromedioTiempoPermanenciaGlobal + "\n"
                + "Tiempo promedio de transmision: " + PromedioTransmisionGlobal + "\n"
                + "Tiempo promedio de servicio: " + PromedioDeServicioGlobal + "\n"
                + "Eficiencia del sistema: " + EficienciaGlobal;

        return res;
    }
}
