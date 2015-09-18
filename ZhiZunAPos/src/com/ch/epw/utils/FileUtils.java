package com.ch.epw.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

/**
 * 文件操作工具包 
 * ===================================================
 */
public class FileUtils {
	private static final String TAG = FileUtils.class.getSimpleName();
	private Context context;
	// 保存路径设置
	public static final String FILE_SDCARD = Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ "epeiwang_upload"
			+ File.separator;

	public FileUtils(Context context) {
		this.context = context;
	}

	/**
	 * 写文本文件 在Android系统中，文件保存在 /data/data/PACKAGE_NAME/files 目录下
	 * 
	 * @param context
	 * @param msg
	 */
	public static void write(Context context, String fileName, String content) {
		if (content == null)
			content = "";

		try {
			FileOutputStream fos = context.openFileOutput(fileName,
					Context.MODE_PRIVATE);
			fos.write(content.getBytes());

			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取文本文件
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String read(Context context, String fileName) {
		try {
			FileInputStream in = context.openFileInput(fileName);
			return readInStream(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String readInStream(InputStream inStream) {
		try {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[512];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, length);
			}

			outStream.close();
			inStream.close();
			return outStream.toString();
		} catch (IOException e) {
			Log.i("FileTest", e.getMessage());
		}
		return null;
	}

	public static File createFile(String folderPath, String fileName) {
		File destDir = new File(folderPath);
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		return new File(folderPath, fileName + fileName);
	}

	/**
	 * 向手机写图片
	 * 
	 * @param buffer
	 * @param folder
	 * @param fileName
	 * @return
	 */
	public static boolean writeFile(byte[] buffer, String folder,
			String fileName) {
		boolean writeSucc = false;

		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);

		String folderPath = "";
		if (sdCardExist) {
			folderPath = Environment.getExternalStorageDirectory()
					+ File.separator + folder + File.separator;
		} else {
			writeSucc = false;
		}

		File fileDir = new File(folderPath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}

		File file = new File(folderPath + fileName);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(buffer);
			writeSucc = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return writeSucc;
	}

	/**
	 * 根据文件绝对路径获取文件名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath) {
		if (StringUtils.isEmpty(filePath))
			return "";
		return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
	}

	/**
	 * 根据文件的绝对路径获取文件名但不包含扩展名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileNameNoFormat(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return "";
		}
		int point = filePath.lastIndexOf('.');
		return filePath.substring(filePath.lastIndexOf(File.separator) + 1,
				point);
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileFormat(String fileName) {
		if (StringUtils.isEmpty(fileName))
			return "";

		int point = fileName.lastIndexOf('.');
		return fileName.substring(point + 1);
	}

	/**
	 * 获取文件大小
	 * 
	 * @param filePath
	 * @return
	 */
	public static long getFileSize(String filePath) {
		long size = 0;

		File file = new File(filePath);
		if (file != null && file.exists()) {
			size = file.length();
		}
		return size;
	}

	/**
	 * 获取文件大小
	 * 
	 * @param size
	 *            字节
	 * @return
	 */
	public static String getFileSize(long size) {
		if (size <= 0)
			return "0";
		java.text.DecimalFormat df = new java.text.DecimalFormat("##.##");
		float temp = (float) size / 1024;
		if (temp >= 1024) {
			return df.format(temp / 1024) + "M";
		} else {
			return df.format(temp) + "K";
		}
	}

	/**
	 * 转换文件大小
	 * 
	 * @param fileS
	 * @return B/KB/MB/GB
	 */
	public static String formatFileSize(long fileS) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 获取目录文件大小
	 * 
	 * @param dir
	 * @return
	 */
	public static long getDirSize(File dir) {
		if (dir == null) {
			return 0;
		}
		if (!dir.isDirectory()) {
			return 0;
		}
		long dirSize = 0;
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				dirSize += file.length();
			} else if (file.isDirectory()) {
				dirSize += file.length();
				dirSize += getDirSize(file); // 递归调用继续统计
			}
		}
		return dirSize;
	}

