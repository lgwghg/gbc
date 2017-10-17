package com.webside.common.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.webside.user.model.UserEntity;
import com.webside.user.service.UserService;
import com.webside.util.DateUtils;


/**
 * 查看CK上传的图片
 * @author ThinkGem
 * @version 2014-06-25
 */
public class UploadServlet extends HttpServlet
{

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private ApplicationContext applicationContext = null;//定义全局变量context
	
	@SuppressWarnings("deprecation")
	public void uploadStream(HttpServletRequest request, HttpServletResponse respose) throws ServletException, IOException 
	{
		StringBuffer path = null;
		String uploader = null;
		FileItemFactory factory = null;
		ServletFileUpload upload = null;
		FileItemIterator files = null;
		BufferedInputStream	inputStream = null;
		BufferedOutputStream outputStream = null;
		PrintWriter out = null;
		String userId = null;
		String uploadDir = null;
		
		try
		{
			if(null == request) return;
			
			path = new StringBuffer();
			out = respose.getWriter();
			
			String sep = "/";
			String six = "png";
			uploadDir = request.getRealPath("/") + "upload".replace('\\', '/').replace("//", "/");
			uploader = request.getContextPath() + "/upload".replace('\\', '/').replace("//", "/");
			
			File dirPath = new File(uploadDir);
			
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
			
			uploadDir = uploadDir + sep + "avatar";
			uploader  = uploader  + sep + "avatar";
			
			dirPath = new File(uploadDir);
			
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
			
			uploadDir = uploadDir + sep + DateUtils.getDate(DateUtils._DEFAULT3);
			uploader  = uploader  + sep + DateUtils.getDate(DateUtils._DEFAULT3);
			dirPath = new File(uploadDir);
			
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
			
			factory = new DiskFileItemFactory();
			upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding(request.getCharacterEncoding());//这里很重要啊 解决linux环境下上传文件名乱码的问题
			files = upload.getItemIterator(request);
			if(null == files) return;
			
			FileItemStream file = null;
			String fileName = UUID.randomUUID().toString().replaceAll("-", "");
			int i = 0;
			userId = (String)request.getParameter("userId");
			//遍历所有文件域
			while(files.hasNext())
			{
				file = files.next();
				
				if(!file.isFormField())
				{
					six = file.getFieldName().indexOf(".") > -1 ? file.getFieldName().trim().split("\\.")[1] : "png";
					six = (null == six || "".equals(six.trim())) ? "png" : six.trim();
					
					inputStream = new BufferedInputStream(file.openStream());
					if (i==0) {
						// 将原图保存数据库
						outputStream = new BufferedOutputStream(new FileOutputStream(new File( uploadDir + sep + fileName + "." +six)));
						String photoUrl =uploader  + sep + fileName + "." +six;
						UserEntity userEntity = new UserEntity();
						userEntity.setId(userId);
						userEntity.setPhoto(photoUrl);
						applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
						UserService userService = (UserService)applicationContext.getBean("userService");
						int count = userService.updateUserOnly(userEntity);
						if (count <= 0) {
							out.print("error:头像保存失败，请稍候重试。。。");
						}
					} else {
						outputStream = new BufferedOutputStream(new FileOutputStream(new File(uploadDir + sep + fileName + "_" + i + "." +six)));
					}
					
					Streams.copy(inputStream, outputStream, true);
					path.append(uploader + sep + fileName + "_" + i + "." +six).append(";");
					
					inputStream.close();
					outputStream.close();
					
					i++;
				}
			}
		}
		catch (MaxUploadSizeExceededException e) 
		{
			logger.error(e.getMessage(), e);
			out.print("文件大小超出限制...");
			return;
		} 
		catch (Exception e) 
		{
			logger.error(e.getMessage(), e);
			out.print("文件上传异常...");
			return;
		}
		finally
		{
			try {
				if(null != inputStream) inputStream.close();
				if(null != outputStream) outputStream.close();
			} 
			catch (IOException e) {}
		}
		
		out.print(path.toString());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		uploadStream(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		uploadStream(req, resp);
	}
}