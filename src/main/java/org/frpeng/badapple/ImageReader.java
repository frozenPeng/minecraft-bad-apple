package org.frpeng.badapple;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


public class ImageReader {

    public static float[][] getImage(String image_name, int width, int height) {
        try {
            // Load the image using ClassLoader
            InputStream inputStream = ImageReader.class.getClassLoader().getResourceAsStream("frames/" + image_name);

            if (inputStream == null) {
                System.out.println("Failed to load the image.");
                return null;
            }

            // Read the image file into a BufferedImage
            BufferedImage image = ImageIO.read(inputStream);

            if (image != null) {
                // Convert BufferedImage to grayscale
                BufferedImage grayscaleImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
                grayscaleImage.getGraphics().drawImage(image, 0, 0, null);
                BufferedImage resizedImage = resizeImage(grayscaleImage, width, height);

                // Convert grayscale image to pixel values
                float[][] pixels = new float[width][height];
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        Color color = new Color(resizedImage.getRGB(x, y));
                        // Convert RGB to grayscale by taking the average of R, G, and B values
                        pixels[x][y] = (color.getRed() + color.getGreen() + color.getBlue()) / 3f;
                    }
                }

                // Now you have a 2D array representing the black and white image
                return pixels;
            } else {
                System.out.println("Failed to load the image.");
                return null;
            }
        } catch (IOException e) {
            System.out.println("Error occurred while reading the image: " + e.getMessage());
            return null;
        }
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }



}