	/**
	 * 获取目录文件个数
	 * 
	 * @param f
	 * @return
	 */
	public long getFileList(File dir) {
		long count = 0;
		File[] files = dir.listFiles();
		count = files.length;
		for (File file : files) {
			if (file.isDirectory()) {
				count = count + getFileList(file);// 递归
				count--;
			}
		}
		return count;
	}

	public static byte[] toBytes(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int ch;
		while ((ch = in.read()) != -1) {
			out.write(ch);
		}
		byte buffer[] = out.toByteArray();
		out.close();
		return buffer;
	}

	/**
	 * 检查文件是否存在
	 * 
	 * @param name
	 * @return
	 */
	public static boolean checkFileExists(String name) {
		boolean status;
		if (!name.equals("")) {
			File path = Environment.getExternalStorageDirectory();
			File newPath = new File(path.toString() + name);
			status = newPath.exists();
		} else {
			status = false;
		}
		return status;
	}

	/**
	 * 检查路径是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean checkFilePathExists(String path) {
		return new File(path).exists();
	}

	/**
	 * 计算SD卡的剩余空间
	 * 
	 * @return 返回-1，说明没有安装sd卡
	 */
	public static long getFreeDiskSpace() {
		String status = Environment.getExternalStorageState();
		long freeSpace = 0;
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			try {
				File path = Environment.getExternalStorageDirectory();
				StatFs stat = new StatFs(path.getPath());
				long blockSize = stat.getBlockSize();
				long availableBlocks = stat.getAvailableBlocks();
				freeSpace = availableBlocks * blockSize / 1024;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return -1;
		}
		return (freeSpace);
	}

	/**
	 * 新建目录
	 * 
	 * @param directoryName
	 * @return
	 */
	public static boolean createDirectory(String directoryName) {
		boolean status;
		if (!directoryName.equals("")) {
			File path = Environment.getExternalStorageDirectory();
			File newPath = new File(path.toString() + directoryName);
			status = newPath.mkdir();
			status = true;
		} else
			status = false;
		return status;
	}

	/**
	 * 检查是否安装SD卡
	 * 
	 * @return
	 */
	public static boolean checkSaveLocationExists() {
		String sDCardStatus = Environment.getExternalStorageState();
		boolean status;
		if (sDCardStatus.equals(Environment.MEDIA_MOUNTED)) {
			status = true;
		} else
			status = false;
		return status;
	}

	/**
	 * 检查是否安装外置的SD卡
	 * 
	 * @return
	 */
	public static boolean checkExternalSDExists() {

		Map<String, String> evn = System.getenv();
		return evn.containsKey("SECONDARY_STORAGE");
	}

	/**
	 * 删除目录(包括：目录里的所有文件)
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean deleteDirectory(String fileName) {
		boolean status;
		SecurityManager checker = new SecurityManager();

		if (!fileName.equals("")) {

			File path = Environment.getExternalStorageDirectory();
			File newPath = new File(path.toString() + fileName);
			checker.checkDelete(newPath.toString());
			if (newPath.isDirectory()) {
				String[] listfile = newPath.list();
				// delete all files within the specified directory and then
				// delete the directory
				try {
					for (int i = 0; i < listfile.length; i++) {
						File deletedFile = new File(newPath.toString() + "/"
								+ listfile[i].toString());
						deletedFile.delete();
					}
					newPath.delete();
					Log.i("DirectoryManager deleteDirectory", fileName);
					status = true;
				} catch (Exception e) {
					e.printStackTrace();
					status = false;
				}

			} else
				status = false;
		} else
			status = false;
		return status;
	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean deleteFile(String fileName) {
		boolean status;
		SecurityManager checker = new SecurityManager();

		if (!fileName.equals("")) {

			File path = Environment.getExternalStorageDirectory();
			File newPath = new File(path.toString() + fileName);
			checker.checkDelete(newPath.toString());
			if (newPath.isFile()) {
				try {
					Log.i("DirectoryManager deleteFile", fileName);
					newPath.delete();
					status = true;
				} catch (SecurityException se) {
					se.printStackTrace();
					status = false;
				}
			} else
				status = false;
		} else
			status = false;
		return status;
	}

	/**
	 * 删除空目录
	 * 
	 * 返回 0代表成功 ,1 代表没有删除权限, 2代表不是空目录,3 代表未知错误
	 * 
	 * @return
	 */
	public static int deleteBlankPath(String path) {
		File f = new File(path);
		if (!f.canWrite()) {
			return 1;
		}
		if (f.list() != null && f.list().length > 0) {
			return 2;
		}
		if (f.delete()) {
			return 0;
		}
		return 3;
	}

