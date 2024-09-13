package emart.dao;

import emart.dbutil.DbConnection;
import emart.pojo.ProductPOJO;
import emart.pojo.UserProfile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO 
{
     public static String getNextOrderId()throws SQLException
    {
         Connection con = DbConnection.getConnection();
         Statement st =con.createStatement();
         ResultSet rs =st.executeQuery("select max(order_id) from orders");
         rs.next();
         String o_id=rs.getString(1);
         
         if(o_id==null)
             return "O-"+101;
         int o_no=Integer.parseInt(o_id.substring(2));
         o_no= o_no+1;
        
         return "O-"+o_no;
    }
    public static boolean addOrder (ArrayList<ProductPOJO> al,String orderId)throws SQLException
    {
        Connection con = DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("insert into orders values(?,?,?,?,?)");
        int x=0;
        for(ProductPOJO p: al)
        {
            ps.setString(1,orderId);
            ps.setString(2,p.getP_id());
            ps.setInt(3,p.getP_quantity());
            ps.setString(4,UserProfile.getUserid());
            ps.setDouble(5,p.getTotal());
            
            x=x+ps.executeUpdate();
        }
        return x==al.size();
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
     
     public static List<String> getAllOrderId(int user)throws SQLException
     {
         Connection con = DbConnection.getConnection();
        ResultSet rs;
         if(user==0)
         {
           Statement st=con.createStatement(); 
           rs =st.executeQuery("SELECT DISTINCT order_id FROM orders");
         }
         else
         {
             PreparedStatement ps=con.prepareStatement("SELECT DISTINCT order_id FROM orders where user_id =?");
             ps.setString(1,UserProfile.getUserid());
             rs =ps.executeQuery();
         }
          ArrayList<String> id= new ArrayList<>();
       while(rs.next())
       {
          id.add( rs.getString(1));
       }
        return id;
     }
      public static List<ProductPOJO> getOrderDetails(String orderId)throws SQLException
    {
         Connection con = DbConnection.getConnection();
         PreparedStatement ps=con.prepareStatement(" SELECT o.quantity, o.total, p.p_id, p.p_name, p.p_companyname, p.p_price, p.our_price, p.p_tax FROM orders o JOIN products p ON o.p_id = p.p_id WHERE o.order_id = ? ");
         ps.setString(1, orderId);
          ResultSet rs =ps.executeQuery();
         ArrayList<ProductPOJO> pl= new ArrayList<>();
       while(rs.next())
       {
           ProductPOJO p= new ProductPOJO();  
           p.setP_quantity(rs.getInt(1));
           p.setTotal(rs.getDouble(2));
           p.setP_id(rs.getString(3));
           p.setP_name(rs.getString(4));
           p.setP_company(rs.getString(5));
           p.setP_price(rs.getDouble(6));
           p.setO_price(rs.getDouble(7));
           p.setP_tax(rs.getByte(8));
           pl.add(p);  
       }
       return pl;    
    }
    
}
