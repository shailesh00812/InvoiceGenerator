import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
 
public class Login extends JFrame implements ActionListener
{
	JLabel l1, l2, l3;
    JTextField tf1;
    JButton btn1;
    JPasswordField p1;
 
    Login()
    {
        setTitle("Login in invoice Generator");
        setVisible(true);
        setSize(800, 800);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        l1 = new JLabel("Login in invoice Generator");
        l1.setForeground(Color.blue);
        l1.setFont(new Font("Serif", Font.BOLD, 20));
 
        l2 = new JLabel("UserName:");
        l3 = new JLabel("Password:");
        tf1 = new JTextField();
        p1 = new JPasswordField();
        btn1 = new JButton("Submit");
 
        l1.setBounds(100, 30, 400, 30);
        l2.setBounds(80, 70, 200, 30);
        l3.setBounds(80, 110, 200, 30);
        tf1.setBounds(300, 70, 200, 30);
        p1.setBounds(300, 110, 200, 30);
        btn1.setBounds(150, 160, 100, 30);
 
        add(l1);
        add(l2);
        add(tf1);
        add(l3);
        add(p1);
        add(btn1);
        btn1.addActionListener(this);
    }
 
    public void actionPerformed(ActionEvent e)
    {
		if(showData())
		{
		  new CustomerBill();
		  dispose();
		}
    }
 
    public boolean showData()
    {
        JLabel l, l0;
 
        String str1 = tf1.getText();
        char[] p = p1.getPassword();
        String str2 = new String(p);
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "1234");
            PreparedStatement ps = con.prepareStatement("select name from emp where username=? and pass=?");
            ps.setString(1, str1);
            ps.setString(2, str2);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                setVisible(true);
                setSize(600, 600);
                setLayout(null);
                l = new JLabel();
                l0 = new JLabel("you are succefully logged in..");
                l0.setForeground(Color.blue);
                l0.setFont(new Font("Serif", Font.BOLD, 30));
                l.setBounds(60, 50, 400, 30);
                l0.setBounds(60, 100, 400, 40);
 
                add(l);
                add(l0);
                l.setText("Welcome " + rs.getString(1));
                l.setForeground(Color.red);
                l.setFont(new Font("Serif", Font.BOLD, 30));
		return true;
 
            } else
            {
                JOptionPane.showMessageDialog(null,"Incorrect UserName or password..Try Again with correct detail");
				 
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
	   JOptionPane.showMessageDialog(null," Error in connecting the database.");
        }
		
		return false;
    }
 
    public static void main(String arr[])
    {
        new Login();
    }
}