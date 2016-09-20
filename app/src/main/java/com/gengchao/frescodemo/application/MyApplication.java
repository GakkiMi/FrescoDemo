package com.gengchao.frescodemo.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;

/**
 *Created by Ocean on 2016/9/19.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //基本使用的初始化方法
//        Fresco.initialize(this);
//        渐进式JPEG图的初始化方法
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig
                .newBuilder(this).setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
                .build();
        Fresco.initialize(this, imagePipelineConfig);

    }
}
