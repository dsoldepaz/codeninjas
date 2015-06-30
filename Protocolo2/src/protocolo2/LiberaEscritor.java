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
        double e = distribuidor.tiempoEscritura();

        if (master.colaB.size() == 1 && llegaFrameB.horaOcurrencia != Double.MAX_VALUE) {
            master.escritorLibre = true;
            horaOcurrencia = Double.MAX_VALUE;
        } else {
            master.escritorLibre = false;
            if (master.colaB.isEmpty()) {
                master.escritorLibre = true;
                this.horaOcurrencia = Double.MAX_VALUE;
            } else {
                this.horaOcurrencia = master.reloj + e;
            }
            master.HistorialRecibidosB.add(master.colaB.remove(0));
        }        
    }

    @Override
    public String getNombre() {
        return "Se libera el escritor";
    }

}
