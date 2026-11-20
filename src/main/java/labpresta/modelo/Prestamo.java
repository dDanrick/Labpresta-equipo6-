package labpresta.modelo;

import java.time.LocalDate;

public class Prestamo {

private final String codigoEquipo;
private final String cedulaUsuario;
private final LocalDate fechaPrestamo;
private final LocalDate fechaLimite;
private LocalDate fechaDevolucion; 

    public Prestamo(String codigoEquipo, String cedulaUsuario,
                    LocalDate fechaPrestamo, LocalDate fechaLimite) {
        this.codigoEquipo = codigoEquipo;
        this.cedulaUsuario = cedulaUsuario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaLimite = fechaLimite;
        this.fechaDevolucion = null;
    }

    public String getCodigoEquipo() { return codigoEquipo; }
    public String getCedulaUsuario() { return cedulaUsuario; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public LocalDate getFechaLimite() { return fechaLimite; }
    public LocalDate getFechaDevolucion() { return fechaDevolucion; }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }


    public boolean estaActivo() {
        return fechaDevolucion == null;
    }

    @Override
    public String toString() {
        return codigoEquipo + " -> " + cedulaUsuario + " (limite " + fechaLimite + ")";
    }
}