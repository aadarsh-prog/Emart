package emart.dao;

import emart.dbutil.DbConnection;
import emart.pojo.ProductPOJO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO 
{
     public static String getNextProductid()throws SQLException
    {
         Connection con = DbConnection.getConnection();
         Statement st =con.createStatement();
         ResultSet rs =st.executeQuery("select max(p_id) from products");
         rs.next();
         String p_id=rs.getString(1);
         
         if(p_id==null)
             return "P"+101;
         int p_no=Integer.parseInt(p_id.substring(1));
         p_no= p_no+1;
        
         return "P"+p_no;
    }
     public static boolean addItem(ProductPOJO p)throws SQLException
     {
         Connection con = DbConnection.getConnection();
         PreparedStatement ps=con.prepareStatement("insert into products values(?,?,?,?,?,?,?,'Y')");
         ps.setString(1, p.getP_id());
         ps.setString(2, p.getP_name());
         ps.setString(3, p.getP_company());
         ps.setDouble(4, p.getP_price());
         ps.setDouble(5, p.getO_price());
         ps.setInt(6, p.getP_tax());
         ps.setInt(7, p.getP_quantity());
         int x = ps.executeUpdate();
        return x!=0;
     
     }
      public static List<ProductPOJO> getProductsDetails()throws SQLException
    {
         Connection con = DbConnection.getConnection();
         Statement st =con.createStatement();
         ResultSet rs =st.executeQuery("select * from products where status='Y'");
         ArrayList<ProductPOJO> pl= new ArrayList<>();
       while(rs.next())
       {
           ProductPOJO p= new ProductPOJO();  
           p.setP_id(rs.getString(1));
           p.setP_name(rs.getString(2));
           p.setP_company(rs.getString(3));
           p.setP_price(rs.getDouble(4));
           p.setO_price(rs.getDouble(5));
           p.setP_tax(rs.getByte(6));
           p.setP_quantity(rs.getInt(7));
           pl.add(p);  
       }
       return pl;    
    } 
      public static boolean deleteItem(String id)throws SQLException
      {
          Connection con = DbConnection.getConnection();
         PreparedStatement ps=con.prepareStatement("update products set status='N' where p_id=?");
          ps.setString(1,id);
          return ps.executeUpdate() !=0;
          
      }
      public static boolean updateItem(ProductPOJO p)throws SQLException
      {
         Connection con = DbConnection.getConnection();
         PreparedStatement ps=con.prepareStatement("update products set p_name=?,p_companyname=?,p_price=?,our_price=?,p_tax=?,quantity=? where p_id=?");
         ps.setString(7, p.getP_id());
         ps.setString(1, p.getP_name());
        ps.setString(2, p.getP_company());
         ps.setDouble(3, p.getP_price());
         ps.setDouble(4, p.getO_price());
         ps.setInt(5, p.getP_tax());
         ps.setInt(6, p.getP_quantity());
         return ps.executeUpdate() !=0; 
      }
      
      public static ProductPOJO getProductsDetails(String id)throws SQLException
      {
         Connection con = DbConnection.getConnection();
         PreparedStatement ps=con.prepareStatement("select * from products where p_id=? and status='Y'");
         ps.setString(1,id);
         ResultSet rs =ps.executeQuery();
          ProductPOJO p= new ProductPOJO();  
         if(rs.next())
         {
           p.setP_id(rs.getString(1));
           p.setP_name(rs.getString(2));
           p.setP_company(rs.getString(3));
           p.setP_price(rs.getDouble(4));
           p.setO_price(rs.getDouble(5));
           p.setP_tax(rs.getByte(6));
           p.setP_quantity(rs.getInt(7)); 
         }
         return p;
      }
      
      public static boolean updateStocks(List<ProductPOJO> productList)throws SQLException
      {
         Connection con = DbConnection.getConnection();
         PreparedStatement ps=con.prepareStatement("update products set quantity=quantity-?  where p_id=? ");
         int x=0;
         for( ProductPOJO p :productList )
         {
             ps.setInt(1,p.getP_quantity());
             ps.setString(2,p.getP_id());
             int row=ps.executeUpdate();
             if(row!=0)
                 x++;
         }
         return productList.size()==x;
      }
}
