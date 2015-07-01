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
            if(master.colaB.isEmpty()){
                master.escritorLibre=true;
                liberaEscritor.horaOcurrencia = Double.MAX_VALUE;
            }
            else{
                liberaEscritor.horaOcurrencia = master.reloj + e;
            }
         
            master.escritorLibre = false;
            master.estadisticador.tiempoPermanencia.add(master.reloj - master.colaB.get(0).tiempoDeLlegada);
            master.HistorialRecibidosB.add(master.colaB.remove(0));
        } 
        horaOcurrencia = Double.MAX_VALUE;        
    }
    
    @Override
    public String getNombre() {
        return "Llega frame a B";
    }
}
