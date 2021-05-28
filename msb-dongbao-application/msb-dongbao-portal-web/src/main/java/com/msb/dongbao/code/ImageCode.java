package com.msb.dongbao.code;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import lombok.Data;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

@Data
public class ImageCode {
    // 图形中的内容
    private String code = "";

    // 图片
    private ByteArrayInputStream image;

    private int width = 400;
    private int height = 100;

    // 课下丰富单利
    public static ImageCode getInstance() throws Exception {
        return new ImageCode();
    }

    private ImageCode() throws Exception {
        System.out.println("===========开始画图===============");
        // 图形缓冲区
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        // 给你一支笔
        Graphics graphics = image.getGraphics();//画布

        // 拿笔涂色 画图形
        graphics.setColor(new Color(255,255,255));
        // 画矩形
        graphics.fillRect(0,0,width,height);
        // 字体
        graphics.setFont(new Font("宋体",Font.PLAIN,30));

        Random random = new Random();
        /**
         * 下面是划数字
         */
//        for (int i = 0; i < 6; i++) {
//            String s = String.valueOf(random.nextInt(10));
//            this.code += s;
//            // System.out.println("imagecode:"+code);
//            graphics.setColor(new Color(0,0,0));
//            graphics.drawString(s,(width/6)*i,40);
//            //识别中文
//            //graphics.drawString("我是中国人",(width/6)*i,40);
//            // 划线
//            graphics.setColor(new Color(100,100,100));
//            graphics.drawLine((width/6)*i,40,(width/6)*i+25,40-30);
//        }

        /**
         * 使用加法验证码
         */
        // 先定义两个数 20以内的随机数
        int num1 = random.nextInt(20);
        int num2 = random.nextInt(20);
        // 设置字体颜色
        graphics.setColor(new Color(0,200,100));
        graphics.drawString(num1+"",(width/6)*0+2,30);
        graphics.drawString("+",(width/6)*1+2,30);
        graphics.drawString(num2+"",(width/6)*2+2,30);
        graphics.drawString("=",(width/6)*3+2,30);
        graphics.drawString("?",(width/6)*4+2,30);

        int result = num1 + num2;
        this.code = result + "";

        //干扰线
        graphics.setColor(new Color(100,100,100));
        for (int i = 0; i < 1000; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(20);
            int y1 = random.nextInt(20);
            graphics.drawLine(x,y,x+x1,y+y1);
        }


        // 收笔
        graphics.dispose();

        ByteArrayInputStream inputStream = null;
        ByteOutputStream outputStream = new ByteOutputStream();

        try {
            // 赋值给byteArrayInputStream
            ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
            ImageIO.write(image,"jpeg", imageOutputStream);

            inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        }catch (Exception e) {
            System.out.println("生成验证码失败");
            e.printStackTrace();
        }

        this.image = inputStream;

    }
}
