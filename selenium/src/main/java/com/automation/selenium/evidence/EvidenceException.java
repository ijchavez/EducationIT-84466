package com.automation.selenium.evidence;

/**
 * Excepcion de dominio para errores relacionados con evidencias.
 *
 * <p>Se usa cuando ocurre un problema durante la captura del screenshot o al
 * escribir el documento Word, y queremos devolver un mensaje mas claro que una
 * excepcion tecnica generica.</p>
 *
 * <p>Por ejemplo, en lugar de propagar directamente una excepcion interna de
 * Selenium o Apache POI, este modulo la envuelve dentro de {@code EvidenceException}
 * para dejar mas claro que el fallo ocurrio en el proceso de generar evidencias.</p>
 */
public class EvidenceException extends RuntimeException {

    /**
     * Crea una excepcion de evidencias con un mensaje amigable y la causa real.
     *
     * @param message descripcion del problema
     * @param cause excepcion original que produjo el error
     */
    public EvidenceException(String message, Throwable cause) {
        super(message, cause);
    }
}