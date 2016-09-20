package com.gengchao.frescodemo;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.image_view)
    private SimpleDraweeView draweeView;
    private static final String img_url01 = "http://101.200.167.75:8080/phoenixshop/img/banner/5608f3b5Nc8d90151.jpg/test";
    private static final String img_url02 = "http://101.200.167.75:8080/phoenixshop/img/banner/5608f3b5Nc8d90151.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
//        requestImage();
        moreImages();
    }

    private void requestImage() {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(img_url01))
                .setProgressiveRenderingEnabled(true).build();
        PipelineDraweeController pipelineDraweeController = (PipelineDraweeController) Fresco
                .newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.getController()).build();

//        DraweeController controller=Fresco.newDraweeControllerBuilder()
//                .setImageRequest(request)
//                .setOldController(draweeView.getController())
//                .build();
        draweeView.setController(pipelineDraweeController);
    }

    private void moreImages() {
        //监听
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {

                if (imageInfo == null) {
                    return;
                }
                QualityInfo qualityInfo = imageInfo.getQualityInfo();
                FLog.d("Final image received! " +
                                "Size %d x %d",
                        "Quality level %d, good enough: %s, full quality: %s",
                        imageInfo.getWidth(),
                        imageInfo.getHeight(),
                        qualityInfo.getQuality(),
                        qualityInfo.isOfGoodEnoughQuality(),
                        qualityInfo.isOfFullQuality());
                Log.e("", "---------" + imageInfo.getWidth() + "-----" + imageInfo
                        .getHeight() + "---------" + qualityInfo.getQuality()
                        + "-------" + qualityInfo.isOfGoodEnoughQuality()
                        + "---------" + qualityInfo.isOfFullQuality());


            }

            @Override
            public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
                super.onIntermediateImageSet(id, imageInfo);
                FLog.d("","Intermediate image received");
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                super.onFailure(id, throwable);
                FLog.e(getClass(),throwable,"Error loading %s",id);
            }
        };


        DraweeController draweeController=Fresco.newDraweeControllerBuilder()
                //设置监听
                .setControllerListener(controllerListener)
                .setLowResImageRequest(ImageRequest.fromUri(img_url01))
                .setImageRequest(ImageRequest.fromUri(img_url02))
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(draweeController);
    }


}
