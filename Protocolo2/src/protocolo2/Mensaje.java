package protocolo2;


public class Mensaje {

    private final int numero;
    private boolean enviado;
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
