package emart.dao;

import emart.dbutil.DbConnection;
import emart.pojo.ReceptionistPOJO;
import emart.pojo.UserPojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReceptionistDAO 
{
    public static Map<String,String> getNonRegisteredReceptionist()throws SQLException
    {
         Connection con = DbConnection.getConnection();
         Statement st =con.createStatement();
         ResultSet rs=st.executeQuery("select empid,empname from employees where job='Receptionist' and empid not in(select empid from users where usertype='Receptionist' )");
         HashMap<String,String> Receptionist = new HashMap<>();
         while(rs.next())
         {
             String id =rs.getString(1);
             String name = rs.getString(2);
             Receptionist.put(id,name);
         }
         return Receptionist;
    }
    public static boolean addReceptionist(UserPojo user)throws SQLException
    {
        Connection con = DbConnection.getConnection();
         PreparedStatement ps=con.prepareStatement("insert into users values(?,?,?,?,?)");
         ps.setString(1, user.getUserid());
         ps.setString(2, user.getEmpid());
         ps.setString(3,user.getPassword());
         ps.setString(4,user.getUsertype());
         ps.setString(5, user.getUsername());
         int result = ps.executeUpdate();
         return result==1;
    }
    public static List<ReceptionistPOJO> viewAllReceptionist()throws SQLException
    {
        Connection con = DbConnection.getConnection();
         Statement st =con.createStatement();
         ResultSet rs =st.executeQuery("select users.empid,username,userid,usertype,salary from users,employees where job='Receptionist'and users.empid=Employees.empid");
         ArrayList<ReceptionistPOJO> list= new ArrayList<>();
       while(rs.next())
       {
            ReceptionistPOJO e=new ReceptionistPOJO();
            e.setEmpid(rs.getString(1));
            e.setEmpname(rs.getString(2));
            e.setUserid(rs.getString(3));
            e.setJob(rs.getString(4));
            e.setSalary(rs.getInt(5));
           list.add(e);  
       }
       return list; 
    }
    public static Map<String,String> getAllReceptionistId()throws SQLException
    {
         Connection con = DbConnection.getConnection();
         Statement st =con.createStatement();
         ResultSet rs=st.executeQuery("select userid,username from users where usertype='Receptionist' order by empid");
         HashMap<String,String> Receptionist = new HashMap<>();
         while(rs.next())
         {
             String id =rs.getString(1);
             String name = rs.getString(2);
             Receptionist.put(id,name);
         }
         return Receptionist;
    }
    public static boolean updatePassword(String id , String psw)throws SQLException
    {
        Connection con = DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("update users set password=? where userid=?");
        ps.setString(2, id);
        ps.setString(1, psw);
        return  ps.executeUpdate()!=0;
    }
    public static List<String> getAllReceptionistUserId()throws SQLException
    {
         Connection con = DbConnection.getConnection();
         Statement st =con.createStatement();
         ResultSet rs=st.executeQuery("select userid,username from users where usertype='Receptionist' order by empid");
         List<String> Receptionist = new ArrayList<>();
         while(rs.next())
         {
             String id =rs.getString(1);
            
             Receptionist.add(id);
         }
         return Receptionist;
    }
    public static boolean removeReceptionist(String id) throws SQLException
    {
        Connection con = DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("delete from users where userid=?");
        ps.setString(1, id);
       
        return ps.executeUpdate()!=0;
        
    }
}
