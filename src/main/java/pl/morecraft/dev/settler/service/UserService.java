package pl.morecraft.dev.settler.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.morecraft.dev.settler.dao.repository.UserRepository;
import pl.morecraft.dev.settler.domain.QUser;
import pl.morecraft.dev.settler.domain.User;
import pl.morecraft.dev.settler.service.prototype.AbstractService;
import pl.morecraft.dev.settler.web.dto.UserDTO;
import pl.morecraft.dev.settler.web.dto.UserListDTO;
import pl.morecraft.dev.settler.web.misc.UserListFilters;

import javax.inject.Inject;

@Service
@Transactional
public class UserService extends AbstractService<User, UserDTO, UserListDTO, UserListFilters, QUser, Long, UserRepository> {

    @Inject
    UserRepository repository;

    @Override
    protected UserRepository getRepository() {
        return repository;
    }

    @Override
    protected Boolean getExtendedFilters() {
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
    protected QUser getEQ() {
        return QUser.user;
    }

}
