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
public class LlegaACKaA extends Evento {

    private static LlegaACKaA instance = null;
    private GoBackN master;
    private VenceTimer venceTimer;

    protected LlegaACKaA() {

    }

    public static LlegaACKaA getInstance() {
        if (instance == null) {
            instance = new LlegaACKaA();
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
        venceTimer = VenceTimer.getInstance();

        master.reloj = horaOcurrencia;
        horaOcurrencia = Double.MAX_VALUE;
        master.ultimoACKRecibidoPorA = master.ultimoACKEnviadoPorB;
        if (!master.ventana.isEmpty()) {
            for (int i = master.ventana.get(0).getNumero(); i <= master.ultimoACKRecibidoPorA - 1; i++) {
                try {
                    Mensaje m = master.ventana.remove(0);
                    int tamCola = master.colaEnviador.size();
                    for (int c = 0; c < tamCola; c++) {
                        if (m.getNumero() == master.colaEnviador.get(c).getNumero()) {
                            master.colaEnviador.remove(c);
                            c=0;
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                }
                for (int t = 0; t < 7; t++) {
                    master.timer[t] = master.timer[t + 1];
                }
                master.timer[7] = Double.MAX_VALUE;
            }

        }
        venceTimer.setHoraOcurrencia(master.timer[0]);
        if (!master.colaA.isEmpty()) {
            for (int i = master.ventana.size(); i < 8; i++) {
                try {
                    master.ventana.add(master.colaA.remove(0));
                } catch (IndexOutOfBoundsException e) {

                }
            }
        }

    }

    @Override
    public String getNombre() {
        return "Llega ACK a A";
    }

}
