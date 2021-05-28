package com.msb.dongbao.util;

import com.octo.captcha.CaptchaFactory;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.word.FileDictionary;
import com.octo.captcha.component.word.wordgenerator.ComposeDictionaryWordGenerator;
import com.octo.captcha.engine.GenericCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.octo.captcha.service.multitype.GenericManageableCaptchaService;

import java.awt.*;

public class JcaptchaUtil {


    private static ImageCaptchaService service = imageCaptchaService();

    public static ImageCaptchaService getService() {
        return service;
    }

    //
    private static ImageCaptchaService imageCaptchaService(){
        //背景
        UniColorBackgroundGenerator backgroundGenerator = new UniColorBackgroundGenerator(100,50);
        //字的颜色
        RandomRangeColorGenerator textColor = new RandomRangeColorGenerator(new int[]{0,100},new int[]{0,200},new int[]{50,250});
        RandomTextPaster randomTextPaster = new RandomTextPaster(4, 4, textColor);
        //字
        RandomFontGenerator randomFontGenerator = new RandomFontGenerator(20,30,new Font[]{new Font("TimeRoman", Font.PLAIN,20)});
        //组装图像
        ComposedWordToImage composedWordToImage = new ComposedWordToImage(randomFontGenerator,backgroundGenerator,randomTextPaster);
        ComposeDictionaryWordGenerator cdwg = new ComposeDictionaryWordGenerator(new FileDictionary("toddlist"));
        GimpyFactory gf = new GimpyFactory(cdwg,composedWordToImage);
        GenericCaptchaEngine gce = new GenericCaptchaEngine(new CaptchaFactory[]{gf});

        return new GenericManageableCaptchaService(gce,20,2000,2000);
    }
}
