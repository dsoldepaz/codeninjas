package protocolo2;

public abstract class Evento {
    protected double horaOcurrencia = Double.MAX_VALUE;
    public abstract double getHoraOcurrencia();
    public abstract double setHoraOcurrencia(double val);
    public abstract void ejecutar();
    public abstract String getNombre();
}
