package pl.morecraft.dev.settler.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.encoding.BasePasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.morecraft.dev.settler.dao.repository.FileObjectRepository;
import pl.morecraft.dev.settler.dao.repository.UserRepository;
import pl.morecraft.dev.settler.domain.FileObject;
import pl.morecraft.dev.settler.domain.QUser;
import pl.morecraft.dev.settler.domain.User;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;
import pl.morecraft.dev.settler.security.authorisation.PermissionManager;
import pl.morecraft.dev.settler.security.util.Security;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractService;
import pl.morecraft.dev.settler.service.exception.DuplicatedEntityException;
import pl.morecraft.dev.settler.service.exception.EntityNotFoundException;
import pl.morecraft.dev.settler.web.dto.AvatarDTO;
import pl.morecraft.dev.settler.web.dto.FileObjectDTO;
import pl.morecraft.dev.settler.web.dto.UserDTO;
import pl.morecraft.dev.settler.web.dto.UserListDTO;
import pl.morecraft.dev.settler.web.misc.ListPage;
import pl.morecraft.dev.settler.web.misc.UserListFilters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.function.UnaryOperator;

@Service
@Transactional
public class UserService extends AbstractService<User, UserDTO, UserListDTO, UserListFilters, QUser, Long, UserRepository> {

    private final FileObjectRepository fileObjectRepository;
    private final BasePasswordEncoder passwordEncoder;
    private final PermissionManager permissionManager;
    private final UserRepository userRepository;
    private final FileService fileService;

    @Autowired
    public UserService(FileObjectRepository fileObjectRepository, BasePasswordEncoder passwordEncoder, PermissionManager permissionManager, UserRepository userRepository, FileService fileService) {
        this.fileObjectRepository = fileObjectRepository;
        this.passwordEncoder = passwordEncoder;
        this.permissionManager = permissionManager;
        this.userRepository = userRepository;
        this.fileService = fileService;
    }

    protected UserRepository getUserRepository() {
        return userRepository;
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
                if (userRepository.findOneByLogin(user.getLogin()) != null) {
                    throw new DuplicatedEntityException("User with this login already exists: " + user.getLogin());
                }
            } else {
                User originUser = userRepository.findOne(user.getId());
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

    public ResponseEntity<AvatarDTO> getAvatar(Long id, String login) throws IOException {
        User user;

        if (id > 0) {
            user = userRepository.findOne(id);
        } else if (!login.isEmpty()) {
            user = userRepository.findOneByLogin(login);
        } else {
            return null;
        }

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        FileObject fileObject = user.getAvatar();

        if (fileObject == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(
                new AvatarDTO(
                        fileObject.getType(),
                        Base64.getEncoder().encodeToString(fileObject.getContent())
                ),
                HttpStatus.OK
        );
    }

    public ResponseEntity<List<UserListDTO>> searchSimple(Integer limit, String string) {
        String filters;
        boolean isAnd;
        String[] tab = string.split("\\s+");
        if (tab.length == 1) {
            filters = "{\"firstName\":\"" + tab[0] + "\",\"lastName\":\"" + tab[0] + "\",\"login\":\"" + tab[0] + "\"}";
            isAnd = false;
        } else if (tab.length == 2) {
            filters = "{\"firstName\":\"" + tab[0] + "\",\"lastName\":\"" + tab[1] + "\"}";
            isAnd = true;
        } else {
            filters = "{\"firstName\":\"" + tab[0] + "\",\"lastName\":\"" + tab[1] + "\",\"login\":\"" + tab[2] + "\"}";
            isAnd = true;
        }
        ListPage<UserListDTO> listPage = get(
                1,
                limit,
                "-login",
                filters,
                isAnd
        );
        return new ResponseEntity<>(listPage.getContent(), HttpStatus.OK);
    }

    public ResponseEntity<FileObjectDTO> setAvatar(String login, FileObjectDTO fileObjectDTO) {
        User user = userRepository.findOneByLogin(login);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ResponseEntity<FileObjectDTO> fileObjectDTOResponseEntity = fileService.save(fileObjectDTO);

        user.setAvatar(fileObjectRepository.findOne(fileObjectDTOResponseEntity.getBody().getId()));
        userRepository.save(user);

        return fileObjectDTOResponseEntity;
    }

}
