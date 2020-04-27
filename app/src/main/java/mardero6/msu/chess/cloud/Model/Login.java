package mardero6.msu.chess.cloud.Model;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "secretary") // matches the XML root node
public class Login {


//    String username;
//    String password;

    @Attribute
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Attribute
    private String username;


    public String getName() { return username;
    }
    public void setName(String name) {this.username = name;}

    public Login() {} // this is required by RetroFit
    public Login(String name, String pwd) {
        this.username = name;
        this.password = pwd;
    }
}
