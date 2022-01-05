package utiles;


import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;



public class OrderTask{ 


	  public static void main (String[] arg) throws InterruptedException { 
		  //System.out.println("120000 de sleeep"); 
		 Thread.sleep(240000);	 
		  try {
		    String setOrderType= "";//
			String OrderIsSend = "";//
			String chk_seq = "";//
			String Order_id = "";//
			InetAddress ipAddress = InetAddress.getLocalHost();
			String ip = ipAddress.getHostAddress();
			String token = "token";
			
			//System.out.println("funciona el trend"); 
			Conexion conex = new Conexion();
			Connection connec = conex.conexionBksrv4();
			PreparedStatement pstam = connec.prepareStatement("select * from micros.dbo.bkmop where ip = '"+ip+"'");		
			ResultSet rset = pstam.executeQuery();
					while (rset.next())
					{			
						token = rset.getString("token");	
						//System.out.println("sca token a la api" +token+""); 
					}		
			Conexion c = new Conexion();
			Connection conn = c.getConnection();
			PreparedStatement pst = conn.prepareStatement("select top 50 chk_seq from micros.chk_dtl where chk_open_date_time > DATEADD(mi,-60,GETDATE())");//select top 50 chk_seq from micros.chk_dtl where order_type_seq = '5' and chk_open_date_time > DATEADD(mi,-10000,GETDATE())");
			ResultSet rs = pst.executeQuery();
					while (rs.next())
					{				
						chk_seq = rs.getString("chk_seq");
						//System.out.println(chk_seq);
						Statement stm3 = conn.createStatement();
						ResultSet rs3 = stm3.executeQuery("select line_11, line_12 from micros.chk_info_dtl where line_07 like '%MENUAPP%' and chk_seq = '"+chk_seq+"'");
						while(rs3.next())//
						{
							Order_id = rs3.getString("line_11");
							String[]  id =   Order_id.split("-");
							String sOrder_idBase = id[0];
							String sOrder_id = sOrder_idBase.substring(4);
													
							//System.out.println("a la api"); 
							
							OrderIsSend = rs3.getString("line_12");//
							if (OrderIsSend == null ) {//
							
							URL urlForPutRequest = new URL("https://api-lac.menu.app/api/tablet/orders/"+sOrder_id+"/ready");  
							//URL urlForPutRequest = new URL("https://api-demo.menu.app/api/tablet/orders/\"+sOrder_id+\"/ready");
						    HttpURLConnection conection = (HttpURLConnection) urlForPutRequest.openConnection();
						    conection.setRequestMethod("PUT");
						    conection.setRequestProperty("tablet-token", token);
						    //conection.setRequestProperty("tablet-token", "e2dd2388d45d9006b78bfa167e344852530e83ae");
						    conection.setRequestProperty("Content-type", "application/json");
						    conection.setRequestProperty("Accept", "application/json");
						    conection.setRequestProperty("Application", "tablet-application");
						    int responseCode = conection.getResponseCode();
						    System.out.println(responseCode);
						    if (responseCode == HttpURLConnection.HTTP_OK) {	
						    	Conexion conect = new Conexion();
								Connection connection = conect.getConnection();
								Statement pstat = connection.createStatement();		
								pstat.executeQuery("UPDATE micros.chk_info_dtl SET line_12 = 'Notificacion: OK' , line_13 = GETDATE() WHERE chk_seq = '"+chk_seq+"'");
							//System.out.println("hizo el update");
						   }		
							}
							
						}
					}				
		  }
		  catch(Exception e) {	
			  System.out.println(e);
		  }  
	  }
	}


/*public class OrderTask{
	public static void main (String[] args ) {
	
			final long timeIntervals = 10;
			Runnable runnable = new Runnable() {
			public void run() {	 
				
				while (true) {
		  try {
			  
		    String setOrderType= "";//
			String OrderType = "";//
			String chk_seq = "";//
			String Order_id = "";//
			
			
			Conexion c = new Conexion();
			Connection conn = c.getConnection();
			PreparedStatement pst = conn.prepareStatement("select * from micros.chk_info_dtl where line_07 like '%MENUAPP%'");
			ResultSet rs = pst.executeQuery();
					while (rs.next())
					{
						
						chk_seq = rs.getString("chk_seq");
						

			
					
						Statement stm3 = conn.createStatement();
						ResultSet rs3 = stm3.executeQuery("select line_11 from micros.chk_info_dtl where chk_seq = '"+chk_seq+"'");
						while(rs3.next())//
						{
							Order_id = rs3.getString("line_11");
							String[]  id =   Order_id.split("-");
							String sOrder_idBase = id[0];
							String sOrder_id = sOrder_idBase.substring(4);
													
							//envio a la api
							URL urlForPutRequest = new URL("https://api-lac.menu.app/api/tablet/orders/"+sOrder_id+"/ready");   
						    HttpURLConnection conection = (HttpURLConnection) urlForPutRequest.openConnection();
						    conection.setRequestMethod("PUT");
						    conection.setRequestProperty("tablet-token", "91uodp");
						    conection.setRequestProperty("Content-type", "application/json");
						    conection.setRequestProperty("Accept", "application/json");
						    conection.setRequestProperty("Application", "tablet-application");
						    int responseCode = conection.getResponseCode();
						    System.out.println(responseCode);
						    if (responseCode == HttpURLConnection.HTTP_OK) {
						    	
						    	System.out.println(responseCode);
						    	
						   }		    
						}
					}				
		  }
		  catch(Exception e) {
			  System.out.println("ERROR");
		  }
				

				try {
					
				Thread.sleep(timeIntervals);
				} catch (Exception e) {
					e.getStackTrace();
						System.out.println("ERROR");
				}
			}
			}
	};
	Thread thread = new Thread(runnable);
	thread.start();
	}
}*/		