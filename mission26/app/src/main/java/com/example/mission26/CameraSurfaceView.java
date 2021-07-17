package com.example.mission26;

import android.content.Context;
import android.hardware.Camera;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera camera = null;

    public static final String TAG = "CameraSurfaceView";

    int surfaceWidth;
    int surfaceHeight;

    int bitmapWidth = 0;
    int bitmapHeight = 0;

    public CameraSurfaceView(Context context) {
        super(context);

        init();
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        camera = Camera.open();

        try {
            camera.setPreviewDisplay(mHolder);
        } catch (Exception ex) {
            Log.e(TAG, "Camera Preview setting failed.", ex);
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        surfaceWidth = width;
        surfaceHeight = height;

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                camera.setDisplayOrientation(90);
            } else {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setRotation(90);
                camera.setParameters(parameters);
            }
            camera.setPreviewDisplay(holder);
        } catch (IOException exception) {
            camera.release();
        }
        camera.startPreview();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.setPreviewCallback(null);
        camera.release();

        camera = null;
    }
}
