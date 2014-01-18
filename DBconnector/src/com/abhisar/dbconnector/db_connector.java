package com.abhisar.dbconnector;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class db_connector {
	//public static void main(String args[]){
	 private Connection conn = null;
	 public Connection getConn() {
			return conn;
		}

		public void setConn(Connection conn) {
			this.conn = conn;
		}
	
	

	public db_connector(){
		try {
		//System.out.println(System.getProperty("os.name"));
		//System.out.println("Working Directory = " +
	      //        System.getProperty("user.dir"));
		File fXmlFile = new File(System.getProperty("user.dir")+"/configuration.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
		//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		NodeList nList = doc.getElementsByTagName("config");
		Element eElement = (Element) nList.item(0);
		
		String driver = eElement.getElementsByTagName("db_driver").item(0).getTextContent();
		//System.out.println(driver);
		String conn_url = eElement.getElementsByTagName("db_vendor").item(0).getTextContent()+ 
				"://"+eElement.getElementsByTagName("db_url").item(0).getTextContent()
				+ "/" + eElement.getElementsByTagName("db_name").item(0).getTextContent();
		//System.out.println(conn_url);
		
		String username = eElement.getElementsByTagName("user_name").item(0).getTextContent();
		String password = eElement.getElementsByTagName("password").item(0).getTextContent();
		
		Class.forName(driver);
		
		System.out.println("JDBC Driver Registered!");
		 
		
		conn = DriverManager.getConnection(
				conn_url,username,
				password);
		if (conn != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
		
		}
		
		catch (ClassNotFoundException e) {
 
			System.out.println("Where is your JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return;
 
		}
		catch (SQLException e) {
			 
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
 
		}
 
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//}
	}

}
