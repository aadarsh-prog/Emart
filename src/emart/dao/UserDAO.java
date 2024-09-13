package emart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import emart.dbutil.DbConnection;
import emart.pojo.UserPojo;
import emart.pojo.UserProfile;

public class UserDAO
{
    public static boolean validateUser (UserPojo user)throws SQLException
    {
        Connection con = DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("select * from users where userid=? and  password=? and usertype=?");
        ps.setString(1,user.getUserid());
        ps.setString(2,user.getPassword());
        ps.setString(3,user.getUsertype());
        ResultSet rs=ps.executeQuery();
        
        if(rs.next())
        {
            String username=rs.getString(5);
            UserProfile.setUsername(username);
            
            return true;
         }
       
        return false;
    }
    public static boolean isUserPresent(String empid)throws SQLException
    {
        Connection con = DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("select 1 from users where empid=?");
        ps.setString(1, empid);
        ResultSet rs=ps.executeQuery();
        return rs.next();
    }
    
}
