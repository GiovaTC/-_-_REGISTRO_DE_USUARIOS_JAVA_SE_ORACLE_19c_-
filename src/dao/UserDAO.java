package dao;

import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public List<User> executeSP(String action, User user) throws Exception {

        List<User> list = new ArrayList<>();
        Connection con = ConnectionDB.getConnection();

        CallableStatement cs = con.prepareCall("{call SP_USERS_CRUD(?,?,?,?,?,?,?,?,?,?,?)}");

        cs.setString(1, action);
        cs.setInt(2, user != null ? user.getId() : 0);
        cs.setString(3, user != null ? user.getFirstName() : null);
        cs.setString(4, user != null ? user.getLastName() : null);
        cs.setString(5, user != null ? user.getEmail() : null);
        cs.setString(6, user != null ? user.getPhone() : null);
        cs.setString(7, user != null ? user.getUsername() : null);
        cs.setString(8, user != null ? user.getPassword() : null);
        cs.setString(9, user != null ? user.getRole() : null);
        cs.setString(10, user != null ? user.getStatus() : null);

        cs.registerOutParameter(11, oracle.jdbc.OracleTypes.CURSOR);

        cs.execute();

        ResultSet rs = (ResultSet) cs.getObject(11);

        while (rs.next()) {
            User u = new User();
            u.setId(rs.getInt("ID"));
            u.setFirstName(rs.getString("FIRST_NAME"));
            u.setLastName(rs.getString("LAST_NAME"));
            u.setEmail(rs.getString("EMAIL"));
            u.setPhone(rs.getString("PHONE"));
            u.setUsername(rs.getString("USERNAME"));
            u.setPassword(rs.getString("PASSWORD"));
            u.setRole(rs.getString("ROLE"));
            u.setStatus(rs.getString("STATUS"));
            list.add(u);
        }

        con.close();
        return list;
    }
}
