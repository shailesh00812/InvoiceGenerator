import javax.swing.table.AbstractTableModel;
public class CustomTableModel extends AbstractTableModel
{
  private static final int TOTAL_COLUMNS = 6;
  private static final String[] tableHeaders = new String [] {"Item:", "Price:", "Qty:", "SubTotal", "VAT:", "Total:" };
  protected Class[] columnClasses = new Class[] { String.class, Double.class, Integer.class, Double.class, Double.class, Double.class};
  private Object[][] rowData;
  public CustomTableModel(Object rowData[][]) 
  {
    this.rowData = rowData;
  }
	   
  public String getColumnName(int columnIndex) 
  {
    return tableHeaders[columnIndex];
  }
  
  public int getColumnCount() 
  { 
    return TOTAL_COLUMNS; 
  }
  
  public int getRowCount() 
  { 
    return rowData.length; 
  }
  
  public Class getColumnClass(int col) 
  { 
    return columnClasses[col]; 
  }
 
  public boolean isCellEditable(int row, int col) 
  {
    return true;
  }
 
  public Object getValueAt(int row, int col)
  {
	return rowData[row][col];
  }
  
  public void setValueAt(Object value, int row, int col) 
  {
    rowData[row][col] = value;
    fireTableCellUpdated(row, col);
  }
}