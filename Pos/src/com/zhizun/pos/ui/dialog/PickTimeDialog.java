package com.zhizun.pos.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import com.zhizun.pos.ui.widget.NumberPicker;
import com.zhizun.pos.ui.widget.NumberPicker.OnValueChangeListener;
import com.zhizun.pos.util.ResUtil;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PickTimeDialog
  extends DialogFragment
  implements NumberPicker.OnValueChangeListener, View.OnClickListener
{
  private Calendar calendar = Calendar.getInstance();
  public String datePicker;
  private NumberPicker day_nuPicker;
  private onDatePickListener mOnTimePickListener;
  private NumberPicker month_nuPicker;
  private NumberPicker year_nuPicker;
  
  private String getCurrentDate()
  {
    try
    {
      String str1 = this.month_nuPicker.getValue();
      String str2 = this.day_nuPicker.getValue();
      if (this.month_nuPicker.getValue() < 10) {
        str1 = "0" + this.month_nuPicker.getValue();
      }
      if (this.day_nuPicker.getValue() < 10) {
        str2 = "0" + this.day_nuPicker.getValue();
      }
      str1 = this.year_nuPicker.getValue() + "-" + str1 + "-" + str2;
      return str1;
    }
    finally {}
  }
  
  private int getMonthOfLastDay(String paramString)
  {
    try
    {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
      Calendar localCalendar = Calendar.getInstance();
      localCalendar.setTime(localSimpleDateFormat.parse(paramString));
      localCalendar.add(5, -1);
      int i = Integer.parseInt(new SimpleDateFormat("dd", Locale.getDefault()).format(localCalendar.getTime()));
      return i;
    }
    catch (Exception paramString)
    {
      throw new IndexOutOfBoundsException();
    }
    finally {}
  }
  
  public void init()
  {
    this.month_nuPicker.setOnValueChangedListener(this);
    this.year_nuPicker.setMaxValue(2100);
    this.year_nuPicker.setMinValue(1900);
    this.year_nuPicker.setFocusable(true);
    this.year_nuPicker.setFocusableInTouchMode(true);
    this.year_nuPicker.setLabel(ResUtil.getString(2131165334));
    this.year_nuPicker.setValue(this.calendar.get(1));
    this.month_nuPicker.setMaxValue(12);
    this.month_nuPicker.setMinValue(1);
    this.month_nuPicker.setFocusable(true);
    this.month_nuPicker.setFocusableInTouchMode(true);
    this.month_nuPicker.setLabel(ResUtil.getString(2131165335));
    this.month_nuPicker.setValue(this.calendar.get(2) + 1);
    this.day_nuPicker.setMaxValue(getMonthOfLastDay(this.year_nuPicker.getValue() + "-" + (this.month_nuPicker.getValue() + 1)));
    this.day_nuPicker.setMinValue(1);
    this.day_nuPicker.setFocusable(true);
    this.day_nuPicker.setFocusableInTouchMode(true);
    this.day_nuPicker.setLabel(ResUtil.getString(2131165336));
    this.day_nuPicker.setValue(this.calendar.get(5));
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default: 
      return;
    case 2131362440: 
      if (this.mOnTimePickListener != null) {
        this.mOnTimePickListener.onPickFinish(getCurrentDate());
      }
      dismiss();
      return;
    }
    dismiss();
  }
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    setStyle(0, 2131230722);
    return super.onCreateDialog(paramBundle);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = getActivity().getLayoutInflater().inflate(2130903147, paramViewGroup);
    this.day_nuPicker = ((NumberPicker)paramLayoutInflater.findViewById(2131362437));
    this.month_nuPicker = ((NumberPicker)paramLayoutInflater.findViewById(2131362436));
    this.year_nuPicker = ((NumberPicker)paramLayoutInflater.findViewById(2131362435));
    paramLayoutInflater.findViewById(2131362440).setOnClickListener(this);
    paramLayoutInflater.findViewById(2131362439).setOnClickListener(this);
    init();
    return paramLayoutInflater;
  }
  
  public void onValueChange(NumberPicker paramNumberPicker, int paramInt1, int paramInt2, EditText paramEditText)
  {
    this.day_nuPicker.setMaxValue(getMonthOfLastDay(this.year_nuPicker.getValue() + "-" + (this.month_nuPicker.getValue() + 1)));
  }
  
  public void setOnTimePickListener(onDatePickListener paramonDatePickListener)
  {
    this.mOnTimePickListener = paramonDatePickListener;
  }
  
  public static abstract interface onDatePickListener
  {
    public abstract void onPickFinish(String paramString);
  }
}