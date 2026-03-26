# Selenium Maven Commands

Este proyecto permite ejecutar pruebas con Maven de tres maneras:

1. Por archivo `testng.xml`
2. Por clase TestNG
3. Por metodo TestNG

## Requisitos

- Java configurado
- Maven disponible en consola
- Ejecutar los comandos desde:

```bash
cd selenium
```

## Ejecutar `testng.xml`

### Bash

```bash
mvn test -Ptestng-suite
```

### PowerShell

```powershell
mvn test -Ptestng-suite
```

Si queres indicar otro archivo suite:

### Bash

```bash
mvn test -Ptestng-suite -DtestngSuiteFile=otro-testng.xml
```

### PowerShell

```powershell
mvn --% test -Ptestng-suite -DtestngSuiteFile=otro-testng.xml
```

## Ejecutar una clase TestNG

Ejemplo con `LoginTests`:

### Bash

```bash
mvn test -Ptestng-direct -Dtest=com.automation.selenium.clase3.TestNG.LoginTests
```

### PowerShell

```powershell
mvn test -Ptestng-direct "-Dtest=com.automation.selenium.clase3.TestNG.LoginTests"
```

O tambien:

```powershell
mvn --% test -Ptestng-direct -Dtest=com.automation.selenium.clase3.TestNG.LoginTests
```

## Ejecutar un metodo TestNG puntual

Ejemplo con el metodo `loginTest`:

### Bash

```bash
mvn test -Ptestng-direct -Dtest=com.automation.selenium.clase3.TestNG.LoginTests#loginTest
```

### PowerShell

```powershell
mvn test -Ptestng-direct "-Dtest=com.automation.selenium.clase3.TestNG.LoginTests#loginTest"
```

O tambien:

```powershell
mvn --% test -Ptestng-direct -Dtest=com.automation.selenium.clase3.TestNG.LoginTests#loginTest
```

## Ejemplos rapidos

- Suite TestNG: `mvn test -Ptestng-suite`
- Clase TestNG en Bash: `mvn test -Ptestng-direct -Dtest=com.automation.selenium.clase3.TestNG.LoginTests`
- Clase TestNG en PowerShell: `mvn test -Ptestng-direct "-Dtest=com.automation.selenium.clase3.TestNG.LoginTests"`
- Metodo TestNG en Bash: `mvn test -Ptestng-direct -Dtest=com.automation.selenium.clase3.TestNG.LoginTests#loginTest`
- Metodo TestNG en PowerShell: `mvn test -Ptestng-direct "-Dtest=com.automation.selenium.clase3.TestNG.LoginTests#loginTest"`

## Nota sobre PowerShell

Si PowerShell interpreta mal parametros `-D...`, usa comillas o `--%`.

## Nota sobre perfiles

- `testng-suite`: ejecuta solamente lo definido en `testng.xml`
- `testng-direct`: ejecuta una clase o metodo TestNG indicado con `-Dtest=...`

## Visualizador HTML para `testng-results.xml`

Se agrego una app Java para leer `testng-results.xml` y generar un HTML mas claro con:

- tarjetas de resumen
- distribucion pass/fail/skip
- detalle por suite, test, clase y metodo
- errores, stacktrace y parametros cuando existen

### Archivo de entrada por defecto

Busca primero:

```text
test-output/testng-results.xml
```

Si no existe, usa como fallback:

```text
target/surefire-reports/testng-results.xml
```

### Como ejecutarlo desde el IDE

Ejecuta la clase:

```text
com.automation.selenium.App
```

### Como ejecutarlo desde consola

```powershell
mvn -DskipTests compile
java -cp target/classes com.automation.selenium.App
```

Eso genera por defecto:

```text
test-output/testng-results-pretty.html
```

### Pasando input y output manualmente

```powershell
java -cp target/classes com.automation.selenium.App "test-output\\testng-results.xml" "test-output\\mi-reporte.html"
```

### Ejecutarlo con Maven

Si `java -cp ...` te toma Java 8, conviene correrlo con Maven usando un JDK 17+:

```powershell
mvn -Ptestng-report-viewer -DskipTests compile exec:java
```

Para indicar input y output manualmente:

```powershell
mvn --% -Ptestng-report-viewer -DskipTests compile exec:java -Dtestng.report.input=test-output/testng-results.xml -Dtestng.report.output=test-output/mi-reporte.html
```

Importante:

- Maven tambien tiene que estar usando un JDK 17 o superior
- si en una consola falla y en otra anda, casi seguro cambia el `JAVA_HOME` o el `Path`
