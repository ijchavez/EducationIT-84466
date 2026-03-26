package com.automation.selenium.evidence;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

import javax.imageio.ImageIO;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * Utilidad interna que escribe evidencias dentro de un archivo Word.
 *
 * <p>Su responsabilidad es abrir o crear un {@code .docx}, agregar un titulo,
 * insertar la imagen y guardar el documento nuevamente.</p>
 *
 * <p>Esta clase no saca screenshots ni decide nombres de archivos. Esas tareas
 * pertenecen a {@link EvidenceService} y {@link ScreenshotEvidenceUtil}. Aqui
 * solo se trabaja con un documento Word ya definido y una imagen ya existente.</p>
 */
final class WordEvidenceWriter {

    private static final int TITLE_FONT_SIZE = 13;

    private final int maxImageWidthPx;

    /**
     * Crea el writer con un ancho maximo por defecto de 900 pixeles para la
     * imagen dentro del documento.
     */
    WordEvidenceWriter() {
        this(900);
    }

    /**
     * Crea el writer indicando el ancho maximo permitido para la imagen dentro
     * del documento Word.
     *
     * <p>Esto no cambia el archivo PNG original. Solo controla el tamano con el
     * que se inserta la imagen dentro del {@code .docx}.</p>
     *
     * @param maxImageWidthPx ancho maximo de la imagen en el documento
     * @throws IllegalArgumentException si el ancho es menor o igual a cero
     */
    WordEvidenceWriter(int maxImageWidthPx) {
        if (maxImageWidthPx <= 0) {
            throw new IllegalArgumentException("maxImageWidthPx must be greater than zero.");
        }
        this.maxImageWidthPx = maxImageWidthPx;
    }

    /**
     * Agrega una evidencia a un archivo Word.
     *
     * <p>Si el documento no existe, lo crea. Si ya existe, lo abre y agrega una
     * nueva seccion con el titulo y la imagen.</p>
     *
     * @param documentPath ruta del archivo Word
     * @param imagePath ruta del screenshot que se insertara
     * @param evidenceTitle titulo visible dentro del documento
     * @return la ruta del documento ya actualizado
     * @throws IOException si ocurre un problema al leer o escribir archivos
     */
    Path appendEvidence(Path documentPath, Path imagePath, String evidenceTitle) throws IOException {
        Objects.requireNonNull(documentPath, "documentPath must not be null");
        Objects.requireNonNull(imagePath, "imagePath must not be null");
        Objects.requireNonNull(evidenceTitle, "evidenceTitle must not be null");

        Path parent = documentPath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        try (XWPFDocument document = openOrCreate(documentPath)) {
            appendTitle(document, evidenceTitle);
            appendImage(document, imagePath);
            writeDocument(documentPath, document);
        }

        return documentPath;
    }

    /**
     * Abre un documento existente o crea uno nuevo si todavia no existe.
     *
     * @param documentPath ruta del archivo Word
     * @return documento listo para ser modificado
     * @throws IOException si ocurre un error de lectura
     */
    private XWPFDocument openOrCreate(Path documentPath) throws IOException {
        if (!Files.exists(documentPath)) {
            return new XWPFDocument();
        }

        try (InputStream inputStream = Files.newInputStream(documentPath)) {
            return new XWPFDocument(inputStream);
        }
    }

    /**
     * Agrega el titulo de la evidencia como un nuevo parrafo dentro del documento.
     *
     * @param document documento Word en memoria
     * @param evidenceTitle titulo que se mostrara arriba de la imagen
     */
    private void appendTitle(XWPFDocument document, String evidenceTitle) {
        XWPFParagraph titleParagraph = document.createParagraph();
        titleParagraph.setSpacingBefore(160);
        titleParagraph.setSpacingAfter(120);

        XWPFRun titleRun = titleParagraph.createRun();
        titleRun.setBold(true);
        titleRun.setFontSize(TITLE_FONT_SIZE);
        titleRun.setText(evidenceTitle);
    }

    /**
     * Inserta la imagen de la evidencia en el documento Word.
     *
     * <p>Antes de agregarla, calcula un tamano razonable para que la captura no
     * desborde el documento.</p>
     *
     * @param document documento Word en memoria
     * @param imagePath ruta de la imagen PNG
     * @throws IOException si no se puede leer la imagen
     */
    private void appendImage(XWPFDocument document, Path imagePath) throws IOException {
        ImageDimension imageDimension = resolveImageDimension(imagePath);

        XWPFParagraph imageParagraph = document.createParagraph();
        imageParagraph.setAlignment(ParagraphAlignment.CENTER);
        imageParagraph.setSpacingAfter(240);

        XWPFRun imageRun = imageParagraph.createRun();
        try (InputStream imageStream = Files.newInputStream(imagePath)) {
            imageRun.addPicture(
                    imageStream,
                    Document.PICTURE_TYPE_PNG,
                    imagePath.getFileName().toString(),
                    Units.toEMU(imageDimension.width()),
                    Units.toEMU(imageDimension.height())
            );
        } catch (InvalidFormatException exception) {
            throw new EvidenceException("Could not add the screenshot to the Word document.", exception);
        }
    }

    /**
     * Guarda el documento Word en la ruta indicada.
     *
     * <p>Si el archivo ya existe, lo reemplaza por la version actualizada.</p>
     *
     * @param documentPath ruta del archivo Word
     * @param document documento en memoria listo para persistir
     * @throws IOException si falla la escritura del archivo
     */
    private void writeDocument(Path documentPath, XWPFDocument document) throws IOException {
        try (OutputStream outputStream = Files.newOutputStream(
                documentPath,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE
        )) {
            document.write(outputStream);
        }
    }

    /**
     * Calcula el tamano con el que debe insertarse la imagen en el documento.
     *
     * <p>Si la imagen ya es menor o igual al ancho maximo, la deja igual. Si es
     * mas grande, la reduce manteniendo la proporcion para que no quede deformada.</p>
     *
     * @param imagePath ruta del PNG a insertar
     * @return dimensiones finales para usar dentro del documento Word
     * @throws IOException si la imagen no puede leerse correctamente
     */
    private ImageDimension resolveImageDimension(Path imagePath) throws IOException {
        BufferedImage image = ImageIO.read(imagePath.toFile());
        if (image == null) {
            throw new IOException("The screenshot could not be read as a valid PNG image: " + imagePath);
        }

        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();

        if (originalWidth <= maxImageWidthPx) {
            return new ImageDimension(originalWidth, originalHeight);
        }

        double scale = (double) maxImageWidthPx / originalWidth;
        int scaledHeight = Math.max(1, (int) Math.round(originalHeight * scale));

        return new ImageDimension(maxImageWidthPx, scaledHeight);
    }

    /**
     * Estructura simple para devolver ancho y alto calculados para la imagen.
     *
     * @param width ancho final
     * @param height alto final
     */
    private record ImageDimension(int width, int height) {
    }
}