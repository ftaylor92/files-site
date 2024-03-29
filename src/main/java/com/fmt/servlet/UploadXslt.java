package com.fmt.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.fmt.xsl.XmlTransformer;

public class UploadXslt extends HttpServlet {
	 private static final long serialVersionUID = 1L;
	 private static final String UPLOAD_DIRECTORY = "tmp";
	 private static final int THRESHOLD_SIZE     = 1024 * 1024 * 3;  // 3MB
	 private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
	 private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
	    
	    /**
	     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	     *      response)
	     */
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	System.out.println("doPost()");
	    	
	    	File xmlFile=  null;
	    	File xslFile=  null;
	    	File transFile=  null;
//	    	String page= "x";
	    	
	    	// checks if the request actually contains upload file
	    	if (!ServletFileUpload.isMultipartContent(request)) {
	    	    PrintWriter writer = response.getWriter();
	    	    System.err.println("Request does not contain upload data");
	    	    writer.println("Request does not contain upload data");
	    	    writer.flush();
	    	    return;
	    	}
	    	
	    	// configures upload settings
	    	DiskFileItemFactory factory = new DiskFileItemFactory();
	    	factory.setSizeThreshold(THRESHOLD_SIZE);
	    	final File repo= new File(System.getProperty("java.io.tmpdir"));
	    	System.out.println("repo: "+ repo.getAbsolutePath());
	    	factory.setRepository(repo);
	    	 
	    	ServletFileUpload upload = new ServletFileUpload(factory);
	    	upload.setFileSizeMax(MAX_FILE_SIZE);
	    	upload.setSizeMax(MAX_REQUEST_SIZE);
	    	    
	    	// constructs the directory path to store upload file
	    	String uploadPath = getServletContext().getRealPath("")+ File.separator+ UPLOAD_DIRECTORY;
	    	System.out.println("uploadPath: "+ uploadPath);
	    	
	    	// creates the directory if it does not exist
	    	File uploadDir = new File(uploadPath);
	    	if (!uploadDir.exists()) {
	    	    uploadDir.mkdir();
	    	}
	    	
	    	//parsing the request to save upload data to a permanent file on disk
	    	List formItems;
			try {
				formItems = upload.parseRequest(request);

		    	Iterator iter = formItems.iterator();
		    	 
		    	// iterates over form's fields
		    	while (iter.hasNext()) {
		    	    FileItem item = (FileItem) iter.next();
		    	    // processes only fields that are not form fields
		    	    if (!item.isFormField()) {
		    	    	if(item.getFieldName().equals("xmlFile")) {
			    	        String fileName = "orig.xml";
			    	    	
			    	        String filePath = uploadPath + File.separator + fileName;
			    	        System.out.println("filePath: "+ filePath);
			    	        xmlFile = new File(filePath);
			    	 
			    	        // saves the file on disk
			    	        item.write(xmlFile);
		    	    	}
		    	    	if(item.getFieldName().equals("xslFile")) {
			    	        String fileName = "trans.xsl";
			    	    	
			    	        String filePath = uploadPath + File.separator + fileName;
			    	        System.out.println("filePath: "+ filePath);
			    	        xslFile = new File(filePath);
			    	        
			    	        fileName = "trans.xml";
			    	        filePath = uploadPath + File.separator + fileName;
			    	        System.out.println("filePath: "+ filePath);
			    	        transFile = new File(filePath);
			    	 
			    	        // saves the file on disk
			    	        item.write(xslFile);
		    	    	}
		    	    }
		    	}
		    	request.setAttribute("message", "Upload has been done successfully!");
		    	
		    	if(null != xmlFile && null != xslFile) {
		    		XmlTransformer.transformXML(xmlFile, xslFile, transFile);
		    		xmlFile.delete();
		    		xslFile.delete();
		    	} else {
		    		//TODO: err
		    	}
			} catch (FileUploadException ex) {
				request.setAttribute("message", "There was an error: " + ex.getMessage());
			} catch(Exception ex) {
				request.setAttribute("message", "There was an error: " + ex.getMessage());
			}
			
			System.out.println("Upload Complete!");
			String fileRelativeUrl= transFile.getCanonicalPath().substring(transFile.getCanonicalPath().indexOf("/"+UPLOAD_DIRECTORY+"/")+(UPLOAD_DIRECTORY.length()+2));
			//System.out.println("getRequestURI: "+ request.getRequestURI());
			System.out.println("fileRelativeUrl: "+ fileRelativeUrl);
			getServletContext().getRequestDispatcher("/"+UPLOAD_DIRECTORY+"/"+fileRelativeUrl).forward(request, response);

	    }
}
