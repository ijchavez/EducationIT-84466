package com.automation.selenium.evidence;

import java.io.IOException;
import java.nio.file.Path;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

import org.openqa.selenium.WebDriver;

/**
 * Centraliza la generacion de evidencias para pruebas Selenium.
 *
 * <p>La idea de esta clase es que el test no tenga que preocuparse por:
 * crear carpetas, elegir nombres de archivos, sacar el screenshot o escribir
 * el documento Word. El test solamente llama al servicio y le pasa el
 * {@link WebDriver} y el nombre del caso de prueba.</p>
 *
 * <p>Cada instancia de {@code EvidenceService} representa una corrida de tests.
 * Cuando se crea, arma una carpeta unica con fecha y hora dentro de la ruta base.
 * Por ejemplo:</p>
 *
 * <pre>{@code
 * test-output/evidencias/20260311_201500/
 * ├── screenshot/
 * │   └── login_test.png
 * └── login_test.docx
 * }</pre>
 *
 * <p>Por eso, en frameworks con TestNG, lo mas comun es instanciar este servicio
 * una sola vez por clase o por suite, por ejemplo en un {@code @BeforeClass}.
 * Si lo crearas en cada {@code @BeforeMethod}, cada test tendria una carpeta de
 * corrida distinta.</p>
 *
 * <p>Uso tipico:</p>
 *
 * <pre>{@code
 * private EvidenceService evidenceService;
 *
 * @BeforeClass
 * public void setupEvidence() {
 *     evidenceService = new EvidenceService();
 * }
 *
 * @AfterMethod
 * public void tearDown(ITestResult result) throws IOException {
 *     evidenceService.captureTestEvidence(driver, result.getMethod().getMethodName());
 * }
 * }</pre>
 */
public final class EvidenceService {

    private static final String DEFAULT_EVIDENCE_TITLE = "Evidencia";
    private static final Path DEFAULT_BASE_DIRECTORY = Path.of("test-output", "evidencias");
    private static final DateTimeFormatter RUN_FOLDER_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private final Path runEvidenceDirectory;
    private final Path screenshotDirectory;
    private final WordEvidenceWriter documentWriter;

    /**
     * Crea el servicio usando la carpeta base por defecto:
     * {@code test-output/evidencias}.
     *
     * <p>Dentro de esa carpeta se crea automaticamente una subcarpeta para la
     * corrida actual, por ejemplo {@code 20260311_201500}.</p>
     */
    public EvidenceService() {
        this(DEFAULT_BASE_DIRECTORY);
    }

    /**
     * Crea el servicio indicando una carpeta base personalizada.
     *
     * <p>Ejemplo: si la ruta base es {@code Path.of("evidencias")}, el servicio
     * generara una estructura similar a:</p>
     *
     * <pre>{@code
     * evidencias/20260311_201500/
     * ├── screenshot/
     * └── login_test.docx
     * }</pre>
     *
     * @param baseEvidenceDirectory carpeta raiz donde se guardaran las evidencias
     * @throws NullPointerException si la ruta es {@code null}
     */
    public EvidenceService(Path baseEvidenceDirectory) {
        this(baseEvidenceDirectory, 900);
    }

    /**
     * Crea el servicio indicando carpeta base y ancho maximo para las imagenes
     * dentro del documento Word.
     *
     * <p>El screenshot original se guarda completo como PNG. El parametro
     * {@code maxImageWidthPx} solo afecta como se inserta la imagen en el
     * archivo {@code .docx}, para que no quede demasiado grande.</p>
     *
     * @param baseEvidenceDirectory carpeta raiz donde se guardaran las evidencias
     * @param maxImageWidthPx ancho maximo en pixeles para la imagen dentro del Word
     * @throws NullPointerException si la ruta es {@code null}
     */
    public EvidenceService(Path baseEvidenceDirectory, int maxImageWidthPx) {
        Objects.requireNonNull(baseEvidenceDirectory, "baseEvidenceDirectory must not be null");

        this.runEvidenceDirectory = baseEvidenceDirectory.resolve(RUN_FOLDER_FORMATTER.format(LocalDateTime.now()));
        this.screenshotDirectory = runEvidenceDirectory.resolve("screenshot");
        this.documentWriter = new WordEvidenceWriter(maxImageWidthPx);
    }

    /**
     * Devuelve la carpeta creada para la corrida actual.
     *
     * <p>Puede ser util para imprimirla en consola, adjuntarla a un reporte o
     * verificar rapidamente donde quedaron guardadas las evidencias.</p>
     *
     * @return ruta de la carpeta de evidencias de esta corrida
     */
    public Path getRunEvidenceDirectory() {
        return runEvidenceDirectory;
    }

    /**
     * Alias simple de {@link #captureTestEvidence(WebDriver, String)}.
     *
     * <p>Existe para que el nombre del metodo sea mas corto cuando el contexto ya
     * deja claro que se trata de una evidencia de test.</p>
     *
     * @param driver instancia activa de Selenium WebDriver
     * @param testName nombre del test, usado para nombrar archivos
     * @return resultado con la ruta del PNG, la ruta del DOCX y el titulo usado
     * @throws IOException si ocurre un problema al guardar archivos
     */
    public EvidenceCaptureResult captureEvidence(WebDriver driver, String testName) throws IOException {
        return captureTestEvidence(driver, testName);
    }

