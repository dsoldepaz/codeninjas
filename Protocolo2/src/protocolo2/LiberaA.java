package protocolo2;


public class LiberaA extends Evento {

    private static LiberaA instance = null;
    private Distribuidor distribuidor;
    private Protocolo2 master;
    private LlegaFrameB llegaFrameB;

    protected LiberaA() {

    }

    public static LiberaA getInstance() {
        if (instance == null) {
            instance = new LiberaA();
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
        master = Protocolo2.getInstance();
        distribuidor = Distribuidor.getInstance();
        llegaFrameB = LlegaFrameB.getInstance();
        double Y = distribuidor.distribucionConvertirMensaje();
        master.reloj = horaOcurrencia;       
        
       
    }

    @Override
    public String getNombre() {
        return "Se libera A";
    }

}
