package gui.center;

import database.FileParser;
import drawing.MapDrawer;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import model.Map;
import org.apache.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MapPreviewView extends ImageView {

    double mouseDragStartX;
    double mouseDragStartY;

    private static final int WIDTH = 250;
    private static final int HEIGHT = 250;
    private static int RESIZE_FACTOR = 1;

    public static WritableImage builtImage = null;

    private static final Logger logger = Logger.getLogger(MapPreviewView.class);

    /**
     * Default constructor.
     */
    public MapPreviewView() {
        super();
        this.setFitWidth(WIDTH);
        this.setFitHeight(HEIGHT);

        this.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseDragStartX = mouseEvent.getScreenX();
                mouseDragStartY = mouseEvent.getScreenY();
            }
        });

        this.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                updateImage(mouseEvent.getScreenX(), mouseEvent.getScreenY());
            }
        });

        this.setImage(buildImage());
    }


    /**
     * Converts an image format to another one used for preview
     *
     * @param bf the input image format
     * @return the converted image
     */
    public static WritableImage convertWeirdImageFormat(BufferedImage bf) {
        WritableImage wr = null;
        if (bf != null) {
            wr = new WritableImage(bf.getWidth(), bf.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < bf.getWidth(); x++) {
                for (int y = 0; y < bf.getHeight(); y++) {
                    pw.setArgb(x, y, bf.getRGB(x, y));
                }
            }
        }

        return wr;
    }

    /**
     * Generates the image
     *
     * @return an image (map tile)
     */
    public static WritableImage buildImage() {
        WritableImage wi = null;
        try {
            InputStream in = MapPreviewView.class.getClassLoader().getResourceAsStream("peugue.osm");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            Map myMap = FileParser.readMapFromFile(br);

            /*UserDesignSingleton designSingleton = UserDesignSingleton.getInstance();

            myMap.setTypesToDrawParametersMap(designSingleton.getCheckedParameters());
            myMap.setColorsToDraw(designSingleton.getTypeToColorMap());
            myMap.setPolygonTypesToDraw(designSingleton.getTypeToPolygonMap());*/

            BufferedImage bi = MapDrawer.drawMapOnImage(myMap);
            wi = convertWeirdImageFormat(bi);
            builtImage = wi;
        } catch (Exception e) {
            logger.error(e);
        }
        return wi;
    }

    /**
     * Scale an input image
     *
     * @param src               the original image
     * @param destinationWidth  the new width we want
     * @param destinationHeight the new height we want
     * @return the scaled image
     */
    public static BufferedImage scale(BufferedImage src, int destinationWidth, int destinationHeight) {
        BufferedImage image = new BufferedImage(destinationWidth, destinationHeight, BufferedImage.TYPE_INT_RGB);
        int sourceWidth = src.getWidth();
        int sourceHeight = src.getHeight();
        int[] newYPosition = new int[destinationHeight];
        for (int y = 0; y < destinationHeight; y++) {
            newYPosition[y] = y * sourceHeight / destinationHeight;
            for (int x = 0; x < destinationWidth; x++) {
                int newXPosition = x * sourceWidth / destinationWidth;
                for (y = 0; y < destinationHeight; y++) {
                    int col = src.getRGB(newXPosition, newYPosition[y]);
                    image.setRGB(x, y, col);
                }
            }
        }
        return image;
    }

    /**
     * Drags the image according to the user's mouse
     *
     * @param currentX mouse X position
     * @param currentY mouse Y position
     */
    private void updateImage(double currentX, double currentY) {
        WritableImage image = builtImage;
        int deltaX = (int) (mouseDragStartX - currentX);
        int deltaY = (int) (mouseDragStartY - currentY);

        WritableImage translatedImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = translatedImage.getPixelWriter();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                javafx.scene.paint.Color color = javafx.scene.paint.Color.WHITE;
                if ((x + deltaX) >= 0 && (x + deltaX) < image.getWidth() && (y + deltaY) >= 0 && (y + deltaY) < image.getHeight()) {
                    color = reader.getColor(x + deltaX, y + deltaY);
                }

                //Setting the color to the writable image
                writer.setColor(x, y, color);
            }
        }

        this.setImage(translatedImage);
    }

    private void updateResizedImage(double deltaY) {
        BufferedImage imageToResize = SwingFXUtils.fromFXImage(builtImage, null);

        if (deltaY > 0) {
            RESIZE_FACTOR += 0.25;
        } else {
            RESIZE_FACTOR -= 0.25;
        }
        BufferedImage resizedImage = scale(imageToResize, WIDTH * RESIZE_FACTOR, HEIGHT * RESIZE_FACTOR);
        WritableImage wi = convertWeirdImageFormat(resizedImage);
        builtImage = wi;
        this.setImage(wi);
    }
}
