package com.automation.selenium.evidence;

import java.nio.file.Path;

/**
 * Resultado devuelto luego de generar una evidencia.
 *
 * <p>Este record agrupa la informacion mas util que normalmente necesita un test
 * o un reporte automatizado despues de guardar la evidencia:</p>
 *
 * <p>1. Donde quedo el documento Word.</p>
 * <p>2. Donde quedo el screenshot PNG.</p>
 * <p>3. Que titulo se escribio dentro del documento.</p>
 *
 * <p>Al usar un {@code record}, Java genera automaticamente constructor,
 * getters con el nombre de cada campo, {@code toString()}, {@code equals()} y
 * {@code hashCode()}, lo que mantiene la clase corta y facil de leer.</p>
 *
 * @param documentPath ruta completa del archivo {@code .docx} generado o actualizado
 * @param screenshotPath ruta completa del archivo PNG guardado
 * @param evidenceTitle titulo que se escribio dentro del documento Word
 */
public record EvidenceCaptureResult(
        Path documentPath,
        Path screenshotPath,
        String evidenceTitle
) {
}