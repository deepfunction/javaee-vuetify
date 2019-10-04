package coursebuy.domain.dao.registration;

import coursebuy.domain.entity.User;
import coursebuy.domain.usecase.registration.RegistrationDao;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Transactional
@RequestScoped
public class RegistrationDaoImpl implements RegistrationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long addUser(User user) {
        entityManager.persist(user);
        entityManager.flush();
        return user.getId();
    }

    @Override
    public boolean checkExistenceEmail(String email) {
        TypedQuery<Boolean> booleanQuery = entityManager.createQuery("select case when count(u) > 0 then true else false end from User u where u.email = :email", Boolean.class)
                .setParameter("email", email);
        return booleanQuery.getSingleResult();
    }
}
