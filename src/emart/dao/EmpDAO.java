package emart.dao;

import emart.pojo.EmployeePOJO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import emart.dbutil.DbConnection;
import java.util.ArrayList;
import java.util.List;


public class EmpDAO {
    public static String getNextEmpid()throws SQLException
    {
         Connection con = DbConnection.getConnection();
         Statement st =con.createStatement();
         ResultSet rs =st.executeQuery("select max(empid) from employees");
         rs.next();
         String empid=rs.getString(1);
         int empno=Integer.parseInt(empid.substring(1));
         empno= empno+1;
         return "E"+empno;
    }
    public static boolean addEmp(EmployeePOJO e)throws SQLException
    {
         Connection con = DbConnection.getConnection();
         PreparedStatement ps=con.prepareStatement("insert into employees values(?,?,?,?)");
         ps.setString(1, e.getEmpid());
         ps.setString(2, e.getEmpname());
         ps.setString(3,e.getJob());
         ps.setInt(4,e.getSalary());
         int result = ps.executeUpdate();
         return result != 0;
    }
    public static List<EmployeePOJO> viewEmp()throws SQLException
    {
         Connection con = DbConnection.getConnection();
         Statement st =con.createStatement();
         ResultSet rs =st.executeQuery("select * from employees order by empid");
         ArrayList<EmployeePOJO> empList= new ArrayList<>();
       while(rs.next())
       {
            EmployeePOJO e=new EmployeePOJO();
            e.setEmpid(rs.getString(1));
            e.setEmpname(rs.getString(2));
            e.setJob(rs.getString(3));
            e.setSalary(rs.getInt(4));
           empList.add(e);  
       }
       return empList;    
    }
    public static List<String> getAllEmpId()throws SQLException
    {
         Connection con = DbConnection.getConnection();
         Statement st =con.createStatement();
         ResultSet rs =st.executeQuery("select empid from employees order by empid");
         ArrayList<String> id= new ArrayList<>();
       while(rs.next())
       {
          id.add( rs.getString(1));
       }
        return id;
    }
    public static EmployeePOJO findEmpById(String empid)throws SQLException
    {
        Connection con = DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("select * from Employees where empid=?");
        ps.setString(1, empid);
        ResultSet rs =ps.executeQuery();
        rs.next();
        EmployeePOJO e=new EmployeePOJO();
        e.setEmpid(rs.getString(1));
        e.setEmpname(rs.getString(2));
        e.setJob(rs.getString(3));
        e.setSalary(rs.getInt(4));
        return e;
    }
    public static boolean updateEmp(EmployeePOJO e)throws SQLException
    {
         Connection con = DbConnection.getConnection();
         PreparedStatement ps=con.prepareStatement("update Employees set empname=?,job=?,salary=? where empid=?");
         ps.setString(1, e.getEmpname());
         ps.setString(2,e.getJob());
         ps.setInt(3,e.getSalary());
          ps.setString(4, e.getEmpid());
          int x = ps.executeUpdate();
        if(x==0)
        {
            return false;
        }
        else
        {
            boolean r=UserDAO.isUserPresent(e.getEmpid());
            if(r==false)
                return true;
            ps=con.prepareStatement("update users set username=?,userType=? where empid=?");
            ps.setString(1, e.getEmpname());
            ps.setString(2,e.getJob());
            ps.setString(3, e.getEmpid());
            int y=ps.executeUpdate();
            return y==1;
        }
    }
    public static boolean removeEmp(String empid)throws SQLException
    {
        Connection con = DbConnection.getConnection();
         PreparedStatement ps=con.prepareStatement("delete from employees where empid=?");
         ps.setString(1,empid);
          int x = ps.executeUpdate();
        return x!=0;
    }
    
        
}
