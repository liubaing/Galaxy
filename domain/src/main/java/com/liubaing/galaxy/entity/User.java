package com.liubaing.galaxy.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Set;

/**
 * 类说明:用户POJO
 *
 * @author heshuai
 * @version Feb 24, 2012
 */

@Entity
@Table(name = "u_user_tab")
public class User extends BaseModel {

    private static final long serialVersionUID = 1132116198278001891L;

    @Id
    @Column(name = "userid", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;                //id主键

    @Column(name = "loginname", nullable = false)
    private String username;            //用户名

    @Column(nullable = false)
    private String password;            //密码

    @Column(name = "name")
    private String name;                //姓名

    private String token;                //用户令牌

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, targetEntity = UserRole.class, mappedBy = "user")
    private Set<UserRole> userRoles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotBlank
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }


}
