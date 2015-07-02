package protocolo2;

import java.util.ArrayList;
import java.util.List;

public class Estadisticador {

    //Locales
    public List<Double> tiempoTransmision;
    public List<Double> tiempoPermanencia;
    public List<Double> tamanyoLista;
    public double tiempoUltimoAgregado;
    public List<Double> tiempoTamanyoLista;

    public double PromedioTamanyo = 0;
    public double PromedioTiempoPermanencia = 0;


    //Globales
    public List<Double> tamanyoListaGlobal;
    public List<Double> tiempoPermanenciaGlobal;


    public double PromedioTamanyoGlobal = 0;
    public double PromedioTiempoPermanenciaGlobal = 0;
    public double limIzquierdo;
    public double limDerecho;

    public Estadisticador() {
        //locales
        tiempoTransmision = new ArrayList<>();
        tiempoPermanencia = new ArrayList<>();
        tamanyoLista = new ArrayList<>();
        tiempoUltimoAgregado = 0;
        tiempoTamanyoLista = new ArrayList<>();

        //globales
        tamanyoListaGlobal = new ArrayList<>();

        tiempoPermanenciaGlobal = new ArrayList<>();

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
                + "Tiempo promedio de permanencia de un mensaje en la cola: " + PromedioTiempoPermanencia + "\n";

        return res;
    }

    public void calcularEstadisticas() {
        PromedioTamanyo = (calcularPromedio(tamanyoLista));
        PromedioTiempoPermanencia = calcularPromedio(tiempoPermanencia);

        //guardar para globales
        tamanyoListaGlobal.add(PromedioTamanyo);
        tiempoPermanenciaGlobal.add(PromedioTiempoPermanencia);

    }

    void calcularEstadisticasGlobales(int veces) {
        //promedios globales
        PromedioTamanyoGlobal = calcularPromedio(tamanyoListaGlobal);
        PromedioTiempoPermanenciaGlobal = calcularPromedio(tiempoPermanenciaGlobal);

        //intervalo de confianza        
        double sumatoria = 0;
        for (Double t : tiempoPermanenciaGlobal) {
            sumatoria += t - PromedioTiempoPermanenciaGlobal;
        }
        double varianzaMuestral = Math.pow(2, sumatoria) / (tiempoPermanenciaGlobal.size() - 1);
        if (veces <= 10) {
            //intervalo de confianza t-student 
            System.out.println("t");
            limIzquierdo = PromedioTiempoPermanenciaGlobal - 2.26 * Math.sqrt(varianzaMuestral / tiempoPermanenciaGlobal.size());
            limDerecho = PromedioTiempoPermanenciaGlobal + 2.26 * Math.sqrt(varianzaMuestral / tiempoPermanenciaGlobal.size());
        } else {
            //intervalo de confianza normal 
            System.out.println("normal");
            limIzquierdo = PromedioTiempoPermanenciaGlobal - 1.96 * Math.sqrt(varianzaMuestral / tiempoPermanenciaGlobal.size());
            limDerecho = PromedioTiempoPermanenciaGlobal + 1.96 * Math.sqrt(varianzaMuestral / tiempoPermanenciaGlobal.size());
        }

    }

    public String estadisticasGlobales() {
        String res = "Tamaño promedio de la cola de A: " + PromedioTamanyoGlobal + "\n"
                + "Tiempo promedio de permanencia de un mensaje en la cola: " + PromedioTiempoPermanenciaGlobal + "\n"
                + "Intervalo de confianza al 95% para el tiempo promedio de permanencia en el sistema de cada mensaje: [ " + limIzquierdo + " , " + limDerecho + " ]";

        return res;
    }
}
