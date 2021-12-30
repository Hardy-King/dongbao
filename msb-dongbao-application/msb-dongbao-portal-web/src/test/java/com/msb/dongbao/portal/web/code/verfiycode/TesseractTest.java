package com.msb.dongbao.portal.web.code.verfiycode;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class TesseractTest {

    public static void main(String[] args) throws TesseractException {



        ITesseract itesseract = new Tesseract();
        // 语言包加载进来
        itesseract.setDatapath("D:\\tessdata");
        itesseract.setLanguage("chi_sim");
        //itesseract.setLanguage("eng");

        File fileDir = new File("D:\\data");
        for (File file : fileDir.listFiles()) {
            String s = itesseract.doOCR(file);
            System.out.println(file.getName()+"识别后数字是：" + s);
        }

    }
}
