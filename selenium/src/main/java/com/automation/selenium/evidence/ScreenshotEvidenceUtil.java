package com.automation.selenium.evidence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

/**
 * Utilidad interna encargada exclusivamente de sacar screenshots con Selenium.
 *
 * <p>Esta clase no conoce nada sobre Word ni sobre el flujo completo de una
 * evidencia. Su unica responsabilidad es:</p>
 *
 * <p>1. Pedirle al {@link WebDriver} una captura del navegador.</p>
 * <p>2. Crear la carpeta destino si todavia no existe.</p>
 * <p>3. Guardar el PNG en disco.</p>
 *
 * <p>Es package-private porque esta pensada para ser usada desde
 * {@link EvidenceService}, no directamente desde los tests.</p>
 */
final class ScreenshotEvidenceUtil {

    private ScreenshotEvidenceUtil() {
    }

    /**
     * Captura el estado actual del navegador y lo guarda en la ruta indicada.
     *
     * <p>Se usa {@link OutputType#BYTES} para obtener directamente los bytes del
     * screenshot y escribirlos con {@link Files}, evitando pasos intermedios
     * innecesarios.</p>
     *
     * @param driver instancia activa de Selenium WebDriver
     * @param targetFile ruta completa donde se debe guardar el PNG
     * @return la misma ruta recibida, ya escrita en disco
     * @throws IOException si falla la creacion de carpetas o la escritura del archivo
     * @throws IllegalArgumentException si el driver no soporta screenshots
     * @throws NullPointerException si alguno de los parametros es {@code null}
     */
    static Path capture(WebDriver driver, Path targetFile) throws IOException {
        Objects.requireNonNull(driver, "driver must not be null");
        Objects.requireNonNull(targetFile, "targetFile must not be null");

        Path parent = targetFile.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        if (!(driver instanceof TakesScreenshot screenshotDriver)) {
            throw new IllegalArgumentException("The provided WebDriver does not support screenshots.");
        }

        try {
            byte[] screenshotBytes = screenshotDriver.getScreenshotAs(OutputType.BYTES);
            return Files.write(targetFile, screenshotBytes);
        } catch (WebDriverException exception) {
            throw new EvidenceException("Could not capture the Selenium screenshot.", exception);
        }
    }
}