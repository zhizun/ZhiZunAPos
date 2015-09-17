package com.zizun.cs.biz.scan;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Build.VERSION;
import android.os.Handler;
import android.view.SurfaceHolder;
import java.io.IOException;

public final class CameraManager
{
  static final int SDK_INT;
  private static CameraManager cameraManager;
  private final AutoFocusCallback autoFocusCallback;
  private Camera camera;
  private final CameraConfigurationManager configManager;
  private boolean initialized;
  private Camera.Parameters parameter;
  private final PreviewCallback previewCallback;
  private boolean previewing;
  private final boolean useOneShotPreviewCallback;
  
  static
  {
    try
    {
      i = Build.VERSION.SDK_INT;
      SDK_INT = i;
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      for (;;)
      {
        int i = 10000;
      }
    }
  }
  
  private CameraManager(Context paramContext)
  {
    this.configManager = new CameraConfigurationManager(paramContext);
    if (SDK_INT > 3) {}
    for (boolean bool = true;; bool = false)
    {
      this.useOneShotPreviewCallback = bool;
      this.previewCallback = new PreviewCallback(this.configManager, this.useOneShotPreviewCallback);
      this.autoFocusCallback = new AutoFocusCallback();
      return;
    }
  }
  
  public static CameraManager get()
  {
    return cameraManager;
  }
  
  public static void init(Context paramContext)
  {
    if (cameraManager == null) {
      cameraManager = new CameraManager(paramContext);
    }
  }
  
  public void closeDriver()
  {
    if (this.camera != null)
    {
      FlashlightManager.disableFlashlight();
      this.camera.release();
      this.camera = null;
    }
  }
  
  public Point getCameraResolution()
  {
    return this.configManager.getCameraResolution();
  }
  
  public void offLight()
  {
    if (this.camera != null)
    {
      this.parameter = this.camera.getParameters();
      this.parameter.setFlashMode("off");
      this.camera.setParameters(this.parameter);
    }
  }
  
  public void openDriver(SurfaceHolder paramSurfaceHolder)
    throws IOException
  {
    if (this.camera == null)
    {
      this.camera = Camera.open();
      if (this.camera == null) {
        throw new IOException();
      }
      this.camera.setPreviewDisplay(paramSurfaceHolder);
      if (!this.initialized)
      {
        this.initialized = true;
        this.configManager.initFromCameraParameters(this.camera);
      }
      this.configManager.setDesiredCameraParameters(this.camera);
      FlashlightManager.enableFlashlight();
    }
  }
  
  public void openLight()
  {
    if (this.camera != null)
    {
      this.parameter = this.camera.getParameters();
      this.parameter.setFlashMode("torch");
      this.camera.setParameters(this.parameter);
    }
  }
  
  public void requestAutoFocus(Handler paramHandler, int paramInt)
  {
    if ((this.camera != null) && (this.previewing))
    {
      this.autoFocusCallback.setHandler(paramHandler, paramInt);
      this.camera.autoFocus(this.autoFocusCallback);
    }
  }
  
  public void requestPreviewFrame(Handler paramHandler, int paramInt)
  {
    if ((this.camera != null) && (this.previewing))
    {
      this.previewCallback.setHandler(paramHandler, paramInt);
      if (this.useOneShotPreviewCallback) {
        this.camera.setOneShotPreviewCallback(this.previewCallback);
      }
    }
    else
    {
      return;
    }
    this.camera.setPreviewCallback(this.previewCallback);
  }
  
  public void startPreview()
  {
    if ((this.camera != null) && (!this.previewing))
    {
      this.camera.startPreview();
      this.previewing = true;
    }
  }
  
  public void stopPreview()
  {
    if ((this.camera != null) && (this.previewing))
    {
      if (!this.useOneShotPreviewCallback) {
        this.camera.setPreviewCallback(null);
      }
      this.camera.stopPreview();
      this.previewCallback.setHandler(null, 0);
      this.autoFocusCallback.setHandler(null, 0);
      this.previewing = false;
    }
  }
}