package labpresta;

/**
 * Clase de arranque para que el .jar abra la aplicación JavaFX.
 * No hereda de Application, por eso el .jar puede ejecutarla.
 */
public class Launcher {
    public static void main(String[] args) {
        App.main(args);
    }
}