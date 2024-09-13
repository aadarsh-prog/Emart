package emart.dbutil;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import javax.swing.JOptionPane;



public class DbConnection 
{
    private static Connection con;
    
    static 
    {
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            con=DriverManager.getConnection("jdbc:oracle:thin:@DESKTOP-C6PEEV7:1521:xe","Grocery", "0000");
           // JOptionPane.showMessageDialog(null,"Successfully connected");
        }
        catch(ClassNotFoundException e)
        {
            JOptionPane.showMessageDialog(null,"Error in Loading Driver");
            e.printStackTrace();
            System.exit(1);
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null,"Error in opening Connection to Database");
            ex.printStackTrace();
            System.exit(1);
        }
    }
    public static Connection getConnection()
    {
        return con;
    }
     public static void closeConnection()
     {
         try
         {
             con.close();
             //JOptionPane.showMessageDialog(null,"Connection closed");
         }
          catch(SQLException ee)
        {
            JOptionPane.showMessageDialog(null,"Error in Closing  Connection");
            ee.printStackTrace();
          
        }
         
     }
}
    