package pl.morecraft.dev.settler.service;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Service;
import pl.morecraft.dev.settler.domain.jtb.QJTBObjectPrivilege;
import pl.morecraft.dev.settler.security.util.Security;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class PermissionService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<String> getShortSummary() {
        QJTBObjectPrivilege jTBObjectPrivilege = QJTBObjectPrivilege.jTBObjectPrivilege;
        return new JPAQuery<>(entityManager).select(
                jTBObjectPrivilege.operationType.stringValue()
        ).distinct().from(
                jTBObjectPrivilege
        ).where(
                jTBObjectPrivilege.objectFrom.id.eq(Security.currentUser().getId())
        ).fetch();
    }

}
