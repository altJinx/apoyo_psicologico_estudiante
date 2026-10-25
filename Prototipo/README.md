# Sistema de Apoyo Psicológico Estudiantil

Prototipo funcional e interactivo del Sistema de Apoyo Psicológico del Departamento de Bienestar Universitario, desarrollado con JavaFX.

## Características

Este prototipo implementa las funcionalidades principales para los 4 roles del sistema:

### 1. Estudiante
- **SOS / Atención Inmediata**: Solicitar llamada en tiempo real con fallback offline (contingencia por falta de internet)
- **Agendar Cita**: Reservar horarios filtrados por facultad
- **Cancelar/Reprogramar**: Modificar reservas sin penalizaciones
- **Cargar Antecedentes**: Adjuntar documentos clínicos simulados
- **Retroalimentación**: Calificar atención (1-5 estrellas) y dejar comentarios

### 2. Psicólogo
- **Triage Clínico Humano**: Registrar nivel de riesgo (Bajo, Moderado, Alto/Vital)
- **Nota Clínica y Firma**: Redactar evolución, revisar antecedentes y cerrar atención con firma electrónica

### 3. Secretaría de Bienestar
- **Agendar Cita Presencial (Vista Ciega)**: Asignar espacios sin acceso a diagnósticos ni triage (solo datos demográficos)

### 4. Director de Bienestar (Administrador)
- **Gestionar Horarios**: Configurar matriz de turnos y disponibilidades
- **Dashboard Estadístico**: Métricas anonimizadas de atención y satisfacción
- **Romper el Cristal**: Acceso excepcional a expedientes con registro obligatorio de auditoría (5 causales legales)

## Requisitos Previos

### 1. Java Development Kit (JDK)
- **Versión mínima**: Java 17 o superior
- **Recomendado**: Java 17 LTS o Java 21 LTS

Verificar instalación:
```bash
java -version
javac -version
```

Si no tiene Java instalado en Linux:

