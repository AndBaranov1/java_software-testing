package qa.test.mantis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mantis_user_table")

public class UsersData {

    @Id
    @Column(name="id")
    private Integer id;

    @Column(name="username")
    private String userName;

    @Column(name="email")
    private String email;

    public Integer getId() {
        return id;
    }

    public UsersData withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UsersData withName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UsersData withEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        return "UsersData{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
