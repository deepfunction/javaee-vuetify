package coursebuy.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "\"accessToken\"")
public class AccessToken extends Union implements Serializable {

    @Column(name = "token", unique = true, nullable = false)
    private String token;

    @Column(name = "\"deviceId\"", nullable = false, unique = true, updatable = false)
    private String deviceId;

    @Column(name = "\"expirationDate\"", nullable = false)
    private LocalDateTime expirationDate;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "\"userId\"", referencedColumnName = "id", updatable = false)
    private User user;

    public AccessToken() {
    }

    public AccessToken(String token, String deviceId, LocalDateTime expirationDate, User user) {
        this.token = token;
        this.deviceId = deviceId;
        this.expirationDate = expirationDate;
        this.user = user;
    }

    public AccessToken(long id, String token, String deviceId, LocalDateTime expirationDate, User user) {
        super(id);
        this.token = token;
        this.deviceId = deviceId;
        this.expirationDate = expirationDate;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccessToken)) return false;
        AccessToken that = (AccessToken) o;
        return getToken().equals(that.getToken()) &&
                getDeviceId().equals(that.getDeviceId()) &&
                getExpirationDate().equals(that.getExpirationDate()) &&
                getUser().equals(that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getToken(), getDeviceId(), getExpirationDate(), getUser());
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "token='" + token + '\'' +
                ", uid='" + deviceId + '\'' +
                ", expirationDate=" + expirationDate +
                ", user=" + user +
                ", id=" + id +
                '}';
    }
}
