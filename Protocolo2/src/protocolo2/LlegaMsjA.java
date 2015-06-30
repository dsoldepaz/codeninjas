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
        master = Protocolo2.getInstance();
        distribuidor = Distribuidor.getInstance();
        llegaFrameB = LlegaFrameB.getInstance();
        liberaA = LiberaA.getInstance();

        master.estadisticador.tiempoUltimoAgregado = master.reloj;
        double tam = (double) master.colaA.size();
        master.estadisticador.tamanyoLista.add(tam);
        master.estadisticador.tiempoTamanyoLista.add(master.reloj - master.estadisticador.tiempoUltimoAgregado);

        ++master.numeroMsj;
        Mensaje nuevoMsj = new Mensaje(master.numeroMsj);
        double y = distribuidor.distribucionConvertirMensaje();
        double x = distribuidor.distribucionLlegaMensajeA();
        master.reloj = horaOcurrencia;
        
        this.horaOcurrencia=distribuidor.distribucionLlegaMensajeA();

        if (master.aLibre) {
            master.aLibre = false;
            liberaA.horaOcurrencia = master.reloj + y;
            llegaFrameB.horaOcurrencia = master.reloj + y + 1;
            master.colaB.add(nuevoMsj);
        } else {
            master.colaA.add(nuevoMsj);
        }

    }

    @Override
    public String getNombre() {
        return "Llega Mensaje a A";
    }

}
