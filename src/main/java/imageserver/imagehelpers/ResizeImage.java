package imageserver.imagehelpers;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ResizeImage {
    public static byte[] bufferedToBytes(BufferedImage source, String outputFormat) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(source, outputFormat, bos);
        return bos.toByteArray();
    }

    public static OutputStream bufferedToOutputStream(BufferedImage source, String outputFormat) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(source, outputFormat, bos);
        return bos;
    }

    public static InputStream bufferedToInputStream(BufferedImage source, String outputFormat) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(source, outputFormat, bos);
        InputStream fis = new ByteArrayInputStream(bos.toByteArray());
        return fis;
    }

//    public static BufferedImage scale(BufferedImage source, double ratio) {
////        int w = (int) (source.getWidth() * ratio);
////        int h = (int) (source.getHeight() * ratio);
////        BufferedImage bi = getCompatibleImage(w, h);
////        Graphics2D g2d = bi.createGraphics();
////        double xScale = (double) w / source.getWidth();
////        double yScale = (double) h / source.getHeight();
////        AffineTransform at = AffineTransform.getScaleInstance(xScale,yScale);
////        g2d.drawRenderedImage(source, at);
////        g2d.dispose();
////        return bi;
////
////        ResampleOp resampleOp = new ResampleOp(width, height);
////        resampleOp.setFilter(ResampleFilters.getLanczos3Filter());
////        resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Normal);
////        BufferedImage destImage = resampleOp.filter(sourceImage, null);
//    }

    private static BufferedImage getCompatibleImage(int w, int h) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        BufferedImage image = gc.createCompatibleImage(w, h);
        return image;
    }
}
