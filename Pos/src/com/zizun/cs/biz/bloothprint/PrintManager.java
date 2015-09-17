package com.zizun.cs.biz.bloothprint;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import java.io.OutputStream;

public class PrintManager
{
  private static PrintManager mInstance;
  private BluetoothDevice currentRemoteDevice;
  
  public static void closeBluetooth()
  {
    BlueToothManager.getInstance().closeBluetooth();
  }
  
  public static void closeBluetoothPrintStream()
  {
    BlueToothPrintManager.getInstance().closeBluetoothPrintStream();
  }
  
  public static boolean getBlueToothState()
  {
    return (BlueToothManager.getInstance().isdrived()) && (BlueToothManager.getInstance().isOpen());
  }
  
  public static PrintManager getInstance()
  {
    if (mInstance == null) {
      mInstance = new PrintManager();
    }
    return mInstance;
  }
  
  public static OutputStream getOutputStreamOutUIThread(BluetoothDevice paramBluetoothDevice)
  {
    BlueToothPrintManager.getInstance().initManager(paramBluetoothDevice);
    return BlueToothPrintManager.getInstance().getOutputStreamOutUIThread();
  }
  
  public static void openBlueTooth(Activity paramActivity)
  {
    BlueToothManager.getInstance().openBluetooth(paramActivity);
  }
  
  public static void registerBlueToothReceiver()
  {
    BlueToothManager.getInstance().registerBlueToothReceiver();
  }
  
  public static void searchDevices()
  {
    BlueToothManager.getInstance().searchDevices();
  }
  
  public static void setSearchListener(BlueToothManager.OnBluetoothSearchListener paramOnBluetoothSearchListener)
  {
    BlueToothManager.getInstance().setSearchListener(paramOnBluetoothSearchListener);
  }
  
  public static void setStateChangeListener(BlueToothManager.OnBluetoothStateChangeListener paramOnBluetoothStateChangeListener)
  {
    BlueToothManager.getInstance().setStateChangeListener(paramOnBluetoothStateChangeListener);
  }
  
  public static void setStatusChangeListener(BlueToothPrintManager.OnBlueToothPrintStatusChangeListener paramOnBlueToothPrintStatusChangeListener)
  {
    BlueToothPrintManager.getInstance().setStatusChangeListener(paramOnBlueToothPrintStatusChangeListener);
  }
  
  public static void unregisterReceiver()
  {
    BlueToothManager.getInstance().unregisterReceiver();
  }
  
  public static void writeToStreamOutUIThread(String paramString)
  {
    BlueToothPrintManager.getInstance().writeToStreamOutUIThread(paramString);
  }
  
  public BluetoothDevice getCurrentRemoteDevice()
  {
    return this.currentRemoteDevice;
  }
  
  public void setCurrentRemoteDevice(BluetoothDevice paramBluetoothDevice)
  {
    this.currentRemoteDevice = paramBluetoothDevice;
  }
}