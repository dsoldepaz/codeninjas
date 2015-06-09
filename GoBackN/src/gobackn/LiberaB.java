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

        master.reloj = horaOcurrencia;
        double w = distribuidor.revisaFrame();
        if (master.colaB.isEmpty()) {
            master.bLibre = true;
            horaOcurrencia = Double.MAX_VALUE;
        } else {
            horaOcurrencia = master.reloj + w + 0.25;
            master.bLibre = false;
            Mensaje msj = master.colaB.remove(0);
            if (msj.getConError()) {
                master.ultimoACKEnviadoPorB = msj.getNumero();
            } else {
                master.ultimoACKEnviadoPorB = msj.getNumero() + 1;
                master.HistorialRecibidosB.add(msj);
            }
            llegaACKaA.horaOcurrencia = master.reloj + w + 1.25;
            if (!(distribuidor.perdidoACK() == Distribuidor.EstadoMensaje.PERDIDO)) {
                master.ultimoACKRecibidoPorA = msj.getNumero() + 1;
            }
        }

    }

    @Override
    public String getNombre() {
        return "Se libera B";
    }

}
