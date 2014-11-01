package com.fmt.xsl;

import org.apache.log4j.Logger;
import org.apache.log4j.FileAppender;
import org.apache.log4j.SimpleLayout;
import javax.xml.transform.Source;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerConfigurationException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import org.w3c.dom.Node;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Matt Taylor, MIT Lincoln Labratory, 2006
 * Transforms XML
 */
public class XmlTransformer {
	public static Logger logger;
	
	static {
		logger= Logger.getLogger(Transformer.class);
	}
	
	/**
	 * Transforms an XML file into another format with an XSLTransform.
	 * @param args <inXMLFilePath> <outXMLFilePath> <XSLTFilePath> <LogFilePath>
	 * @exception IOException if input files do not exist
	 * @exception Exception if not enough parameters are given
	 * ex. c:/usr/tmp/Applications/xml/udate.xml c:/usr/tmp/Applications/xml/f-date.xml c:/usr/tmp/Applications/xml/date.xsl c:/usr/tmp/Applications/xml/date.log
	 */
	public static void main(String[] args) throws IOException, Exception
	{
		if(args.length != 4) {
			throw new Exception("Must provide all foure arguments: <inXMLFilePath> <outXMLFilePath> <XSLTFilePath> <LogFilePath>");
		}
		FileAppender appender= new FileAppender(new SimpleLayout(), args[3], true);
		logger.addAppender(appender);
		
		File origXML= new File(args[0]);
		File newXML= new File(args[1]);
		File XSLT= new File(args[2]);

		XmlTransformer.transformXML(origXML, XSLT, newXML);
	}

	/**
	 * Transforms an XML file into another format.
	 * Transform described in XSLT file.
	 * Newly transformed file created, while original remains untouched.
	 * @param origXML Original XML file to Transform
	 * @param XSLT Transformation description file
	 * @param newXML newly transformed file
	 **/
	public static void transformXML(File origXML, File XSLT, File newXML)
	{
		Source inXML= new StreamSource(origXML);
		Result outXML= new StreamResult(newXML);
		Source xslt= new StreamSource(XSLT);
		
		transformXML(inXML, xslt, outXML);
	}
	
	/**
	 * Transforms an XML Source into another format.
	 * Transform described in XSLT Source.
	 * @param inXML Original XML to transform
	 * @param xslt transformetion description
	 * @param outXML newly transformed Result
	 **/
	public static void transformXML(Source inXML, Source xslt, Result outXML)
	{
		try {
			TransformerFactory xslFactory= TransformerFactory.newInstance();
			Templates cachedXSL= xslFactory.newTemplates(xslt);
			Transformer xslTrans= cachedXSL.newTransformer();
			
			xslTrans.transform(inXML, outXML);
		} catch(TransformerConfigurationException ex) {
			logger.error(ex);
		} catch(TransformerException ex) {
			logger.error(ex);
		}
	}
	
	/**
	 * Transforms a date string into an XML Node
	 * @param sDate Date as String
	 * @return XML Node describing this date
	 * @throws ParserConfigurationException if XML cannot be created
	 * @throws ParseException is Date is not in correct format
	 **/
	public static Node format(String sDate) throws ParserConfigurationException, ParseException
	{
		System.out.println("begin-format");
		
		Document doc= DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element nDate= doc.createElement("formatted-date");
		SimpleDateFormat df= (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.FULL);
		df.setLenient(true);
		Date date= df.parse(sDate);

		df.applyPattern("MMMM");
		addChild(nDate, "month", df.format(date));
		df.applyPattern("dd");
		addChild(nDate, "day-of-month", df.format(date));
		df.applyPattern("EEEE");
		addChild(nDate, "day-of-week", df.format(date));
		df.applyPattern("yyyy");
		addChild(nDate, "year", df.format(date));
		
		return nDate;
	}
	
    /**
     * utility method for adding text nodes.
     */
    private static void addChild(Node parent, String name, String text) {
        Element child = parent.getOwnerDocument().createElement(name);
        child.appendChild(parent.getOwnerDocument().createTextNode(text));
        parent.appendChild(child);
    }
}
