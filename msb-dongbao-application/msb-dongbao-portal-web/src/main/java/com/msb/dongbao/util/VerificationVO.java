package com.msb.dongbao.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class VerificationVO implements Serializable {

    /**
     *  滑块图
     */
    private String slidingImage;

    /**
     * 原图
     */
    private String originalImage;

    /**
     *  宽
     */
    private Integer xWidth;

    /**
     *  高
     */
    private Integer yHeight;

}
