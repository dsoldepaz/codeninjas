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
        Mensaje m = master.colaB.remove(0);
        if (master.escritorLibre) {
            master.escritorLibre = false;
            liberaEscritor.horaOcurrencia = master.reloj + e;
            master.estadisticador.tiempoPermanencia.add(master.reloj - m.tiempoDeLlegada);
            master.HistorialRecibidosB.add(m);
        } 
        else{
            master.colaEscritor.add(m);
        }
        
        if(master.colaB.isEmpty()){
                this.horaOcurrencia = Double.MAX_VALUE;
            }
            else{
                this.horaOcurrencia= master.colaB.get(0).horaArriboB;
            }
        horaOcurrencia = Double.MAX_VALUE;        
    }
    
    @Override
    public String getNombre() {
        return "Llega frame a B";
    }
}
