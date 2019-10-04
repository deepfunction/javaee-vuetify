package coursebuy.domain.dao.logout;

import coursebuy.domain.entity.User;
import coursebuy.domain.usecase.logout.LogoutDao;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Transactional
@RequestScoped
public class LogoutDaoImpl implements LogoutDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void deleteAccessTokenByDeviceId(String deviceId) {
        entityManager.createQuery("delete from AccessToken at where at.deviceId = :deviceId")
                .setParameter("deviceId", deviceId)
                .executeUpdate();
    }
}