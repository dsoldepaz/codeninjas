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
        double y = distribuidor.distribucionConvertirMensaje();
        master.reloj = horaOcurrencia;

        if (master.colaA.isEmpty()) {
            master.aLibre = true;
            this.horaOcurrencia = Double.MAX_VALUE;
        } else {
            master.aLibre = false;
            Mensaje m = master.colaA.remove(0);
            this.horaOcurrencia = master.reloj + y + 1;
            m.horaArriboB = master.reloj + y + 1 + 1;
            master.estadisticador.tamanyoLista.add(((double)master.colaA.size()));
            master.estadisticador.tiempoTamanyoLista.add(master.reloj-master.estadisticador.tiempoUltimoAgregado);
            master.estadisticador.tiempoUltimoAgregado = master.reloj;
            master.colaB.add(m);
            llegaFrameB.horaOcurrencia = master.colaB.get(0).horaArriboB; 
        }

    }

    @Override
    public String getNombre() {
        return "Se libera A";
    }

}
