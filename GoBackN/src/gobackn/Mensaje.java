/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gobackn;

/**
 *
 * @author bana
 */
public class Mensaje {

    private final int numero;
    private boolean enviado;
    private boolean conError;
    private double tiempoDeLlegada;
    public double tiempoDeTransferencia;
    
    Mensaje(int numero) {
        this.numero = numero;
        enviado = false;
        tiempoDeLlegada = 0;
        tiempoDeTransferencia =0;
    }

    public int getNumero() {
        return numero;
    }

    public boolean getEnviado() {
        return enviado;
    }

    public void setEnviado(boolean enviado) {
        this.enviado = enviado;
    }    
    public boolean getConError() {
        return conError;
    }
    
    public void setConError(boolean error) {
        this.conError = error;
    }
    
    public void setTiempoDeLlegada(double t){
       this.tiempoDeLlegada = t;
    }
    
    public double getTiempoDeLlegada(){
       return this.tiempoDeLlegada;
    }
    
    public void setTiempoTransferencia(double t){
        this.tiempoDeTransferencia = t;
    }

}
