package com.zhizun.pos.view.photograph;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bimp {
	private int max = 0;
	private  List<Bitmap> bmp = new ArrayList<Bitmap>();
	private List<String> drr = new ArrayList<String>();
	private List<String> errorFiles = new ArrayList<String>();
	
	public static Bitmap revitionImageSize(String path) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				new File(path)));
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(in, null, options);
		in.close();
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			if ((options.outWidth >> i <= 1000)
					&& (options.outHeight >> i <= 1000)) {
				in = new BufferedInputStream(
						new FileInputStream(new File(path)));
				options.inSampleSize = (int) Math.pow(2.0D, i);
				options.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeStream(in, null, options);
				break;
			}
			i += 1;
		}
		return bitmap;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public List<Bitmap> getBmp() {
		return bmp;
	}

	public void setBmp(List<Bitmap> bmp) {
		this.bmp = bmp;
	}

	public List<String> getDrr() {
		return drr;
	}

	public void setDrr(List<String> drr) {
		this.drr = drr;
	}

	public List<String> getErrorFiles() {
		return errorFiles;
	}

	public void setErrorFiles(List<String> errorFiles) {
		this.errorFiles = errorFiles;
	}
	
}
