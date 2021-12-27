package qa.test.addressbook.model;

public class ContactData {
  private int id;
  private final String middlename;
  private final String lastname;
  private final String nickname;
  private final String title;
  private final String company;
  private final String address;
  private final String fname;

  public ContactData(String middlename, String lastname, String nickname, String title, String company,
                     String address, String fname) {
    this.id = Integer.MAX_VALUE;
    this.middlename = middlename;
    this.lastname = lastname;
    this.nickname = nickname;
    this.title = title;
    this.company = company;
    this.address = address;
    this.fname = fname;
  }

  public ContactData(int id, String middlename, String lastname, String nickname, String title, String company,
                     String address, String fname) {
    this.id = id;
    this.middlename = middlename;
    this.lastname = lastname;
    this.nickname = nickname;
    this.title = title;
    this.company = company;
    this.address = address;
    this.fname = fname;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ContactData that = (ContactData) o;

    if (id != that.id) return false;
    if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
    return fname != null ? fname.equals(that.fname) : that.fname == null;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
    result = 31 * result + (fname != null ? fname.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "lastname='" + lastname + '\'' +
            ", fname='" + fname + '\'' +
            '}';
  }

  public String getMiddlename() {
    return middlename;
  }

  public String getLastname() {
    return lastname;
  }

  public String getNickname() {
    return nickname;
  }

  public String getTitle() {
    return title;
  }

  public String getCompany() {
    return company;
  }

  public String getAddress() {
    return address;
  }

  public String getFname() {
    return fname;
  }

}
