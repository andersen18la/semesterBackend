package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import security.IUser;
import security.PasswordStorage;

@Entity(name = "SEED_USER")
@NamedQueries(
        {
            @NamedQuery(name = "User.findAllUsers", query = "SELECT u FROM SEED_USER u")
        }
)
public class User implements IUser, Serializable {

    //You will need to change this to save a Hashed/salted password 
    @Column(length = 255, name = "PASSWORD_HASH", nullable = false)
    private String passwordHash;

    @Id
    @Column(length = 35, name = "USER_NAME", nullable = false)
    private String userName;

    @ManyToMany
    List<Role> roles;
    @OneToOne(mappedBy = "user")
    private Rating rating;

    public User()
    {
    }

    public User(String userName, String password) throws PasswordStorage.CannotPerformOperationException
    {
        this.userName = userName;
        this.passwordHash = PasswordStorage.createHash(password);
    }

    public void addRole(Role role)
    {
        if (roles == null)
        {
            roles = new ArrayList();
        }
        roles.add(role);
        role.addUser(this);
    }

    public void addSingleRole(Role role)
    {
        roles = new ArrayList();
        roles.add(role);
        role.addUser(this);
    }

    public List<Role> getRoles()
    {
        return roles;
    }

    @Override
    public List<String> getRolesAsStrings()
    {
        if (roles.isEmpty())
        {
            return null;
        }
        List<String> rolesAsStrings = new ArrayList();
        for (Role role : roles)
        {
            rolesAsStrings.add(role.getRoleName());
        }
        return rolesAsStrings;
    }

    @Override
    public String getPasswordHash()
    {
        return passwordHash;
    }

    public void setPassword(String password) throws PasswordStorage.CannotPerformOperationException
    {
        this.passwordHash = PasswordStorage.createHash(password);
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    @Override
    public String getUserName()
    {
        return userName;
    }

}
