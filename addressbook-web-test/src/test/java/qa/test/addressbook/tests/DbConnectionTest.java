package qa.test.addressbook.tests;

import org.testng.annotations.Test;
import qa.test.addressbook.model.ContactData;
import qa.test.addressbook.model.Contacts;
import qa.test.addressbook.model.GroupData;
import qa.test.addressbook.model.Groups;


import java.sql.*;

public class DbConnectionTest {
  @Test
  public void testDbConnectionGroup() {
    Connection conn = null;
    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook?user=root&password=");
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery("select group_id, group_name, group_header, group_footer from group_list");
      Groups groups = new Groups();
      while (rs.next()) {
        groups.add(new GroupData().withId(rs.getInt("group_id")).withName(rs.getString("group_name"))
                .withHeader(rs.getString("group_header")).withFooter(rs.getString("group_footer")));
      }
      rs.close();
      st.close();
      conn.close();
      System.out.println(groups);

    } catch (SQLException ex) {
      System.out.println("SQLExeption: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError " + ex.getErrorCode());
    }
  }

  @Test
  public void testDbConnectionContact() {
    Connection conn = null;
    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook?user=root&password=");
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery("select id, firstname, lastname, address from addressbook");
      Contacts contact = new Contacts();
      while (rs.next()) {
        contact.add(new ContactData().withId(rs.getInt("id")).withMiddlename(rs.getString("middlename"))
                        .withLastname(rs.getString("lastname")).withAddress(rs.getString("address"))
                .withPhoneHome(rs.getString("phone")).withNickname(rs.getString("nickname"))
                .withPhoneMobile(rs.getString("mobile")).withEmail(rs.getString("email")));
      }
      rs.close();
      st.close();
      conn.close();
      System.out.println(contact);

    } catch (SQLException ex) {
      // handle any errors
      System.out.println("SQLExeption: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError " + ex.getErrorCode());
    }
  }
}
