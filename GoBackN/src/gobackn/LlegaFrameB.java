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
public class LlegaFrameB extends Evento {

    private static LlegaFrameB instance = null;
    private Distribuidor distribuidor;
    private GoBackN master;
    private LiberaB liberaB;
    private LlegaACKaA llegaACKaA;

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
        master = GoBackN.getInstance();
        distribuidor = Distribuidor.getInstance();
        liberaB = LiberaB.getInstance();
        llegaACKaA = LlegaACKaA.getInstance();

        double w = distribuidor.revisaFrame();
        master.reloj = this.horaOcurrencia;

        if (master.bLibre) {
            
            master.bLibre = false;
            Mensaje msj = master.colaB.remove(0);
            if(master.colaB.isEmpty()){
                 master.bLibre = true;
                liberaB.horaOcurrencia = Double.MAX_VALUE;
            }
            else{
                liberaB.horaOcurrencia = master.reloj + w + 0.25;
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
        horaOcurrencia = Double.MAX_VALUE;
    }

    @Override
    public String getNombre() {
        return "Llega frame a B";
    }
}
