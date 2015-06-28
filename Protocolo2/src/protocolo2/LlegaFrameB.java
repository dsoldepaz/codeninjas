package protocolo2;

public class LlegaFrameB extends Evento {
    
    private static LlegaFrameB instance = null;
    private Distribuidor distribuidor;
    private Protocolo2 master;
    private LiberaEscritor liberaEscritor;
    
    protected LlegaFrameB() {
    }
    
    public static LlegaFrameB getInstance() {
        if (instance == null) {
            instance = new LlegaFrameB();
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
        liberaEscritor = LiberaEscritor.getInstance();
        
        double e = distribuidor.tiempoEscritura();
        master.reloj = this.horaOcurrencia;
        
        if (master.escritorLibre) {
            liberaEscritor.horaOcurrencia = master.reloj + e;
            master.escritorLibre = false;
            master.colaB.remove(0);
        } else {
            master.colaEscritor.add(master.colaB.remove(0));
        }
        
    }
    
    @Override
    public String getNombre() {
        return "Llega frame a B";
    }
}
