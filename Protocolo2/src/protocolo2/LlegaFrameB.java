/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocolo2;

/**
 *
 * @author b06171
 */
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


        double w = distribuidor.revisaFrame();
        master.reloj = this.horaOcurrencia;

    
    }

    @Override
    public String getNombre() {
        return "Llega frame a B";
    }
}
