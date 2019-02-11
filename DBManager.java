import java.sql.*;

public class DBManager
{
  private Connection connection;
  public DBManager()
  {
	try
	{
	  connection = openConnection();
	}
	catch(Exception ex)
	{
	  ex.printStackTrace();
	}
  }
  
  private Connection openConnection()
  {
 	try
	{
      Class.forName("oracle.jdbc.driver.OracleDriver");
      return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "1234");	
    }
	catch(Exception ex)
	{
	  ex.printStackTrace();
	  return null;
	}	
  }			
  
  public void insertSenderDetails(String[] senderData)
  {
	  try
	  {
		  if(connection == null){
			  System.out.println(" Connection is null.");
			  System.exit(0);
		  }
		PreparedStatement ps = connection.prepareStatement("insert into senderdetails values(?,?,?)");
        ps.setString(1, senderData[0]);
        ps.setString(2, senderData[1]);
        ps.setString(3, senderData[2]);
        ps.executeQuery();  
	  }
	  catch(Exception ex)
	  {
	    ex.printStackTrace();
	  }
  }
  
  
  public void insertReceiverDetails(String[] receiverData)
  {
	  try
	  {
		PreparedStatement ps = connection.prepareStatement("insert into receiverdetails values(?,?,?)");
        ps.setString(1, receiverData[0]);
        ps.setString(2, receiverData[1]);
        ps.setString(3, receiverData[2]);
        ps.executeQuery();  
	  }
	  catch(Exception ex)
	  {
	    ex.printStackTrace();
	  }
  } 
  
  public void insertPayableDetails(String[] payableData)
  {
	  try
	  {
		PreparedStatement ps = connection.prepareStatement("insert into payabledata values(?,?,?)");
        ps.setString(1, payableData[0]);
        ps.setString(2, payableData[1]);
        ps.setString(3, payableData[2]);
        ps.executeQuery();  
	  }
	  catch(Exception ex)
	  {
	    ex.printStackTrace();
	  }
  }
   
  public void insertRecordInCustomerData(Object[] singleRowData)
  {
	try
	{
      String itemName = singleRowData[0].toString();
	  double price = Double.parseDouble(singleRowData[CustomerBill.PRICE_INDEX].toString());
	  int quantity = Integer.parseInt(singleRowData[CustomerBill.QUANTITY_INDEX].toString());
	  double subTotalAmount = Double.parseDouble(singleRowData[CustomerBill.SUBTOTAL_INDEX].toString());
	  double vatAmount = Double.parseDouble(singleRowData[CustomerBill.VAT_INDEX].toString());
	  double totalAmount = Double.parseDouble(singleRowData[CustomerBill.TOTAL_INDEX].toString());   

      PreparedStatement ps = connection.prepareStatement("insert into customerdata values(?,?,?,?,?,?)");
      ps.setString(1, itemName);
      ps.setString(2, "" + price);
      ps.setString(3, "" + quantity);
      ps.setString(4, "" + subTotalAmount);
      ps.setString(5, "" + vatAmount);
      ps.setString(6, "" + totalAmount);
      ps.executeQuery();		
	}
	catch(Exception ex)
	{
	  ex.printStackTrace();
	}
  }		
  
  public void closeConnection()
  {
	  try
	  {
	     if(null != connection)
		 {
	       connection.close();
		 }
	  }
	  catch(Exception ex)
	  {
		  ex.printStackTrace();
	  }
  }
}