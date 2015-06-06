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

    Mensaje(int numero) {
        this.numero = numero;
        enviado = false;
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

}
