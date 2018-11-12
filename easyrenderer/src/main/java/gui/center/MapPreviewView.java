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
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import model.Map;
import org.apache.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class MapPreviewView extends ImageView {

    double mouseDragStartX;
    double mouseDragEndX;

    double mouseDragStartY;
    double mouseDragEndY;

    double scrollStart;
    double scrollEnd;

    WritableImage previewImage;

    private static final int WIDTH = 250;
    private static final int HEIGHT = 250;
    private static int RESIZE_FACTOR = 1;

    public static WritableImage builtImage = null;

    private static final Logger logger = Logger.getLogger(MapPreviewView.class);

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
        
        /*this.addEventFilter(ScrollEvent.SCROLL_STARTED, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent scrollEvent) {
            	scrollStart = scrollEvent.getDeltaY();
            }
        });*/

        this.addEventFilter(ScrollEvent.SCROLL_FINISHED, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent scrollEvent) {
                System.out.println("shit started");
                updateResizedImage(scrollEvent.getDeltaY());
            }
        });

        this.setImage(buildImage(getParametersToDrawToMap()));
    }

    private HashMap getParametersToDrawToMap() {
        HashMap<String, Boolean> typesToDraw = null;
        if (this.getParent() instanceof CenterView) {
            typesToDraw = (HashMap<String, Boolean>) ((CenterView) this.getParent()).getParameters();
        }
        return typesToDraw;
    }

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

    public static WritableImage buildImage(HashMap<String, Boolean> typesToDraw) {
        WritableImage wi = null;
        try {
            InputStream in = MapPreviewView.class.getClassLoader().getResourceAsStream("peugue.osm");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            Map myMap = FileParser.readMapFromFile(br);
            myMap.setTypesToDrawParametersMap(typesToDraw);

            BufferedImage bi = MapDrawer.drawMapOnImage(myMap);
            wi = convertWeirdImageFormat(bi);
            builtImage = wi;
        } catch (Exception e) {
            logger.error(e);
        }
        return wi;
    }

    public static BufferedImage scale(BufferedImage src, int w, int h) {
        BufferedImage img =
                new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        int x, y;
        int ww = src.getWidth();
        int hh = src.getHeight();
        int[] ys = new int[h];
        for (y = 0; y < h; y++)
            ys[y] = y * hh / h;
        for (x = 0; x < w; x++) {
            int newX = x * ww / w;
            for (y = 0; y < h; y++) {
                int col = src.getRGB(newX, ys[y]);
                img.setRGB(x, y, col);
            }
        }
        return img;
    }

    private void updateImage(double currentX, double currentY) {
        WritableImage image = builtImage;
        int deltaX = (int) (mouseDragStartX - currentX);
        int deltaY = (int) (mouseDragStartY - currentY);

        WritableImage translatedImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = translatedImage.getPixelWriter();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                Color color = Color.WHITE;
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
