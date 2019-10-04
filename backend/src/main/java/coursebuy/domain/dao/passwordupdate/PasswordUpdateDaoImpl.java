package coursebuy.domain.dao.passwordupdate;

import coursebuy.domain.usecase.passwordupdate.PasswordUpdateDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
@RequestScoped
public class PasswordUpdateDaoImpl implements PasswordUpdateDao {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger LOGGER = LogManager.getLogger(PasswordUpdateDaoImpl.class);

    @Override
    public boolean findUserByEmail(String email) {
        try {
            entityManager.createQuery("select u from User u where u.email = :email")
                    .setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("User not found.");
            return false;
        }
        return true;
    }

    @Override
    public int updatePassword(String email, String password) {
        return entityManager.createQuery("update User u set u.password = :password where u.email = :email")
                .setParameter("password", password)
                .setParameter("email", email).executeUpdate();
    }
}
