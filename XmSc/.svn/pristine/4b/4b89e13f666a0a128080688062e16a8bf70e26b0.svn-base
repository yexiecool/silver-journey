package com.lsp.shop.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.BufferedReader;
import java.io.File;

import java.io.IOException;

import javax.imageio.ImageIO;
import org.apache.log4j.Logger;


/**
 * 图片缩放
 * 
 * @author db2admin
 * 
 */
public class ImageUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ImageUtil.class);
	
    public static void thumbnailImageT(File imgFile, int w, int h, boolean force){
        if(imgFile.exists()){
            try {
                Image img = ImageIO.read(imgFile);
                if(!force){
                    // 根据原图与要求的缩略图比例，找到最合适的缩略图比例
                    int width = img.getWidth(null);
                    int height = img.getHeight(null);
                    if((width*1.0)/w < (height*1.0)/h){
                        if(width > w){
                            h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w/(width*1.0)));
//                            logger.debug("change image's height, width:{}, height:{}.",w,h);
                        }
                    } else {
                        if(height > h){
                            w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h/(height*1.0)));
//                            logger.debug("change image's width, width:{}, height:{}.",w,h);
                        }
                    }
                }
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.getGraphics();
                g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
                g.dispose();
                String p = imgFile.getPath();
                // 将图片保存在原目录并加上前缀
                ImageIO.write(bi, "jpg", new File(p.substring(0,p.lastIndexOf(File.separator)) + File.separator +imgFile.getName()));
            } catch (IOException e) {
            	logger.error("generate thumbnail image failed.",e);
            }
        }else{
        	logger.warn("the image is not exist.");
        }
    }
}
