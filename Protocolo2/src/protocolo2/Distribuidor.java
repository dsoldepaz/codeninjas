package protocolo2;

public class Distribuidor {

    private static Distribuidor instance = null;

    protected Distribuidor() {

    }

    public static Distribuidor getInstance() {
        if (instance == null) {
            instance = new Distribuidor();
        }
        return instance;
    }

    public double distribucionLlegaMensajeA() {
        double z =0.0316227766016838*Zn() + 2;
        while(z < 0){
            z = 0.0316227766016838*Zn() + 2;
        }
        return z;
    }

    private double Zn() {
        return Math.pow((-2.0 * Math.log(Math.random())), (1.0 / 2.0)) * Math.sin(2.0 * Math.PI * Math.random());
    }

    public double distribucionConvertirMensaje() {
        double z = -2.0 * Math.log(1-Math.random());
        return z;
    }

    double tiempoEscritura() {
        return Math.sqrt(5 * Math.random() + 4);
    }

}
