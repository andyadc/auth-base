package com.adc.idea.common.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class ImageUtils {
	
	public static void zoomImage(InputStream inputStream, OutputStream outputStream, int x1, int y1, int x2, int y2)
			throws Exception {
		BufferedImage bufferedImage = ImageIO.read(inputStream);

		//
		int height = bufferedImage.getHeight();
		int width = bufferedImage.getWidth();

		if (height > width) {
			int h2 = 512;
			int w2 = (512 * width) / height;
			bufferedImage = zoomImage(bufferedImage, w2, h2);
		} else {
			int w2 = 512;
			int h2 = (512 * height) / width;
			bufferedImage = zoomImage(bufferedImage, w2, h2);
		}

		//
		BufferedImage outImage = bufferedImage.getSubimage(x1, y1, x2 - x1, y2 - y1);
		ImageIO.write(outImage, "png", outputStream);
		inputStream.close();
		outputStream.flush();
	}

	public static void zoomImage(InputStream inputStream, OutputStream outputStream, int toWidth, int toHeight)
			throws Exception {
		BufferedImage bufferedImage = ImageIO.read(inputStream);

		bufferedImage = zoomImage(bufferedImage, toWidth, toHeight);

		BufferedImage outImage = bufferedImage;
		ImageIO.write(outImage, "png", outputStream);
		inputStream.close();
		outputStream.flush();
	}

	/**
	 * @param im
	 *            原始图像
	 * @param resizeTimes
	 *            倍数,比如0.5就是缩小一半,0.98等等double类型
	 * @return 返回处理后的图像
	 */
	public static BufferedImage zoomImage(BufferedImage srcImage, int toWidth, int toHeight) {
		BufferedImage result = null;

		try {
			BufferedImage im = srcImage;

			/* 原始图像的宽度和高度 */
			// int width = im.getWidth();
			// int height = im.getHeight();

			/* 新生成结果图片 */
			result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);

			result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, Image.SCALE_SMOOTH), 0, 0, null);
		} catch (Exception e) {
			System.out.println("创建缩略图发生异常" + e.getMessage());
		}

		return result;
	}
}
