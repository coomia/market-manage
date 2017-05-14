package cn.lmjia.market.core.entity;

import lombok.Getter;
import lombok.Setter;
import me.jiangcai.wx.standard.entity.StandardWeixinUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Collection;
import java.util.Objects;

/**
 * 表明这是一个可登录的法人或者自然人
 *
 * @author CJ
 */
@Entity
@Setter
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"loginName", "code"})})
public class Login implements UserDetails {

    /**
     * 可以管理所有关于代理的项目
     */
    public static final String ROLE_AllAgent = "ALL_AGENT";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30)
    private String loginName;
    /**
     * 推荐码，需要唯一；
     * TODO 算法待定
     */
    private String code;
    private String password;
    private boolean enabled = true;
    /**
     * 引导者
     */
    @ManyToOne
    private Login guideUser;
    @OneToOne(cascade = CascadeType.ALL)
    private ContactWay contactWay;
    /**
     * 这个身份所关联的用户，通常应该是唯一的
     */
    @OneToOne
    private StandardWeixinUser wechatUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Login)) return false;
        Login login = (Login) o;
        return Objects.equals(loginName, login.loginName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loginName);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return loginName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isManageable() {
        return false;
    }

    /**
     * @return 登录身份的头衔
     */
    public String getLoginTitle() {
        return "一般";
    }

    @Override
    public String toString() {
        return "Login{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", enabled=" + enabled +
                ", guideUser=" + guideUser +
                '}';
    }
}
