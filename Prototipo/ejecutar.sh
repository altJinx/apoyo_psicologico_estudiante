#!/bin/bash
# Script para ejecutar el Sistema de Apoyo Psicológico

# 1. Obtener la ruta del directorio donde se encuentra este mismo script
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

# 2. Apuntar la ruta de JavaFX a la carpeta 'lib' que colocaste junto al script
export PATH_TO_FX="$DIR/lib"

# Verificar que la carpeta lib local existe
if [ ! -d "$PATH_TO_FX" ]; then
    echo "ERROR: No se encontró la carpeta 'lib' con JavaFX en $PATH_TO_FX"
    echo "Asegúrate de colocar la carpeta 'lib' de JavaFX en el mismo directorio que este script."
    exit 1
fi

# Crear directorio bin si no existe
mkdir -p bin

echo "Compilando el proyecto..."

# Compilar todos los archivos .java que estén en src y sus subcarpetas
find src -name "*.java" > sources.txt
javac --module-path "$PATH_TO_FX" --add-modules javafx.controls,javafx.fxml \
      -d bin/ \
      @sources.txt
COMPILATION_RESULT=$?
rm -f sources.txt

if [ $COMPILATION_RESULT -ne 0 ]; then
    echo "ERROR: La compilación falló"
    exit 1
fi

echo "Compilación exitosa."
echo "Iniciando aplicación..."
echo ""

# Ejecutar la aplicación
java --module-path "$PATH_TO_FX" --add-modules javafx.controls,javafx.fxml \
     -cp bin/ Main
