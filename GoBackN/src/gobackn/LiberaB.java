/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gobackn;

/**
 *
 * @author b06171
 */
public class LiberaB extends Evento {

    private static LiberaB instance = null;
    private GoBackN master;
    private LlegaACKaA llegaACKaA;
    private Distribuidor distribuidor;
    private LlegaFrameB llegaFrameB; 

    protected LiberaB() {

    }

    public static LiberaB getInstance() {
        if (instance == null) {
            instance = new LiberaB();
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
        master = GoBackN.getInstance();
        distribuidor = Distribuidor.getInstance();
        llegaACKaA = LlegaACKaA.getInstance();
        llegaFrameB = LlegaFrameB.getInstance();
        
        master.reloj = horaOcurrencia;
        double w = distribuidor.revisaFrame();
        if (master.colaB.size() == 1 && llegaFrameB.horaOcurrencia != Double.MAX_VALUE ) {
            master.bLibre = true;
            horaOcurrencia = Double.MAX_VALUE;
        } else {
            master.bLibre = false;
            Mensaje msj = master.colaB.remove(0);
             if(master.colaB.isEmpty()){
                 master.bLibre = true;
                this.horaOcurrencia = Double.MAX_VALUE;
            }
            else{
                this.horaOcurrencia = master.reloj + w + 0.25;
            }
            if (msj.getConError()) {
                master.ultimoACKEnviadoPorB = master.frameEsperado;
            } else {
                 if (msj.getNumero() == master.frameEsperado) {//está en sequencia correcta
                    master.ultimoACKEnviadoPorB = msj.getNumero() + 1;
                    master.HistorialRecibidosB.add(msj);
                    master.frameEsperado++;
                } else {//llegó fuera de sequencia
                    master.ultimoACKEnviadoPorB = master.frameEsperado;
                }
            }            
            if (distribuidor.perdidoACK() == Distribuidor.EstadoMensaje.LLEGO) {
                llegaACKaA.horaOcurrencia = master.reloj + w + 1.25;
                master.ultimoACKRecibidoPorA = master.ultimoACKEnviadoPorB;
            }
        }

    }

    @Override
    public String getNombre() {
        return "Se libera B";
    }

}
