package pl.morecraft.dev.settler.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.CollectionUtils;
import org.springframework.security.authentication.encoding.BasePasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.morecraft.dev.settler.dao.repository.UserRepository;
import pl.morecraft.dev.settler.domain.QUser;
import pl.morecraft.dev.settler.domain.User;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;
import pl.morecraft.dev.settler.security.authorisation.PermissionManager;
import pl.morecraft.dev.settler.security.util.Security;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractService;
import pl.morecraft.dev.settler.service.exception.DuplicatedEntityException;
import pl.morecraft.dev.settler.service.exception.EntityNotFoundException;
import pl.morecraft.dev.settler.web.dto.UserDTO;
import pl.morecraft.dev.settler.web.dto.UserListDTO;
import pl.morecraft.dev.settler.web.misc.UserListFilters;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

@Service
@Transactional
public class UserService extends AbstractService<User, UserDTO, UserListDTO, UserListFilters, QUser, Long, UserRepository> {

    @Inject
    private PermissionManager permissionManager;

    @Inject
    private UserRepository repository;

    @Inject
    private BasePasswordEncoder passwordEncoder;

    @Override
    protected UserRepository getRepository() {
        return repository;
    }

    @Override
    protected Boolean isFilterClassExtended() {
        return true;
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    protected Class<UserDTO> getDtoClass() {
        return UserDTO.class;
    }

    @Override
    protected Class<UserListDTO> getListDtoClass() {
        return UserListDTO.class;
    }

    @Override
    protected Class<UserListFilters> getListFilterClass() {
        return UserListFilters.class;
    }

    @Override
    protected List<BooleanExpression> getPreFilters() {
        return CollectionUtils.add(
                new ArrayList<>(),
                permissionManager.objectFilter(
                        Security.currentUser(),
                        QUser.user._super,
                        OperationType.RDM
                )
        );
    }

    @Override
    protected QUser getEQ() {
        return QUser.user;
    }

    @Override
    protected UnaryOperator<UserDTO> getGetPostProcessingFunction() {
        return (userDTO) -> {
            userDTO.setPassword(null);
            return userDTO;
        };
    }

    @Override
    protected UnaryOperator<User> getSavePostProcessingFunction() {
        return (user) -> {
            if (user.getId() == null) {
                if (repository.findOneByLogin(user.getLogin()) != null) {
                    throw new DuplicatedEntityException("User with this login already exists: " + user.getLogin());
                }
            } else {
                User originUser = repository.findOne(user.getId());
                if (originUser == null) {
                    throw new EntityNotFoundException("User with this ID was not found: " + user.getId());
                } else {
                    if (user.getPassword() == null) {
                        user.setPassword(originUser.getPassword());
                    } else {
                        user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
                    }
                    if (user.getAccountExpireDate() == null) {
                        user.setAccountExpireDate(originUser.getAccountExpireDate());
                    }
                }
            }
            return user;
        };
    }

}
