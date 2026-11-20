# LabPresta · Préstamo de equipos del laboratorio

**Equipo:** _Grupo6_
**Escenario:** C – LabPresta
**Materia:** ISID432 Desarrollo y Mantenimiento de Software · Escuela Politécnica Nacional
**Tablero de Trello:** _https://trello.com/invite/b/6aff97bab309f1902bddacc1/ATTIcbab0090d8bbac0d05de5c4037cd598c44E47367/lebpresta_

## Descripción
LabPresta es una aplicación de escritorio en Java que reemplaza la hoja de papel con la que la Lcda. Torres controla el laboratorio de computación. Permite registrar los equipos (laptops, proyectores, kits de Arduino) y a los usuarios (estudiantes y docentes), prestar y recibir equipos, y saber en todo momento dónde está cada uno y quién se atrasa en devolverlo.

## Integrantes
| Integrante | Rol | Usuario de GitHub |
|---|---|---|
| _Genesis Tufiño_ | Product Owner | _[@usuario]_ |
| _Joselyn Pauta_ | Scrum Master | _[@usuario]_ |
| _Valeria Zapata_ | Desarrollador | _[@usuario]_ |
| _Daniel Hidalgo_ | Desarrollador | _@dDanrick_ |

## Historias del Sprint 1
| Código | Historia | Estimación |
|---|---|---|
| HU-C1 | Registrar equipos | _[S/M/L]_ |
| HU-C2 | Registrar usuarios | _[S/M/L]_ |
| HU-C3 | Prestar y recibir equipos | _[S/M/L]_ |

**Objetivo del sprint:** Que la Lcda. Torres pueda registrar sus equipos y usuarios, y saber en todo momento qué equipo está prestado y a quién.

## Reglas del negocio
- El código del equipo y la cédula del usuario son únicos.
- Un equipo puede estar **Disponible**, **Prestado** o **En mantenimiento**.
- Solo se prestan equipos Disponibles. Al prestarlo pasa a Prestado y al devolverlo vuelve a Disponible.
- La fecha límite se calcula sola: 1 día después del préstamo para estudiantes y 3 días para docentes.
- Un préstamo está **vencido** si no se ha devuelto y su fecha límite ya pasó.
- Un préstamo está **atrasado** si se devolvió después de su fecha límite.

## Tecnologías
- Java _25_ con _JavaFX_
- Persistencia en _[archivos CSV / SQLite con JDBC (sqlite-jdbc)]_
- Sin librerías externas, salvo el driver de SQLite

## Cómo ejecutar la aplicación
### Requisitos
- Java _[17]_ o superior instalado (`java -version` para comprobarlo).

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
La aplicación abre con los datos de prueba que están en la misma carpeta (`_[labpresta.db / equipos.csv, usuarios.csv, prestamos.csv]_`). Si se mueve el `.jar`, hay que mover también los datos.

### Desde el código fuente
1. Abrir el proyecto en _NetBeans_.
2. Ejecutar la clase principal `_[paquete.Main]_`.

## Estructura del repositorio
```
labpresta-equipoX/
├── src/          Código fuente
├── dist/         LabPresta.jar y datos de prueba
└── README.md
```

## Convención de commits
Cada mensaje empieza con el código de la historia, por ejemplo:
`HU-C1: validar código repetido`