	/**
	 * 重命名
	 * 
	 * @param oldName
	 * @param newName
	 * @return
	 */
	public static boolean reNamePath(String oldName, String newName) {
		File f = new File(oldName);
		return f.renameTo(new File(newName));
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 */
	public static boolean deleteFileWithPath(String filePath) {
		SecurityManager checker = new SecurityManager();
		File f = new File(filePath);
		checker.checkDelete(filePath);
		if (f.isFile()) {
			Log.i("DirectoryManager deleteFile", filePath);
			f.delete();
			return true;
		}
		return false;
	}

	/**
	 * 清空一个文件夹
	 * 
	 * @param files
	 */
	public static void clearFileWithPath(String filePath) {
		List<File> files = FileUtils.listPathFiles(filePath);
		if (files == null || files.isEmpty()) {
			return;
		}
		for (File f : files) {
			if (f.isDirectory()) {
				clearFileWithPath(f.getAbsolutePath());
			} else {
				f.delete();
			}
		}
	}

	/**
	 * 获取SD卡的根目录
	 * 
	 * @return
	 */
	public static String getSDRoot() {

		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	/**
	 * 获取手机外置SD卡的根目录
	 * 
	 * @return
	 */
	public static String getExternalSDRoot() {

		Map<String, String> evn = System.getenv();

		return evn.get("SECONDARY_STORAGE");
	}

	/**
	 * 列出root目录下所有子目录
	 * 
	 * @param path
	 * @return 绝对路径
	 */
	public static List<String> listPath(String root) {
		List<String> allDir = new ArrayList<String>();
		SecurityManager checker = new SecurityManager();
		File path = new File(root);
		checker.checkRead(root);
		// 过滤掉以.开始的文件夹
		if (path.isDirectory()) {
			for (File f : path.listFiles()) {
				if (f.isDirectory() && !f.getName().startsWith(".")) {
					allDir.add(f.getAbsolutePath());
				}
			}
		}
		return allDir;
	}

	/**
	 * 获取一个文件夹下的所有文件
	 * 
	 * @param root
	 * @return
	 */
	public static List<File> listPathFiles(String root) {
		List<File> allDir = new ArrayList<File>();
		SecurityManager checker = new SecurityManager();
		File path = new File(root);
		if (!path.exists()) {
			return null;
		}
		checker.checkRead(root);
		File[] files = path.listFiles();
		for (File f : files) {
			if (f.isFile())
				allDir.add(f);
			else
				listPath(f.getAbsolutePath());
		}
		return allDir;
	}

	public enum PathStatus {
		SUCCESS, EXITS, ERROR
	}

	/**
	 * 创建目录
	 * 
	 * @param path
	 */
	public static PathStatus createPath(String newPath) {
		File path = new File(newPath);
		if (path.exists()) {
			return PathStatus.EXITS;
		}
		if (path.mkdir()) {
			return PathStatus.SUCCESS;
		} else {
			return PathStatus.ERROR;
		}
	}

	/**
	 * 截取路径名
	 * 
	 * @return
	 */
	public static String getPathName(String absolutePath) {
		int start = absolutePath.lastIndexOf(File.separator) + 1;
		int end = absolutePath.length();
		return absolutePath.substring(start, end);
	}

	/**
	 * 获取应用程序缓存文件夹下的指定目录
	 * 
	 * @param context
	 * @param dir
	 * @return
	 */
	public static String getAppCache(Context context, String dir) {
		String savePath = context.getCacheDir().getAbsolutePath() + "/" + dir
				+ "/";
		File savedir = new File(savePath);
		if (!savedir.exists()) {
			savedir.mkdirs();
		}
		savedir = null;
		return savePath;
	}

	/**
	 * 在SD卡上创建文件
	 * 
	 * @param path
	 * @param fileName
	 * @return
	 */

	public static File createRealFile(String path, String fileName) {
		File file = null;
		String Sdcard_Path = Environment.getExternalStorageDirectory() + "/";
		file = new File(Sdcard_Path + path + "/" + fileName); // 根目录+路径+文件名

		// 判断目录是否存在
		if (!isValid(path)) {
			CreateDir(path); // 创建目录
		}
		// 判断文件是否存在
		if (isValid(path + "/" + fileName)) {
			// 存在就删除文件
			deleteFile("/" + path + "/" + fileName);
		}
		try {
			file.createNewFile();// 创建文件
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * 王冲冲 判断文件或目录是否存在
	 * 
	 * @param PathOrName
	 * @return 存在返回TRUE 不存在则返回FALSE
	 */
	public static boolean isValid(String PathOrName) {
		String Sdcard_Path = Environment.getExternalStorageDirectory() + "/";
		File file = new File(Sdcard_Path + PathOrName);
		if (file.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * 王冲冲 创建指定的文件
	 * 
	 * @param path
	 */
	public static void CreateDir(String path) {
		String Sdcard_Path = Environment.getExternalStorageDirectory() + "/";
		File file = new File(Sdcard_Path + path);
		file.mkdirs();
	}

	/**
	 * Description: 向FTP服务器上传文件
	 * 
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param path
	 *            FTP服务器保存目录，是linux下的目录形式,如/photo/
	 * @param filename
	 *            上传到FTP服务器上的文件名,是自己定义的名字，
	 * @return 成功返回true，否则返回false
	 */
	public static boolean uploadFile(String url, int port, String username,
			String password, String path, String filename, InputStream input) {

		boolean success = false;
		FTPClient ftp = new FTPClient();

		try {
			int reply;
			ftp.connect(url, port);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			Boolean lg = ftp.login(username, password);// 登录
			System.out.println("lg=" + lg);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			// // 创建上传目录
			// Boolean mb = ftp.makeDirectory(path);
			// System.out.println("path=" + path);
			// System.out.println("mb=" + mb);
			// // 设置上传目录
			// Boolean mc = ftp.changeWorkingDirectory(path);
			// System.out.println("mc=" + mc);
			// 设置PassiveMode传输
			ftp.enterLocalPassiveMode();
			// 设置以二进制流的方式传输
			ftp.setFileType(FTP.BINARY_FILE_TYPE);

			if (path.contains("/")) {
				// 创建服务器远程目录结构，创建失败直接返回
				if (!CreateDirecroty(path, ftp)) {
					return success;
				}
			}

			Boolean st = ftp.storeFile(filename, input);

			System.out.println("filename=" + filename);
			System.out.println("st=" + st);
			if (st) {
				input.close();
				ftp.logout();
			}

			success = true;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("FTP客户端出错！", e);
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					ioe.printStackTrace();
					throw new RuntimeException("关闭FTP连接发生异常！", ioe);
				}
			}
		}
		return success;
	}

	/**
	 *     * 递归创建远程服务器目录     *     * @param remote     *            远程服务器文件绝对路径
	 *     *     * @return 目录创建是否成功     * @throws IOException    
	 */
	public static boolean CreateDirecroty(String remote, FTPClient ftp)
			throws IOException {
		boolean success = true;
		String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
		// 如果远程目录不存在，则递归创建远程服务器目录
		if (!directory.equalsIgnoreCase("/")
				&& !ftp.changeWorkingDirectory(new String(directory))) {
			int start = 0;
			int end = 0;
			if (directory.startsWith("/")) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			while (true) {
				String subDirectory = new String(remote.substring(start, end));
				if (!ftp.changeWorkingDirectory(subDirectory)) {
					if (ftp.makeDirectory(subDirectory)) {
						ftp.changeWorkingDirectory(subDirectory);
					} else {
						System.out.println("创建目录失败");
						success = false;
						return success;
					}
				}
				start = end + 1;
				end = directory.indexOf("/", start);
				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		}
		return success;
	}

	/* Checks if external storage is available for read and write */
	public static boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	/**
	 * 保存二进制流到指定路径
	 * 
	 * @param instream
	 * @param filepath
	 */
	public void saveFile(InputStream instream, String filepath) {
		if (!isExternalStorageWritable()) {
			Log.i(TAG, "SD卡不可用，保存失败");
			return;
		}

		File file = new File(filepath);

		try {
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int cnt = 0;

			while ((cnt = instream.read(buffer)) != -1) {
				fos.write(buffer, 0, cnt);
			}

			instream.close();
			fos.close();

		} catch (FileNotFoundException e) {
			Log.i(TAG, e.getMessage());
		} catch (IOException e) {
			Log.i(TAG, e.getMessage());
		}
	}

	/**
	 * Copy file
	 * 
	 * @param from
	 *            origin file path
	 * @param to
	 *            target file path
	 */
	public void copyFile(String from, String to) {
		if (!isExternalStorageWritable()) {
			Log.i(TAG, "SD卡不可用，保存失败");
			return;
		}

		File fileFrom = new File(from);
		File fileTo = new File(to);

		try {

			FileInputStream fis = new FileInputStream(fileFrom);
			FileOutputStream fos = new FileOutputStream(fileTo);
			byte[] buffer = new byte[1024];
			int cnt = 0;

			while ((cnt = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, cnt);
			}

			fis.close();
			fos.close();

		} catch (FileNotFoundException e) {
			Log.i(TAG, e.getMessage());
		} catch (IOException e) {
			Log.i(TAG, e.getMessage());
		}
	}

	/**
	 * 保存 JSON 字符串到指定文件
	 * 
	 * @param json
	 * @param filepath
	 */
	public boolean saveJson(String json, String filepath) {
		if (!isExternalStorageWritable()) {
			Log.i(TAG, "SD卡不可用，保存失败");
			return false;
		}

		File file = new File(filepath);

		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(json.getBytes());
			fos.close();

		} catch (FileNotFoundException e) {
			Log.i(TAG, e.getMessage());
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	/**
	 * 删除指定的 JSON 文件
	 * 
	 * @param filepath
	 * @return
	 */
	public boolean deleteJson(String filepath) {
		if (!isExternalStorageWritable()) {
			Log.i(TAG, "SD卡不可用，保存失败");
			return false;
		}

		File file = new File(filepath);

		if (file.exists()) {
			file.delete();
		}

		return false;
	}

	/**
	 * 从指定文件读取 JSON 字符串
	 * 
	 * @param filepath
	 * @return
	 */
	public String readJson(String filepath) {
		if (!isExternalStorageWritable()) {
			Log.i(TAG, "SD卡不可用，保存失败");
			return null;
		}

		File file = new File(filepath);
		StringBuilder sb = new StringBuilder();

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			String line = null;

			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			reader.close();

		} catch (FileNotFoundException e) {
			Log.i(TAG, e.getMessage());
		} catch (IOException e) {
			Log.i(TAG, e.getMessage());
		}

		return sb.toString();
	}

	/**
	 * 保存图片到制定路径
	 * 
	 * @param filepath
	 * @param bitmap
	 */
	public static void saveBitmap(String filepath, Bitmap bitmap) {
		if (!isExternalStorageWritable()) {
			Log.i(TAG, "SD卡不可用，保存失败");
			return;
		}

		if (bitmap == null) {
			return;
		}

		try {
			File file = new File(filepath);
			FileOutputStream outputstream = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputstream);
			outputstream.flush();
			outputstream.close();
		} catch (FileNotFoundException e) {
			Log.i(TAG, e.getMessage());
		} catch (IOException e) {
			Log.i(TAG, e.getMessage());
		}
	}

	/**
	 * 保存图片到制定路径
	 * 
	 * @param filepath
	 * @param bitmap
	 * @param quality
	 *            压缩率 如果是70，代表压缩30%
	 */
	public static String saveBitmap(String filepath, Bitmap bitmap, int quality) {
		if (!isExternalStorageWritable()) {
			Log.i(TAG, "SD卡不可用，保存失败");
			return null;
		}

		if (bitmap == null) {
			return null;
		}

		try {
			File file = new File(filepath);
			FileOutputStream outputstream = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputstream);
			outputstream.flush();
			outputstream.close();
		} catch (FileNotFoundException e) {
			Log.i(TAG, e.getMessage());
		} catch (IOException e) {
			Log.i(TAG, e.getMessage());
		}
		return filepath;
	}

	/**
	 * 保存图片
	 * 
	 * @param bm
	 * @param picName
	 */
	public static void saveBitmap(Bitmap bm, String picName) {
		// Log.e("", "保存图片");
		try {
			if (!isFileExist("")) {
				File tempf = createSDDir("");
			}
			File f = new File(FILE_SDCARD, picName + ".JPEG");
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
			// Log.e("", "已经保存");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(FILE_SDCARD + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	public static boolean isFileExist(String fileName) {
		File file = new File(FILE_SDCARD + fileName);
		file.isFile();
		return file.exists();
	}

	public static void deleteDir() {
		File dir = new File(FILE_SDCARD);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;

		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); // 删除所有文件
			else if (file.isDirectory())
				deleteDir(); // 递规的方式删除文件夹
		}
		dir.delete();// 删除目录本身
	}

	public static void delFile(String fileName) {
		File file = new File(FILE_SDCARD + fileName);
		if (file.isFile()) {
			file.delete();
		}
		file.exists();
	}

	/**
	 * 删除 Files 目录下所有的图片
	 * 
	 * @return
	 */
	public boolean cleanCache() {
		if (!isExternalStorageWritable()) {
			Log.i(TAG, "SD卡不可用，保存失败");
			return false;
		}

		File dir = context.getExternalFilesDir(null);

		if (dir != null) {
			for (File file : dir.listFiles()) {
				file.delete();
			}
		}

		return true;
	}

	/**
	 * 计算 Files 目录下图片的大小
	 * 
	 * @return
	 */
	public String getCacheSize() {
		if (!isExternalStorageWritable()) {
			Log.i(TAG, "SD卡不可用，保存失败");
			return null;
		}

		long sum = 0;
		File dir = context.getExternalFilesDir(null);

		if (dir != null) {
			for (File file : dir.listFiles()) {
				sum += file.length();
			}
		}

		if (sum < 1024) {
			return sum + "字节";
		} else if (sum < 1024 * 1024) {
			return (sum / 1024) + "K";
		} else {
			return (sum / (1024 * 1024)) + "M";
		}
	}

	/**
	 * 返回当前应用 SD 卡的绝对路径 like
	 * /storage/sdcard0/Android/data/com.example.test/files
	 */
	public String getAbsolutePath() {
		File root = context.getExternalFilesDir(null);

		if (root != null) {
			return root.getAbsolutePath();
		}

		return null;
	}

	/**
	 * 返回当前应用的 SD卡缓存文件夹绝对路径 like
	 * /storage/sdcard0/Android/data/com.example.test/cache
	 */
	public String getCachePath() {
		File root = context.getExternalCacheDir();

		if (root != null) {
			return root.getAbsolutePath();
		}

		return null;
	}

	public boolean isBitmapExists(String filename) {
		File dir = context.getExternalFilesDir(null);
		File file = new File(dir, filename);

		return file.exists();
	}
}