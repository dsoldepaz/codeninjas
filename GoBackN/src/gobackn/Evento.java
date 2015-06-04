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
public abstract class Evento {
    protected double horaOcurrencia = Double.MAX_VALUE;
    public abstract double getHoraOcurrencia();
    public abstract double setHoraOcurrencia(int val);
    public abstract void ejecutar();
    //lalolanda
}
