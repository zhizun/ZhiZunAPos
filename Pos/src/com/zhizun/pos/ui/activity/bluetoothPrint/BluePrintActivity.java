package com.zhizun.pos.ui.activity.bluetoothPrint;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.bloothprint.BlueToothManager.OnBluetoothSearchListener;
import com.zizun.cs.biz.bloothprint.BlueToothManager.OnBluetoothStateChangeListener;
import com.zizun.cs.biz.bloothprint.BlueToothPrintManager.OnBlueToothPrintStatusChangeListener;
import com.zizun.cs.biz.bloothprint.BlueToothPrintStatus;
import com.zizun.cs.biz.bloothprint.PrintManager;
import com.zizun.cs.biz.receipt.SalesReceipt;
import com.zizun.cs.common.utils.ToastUtil;
import com.zizun.cs.ui.entity.Setting;
import com.zhizun.pos.ui.activity.BaseFragmentTitleTopBarActivity;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarLeftClickListener;
import com.zhizun.pos.ui.widget.TitleTopBar;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class BluePrintActivity
  extends BaseFragmentTitleTopBarActivity
  implements View.OnClickListener, BaseTopBar.OnTopBarLeftClickListener, BlueToothPrintManager.OnBlueToothPrintStatusChangeListener, BlueToothManager.OnBluetoothSearchListener, BlueToothManager.OnBluetoothStateChangeListener
{
  private static final int BLUETOOTH_SOCKET_CONNECTING = 5;
  private static final int BLUETOOTH_SOCKET_CONNECT_FAIL = 7;
  private static final int BLUETOOTH_SOCKET_CONNECT_SUCCESS = 6;
  private static final int BLUETOOTH_SOCKET_CREATE_FAIL = 4;
  private static final int BLUETOOTH_STATE_OFF = 3;
  private static final int BLUETOOTH_STATE_ON = 2;
  private static final int BLUETOOTH_STREAM_OPEN_FAIL = 8;
  private static final int BLUETOOTH_STREAM_OPEN_SUCCESS = 9;
  private static final int BLUETOOTH_STREAM_WRITE_FAIL = 16;
  public static final int PRINT_READY_FALSE = 4;
  public static final int PRINT_READY_TRUE = 3;
  private ArrayList<BluetoothDevice> bandDevices;
  private SimpleAdapter bandDevicesAdapter;
  private Button btn_bluetooth_action;
  private Button btn_device_search;
  private Button btn_print;
  private BluetoothDevice currentDevice;
  private ListView lv_bluetooth_pairing_no;
  private ListView lv_bluetooth_pairing_yes;
  private Context mContext;
  Handler mHandler = new Handler(new Handler.Callback()
  {
    public boolean handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      }
      for (;;)
      {
        return false;
        BluePrintActivity.this.btn_bluetooth_action.setText(2131165648);
        Log.i("BLUETOOTH_STATE_ON", "打开");
        continue;
        BluePrintActivity.this.btn_bluetooth_action.setText(2131165647);
        Log.i("BLUETOOTH_STATE_OFF", "关闭");
        continue;
        BluePrintActivity.this.tv_print_state_text.setText(2131165247);
        continue;
        BluePrintActivity.this.tv_print_state_text.setText(2131165248);
        continue;
        BluePrintActivity.this.tv_print_state_text.setText(2131165249);
        continue;
        BluePrintActivity.this.tv_print_state_text.setText(2131165250);
        continue;
        BluePrintActivity.this.tv_print_state_text.setText(2131165251);
        continue;
        BluePrintActivity.this.tv_print_state_text.setText(2131165238);
        BluePrintActivity.this.setResult(3);
        BluePrintActivity.this.finish();
        continue;
        BluePrintActivity.this.tv_print_state_text.setText(2131165252);
      }
    }
  });
  private SalesReceipt receipt;
  private TextView tv_print_device_text;
  private TextView tv_print_state_text;
  private ArrayList<BluetoothDevice> unBandDevices;
  private SimpleAdapter unBandDevicesAdapter;
  
  private void fillAdapter()
  {
    ArrayList localArrayList1 = initData(this.bandDevices);
    ArrayList localArrayList2 = initData(this.unBandDevices);
    String[] arrayOfString = new String[1];
    arrayOfString[0] = "deviceName";
    int[] arrayOfInt = new int[1];
    arrayOfInt[0] = 2131362605;
    this.bandDevicesAdapter = new SimpleAdapter(this, localArrayList1, 2130903195, arrayOfString, arrayOfInt);
    this.unBandDevicesAdapter = new SimpleAdapter(this, localArrayList2, 2130903195, arrayOfString, arrayOfInt);
    this.lv_bluetooth_pairing_yes.setAdapter(this.bandDevicesAdapter);
    this.lv_bluetooth_pairing_no.setAdapter(this.unBandDevicesAdapter);
  }
  
  private void getIntentData()
  {
    Bundle localBundle = getIntent().getExtras();
    if (localBundle != null) {
      this.receipt = ((SalesReceipt)localBundle.getSerializable(SalesReceipt.class.getSimpleName()));
    }
  }
  
  private void initBaseData()
  {
    this.mContext = this;
    PrintManager.registerBlueToothReceiver();
    PrintManager.setSearchListener(this);
    PrintManager.setStateChangeListener(this);
    PrintManager.setStatusChangeListener(this);
    PrintManager.openBlueTooth(this);
    PrintManager.searchDevices();
  }
  
  private ArrayList<HashMap<String, Object>> initData(ArrayList<BluetoothDevice> paramArrayList)
  {
    ArrayList localArrayList = new ArrayList();
    int j = paramArrayList.size();
    int i = 0;
    for (;;)
    {
      if (i >= j) {
        return localArrayList;
      }
      HashMap localHashMap = new HashMap();
      localHashMap.put("deviceName", ((BluetoothDevice)paramArrayList.get(i)).getName());
      localArrayList.add(localHashMap);
      i += 1;
    }
  }
  
  private void initView()
  {
    this.tv_print_device_text = ((TextView)findViewById(2131362335));
    this.tv_print_state_text = ((TextView)findViewById(2131362338));
    this.btn_device_search = ((Button)findViewById(2131362339));
    this.btn_print = ((Button)findViewById(2131362346));
    this.btn_bluetooth_action = ((Button)findViewById(2131362345));
    this.lv_bluetooth_pairing_yes = ((ListView)findViewById(2131362341));
    this.lv_bluetooth_pairing_no = ((ListView)findViewById(2131362343));
    registerListViewListener();
    this.btn_device_search.setOnClickListener(this);
    this.btn_print.setOnClickListener(this);
    this.btn_bluetooth_action.setOnClickListener(this);
  }
  
  private void registerListViewListener()
  {
    this.lv_bluetooth_pairing_yes.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        if (BluePrintActivity.this.bandDevices.isEmpty()) {
          return;
        }
        paramAnonymousAdapterView = (BluetoothDevice)BluePrintActivity.this.bandDevices.get(paramAnonymousInt);
        BluePrintActivity.this.currentDevice = paramAnonymousAdapterView;
        BluePrintActivity.this.tv_print_device_text.setText(BluePrintActivity.this.currentDevice.getName());
      }
    });
    this.lv_bluetooth_pairing_no.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        if (BluePrintActivity.this.unBandDevices.isEmpty()) {
          return;
        }
        paramAnonymousAdapterView = (BluetoothDevice)BluePrintActivity.this.unBandDevices.get(paramAnonymousInt);
        if (12 == paramAnonymousAdapterView.getBondState())
        {
          BluePrintActivity.this.bandDevices.add(paramAnonymousAdapterView);
          BluePrintActivity.this.unBandDevices.remove(paramAnonymousInt);
          BluePrintActivity.this.fillAdapter();
          return;
        }
        try
        {
          BluetoothDevice.class.getMethod("createBond", new Class[0]).invoke(paramAnonymousAdapterView, new Object[0]);
          return;
        }
        catch (Exception paramAnonymousAdapterView)
        {
          Toast.makeText(BluePrintActivity.this, 2131165241, 0).show();
        }
      }
    });
  }
  
  public void addBody(ViewGroup paramViewGroup)
  {
    getLayoutInflater().inflate(2130903119, paramViewGroup);
    this.mTopBar.setTitle(2131165354);
    this.mTopBar.setLeftButton(2130837526, this);
    initView();
    initBaseData();
    getIntentData();
  }
  
  public void onBlueToothPrintStatusChange(BlueToothPrintStatus paramBlueToothPrintStatus)
  {
    switch (paramBlueToothPrintStatus)
    {
    case SOCKET_CONNECT_SUCCESS: 
    default: 
      return;
    case SOCKET_CONNECT_FAIL: 
      this.mHandler.obtainMessage(4).sendToTarget();
      return;
    case SOCKET_CREATE_FAIL: 
      this.mHandler.obtainMessage(6).sendToTarget();
      return;
    case STREAM_OPEN_FAIL: 
      this.mHandler.obtainMessage(7).sendToTarget();
      return;
    case STREAM_OPEN_SUCCESS: 
      this.mHandler.obtainMessage(16).sendToTarget();
      return;
    case STREAM_WRITE_FAIL: 
      this.mHandler.obtainMessage(8).sendToTarget();
      return;
    }
    this.mHandler.obtainMessage(9).sendToTarget();
  }
  
  public void onBluetoothStateClosed()
  {
    this.btn_bluetooth_action.setText(2131165648);
  }
  
  public void onBluetoothStateOpened()
  {
    this.btn_bluetooth_action.setText(2131165647);
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default: 
      return;
    case 2131362339: 
      if (PrintManager.getBlueToothState())
      {
        PrintManager.searchDevices();
        return;
      }
      Toast.makeText(this, 2131165242, 1).show();
      return;
    case 2131362346: 
      if (this.currentDevice == null)
      {
        ToastUtil.toastLong(this.mContext, 2131165236);
        return;
      }
      PrintManager.getInstance().setCurrentRemoteDevice(this.currentDevice);
      new Thread(new Runnable()
      {
        public void run()
        {
          OutputStream localOutputStream = PrintManager.getOutputStreamOutUIThread(BluePrintActivity.this.currentDevice);
          if ((localOutputStream != null) && (BluePrintActivity.this.receipt != null))
          {
            int i = UserManager.getInstance().getSetting().getPrintModelType();
            BluePrintActivity.this.receipt.printOutUIThread(localOutputStream, i);
          }
          PrintManager.closeBluetoothPrintStream();
          BluePrintActivity.this.mHandler.obtainMessage(9).sendToTarget();
        }
      }).start();
      return;
    }
    PrintManager.openBlueTooth(this);
  }
  
  protected void onDestroy()
  {
    Log.i("BluePrintActivity", "onDestroy");
    PrintManager.unregisterReceiver();
    super.onDestroy();
  }
  
  public void onSearchFinished(ArrayList<BluetoothDevice> paramArrayList1, ArrayList<BluetoothDevice> paramArrayList2)
  {
    dismissWaitDialog();
    this.bandDevices = paramArrayList1;
    this.unBandDevices = paramArrayList2;
    Log.i("BLUETOOTH_DISCOVERY_FINISHED", this.bandDevices.toString());
    fillAdapter();
    this.btn_device_search.setEnabled(true);
  }
  
  public void onSearchStarted()
  {
    showWaitDialog("开始搜索蓝牙设备...");
    this.btn_device_search.setEnabled(false);
  }
  
  public void onTopBarLeftClick()
  {
    finish();
  }
}