    /**
     * Genera una evidencia usando el mismo valor tanto para el nombre del archivo
     * como para el titulo dentro del documento Word.
     *
     * <p>Por ejemplo, si {@code testName} es {@code loginTest}, se crea:</p>
     *
     * <pre>{@code
     * screenshot/login_test.png
     * login_test.docx
     * }</pre>
     *
     * <p>y dentro del documento se agrega el titulo {@code loginTest} antes de la
     * imagen.</p>
     *
     * @param driver instancia activa de Selenium WebDriver
     * @param testName nombre del test
     * @return resultado con informacion de los archivos generados
     * @throws IOException si ocurre un problema al guardar archivos
     */
    public EvidenceCaptureResult captureTestEvidence(WebDriver driver, String testName) throws IOException {
        return captureTestEvidence(driver, testName, testName);
    }

    /**
     * Genera una evidencia completa del estado actual del navegador.
     *
     * <p>Este metodo hace tres cosas:</p>
     *
     * <p>1. Saca un screenshot del navegador.</p>
     * <p>2. Lo guarda como PNG dentro de {@code screenshot/}.</p>
     * <p>3. Crea o actualiza un archivo {@code .docx} con el mismo nombre del test,
     * agregando un titulo y la imagen.</p>
     *
     * <p>El parametro {@code testName} se usa para construir los nombres de archivo.
     * El parametro {@code evidenceTitle} se usa como texto visible dentro del Word.</p>
     *
     * <p>Ejemplo:</p>
     *
     * <pre>{@code
     * captureTestEvidence(driver, "login_ok", "Login correcto");
     * }</pre>
     *
     * <p>Puede generar algo como:</p>
     *
     * <pre>{@code
     * screenshot/login_ok.png
     * login_ok.docx
     * }</pre>
     *
     * @param driver instancia activa de Selenium WebDriver
     * @param testName nombre tecnico del test, usado para los archivos
     * @param evidenceTitle titulo visible dentro del documento Word
     * @return resultado con la ruta del documento, la imagen y el titulo final usado
     * @throws IOException si ocurre un problema al crear directorios o escribir archivos
     * @throws IllegalArgumentException si {@code testName} es vacio o invalido
     * @throws NullPointerException si {@code driver} es {@code null}
     */
    public EvidenceCaptureResult captureTestEvidence(
            WebDriver driver,
            String testName,
            String evidenceTitle
    ) throws IOException {
        Objects.requireNonNull(driver, "driver must not be null");

        String normalizedTestName = normalizeTestName(testName);
        String normalizedTitle = normalizeTitle(evidenceTitle, testName);

        Path screenshotPath = screenshotDirectory.resolve(normalizedTestName + ".png");
        Path documentPath = runEvidenceDirectory.resolve(normalizedTestName + ".docx");

        Path storedScreenshot = ScreenshotEvidenceUtil.capture(driver, screenshotPath);
        Path generatedDocument = documentWriter.appendEvidence(documentPath, storedScreenshot, normalizedTitle);

        return new EvidenceCaptureResult(generatedDocument, storedScreenshot, normalizedTitle);
    }

    /**
     * Valida el nombre del test y lo convierte en un nombre seguro para archivo.
     *
     * <p>Esto evita problemas con espacios, acentos o caracteres especiales.
     * Por ejemplo, {@code "Login OK"} pasa a algo parecido a {@code "login_ok"}.</p>
     *
     * @param testName nombre original del test
     * @return nombre saneado y listo para usar en archivos
     */
    private String normalizeTestName(String testName) {
        if (testName == null || testName.isBlank()) {
            throw new IllegalArgumentException("testName must not be blank");
        }

        String sanitizedTestName = sanitizeFileName(testName);
        if (sanitizedTestName.isBlank()) {
            throw new IllegalArgumentException("testName must contain at least one valid character");
        }

        return sanitizedTestName;
    }

    /**
     * Define el titulo que se escribira dentro del documento Word.
     *
     * <p>Reglas:</p>
     *
     * <p>1. Si {@code evidenceTitle} viene informado, usa ese valor.</p>
     * <p>2. Si no viene, usa {@code testName}.</p>
     * <p>3. Si ambos vienen vacios, usa {@code "Evidencia"}.</p>
     *
     * @param evidenceTitle titulo deseado para la evidencia
     * @param testName nombre del test como fallback
     * @return titulo final que se escribira dentro del Word
     */
    private String normalizeTitle(String evidenceTitle, String testName) {
        if (evidenceTitle != null && !evidenceTitle.isBlank()) {
            return evidenceTitle.trim();
        }

        if (testName != null && !testName.isBlank()) {
            return testName.trim();
        }

        return DEFAULT_EVIDENCE_TITLE;
    }

    /**
     * Convierte un texto cualquiera en un nombre de archivo simple y seguro.
     *
     * <p>Este metodo:</p>
     *
     * <p>1. Quita acentos.</p>
     * <p>2. Convierte a minusculas.</p>
     * <p>3. Reemplaza grupos de caracteres no validos por guiones bajos.</p>
     * <p>4. Elimina guiones bajos al inicio o al final.</p>
     *
     * @param value texto original
     * @return texto listo para usar como nombre de archivo
     */
    private String sanitizeFileName(String value) {
        String withoutAccents = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "");

        return withoutAccents
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "_")
                .replaceAll("^_+|_+$", "");
    }
}