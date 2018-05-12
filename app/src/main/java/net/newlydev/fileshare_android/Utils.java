package net.newlydev.fileshare_android;

import android.app.*;
import android.content.*;
import android.net.wifi.*;
import java.io.*;
import java.net.*;
import java.security.*;
import java.util.*;
import android.net.*;

public class Utils
{
	
	private static final String[][] MIME_StrTable = {
		//{后缀名，MIME类型}
		//Video
		{".3gp", "video/3gpp"},
		{".asf", "video/x-ms-asf"},
		{".avi", "video/x-msvideo"},
		{".m4u", "video/vnd.mpegurl"},
		{".m4v", "video/x-m4v"},
		{".mov", "video/quicktime"},
		//mp4 统一使用mp4
		{".mp4", "video/mp4"},
		{".mpg4", "video/mp4"},

		{".mpe", "video/x-mpeg"},
		//mpeg 使用相应的默认程序打开，但不添加文件拓展名
		{"", "video/mpg"},
		{".mpeg", "video/mpg"},
		{".mpg", "video/mpg"},

		//audio
		{".m3u", "audio/x-mpegurl"},
		//mp4a-latm 使用相应的默认程序打开，但不添加文件拓展名
		{"", "audio/mp4a-latm"},
		{".m4a", "audio/mp4a-latm"},
		{".m4b", "audio/mp4a-latm"},
		{".m4p", "audio/mp4a-latm"},

		//x-mpeg
		{".mp2", "x-mpeg"},
		{".mp3", "audio/x-mpeg"},

		{".mpga", "audio/mpeg"},
		{".ogg", "audio/ogg"},
		{".rmvb", "audio/x-pn-realaudio"},
		{".wav", "audio/x-wav"},
		{".wma", "audio/x-ms-wma"},
		{".wmv", "audio/x-ms-wmv"},

		//text
		//plain 使用相应的默认程序打开，但不添加文件拓展名
		{"", "text/plain"},
		{".c", "text/plain"},
		{".java", "text/plain"},
		{".conf", "text/plain"},
		{".cpp", "text/plain"},
		{".h", "text/plain"},
		{".prop", "text/plain"},
		{".rc", "text/plain"},
		{".sh", "text/plain"},
		{".log", "text/plain"},
		{".txt", "text/plain"},
		{".xml", "text/plain"},

		//统一使用html
		{".html", "text/html"},
		{".htm", "text/html"},

		{".css", "text/css"},

		//image
		//jpeg统一使用jpg
		{".jpg", "image/jpeg"},
		{".jpeg", "image/jpeg"},


		{".bmp", "image/bmp"},
		{".gif", "image/gif"},
		{".png", "image/png"},

		//application
		{"", "application/octet-stream"},
		{".bin", "application/octet-stream"},
		{".class", "application/octet-stream"},
		{".exe", "application/octet-stream"},
		{"class", "application/octet-stream"},

		{".apk", "application/vnd.android.package-archive"},
		{".doc", "application/msword"},
		{".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
		{".xls", "application/vnd.ms-excel"},
		{".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},

		{".gtar", "application/x-gtar"},
		{".gz", "application/x-gzip"},
		{".jar", "application/java-archive"},
		{".js", "application/x-javascript"},
		{".mpc", "application/vnd.mpohun.certificate"},
		{".msg", "application/vnd.ms-outlook"},
		{".pdf", "application/pdf"},
		//vnd.ms-powerpoint 使用相应的默认程序打开，但不添加文件拓展名
		{"", "application/vnd.ms-powerpoint"},
		{".pps", "application/vnd.ms-powerpoint"},
		{".ppt", "application/vnd.ms-powerpoint"},

		{".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
		{".rtf", "application/rtf"},
		{".tar", "application/x-tar"},
		{".tgz", "application/x-compressed"},
		{".wps", "application/vnd.ms-works"},
		{".z", "application/x-compress"},
		{".zip", "application/x-zip-compressed"},
//      {"", "*/*"}
	};
	static HashMap<String,String> mimeMapKeyIsContentType = null;
	static HashMap<String,String> mimeMapKeyIsExpands = null;
	public static HashMap<String,String> CreateMIMEMapKeyIsContentType(){

		HashMap<String,String> mimeHashMap = new HashMap<String,String>();

		for(int i = 0; i < MIME_StrTable.length; i ++){
			if(MIME_StrTable[i][1].length() > 0 && (!mimeHashMap.containsKey(MIME_StrTable[i][1]))){
				mimeHashMap.put(MIME_StrTable[i][1],MIME_StrTable[i][0]);
			}
		}
		return mimeHashMap;
	}
	
	public static String getLocalIpAddress() {
		try {
			Enumeration<NetworkInterface> en = NetworkInterface
				.getNetworkInterfaces();
			while (en.hasMoreElements()) {
				NetworkInterface ni = en.nextElement();
				Enumeration<InetAddress> enIp= ni.getInetAddresses();
				while (enIp.hasMoreElements()) {
					InetAddress inet = enIp.nextElement();
					if (!inet.isLoopbackAddress()
						&& (inet instanceof Inet4Address)) {
						return inet.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "0";
	}
	public static byte[] byteMerger(byte[] bt1, byte[] bt2){  
        byte[] bt3 = new byte[bt1.length+bt2.length];  
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);  
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);  
        return bt3;  
    }
	
	public static String getExtensionByCutStr(String fileName){
		int lastIndexOfDot = fileName.lastIndexOf(".");
		if(lastIndexOfDot < 0){
			return "";//没有拓展名
		}
		String extension = fileName.substring(lastIndexOfDot+1);
		return extension;
	}
//创建以拓展名为key值的HashMap
	public static HashMap<String,String> CreateMIMEMapKeyIsExpands(){

		HashMap<String,String> mimeHashMap = new HashMap<String,String>();

		for(int i = 0; i < MIME_StrTable.length; i ++){
			if(MIME_StrTable[i][0].length() > 0 && (!mimeHashMap.containsKey(MIME_StrTable[i][0]))){
				mimeHashMap.put(MIME_StrTable[i][0],MIME_StrTable[i][1]);
			}
		}
		return mimeHashMap;
	}
//获取MIME列表的HashMap，设置Content-type为key值
	public static HashMap<String,String> getMIMEMapKeyIsContentType(){
		//为了防止重复创建消耗时间和消耗资源，将mimeMapKeyIsContentType设置全局变量并赋初值null
		if(mimeMapKeyIsContentType == null){
			mimeMapKeyIsContentType = CreateMIMEMapKeyIsContentType();
		}
		return mimeMapKeyIsContentType;
	}

//获取MIME列表的HashMap,设置拓展名为文件拓展名（含有"."）
	public static HashMap<String,String> getMIMEMapKeyIsExpands(){
		//为了防止重复创建消耗时间和消耗资源，将mimeMapKeyIsExpands设置全局变量并赋初值null
		if(mimeMapKeyIsExpands == null){
			mimeMapKeyIsExpands = CreateMIMEMapKeyIsExpands();
		}
		return mimeMapKeyIsExpands;
	}
	public static String getContentTypeByExpansion(String expansionName){
		String expansion = expansionName;
		if(!expansion.startsWith(".")){
			expansion = "." + expansion;
		}
		HashMap<String,String> expandMap = getMIMEMapKeyIsExpands();
		String contentType = expandMap.get(expansion);
		return contentType == null?"*/*":contentType; //当找不到的时候就会返回空
	}
	public static boolean isMainServiceRunning(Context context) {
	    ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	    for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if ("net.newlydev.fileshare_android.MainService".equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public static String EncoderByMd5(String buf) {
		try {
			MessageDigest digist = MessageDigest.getInstance("MD5");
			byte[] rs = digist.digest(buf.getBytes("UTF-8"));
			StringBuffer digestHexStr = new StringBuffer();
			for (int i = 0; i < 16; i++) {
				digestHexStr.append(byteHEX(rs[i]));
			}
			return digestHexStr.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String byteHEX(byte ib) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		char[] ob = new char[2];
		ob[0] = Digit[(ib >>> 4) & 0X0F];
		ob[1] = Digit[ib & 0X0F];
		String s = new String(ob);
		return s;
	}
	
	public static String getRandomString(int length){
		//产生随机数
		Random random=new Random();
		StringBuffer sb=new StringBuffer();
		//循环length次
		for(int i=0; i<length; i++){
			//产生0-2个随机数，既与a-z，A-Z，0-9三种可能
			int number=random.nextInt(3);
			long result=0;
			switch(number){
					//如果number产生的是数字0；
				case 0:
					//产生A-Z的ASCII码
					result=Math.round(Math.random()*25+65);
					//将ASCII码转换成字符
					sb.append(String.valueOf((char)result));
					break;
				case 1:
					//产生a-z的ASCII码
					result=Math.round(Math.random()*25+97);
					sb.append(String.valueOf((char)result));
					break;
				case 2:
					//产生0-9的数字
					sb.append(String.valueOf
							  (new Random().nextInt(10)));
					break; 
			}
		}
		return sb.toString();
	}
	public static byte[] readBytes(InputStream in,int length) throws IOException {  
        int r=0;  
        byte[] data=new byte[length];  
        while(r<length){  
            r+=in.read(data,r,length-r);  
        }
        return data;  
    }
	public static ArrayList<mFile> listFiles(String path,boolean useroot) throws IOException
	{
		ArrayList<mFile> files=new ArrayList<mFile>();
		Process p=null;
		if(useroot)
		{
			p=Runtime.getRuntime().exec("su");
		}else{
			p=Runtime.getRuntime().exec("sh");
		}
		BufferedReader is=new BufferedReader(new InputStreamReader(p.getInputStream(),"UTF-8"));
		BufferedWriter os=new BufferedWriter(new OutputStreamWriter(p.getOutputStream(),"UTF-8"));
		String endflag=Utils.getRandomString(32);
		os.write("ls -l -a "+path+"\n");
		os.write("echo "+endflag+"\n");
		os.flush();
		String buf=is.readLine();
		if(buf.equals(endflag))
		{
			return null;
		}else while(true)
		{
			buf=is.readLine();
			if(buf.equals(endflag))
			{
				break;
			}
			if(!(buf.startsWith("-")||buf.startsWith("d")))
			{
				continue;
			}
			mFile file=new mFile(buf,path);
			files.add(file);
			
			//body=body+"\n"+cs.get(7);
		}
		//body="ok "+lines+body;
		os.write("exit");
		os.flush();
		return files;
	}
}
