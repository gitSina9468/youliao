package com.seahorse.youliao.entity.freemarker;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumMap;
import java.util.Map;

/**
 * @description: 二维码实体
 * @author: Mr.Song
 * @create: 2019-12-24 21:26
 **/
public class QRCode {

    private int width  = 400;
    private int height = 400;
    private String encoding = "UTF-8";
    private String formatName = "png";
    private float logoScale = 0.2f;
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    public int getWidth() {
        return width;
    }
    /**
     * set width of QRCOde
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }
    /**
     * set height of RQCode
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getFormatName() {
        return formatName;
    }
    /**
     * set image format
     * @param formatName
     */
    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public float getLogoScale() {
        return logoScale;
    }
    /**
     * 设置logo相对于二维码的缩放比例
     * @param logoScale
     */
    public void setLogoScale(float logoScale) {
        if(logoScale<0 || logoScale>1){
            this.logoScale=0.2f;
        }
        this.logoScale = logoScale;
    }

    public QRCode() {

    }
    /**
     * 设置二维码宽度和高度
     * @param width
     * @param height
     */
    public QRCode(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void creatQrCode(String content, OutputStream out) throws Exception{
        BitMatrix bitMatrix = getBitMatrix(content);

        BufferedImage image=convertBitMatrixToImage(bitMatrix);

        ImageIO.write(image, formatName, out);
    }

    public void creatQrCode(String content, InputStream logoImgInput, OutputStream out)
            throws Exception{
        BitMatrix bitMatrix = getBitMatrix(content);

        BufferedImage image=convertBitMatrixToImage(bitMatrix);

        addLogoToImage(image, logoImgInput);

        ImageIO.write(image, formatName, out);
    }
    /**
     * 解析二维码
     * @param file
     * @return
     * @throws Exception
     */
    public String decode(File file) throws Exception {
        BufferedImage image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(
                image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        Map< DecodeHintType, Object > tmpHintsMap = new EnumMap< DecodeHintType, Object >(
                DecodeHintType.class);
        tmpHintsMap.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
        tmpHintsMap.put(DecodeHintType.CHARACTER_SET, encoding);
        Result result = new MultiFormatReader().decode(bitmap, tmpHintsMap);
        String resultStr = result.getText();
        return resultStr;
    }

    /**
     * 获取2D位图的二维码图片
     * @param content
     * @return
     * @throws Exception
     */
    private BitMatrix getBitMatrix(String content) throws Exception{
        Map <EncodeHintType, Object > tmpHintsMap = new EnumMap < EncodeHintType, Object > (
                EncodeHintType.class);
        tmpHintsMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        tmpHintsMap.put(EncodeHintType.CHARACTER_SET, encoding);
        tmpHintsMap.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, width, height, tmpHintsMap );
        return bitMatrix;
    }

    /**
     * 添加logo到二维码上
     * @param image
     * @param logoInput
     * @throws Exception
     */
    private void addLogoToImage(BufferedImage image,InputStream logoInput) throws Exception{
        BufferedImage logoImage = ImageIO.read(logoInput);
        int width = image.getWidth();
        int height = image.getHeight();
        int logoWidth = (int)(width*logoScale);
        int logoHeight  = (int)(height*logoScale);
        Image logo = logoImage.getScaledInstance(logoWidth, logoHeight,
                Image.SCALE_SMOOTH);
        // 插入LOGO
        Graphics2D graph = image.createGraphics();
        int x = (width - logoWidth) / 2;
        int y = (height - logoHeight) / 2;
        graph.drawImage(logo, x, y, logoWidth, logoHeight, null);
        Shape shape = new RoundRectangle2D.Float(x, y, logoWidth, logoHeight, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    private BufferedImage convertBitMatrixToImage(BitMatrix bitMatrix){
        int qrWidth = bitMatrix.getWidth();
        int qrHeight = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(qrWidth, qrHeight, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
            }
        }

        return image;
    }
    @Deprecated
    private int makeColor(boolean background){
        int[] backgrounds = {Color.WHITE.getRGB(),Color.BLUE.getRGB(),Color.GREEN.getRGB()};
        int[] foregrounds = {Color.ORANGE.getRGB(),Color.CYAN.getRGB(),Color.RED.getRGB(),Color.YELLOW.getRGB()};
        if(background){
            return backgrounds[(int)(System.currentTimeMillis())%3];
        }else {
            return foregrounds[(int)(System.currentTimeMillis())%4];
        }
    }
}
