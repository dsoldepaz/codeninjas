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
public class VenceTimer extends Evento {
    private static VenceTimer instance = null;
    protected VenceTimer() {
    }
    public static VenceTimer getInstance() {
        if (instance == null) {
            instance = new VenceTimer();
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
        //GoBackN.reloj = horaOcurrencia;
        
    }

}
