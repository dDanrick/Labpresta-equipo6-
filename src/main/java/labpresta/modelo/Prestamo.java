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

        public String toCsv() {
        String devolucion = fechaDevolucion == null ? "" : fechaDevolucion.toString();
        return codigoEquipo + ";" + cedulaUsuario + ";" + fechaPrestamo + ";" + fechaLimite + ";" + devolucion;
    }

    public static Prestamo fromCsv(String linea) {
        String[] partes = linea.split(";", -1);
        if (partes.length < 5) {
            throw new IllegalArgumentException("Línea CSV inválida para Prestamo: " + linea);
        }
        Prestamo p = new Prestamo(partes[0].trim(), partes[1].trim(),
                LocalDate.parse(partes[2].trim()), LocalDate.parse(partes[3].trim()));
        if (!partes[4].trim().isEmpty()) {
            p.setFechaDevolucion(LocalDate.parse(partes[4].trim()));
        }
        return p;
    }
    
    @Override
    public String toString() {
        return codigoEquipo + " -> " + cedulaUsuario + " (limite " + fechaLimite + ")";
    }
}