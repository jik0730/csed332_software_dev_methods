package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.OrthopedicOVRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class GenOrthopedicOVRecordBeanFromFormAction {
	
	private DAOFactory factory;
	private long loggedInMID;
	
	public GenOrthopedicOVRecordBeanFromFormAction(DAOFactory factory, long loggedInMID) {
		this.factory = factory;
		this.loggedInMID = loggedInMID;
	}
	
	public OrthopedicOVRecordBean genBean(HttpServletRequest request, ServletContext servletContext) {
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Configure a repository (to ensure a secure temp location is used)
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Parse the request
		List<FileItem> items = null;
		try {
			items = upload.parseRequest(request);
		} catch(FileUploadException e) {
			e.printStackTrace();
			return null;
		}
		
		OrthopedicOVRecordBean bean = new OrthopedicOVRecordBean();
		
		for (FileItem f : items) {
			switch (f.getFieldName()) {
				case "Injured": bean.setInjured(f.getString());
				//case "XRay": bean.setXray(f.getInputStream());
				//TODO: Finish File Reading
			}
		}
		return bean;
	}
	
}
