# LabPresta · Préstamo de equipos del laboratorio

**Equipo:** Equipo 6
**Escenario:** C – LabPresta
**Materia:** ISID432 Desarrollo y Mantenimiento de Software · Escuela Politécnica Nacional

**Repositorio:** https://github.com/dDanrick/Labpresta-equipo6-

**Tablero de Trello:** https://trello.com/invite/b/6aff97bab309f1902bddacc1/ATTIdc84f10527a069fbb3cc73dc34173f110E83F90A/lebpresta

## Descripción
LabPresta es una aplicación de escritorio en Java que reemplaza la hoja de papel con la que la Lcda. Torres controla el laboratorio de computación. Permite registrar los equipos (laptops, proyectores, kits de Arduino) y a los usuarios (estudiantes y docentes), prestar y recibir equipos, y saber en todo momento dónde está cada uno.

## Integrantes
| Integrante | Rol | Usuario de GitHub |
|---|---|---|
|  | Product Owner |  |
| Josselyn Pauta | Scrum Master | @Jose-Pau2307 |
| Valeria Zapata | Desarrolladora | @Vaalxl |
| Génesis Tufiño | Desarrolladora | @Genetb10|
| Daniel Hidalgo | Desarrollador | @dDanrick |

## Objetivo del sprint 1
Que la Lcda. Torres pueda registrar sus equipos y usuarios, prestar y recibir equipos, y saber en todo momento dónde está cada uno.

## Épicas y features
| ID | Épica / Feature | Historia |
|---|---|---|
| E1 | Administración del laboratorio | |
| F1.1 | Inventario de equipos con su estado | HU-C1 |
| F1.2 | Registro y búsqueda de usuarios | HU-C2 |
| E2 | Control de préstamos | |
| F2.1 | Préstamo y devolución de equipos | HU-C3 |
| F2.2 | Seguimiento de préstamos vencidos y atrasados | Próximo sprint |

## Historias del Sprint 1
| Código | Historia | Talla | N.º de tareas |
|---|---|---|---|
| HU-C1 | Registrar equipos | M | 7 |
| HU-C2 | Registrar usuarios | M | 7 |
| HU-C3 | Prestar y recibir equipos | L | 9 |

### HU-C1 · Registrar equipos
Como encargada del laboratorio, quiero registrar los equipos con su código, tipo y descripción para tener el inventario del laboratorio.
- CA1. Si ingreso datos válidos, el equipo se guarda con estado Disponible y aparece en la lista.
- CA2. Si el código ya existe, aparece un mensaje y no se guarda.
- CA3. La lista muestra el estado de cada equipo, y puedo marcar uno como En mantenimiento o devolverlo a Disponible.

### HU-C2 · Registrar usuarios
Como encargada del laboratorio, quiero registrar a estudiantes y docentes para saber a quién le presto cada equipo.
- CA1. Si ingreso datos válidos, el usuario se guarda y aparece en la lista.
- CA2. Si la cédula ya existe o falta el tipo de usuario, aparece un mensaje y no se guarda.
- CA3. Puedo buscar un usuario por su cédula o por parte de su nombre.

### HU-C3 · Prestar y recibir equipos
Como encargada del laboratorio, quiero registrar cada préstamo y cada devolución para saber en todo momento dónde está cada equipo.
- CA1. Al prestar, elijo un usuario y un equipo Disponible; el sistema calcula la fecha límite y el equipo pasa a Prestado.
- CA2. Si intento prestar un equipo Prestado o En mantenimiento, aparece un mensaje y el préstamo no se registra.
- CA3. Al devolver, elijo un préstamo activo de la lista; se guarda la fecha de devolución y el equipo vuelve a Disponible.

## Reglas del negocio
- El código del equipo y la cédula del usuario son únicos.
- Tipos de equipo: Laptop, Proyector, Kit Arduino u Otro. Tipos de usuario: Estudiante o Docente.
- Un equipo puede estar **Disponible**, **Prestado** o **En mantenimiento**.
- Solo se prestan equipos Disponibles. Al prestarlo pasa a Prestado y al devolverlo vuelve a Disponible.
- La fecha del préstamo es editable.
- La fecha límite se calcula sola: 1 día después del préstamo para estudiantes y 3 días para docentes.
- Un préstamo está **vencido** si no se ha devuelto y su fecha límite ya pasó (próximo sprint).
- Un préstamo está **atrasado** si se devolvió después de su fecha límite (próximo sprint).

## Definición de Hecho
- Funciona desde el `.jar`, no solo desde el IDE.
- Los datos persisten al cerrar y volver a abrir la aplicación.
- No se cae con campos vacíos o datos inválidos.
- Fue probada por otro integrante.
- Está subida al repositorio.

## Tecnologías
- Java 11 con JavaFX 13 (FXML)
- Maven (proyecto creado en NetBeans)
- Persistencia en archivos CSV, sin base de datos

## Cómo ejecutar la aplicación
### Requisitos
- Java 11 o superior instalado (`java -version` para comprobarlo). Con Java 8 aparece `UnsupportedClassVersionError`.

### Desde el .jar
1. Clonar o descargar el repositorio.
2. Entrar a la carpeta `dist`:
   ```
   cd dist
   ```
3. Ejecutar:
   ```
   java -jar LabPresta.jar
   ```
La aplicación abre con los datos de prueba de la carpeta `data` (`equipos.csv`, `usuarios.csv`, `prestamos.csv`), que debe estar junto al `.jar`. Si se mueve el `.jar`, hay que mover también la carpeta `data`.

### Desde el código fuente
1. Abrir el proyecto en NetBeans.
2. Ejecutar con **Run** (usa `javafx:run`, clase `labpresta.App`).
3. Para generar el `.jar`: **Clean and Build**. Queda en `target/LabPresta-1.0-SNAPSHOT.jar` (clase principal `labpresta.Launcher`).

## Estructura del repositorio
```
Labpresta-equipo6-/
├── src/main/java/labpresta/          Controladores y App
│   ├── modelo/                       Equipo, Usuario, Prestamo y validadores
│   └── datos/                        Lectura y escritura de CSV
├── src/main/resources/labpresta/     Pantallas FXML y CSS
├── data/                             equipos.csv, usuarios.csv, prestamos.csv
├── dist/                             LabPresta.jar y datos de prueba
├── pom.xml
└── README.md
```

## Convención de commits
Cada mensaje empieza con el código de la historia, por ejemplo:
`HU-C1: validar código repetido`
