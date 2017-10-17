package com.webside.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.webside.exception.ServiceException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @title :文件读写工具类  围绕 apache 的 FileUtils丰富这个类，尽量采用apache的工具包，不要重复编写代码
 * @author: zhaiguangtao
 * @date: 2010-9-9
 */
public class FileRWUtil {

	public static Logger logger = Logger.getLogger(FileRWUtil.class);
    /**
     * 读取文件返回一个字符串
     * @param file: 文件
     * @param String: 字符串
     * */
    public static String readFile(File file) {
        StringBuffer sb = new StringBuffer();
        Reader reader = null;
        try {
        	// 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
	            if (((char) tempchar) != '\r') {
	                sb.append((char) tempchar);
	            }
            }
            reader.close();
        } catch (Exception e) {
        	logger.error("FileRWUtil.readFile", e);
        }finally{
            try {
            	reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString().trim();
    }

    /**
     * 将字符串写入文件
     * @param path: 文件路径
     * @param str:字符串
     * */
    public static void writeFile(String path, String str) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            fos.write(str.getBytes());
        } catch (Exception e) {
        	logger.error("FileRWUtil.writeFile", e);
        }finally{
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 将文件内容按行读取到List中
     * @param file: 文件
     * @return list 
     * */
    public static List<String> readFileByLine(File file){
        List<String> sqlDataList = null;
        try {
            // 将文件内容逐行读取到sqlDataList中
           BufferedReader br = new BufferedReader(new FileReader(file));
           String temp = null;
           sqlDataList = new ArrayList<String>();
            while((temp = br.readLine())!=null){
                if(!"".equals(temp.trim())){// 忽略空行
                    sqlDataList.add(temp);
                }
            }
            br.close();
        } catch (Exception e) {
        	logger.error("", e);
        }
        return sqlDataList;
    }
    
    /**
     * @description:把源目录下的所有文件复制到目标目录下
     * @param srcDir 源目录
     * @param dstDir ：目标目录
     */
    public static void copy(String srcDir, String dstDir) {
        if (!StringUtils.isEmpty(srcDir) && !StringUtils.isEmpty(dstDir)) {
            File file = new File(srcDir);
            String name = null;// 待上传文件名
            if (file.isDirectory()) {// 如果为目录，则按目录传
                File[] files = file.listFiles();
                File dstFile = null;
                for (File f : files) {
                    if (f.isFile()) {
                    name = dstDir + "/" + f.getName();
                    dstFile = new File(name);
                    copy(f, dstFile);
                    }
                }
            }
        }
    }
    
    /**
     * @description: 文件复制
     * @param src：被复制文件源地址
     * @param dst：复制到的文件地址
     */
    public static void copy(File srcFile, File destFile) {
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @title: 针对springMVC文件上传处理，保存文件到指定路径
     * @param CommonsMultipartFile cfile springMVC文件上传对象
     * @param String outPutFile 文件输出路径,可自动判断路径是否存在，来创建文件
     */
    public static void copy(CommonsMultipartFile cfile, String outPutFile) {
        FileOutputStream fos = null;
        InputStream is = null;
        try {
            File dstFile = new File(outPutFile);//输出路径
            File dir = dstFile.getParentFile();//获取目录
            if (!dir.exists()){
                dir.mkdirs();//创建目录
                dstFile.createNewFile();//创建文件
            }
            is  = cfile.getInputStream();
            fos = new FileOutputStream(dstFile);
            byte[] b = new byte[1024];
            int len = -1;
            while ((len = is.read(b)) != -1) {
                fos.write(b, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null){
                    is.close();
                }
                if (fos != null){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * @description: 文件复制
     * @param src 源文件
     * @param dst 目标文件例如：path+name.txt
     */
    public static void copy(File src, String dst) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(src);
            File dstFile = new File(dst);
            File dir = dstFile.getParentFile();
            if (!dir.exists())
                dir.mkdirs();
            fos = new FileOutputStream(dstFile);
            byte[] b = new byte[1024];
            int len = -1;
            while ((len = fis.read(b)) != -1) {
                fos.write(b, 0, len);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * @description: 根据文件名获取文件的后缀
     * @param fileName 文件全名
     * @return：文件后缀
     */
    public static String getExtention(String fileName) {
        int pos = fileName.lastIndexOf(".");
        return fileName.substring(pos);
    }
    
    /**
     * @description:将文件转换成字符串
     * @param File：文件对象
     * @return：转换后的字符串
     */
    public static String file2str(File file){
        byte[] bytes = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            bytes = new byte[fis.available()];
            fis.read(bytes);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(bytes==null){
            return null;
        }else{
            // 将二进制转为字符串
            return StringUtils.byte2hex( bytes );
        }
    }
    /**
     * @description:将字符串转为文件
     * @param String：源字符串
     * @param File：文件输出地址
     */
    public static void str2file(String str,String pathout){
        try {
            byte[] fileByte = StringUtils.hex2byte(str);//字符串转二进制
            InputStream in = new ByteArrayInputStream(fileByte);
            byte[] b = new byte[1024];
            int nRead = 0;
            
            File outfile=new File(pathout);
    		File dir = outfile.getParentFile();//获取目录
    		if (!dir.exists()){
    			 dir.mkdirs();//创建目录
    			 outfile.createNewFile();//创建文件
            }
            OutputStream o = new FileOutputStream(pathout);
            while( ( nRead = in.read(b) ) != -1 ){
                o.write( b, 0, nRead );
            }
            o.flush();
            o.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 将文件转成base64 字符串
     * @param path文件路径
     * @return  * 
     * @throws Exception
     */
    public static String encodeBase64File(String path){
	     try {
	    	 File file = new File(path);;
	         FileInputStream inputFile = new FileInputStream(file);
	         byte[] buffer = new byte[(int) file.length()];
	         inputFile.read(buffer);
	         inputFile.close();
	         return new BASE64Encoder().encode(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	     return "";
    }
    
    /**
     * 将base64字符解码保存文件
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */
    public static void decoderBase64File(String base64Code, String pathout){
    	try {
    		 byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
    		 InputStream in = new ByteArrayInputStream(buffer);
             byte[] b = new byte[1024];
             int nRead = 0;
             
             File outfile=new File(pathout);
     		 File dir = outfile.getParentFile();//获取目录
     		 if (!dir.exists()){
     			 dir.mkdirs();//创建目录
     			 outfile.createNewFile();//创建文件
             }
             OutputStream o = new FileOutputStream(pathout);
             while( ( nRead = in.read(b) ) != -1 ){
                 o.write( b, 0, nRead );
             }
             o.flush();
             o.close();
             in.close();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
    }
    
    /**
     * @description:把字符串写入文本中
     * @param fileName 生成的文件绝对路径
     * @param content 文件要保存的内容
     * @param enc 文件编码
     * @return
     */
    public static boolean writeStringToFile(String fileName, String content,
        String enc) throws Exception {
    File file = new File(fileName);
    try {
        if (file.isFile()) {
            file.deleteOnExit();
            file = new File(file.getAbsolutePath());
        }
        /** 判断文件所在目录是否存在 BGN JiJianMing 2011-07-21 **/
        // 如果所在文件目录不存在，则先创建所须目录，再操作文件
        String parentFileName = file.getParent();
        if (!(new File(parentFileName)).exists()) {
            (new File(parentFileName)).mkdirs();
        }
        /** 判断文件所在目录是否存在 END JiJianMing 2011-07-21 **/
        OutputStreamWriter os = null;
        if (enc == null || enc.length() == 0) {
            os = new OutputStreamWriter(new FileOutputStream(file));
        } else {
            os = new OutputStreamWriter(new FileOutputStream(file), enc);
        }
        os.write(content);
        os.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return true;
    }

    /**
     * @description: 读取文件
     * @param fileName
     * @return：
     */
    public static String readFileByChars(String fileName) {
        File file = new File(fileName);
        StringBuffer sBuffer = new StringBuffer();
        Reader reader = null;
        if(file!=null && file.exists() && file.isFile()){
            try {
                // 一次读一个字符
                reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
                int tempchar;
                while ((tempchar = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                if (((char) tempchar) != '\r') {
                    sBuffer.append((char) tempchar);
                }
                }
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                   e1.printStackTrace();
                }
                }
            }
            String temp = sBuffer.toString();
            return temp;
        }else{
            return "";
        }
    }
    /**
     * @description: 创建文件
     * @param filePath：文件绝对路径
     * @author JiJianMing
     * @date 2011-7-26
     */
    public static File newFile(String filePath){
        File file=null;
        try{
            if(filePath.indexOf(File.separator+File.separator)!=-1){
                filePath = filePath.replace(File.separator+File.separator, File.separator);
            }
            file=new File(filePath);
            File parentFile = new File(file.getParent());
            //如果文件所在目录不存在，则先创建目录
            if(!parentFile.exists()){
                parentFile.mkdirs();
            }
            if(!file.exists()){
                file.createNewFile();
            }
        }catch (Exception e) {
        	e.printStackTrace();
        }
        return file;
    }
    /**
     * @description: 检查文件目录分隔符，将不一样的替换为系统目录分隔符
     * @param filePath
     * @return： String
     */
    public static String checkFolderSeparator(String filePath){
        String newFilePath = null;
        boolean winFlag = true ;
        while(winFlag){
            if(filePath.indexOf("\\\\")!=-1){
                filePath=filePath.replace("\\\\", "\\");
            }else{
                winFlag = false ;
            }
        }
        boolean unixFlag = true ;
        while(unixFlag){
            if(filePath.indexOf("//")!=-1){
                filePath=filePath.replace("//", "/");
            }else{
                unixFlag = false ;
            }
        }
        newFilePath = filePath.replace("\\", File.separator).replace("/", File.separator);
        return newFilePath;
    }
    /**
     * @description:将src图片输出为指定大小JPG格式的dest图片（保持src原图比例）
     * @param src：原图片的物理绝对路径
     * @param dest：生成图片的物理绝对路径
     * @param width:生成图片的宽度
     * @param height：生成图片的高度
     */
//    public static void setJPGImageSize(String src,String dest,int width,int height){
//        try {
//            toJPG(src,dest);
//            //背景大小
//            int bg_width=width;
//            int bg_height=height;
//            
//            File _file = new File(dest); // 读入文件
//            Image srcImage = javax.imageio.ImageIO.read(_file); //构造Image对象
//            double oldwidth = (double) srcImage.getWidth(null); //得到源图宽
//            double oldheight = (double) srcImage.getHeight(null);//得到源图高
//            //获取缩放比例
//            double per_1 = oldwidth/width;
//            double per_2 = oldheight/height;
//            //保持原图比例缩放后，图片的大小
//            if(per_1>per_2){
//                width = (int) (oldwidth/per_1);
//                height = (int) (oldheight/per_1);
//            }else{
//                width = (int) (oldwidth/per_2);
//                height = (int) (oldheight/per_2);
//            }
//            BufferedImage tag = new BufferedImage(bg_width, bg_height,BufferedImage.TYPE_INT_RGB);//图片背景
//            tag.getGraphics().drawImage(srcImage, (bg_width-width)/2, (bg_height-height)/2, width, height, null);//绘制图片
//            JimiWriter writer = Jimi.createJimiWriter(dest);
//            writer.setSource(tag);
//            writer.putImage(dest);
//        } catch (Exception e) {
//            BaseLog.e(FileRWUtil.class, "setImageSize",e);
//            throw new BaseException("获取图片格式异常！");
//        }
//    }
    
    /**
     * @description:将src图片转换为JPG格式的dest图片 
     * @param src 原图片的物理绝对路径
     * @param dest：生成图片的物理绝对路径
     */
//    public static void toJPG(String src,String dest){
//        String sourceTemp=null;
//        File sourceTempFile = null;
//        boolean flag=true;
//        try{
//            if(!src.equals(dest)){
//                File destFile=new File(dest);
//                if(destFile.exists()){
//                    destFile.delete();
//                }
//            }
//            String type = getImageType(src);
//            type =type==null?"":type;
//            //图片格式与后缀不符。eg:格式为bmp，后缀为jpg
//            if(!type.equals(src.substring(src.lastIndexOf(".")).toLowerCase())){
//                sourceTemp=src.substring(0,src.lastIndexOf("."))+type;
//                sourceTempFile=new File(sourceTemp);
//                copy(new File(src),sourceTempFile);
//                if(sourceTemp.equals(dest)){//src与dest仅后缀不同,而图片格式相同
//                    flag=false;
//                }
//            }else{
//                if(src.equals(dest)){
//                    flag=false;
//                }else{
//                    sourceTemp=src;
//                }
//            }
//            if(flag){
//                ImageProducer image = Jimi.getImageProducer(sourceTemp);
//                JimiWriter writer = Jimi.createJimiWriter(dest);
//                writer.setSource(image);
//                JPGOptions options = new JPGOptions();
//                options.setQuality(80);
//                writer.setOptions(options);
//                writer.putImage(dest);
//                if(sourceTempFile!=null){
//                    sourceTempFile.delete();
//                }
//            }
//        }catch (Exception e) {
//            BaseLog.e(FileRWUtil.class, "toJPG",e);
//            throw new BaseException("获取图片格式异常！");
//        }finally{
//            if(sourceTempFile!=null && flag)
//                sourceTempFile.delete();
//        }
//    }
    
    /**
     * @description:获取图片格式 [JPEGImageReader/BMPImageReader/PNGImageReader/GIFImageReader]
     * @param src
     * @return：
     */
    public static String getImageType(String src){
        String type=null;
        InputStream input = null;
        ByteArrayInputStream byteInputS = null;
        MemoryCacheImageInputStream imageInput = null;
        try{
            input = new FileInputStream(src);
            byteInputS = new ByteArrayInputStream(IOUtils.toByteArray(input));  
            imageInput = new MemoryCacheImageInputStream(byteInputS);
            Iterator<ImageReader> it = ImageIO.getImageReaders(imageInput);  
            while(it.hasNext()){
                ImageReader reader = (ImageReader)it.next();
                String imageName = reader.getClass().getSimpleName();
                if(imageName!=null){
                    if(imageName.equals("JPEGImageReader")){
                        type = ".jpg";
                    }else if(imageName.equals("BMPImageReader")){
                        type = ".bmp";
                    }else if(imageName.equals("PNGImageReader")){
                        type = ".png";
                    }else if(imageName.equals("GIFImageReader")){
                        type = ".gif";
                    }
                }
            }
        }catch (Exception e) {
        	e.printStackTrace();
        }finally{
            try{
                if(imageInput!=null)
                    imageInput.close();
                if(byteInputS!=null)
                    byteInputS.close();
                if(input!=null)
                    input.close();
            }catch (Exception e) { }
        }
        return type;
    }
    
    /**
     * @description 将文件路径分割符转换为系统路径分割符；
     *          如果存在多余或重复的，替换为一个系统路径分割符。
     *          eg：\\a\b\c   = /a/b/c \\a\\b\c=/a/b/c
     * @param filePath 文件路径
     */
    public static String FileSpitCharToSystemSplitChar(String filePath){
        try{
            String tempStr=filePath.replace("/", File.separator).replace("\\", File.separator);
            boolean flag =true;
            while(flag){
                if(tempStr.indexOf(File.separator+File.separator)!=-1){
                    tempStr=tempStr.replace(File.separator+File.separator,File.separator);
                }else{
                    flag=false;
                }
            }
            filePath = tempStr;
        }catch (Exception e) {
        }
        return filePath;
    }
    
    /**
     * @title 获取本地目录下的所有文件
     * @description 获取目录下的所有文件。
     * @author Caozengwei
     * @date 2012-09-16
     * @param path 文件夹的物理地址
     * @return 所有文件用逗号分隔
     */
    public static String getDirContent(String path){	
    	//用路径实例化一个文件对象
		File file=new File(path);	
		//取得目录内所有文件列表
		File[] files=file.listFiles();	
		//实例化一个StringBuffer,用于处理显示的字符串
		StringBuffer message=new StringBuffer();
		int fileCount = files.length;
		if(fileCount > 0){
			for (int i=0;i<fileCount;i++){
				//增加文件
				if(files[i].isFile()){
					message.append(files[i].getName() + (i == fileCount -1 ? "" : ","));
				}
			}
		}
		return message.toString();
	}
    
    /**
	 * @title 删除指定文件夹下所有文件
	 * @description 删除指定文件夹下所有文件.
	 * @author CaoZengWei
	 * @date 2012-09-16
	 * @param path 文件夹完整绝对路径
	 * @return (成功/失败)
	 */
	public static boolean delFloderFile(String path) {
		boolean flag = false;
		String[] files = getDirContent(path).split(",");
		int fileLength = files.length;
		if(fileLength > 0){
			for(String file:files){
				String absFilePath = path + File.separator + file;
				File delFile = new File(absFilePath);
				if(delFile.exists()){
					delFile.delete();
				}
			}
			flag = true;
		}
		return flag;
	}
	
	/** 
	 * 删除单个文件 
	 * @param   sPath    被删除文件的文件名 
	 * @return 单个文件删除成功返回true，否则返回false 
	 */  
	public static boolean deleteFile(String sPath) {  
	    boolean flag = false;  
	    try {
	    	 File file = new File(sPath);  
	 	    // 路径为文件且不为空则进行删除  
	 	    if (file.isFile() && file.exists()) {  
	 	        file.delete();  
	 	        flag = true;  
	 	    }  
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return flag;  
	}  
	/**
	 * @title ：上传文件转换
	 * @description: SpringMvc文件上传转换文件对象
	 * @param : comFile 转换源文件
     * @date : 2012-10-22
	 * @return： File 转换之后的文件对象
	 */
	public static File parseFile(CommonsMultipartFile comFile){
		try {
			String fileName=comFile.getOriginalFilename();
			String prefix=fileName.substring(0,fileName.indexOf("."));
			String suffix=fileName.substring(fileName.indexOf("."), fileName.length());
			File file=File.createTempFile(prefix,suffix);
			if(!comFile.isEmpty()){
				comFile.transferTo(file);
			}
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 存储文件
	 * @param stream
	 * @param tagFileName
	 * @throws IOException
	 */
	public static Boolean SaveFileFromInputStream(InputStream stream,String tagFileName) throws IOException    
   {
		boolean b = false;
		File outfile=new File(tagFileName);
		File dir = outfile.getParentFile();//获取目录
		if (!dir.exists()){
			 dir.mkdirs();//创建目录
			 outfile.createNewFile();//创建文件
        }
		OutputStream os=null;
		try{
			os=new FileOutputStream(outfile);
			byte buffer[]=new byte[4*1024];
			int len=0;
			while((len=stream.read(buffer))!=-1){
				os.write(buffer,0,len);
			}
			os.flush();
			b=true;
		}catch(Exception e){
			b = false;
			e.printStackTrace();
		}
		finally{
			try{
				os.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return b;
    }
	
	public static Map<String, Object>  upload(HttpServletRequest request, HttpServletResponse response,String fileId,String urlAddr){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		InputStream is = null;
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 获得文件
			MultipartFile multipartFile = multipartRequest.getFile(fileId);
			if(multipartFile!=null){
				if(!multipartFile.isEmpty()){
					// 获得文件名
					String filename = multipartFile.getOriginalFilename();
					// 获取文件后缀名
					String subName = filename.substring(filename.lastIndexOf("."),filename.length());
					String time = String.valueOf(System.currentTimeMillis());
					filename = (fileId.concat(time)).concat(subName);
					String tagFileName =  urlAddr + filename;
					// 输入流
					is = multipartFile.getInputStream();
					// 存储文件
					boolean b = SaveFileFromInputStream(is, tagFileName);
					if(b){
						returnMap.put("result", "success");
						returnMap.put("fileName", filename);
					}
				}else{
					returnMap.put("result", "null");
				}
			}
		} catch (Exception e) {
			returnMap.put("result", "error");
			e.printStackTrace();
		}finally{
			try {
				is.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return returnMap;
	}
	 public static boolean httpDownload(String httpUrl,String saveFile){  
	        // 下载网络文件  
	        int bytesum = 0;  
	        int byteread = 0;  
	        URL url = null;  
	     try {  
	         url = new URL(httpUrl);  
	     } catch (MalformedURLException e1) {  
	         // TODO Auto-generated catch block  
	         e1.printStackTrace();  
	         return false;  
	     }  
	   
	        try {  
	            URLConnection conn = url.openConnection();  
	            InputStream inStream = conn.getInputStream();  
	            
	            File outfile=new File(saveFile);
	    		File dir = outfile.getParentFile();//获取目录
	    		if (!dir.exists()){
	    			 dir.mkdirs();//创建目录
	    			 outfile.createNewFile();//创建文件
	            }
	            FileOutputStream fs = new FileOutputStream(outfile);  
	            
	            byte[] buffer = new byte[1204];  
	            while ((byteread = inStream.read(buffer)) != -1) {  
	                bytesum += byteread;  
	                fs.write(buffer, 0, byteread);  
	            }  
	            return true;  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	            return false;  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	            return false;  
	        }  
	    }  
}
