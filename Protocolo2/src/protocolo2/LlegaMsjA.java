package protocolo2;


public class LlegaMsjA extends Evento {

    private static LlegaMsjA instance = null;
    private Protocolo2 master;
    private Distribuidor distribuidor;
    private LlegaFrameB llegaFrameB;
    private LiberaA liberaA;

    protected LlegaMsjA() {

    }

    public static LlegaMsjA getInstance() {
        if (instance == null) {
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
        return horaOcurrencia = val;
    }

    @Override
    public void ejecutar() {
       

    }

    @Override
    public String getNombre() {
        return "Llega Mensaje a A";
    }

}
