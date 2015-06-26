package protocolo2;


public class LiberaEscritor extends Evento {

    private static LiberaEscritor instance = null;
    private Protocolo2 master;

    private Distribuidor distribuidor;
    private LlegaFrameB llegaFrameB; 

    protected LiberaEscritor() {

    }

    public static LiberaEscritor getInstance() {
        if (instance == null) {
            instance = new LiberaEscritor();
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
        
        master.reloj = horaOcurrencia;
        double w = distribuidor.revisaFrame();
       

    }

    @Override
    public String getNombre() {
        return "Se libera el escritor";
    }

}
