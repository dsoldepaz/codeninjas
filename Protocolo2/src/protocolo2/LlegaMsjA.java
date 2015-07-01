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
        
        master.reloj = this.horaOcurrencia;
        
        
        double tam = (double) master.colaA.size();
        master.estadisticador.tamanyoLista.add(tam);
        master.estadisticador.tiempoTamanyoLista.add(master.reloj - master.estadisticador.tiempoUltimoAgregado);

        master.estadisticador.tiempoUltimoAgregado = master.reloj;
        ++master.numeroMsj;
        Mensaje nuevoMsj = new Mensaje(master.numeroMsj);
        double y = distribuidor.distribucionConvertirMensaje();
        nuevoMsj.tiempoDeLlegada = master.reloj = horaOcurrencia;
        
        this.horaOcurrencia= master.reloj+distribuidor.distribucionLlegaMensajeA();
        master.colaA.add(nuevoMsj);
        if (master.aLibre) {
            master.aLibre = false;
            Mensaje m = master.colaA.remove(0);
            liberaA.horaOcurrencia = master.reloj + y + 1;
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
        return "Llega Mensaje a A";
    }

}
