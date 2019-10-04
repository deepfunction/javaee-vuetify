package coursebuy.domain.dao.login;

import coursebuy.domain.entity.AccessToken;
import coursebuy.domain.entity.User;
import coursebuy.domain.usecase.login.LoginDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Transactional
@RequestScoped
public class LoginDaoImpl implements LoginDao {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger LOGGER = LogManager.getLogger(LoginDaoImpl.class);

    @Override
    public void addAccessToken(AccessToken accessToken) {
        entityManager.persist(accessToken);
    }

    @Override
    public void deleteExpiredTokens(User user) {
        entityManager.createQuery("delete from AccessToken at where at.user.id = :id and at.expirationDate < :currentDate")
                .setParameter("id", user.getId())
                .setParameter("currentDate", LocalDateTime.now()).executeUpdate();
    }

    @Override
    public User findUserByEmail(String email) {
        try {
            return entityManager.createQuery("select u from User u where u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("User not found.");
        }
        return null;
    }

    @Override
    public AccessToken findAccessTokenByDeviceId(String deviceId) {
        try {
            return entityManager.createQuery("select at from AccessToken at where at.deviceId = :deviceId", AccessToken.class)
                    .setParameter("deviceId", deviceId).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("AccessToken not found.");
        }
        return null;
    }

    @Override
    public AccessToken findAccessTokenByToken(@NotNull String token) {
        try {
            return entityManager.createQuery("select at from AccessToken at where at.token = :token", AccessToken.class)
                    .setParameter("token", token).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("AccessToken not found.");
        }
        return null;
    }
}
