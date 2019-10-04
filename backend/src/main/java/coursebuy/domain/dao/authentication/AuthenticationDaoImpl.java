package coursebuy.domain.dao.authentication;

import coursebuy.domain.usecase.authentication.AuthenticationDao;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Transactional
@RequestScoped
public class AuthenticationDaoImpl implements AuthenticationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean validateToken(String token) {
        TypedQuery<Boolean> booleanQuery = entityManager.createQuery("select case when count(at) > 0 then true else false end from AccessToken at where at.token = :token", Boolean.class)
                .setParameter("token", token);
        return booleanQuery.getSingleResult();
    }
}
