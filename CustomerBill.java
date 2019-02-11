import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class CustomerBill
{
  private JFrame mainFrame;

  public static final int PRICE_INDEX = 1;
  public static final int QUANTITY_INDEX = 2;
  public static final int SUBTOTAL_INDEX = 3;
  public static final int VAT_INDEX = 4;
  public static final int TOTAL_INDEX = 5;

  private static final double vatPercentage = 14.5;	
	
  /**
   * Table Panel Data members
   */
  private JTable mainTable;
  private JTextField subTotalText;
  private static final Double zeroValue = new Double(0.00);
  private static final Integer oneValue = new Integer(1);
  private static final Object[][] columnData = new Object[][] { { "Item - 2", zeroValue, oneValue, zeroValue, zeroValue, zeroValue }, { "Item - 3", zeroValue, oneValue, zeroValue, zeroValue, zeroValue }, { "Item - 4", zeroValue, oneValue, zeroValue, zeroValue, zeroValue }, { "Item - 5", zeroValue, oneValue, zeroValue, zeroValue, zeroValue }, { "Item - 6", zeroValue, oneValue, zeroValue, zeroValue, zeroValue } };

  /**
   * Sender Data Members
   */
  private JTextField senderNameText, senderTaxId;
  private JTextArea senderAddressText;

  /**
   * Receiver Data Members
   */
  private JTextField receiverNameText, receiverTaxIdText;
  private JTextArea receiverAddressText;

  /**
   * Data Members for bank details
   */
  private JTextField bankNameText, bankIFSCText;

  public CustomerBill()
  {
    initUI();
  }

  private void initUI()
  {
    JPanel billSenderPanel = createBillSenderPanel();
    JPanel billReceiverPanel = createBillReceiverPanel();
    JPanel tablePanel = createTablePane();

    mainFrame = new JFrame();
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.pack();
    mainFrame.setLocationRelativeTo(null);
    mainFrame.setTitle("Customer Bill Management");
    mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    mainFrame.setVisible(true);
    mainFrame.setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(4, 4, 4, 4);
    gbc.anchor = GridBagConstraints.WEST;

    mainFrame.add(billSenderPanel, gbc);
    gbc.gridx++;
    mainFrame.add(billReceiverPanel, gbc);

    gbc.gridx = 0;
    gbc.gridy++;
    gbc.gridwidth = 6;
    mainFrame.add(tablePanel, gbc);

    JPanel bottumPanel = createButtonPanel();
    gbc.gridx = 0;
    gbc.gridy++;
    gbc.gridwidth = 6;
    mainFrame.add(bottumPanel, gbc);
  }

  /**
   * Sender Panel
   * 
   * @return
   */
  private JPanel createBillSenderPanel()
  {
    senderNameText = new JTextField(10);
    senderTaxId = new JTextField(10);
    senderAddressText = new JTextArea(5, 20);

    JPanel billSenderPanel = new JPanel();
    billSenderPanel.setBorder(BorderFactory.createEtchedBorder());
    billSenderPanel.setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(4, 4, 4, 4);
    gbc.anchor = GridBagConstraints.WEST;

    billSenderPanel.add(new JLabel("Name : "), gbc);
    gbc.gridx++;
    billSenderPanel.add(senderNameText, gbc);
    gbc.gridx++;

    billSenderPanel.add(new JLabel("Tax Id: "), gbc);
    gbc.gridx++;
    billSenderPanel.add(senderTaxId, gbc);

    gbc.gridx = 0;
    gbc.gridy++;
    gbc.anchor = GridBagConstraints.NORTHWEST;
    billSenderPanel.add(new JLabel("Address : "), gbc);
    gbc.gridx++;
    gbc.gridwidth = 3;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    billSenderPanel.add(new JScrollPane(senderAddressText), gbc);

    return billSenderPanel;
  }

  /**
   * Receiver Panel
   * 
   * @return
   */
  private JPanel createBillReceiverPanel()
  {
    receiverNameText = new JTextField(10);
    receiverTaxIdText = new JTextField(10);
    receiverAddressText = new JTextArea(5, 20);

    JPanel billReceiverPanel = new JPanel();
    billReceiverPanel.setBorder(BorderFactory.createEtchedBorder());
    billReceiverPanel.setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(4, 4, 4, 4);
    gbc.anchor = GridBagConstraints.WEST;

    billReceiverPanel.add(new JLabel("Name : "), gbc);
    gbc.gridx++;
    billReceiverPanel.add(receiverNameText, gbc);
    gbc.gridx++;

    billReceiverPanel.add(new JLabel("Tax Id : "), gbc);
    gbc.gridx++;
    billReceiverPanel.add(receiverTaxIdText, gbc);

    gbc.gridx = 0;
    gbc.gridy++;
    gbc.anchor = GridBagConstraints.NORTHWEST;
    billReceiverPanel.add(new JLabel("Address : "), gbc);
    gbc.gridx++;
    gbc.gridwidth = 3;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    billReceiverPanel.add(new JScrollPane(receiverAddressText), gbc);

    return billReceiverPanel;
  }

  private JPanel createTablePane()
  {
    JPanel tablePanel = new JPanel();
    tablePanel.setBorder(BorderFactory.createEtchedBorder());
    tablePanel.setLayout(new GridBagLayout());

    mainTable = new JTable();
    mainTable.setModel(new CustomTableModel(columnData));
    JScrollPane tableScrollPane = new JScrollPane();
    tableScrollPane.setViewportView(mainTable);
    mainTable.getModel().addTableModelListener(new TableModelListener()
    {
      public void tableChanged(TableModelEvent evt)
      {
        CustomTableModel modal = (CustomTableModel) evt.getSource();
		int selectedRowIndex = mainTable.getSelectedRow();
				
		double price = Double.parseDouble(modal.getValueAt(selectedRowIndex, PRICE_INDEX).toString().trim());
		int quantity = Integer.parseInt(modal.getValueAt(selectedRowIndex, QUANTITY_INDEX).toString().trim());
			
		double subTotalAmount = price * quantity;
		double vatAmount = (vatPercentage * subTotalAmount) / 100;
		double total = subTotalAmount + vatAmount;
				
		columnData[selectedRowIndex][SUBTOTAL_INDEX] = subTotalAmount;
		columnData[selectedRowIndex][VAT_INDEX] = vatAmount;
		columnData[selectedRowIndex][TOTAL_INDEX] = total;
				
		double totalAmountToPay = 0;
		for(int i = 0; i < columnData.length; i++)
		{
		  totalAmountToPay += Double.parseDouble(columnData[i][TOTAL_INDEX].toString());
		}
		updateSubTotal(totalAmountToPay);
      }
    });

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(4, 4, 4, 4);
    gbc.anchor = GridBagConstraints.WEST;
    gbc.gridwidth = 2;
    tablePanel.add(tableScrollPane, gbc);

    JLabel subTotal = new JLabel("Sub Total :");
    subTotalText = new JTextField(10);
    gbc.gridy++;
    gbc.gridwidth = 1;
    tablePanel.add(subTotal, gbc);

    gbc.gridx++;
    tablePanel.add(subTotalText, gbc);
    return tablePanel;
  }

  
  private void updateSubTotal(double amount)
 {
   subTotalText.setText("" + amount);
   subTotalText.revalidate();
 }	
	
  /**
   * This method creates the button panel
   * 
   * @return
   */
  private JPanel createButtonPanel()
  {
    JPanel buttonPanel = new JPanel();
    JButton okButton = new JButton("OK");
    okButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        okButtonActionPerformed(evt);
      }
    });

    JButton cancelButton = new JButton("Cancel");
    cancelButton.setText("CANCEL");
    cancelButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        cancelButtonActionPerformed(evt);
      }
    });

    JLabel bankNameLbl = new JLabel("Bank Name : ");
    JLabel bankIFSCLbl = new JLabel("Bank IFSC : ");
    bankNameText = new JTextField(10);
    bankIFSCText = new JTextField(10);

    buttonPanel.setBorder(BorderFactory.createEtchedBorder());
    buttonPanel.setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(4, 4, 4, 4);
    gbc.anchor = GridBagConstraints.WEST;

    buttonPanel.add(bankNameLbl, gbc);
    gbc.gridx++;
    buttonPanel.add(bankNameText, gbc);
    gbc.gridx++;
    buttonPanel.add(bankIFSCLbl, gbc);
    gbc.gridx++;
    buttonPanel.add(bankIFSCText, gbc);
    gbc.gridx++;

    gbc.gridx = 0;
    gbc.gridy++;

    gbc.gridwidth = 2;
    buttonPanel.add(okButton, gbc);
    gbc.gridx++;
    gbc.gridwidth = 2;
    buttonPanel.add(cancelButton, gbc);

    return buttonPanel;
  }

  private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt)
  {
    mainFrame.dispose();
  }

  private void okButtonActionPerformed(java.awt.event.ActionEvent evt)
  {
    try {
      DBManager dBManager = new DBManager();
      for (int i = 0; i < columnData.length; i++) {
        Object[] singleRowData = columnData[i];
        dBManager.insertRecordInCustomerData(singleRowData);
      }

      String[] payableData = new String[3];
      payableData[0] = bankNameText.getText().toString().trim();
      payableData[1] = bankIFSCText.getText().toString().trim();
      payableData[2] = subTotalText.getText().toString().trim();
      dBManager.insertPayableDetails(payableData);

      String[] receiverData = new String[3];

      receiverData[0] = receiverNameText.getText().toString().trim();
      receiverData[1] = receiverAddressText.getText().toString().trim();
      receiverData[2] = receiverTaxIdText.getText().toString().trim();
      dBManager.insertReceiverDetails(receiverData);

      String[] senderData = new String[3];
      senderData[0] = senderNameText.getText().toString().trim();
      senderData[1] = senderAddressText.getText().toString().trim();
      senderData[2] = senderTaxId.getText().toString().trim();

      dBManager.insertSenderDetails(senderData);
      dBManager.closeConnection();
      JOptionPane.showMessageDialog(mainFrame, "Thank you for shopping !!! Please visit again !!!", "Invoice Genaration Successful", JOptionPane.INFORMATION_MESSAGE);
      mainFrame.dispose();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(new Runnable()
    {

      public void run()
      {
        new CustomerBill();
      }
    });
  }
}