**Ubuntu/Debian:**
```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

**Arch Linux:**
```bash
sudo pacman -S jdk17-openjdk
```

### 2. JavaFX SDK
El proyecto requiere JavaFX SDK para Linux. Siga estos pasos:

#### Opción A: Descargar manualmente (Recomendado)
1. Descargue JavaFX SDK 21+ desde: https://gluonhq.com/products/javafx/
2. Seleccione la versión para Linux (javafx-sdk-21.0.2_linux-x64-bin.tar.gz o superior)
3. Descomprima en una ubicación permanente, por ejemplo:
```bash
cd ~
tar -xzf javafx-sdk-21.0.2_linux-x64-bin.tar.gz
```
4. Anote la ruta completa, ejemplo: `/home/usuario/javafx-sdk-21.0.2/`

#### Opción B: Usar gestor de paquetes (Ubuntu/Debian)
```bash
sudo apt install openjfx
```
**Nota**: La versión del repositorio puede estar desactualizada. Se recomienda la Opción A.

## Estructura del Proyecto

```
prototipo/
├── src/
│   ├── Main.java                    # Clase principal (punto de entrada)
│   ├── module-info.java             # Configuración de módulos Java 9+
│   ├── modelo/                      # Entidades y lógica de negocio
│   │   ├── Estudiante.java
│   │   ├── Psicologo.java
│   │   ├── Cita.java
│   │   ├── EstadoCita.java
│   │   ├── NivelRiesgo.java
│   │   ├── Horario.java
│   │   ├── Retroalimentacion.java
│   │   ├── AuditoriaRompimientoCristal.java
│   │   └── SimuladorDatos.java      # Datos en memoria
│   ├── controlador/                 # (Lógica de control integrada en vistas)
│   └── vista/                       # Interfaces JavaFX
│       ├── VistaPrincipal.java
│       ├── VistaEstudiante.java
│       ├── VistaPsicologo.java
│       ├── VistaSecretaria.java
│       └── VistaDirector.java
└── README.md
```

## Compilación y Ejecución

### Paso 1: Configurar variables de entorno (solo una vez)

Agregue estas líneas a su archivo `~/.bashrc` o `~/.zshrc`:

```bash
# Ruta a JavaFX SDK (ajuste según su instalación)
export PATH_TO_FX=/home/usuario/javafx-sdk-21.0.2/lib
```

Luego recargue la configuración:
```bash
source ~/.bashrc
```

### Paso 2: Compilar el proyecto

Navegue al directorio del proyecto:
```bash
cd /home/alan/u/26-2/software/proyecto/prototipo
```

Compile todos los archivos Java:
```bash
javac --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml \
     -d bin/ \
     src/*.java src/modelo/*.java src/vista/*.java
```

**Si usa la opción B (openjfx del sistema):**
```bash
javac --module-path /usr/share/openjfx/lib --add-modules javafx.controls,javafx.fxml \
     -d bin/ \
     src/*.java src/modelo/*.java src/vista/*.java
```

### Paso 3: Ejecutar la aplicación

```bash
java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml \
     -cp bin/ Main
```

**Si usa la opción B (openjfx del sistema):**
```bash
java --module-path /usr/share/openjfx/lib --add-modules javafx.controls,javafx.fxml \
     -cp bin/ Main
```

### Script de Ejecución Alternativo

Cree un script `ejecutar.sh` para facilitar la ejecución:

```bash
#!/bin/bash
# Script para ejecutar el Sistema de Apoyo Psicológico

# Configurar ruta de JavaFX (ajuste según su instalación)
export PATH_TO_FX=/home/usuario/javafx-sdk-21.0.2/lib

# Compilar
javac --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml \
     -d bin/ \
     src/*.java src/modelo/*.java src/vista/*.java

# Ejecutar
java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml \
     -cp bin/ Main
```

Dar permisos de ejecución:
```bash
chmod +x ejecutar.sh
./ejecutar.sh
```

## Clase Principal

La clase principal que inicia la aplicación es **`Main`** ubicada en:
```
src/Main.java
```

Esta clase extiende `javafx.application.Application` y lanza la vista principal del sistema.

## Datos de Prueba

El prototipo incluye un `SimuladorDatos` que precarga datos en memoria:
- **4 psicólogos** distribuidos por facultades (Ingeniería, Medicina, Derecho, Ciencias)
- **5 estudiantes** con diferentes carreras
- **3 citas de ejemplo** (1 programada, 1 completada, 1 programada)
- **Horarios** preconfigurados para cada psicólogo
- **Antecedentes** y retroalimentaciones de ejemplo

Los datos persisten solo durante la ejecución de la aplicación (no hay base de datos real).

## Solución de Problemas

### Error: "module javafx.controls not found"
**Causa**: JavaFX no está en el module-path correctamente.
**Solución**: Verifique que la variable `$PATH_TO_FX` apunte al directorio `lib/` del JavaFX SDK.

### Error: "package javafx does not exist"
**Causa**: El compilador no encuentra JavaFX.
**Solución**: Asegúrese de usar las flags `--module-path` y `--add-modules` al compilar.

### Error: "class file has wrong version"
**Causa**: Incompatibilidad de versión entre compilador y JavaFX.
**Solución**: Use la misma versión de Java para compilar y ejecutar, y asegúrese de usar JavaFX 21+ con Java 17+.

### Arch Linux: java-openjfx sin JARs
**Nota importante**: En Arch Linux, el paquete `java-openjfx` del repositorio oficial NO incluye los archivos JAR necesarios para compilar y ejecutar aplicaciones JavaFX. 

**Solución**: Debe descargar el JavaFX SDK manualmente desde GluonHQ:
```bash
cd ~
wget https://download2.gluonhq.com/openjfx/21.0.2/openjfx-21.0.2_linux-x64-bin-sdk.zip
unzip openjfx-21.0.2_linux-x64-bin-sdk.zip
# Luego actualice PATH_TO_FX en ejecutar.sh a: ~/javafx-sdk-21.0.2/lib
```

### Problemas de visualización en Linux
Si la interfaz no se renderiza correctamente, instale las dependencias de OpenGL:

**Ubuntu/Debian:**
```bash
sudo apt install libgl1-mesa-glx libglu1-mesa
```

**Arch Linux:**
```bash
sudo pacman -S mesa libglvnd
```

## Notas Técnicas

- **Arquitectura**: Limpia orientada a objetos con separación en paquetes (modelo, vista, controlador)
- **Datos**: Simulados en memoria usando Java Collections (sin base de datos real)
- **UI**: JavaFX puro (sin Swing) para máxima compatibilidad en Linux
- **Java Version**: Compatible con Java 17+ y JavaFX 21+
- **Licencia**: Prototipo para demostración

## Contacto

Para soporte técnico o preguntas sobre el prototipo, contacte al equipo de desarrollo del Departamento de Bienestar Universitario.
