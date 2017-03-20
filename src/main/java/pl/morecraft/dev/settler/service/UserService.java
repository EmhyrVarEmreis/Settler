package pl.morecraft.dev.settler.service;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.CollectionUtils;
import com.querydsl.jpa.impl.JPAQuery;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.encoding.BasePasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.morecraft.dev.settler.dao.repository.FileObjectRepository;
import pl.morecraft.dev.settler.dao.repository.UserRepository;
import pl.morecraft.dev.settler.domain.FileObject;
import pl.morecraft.dev.settler.domain.QTransaction;
import pl.morecraft.dev.settler.domain.QUser;
import pl.morecraft.dev.settler.domain.User;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;
import pl.morecraft.dev.settler.domain.dictionaries.internal.SocialEnum;
import pl.morecraft.dev.settler.security.authorisation.PermissionManager;
import pl.morecraft.dev.settler.security.exception.AuthorizationMethodNotImplemented;
import pl.morecraft.dev.settler.security.exception.FacebookLoginException;
import pl.morecraft.dev.settler.security.util.Security;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractService;
import pl.morecraft.dev.settler.service.exception.DuplicatedEntityException;
import pl.morecraft.dev.settler.service.exception.EntityNotFoundException;
import pl.morecraft.dev.settler.service.exception.UserNotFoundException;
import pl.morecraft.dev.settler.service.social.FacebookService;
import pl.morecraft.dev.settler.web.dto.*;
import pl.morecraft.dev.settler.web.misc.ListPage;
import pl.morecraft.dev.settler.web.misc.UserListFilters;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;

@Service
@Transactional
public class UserService extends AbstractService<User, UserDTO, UserListDTO, UserListFilters, QUser, Long, UserRepository> {

    private final FileObjectRepository fileObjectRepository;
    private final BasePasswordEncoder passwordEncoder;
    private final PermissionManager permissionManager;
    private final FacebookService facebookService;
    private final UserRepository userRepository;
    private final EntityManager entityManager;
    private final FileService fileService;

    @Autowired
    public UserService(FileObjectRepository fileObjectRepository, BasePasswordEncoder passwordEncoder, PermissionManager permissionManager, FacebookService facebookService, UserRepository userRepository, EntityManager entityManager, FileService fileService) {
        this.fileObjectRepository = fileObjectRepository;
        this.passwordEncoder = passwordEncoder;
        this.permissionManager = permissionManager;
        this.facebookService = facebookService;
        this.userRepository = userRepository;
        this.entityManager = entityManager;
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

    public ResponseEntity<ProfileDTO> getProfile() {
        User user = Security.currentUser();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        user.setPassword(null);
        return new ResponseEntity<>(
                getModelMapper().map(user, ProfileDTO.class),
                HttpStatus.OK
        );
    }

    public ResponseEntity<ProfileDTO> saveProfile(ProfileDTO profileDTO) {
        User user = userRepository.findOneByLogin(profileDTO.getLogin());

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserDTO userDTO = getModelMapper().map(user, UserDTO.class);

        userDTO.setEmail(profileDTO.getEmail());
        userDTO.setFirstName(profileDTO.getFirstName());
        userDTO.setLastName(profileDTO.getLastName());
        userDTO.setPassword(profileDTO.getPassword());

        ResponseEntity<UserDTO> responseEntity = save(userDTO);

        return new ResponseEntity<>(
                getModelMapper().map(responseEntity.getBody(), ProfileDTO.class),
                responseEntity.getStatusCode()
        );
    }

    public ResponseEntity<List<UserWithValueDTO>> getUsersWithValue(Long userId) {
        if (Objects.isNull(userId) || userId < 0) {
            userId = Security.currentUser().getId();
        }
        QUser user = QUser.user;
        QTransaction transaction = QTransaction.transaction;
        List<Tuple> fetch = new JPAQuery<>(entityManager)
                .from(user, transaction)
                .select(user, transaction.value.sum())
                .where(transaction.creator.id.eq(userId))
                .where(user.id.ne(userId))
                .where(
                        transaction.owners.any().id.user.eq(user).or(
                                transaction.contractors.any().id.user.eq(user)
                        )
                )
                .groupBy(user.id, user.firstName, user.lastName, user.email, user.created, user.avatar, user.login, user.accountExpireDate)
                .orderBy(transaction.value.sum().desc())
                .fetch();
        ModelMapper preparedModelMapper = getModelMapper();
        List<UserWithValueDTO> userWithValueDTOList = new ArrayList<>(fetch.size());
        for (Tuple tuple : fetch) {
            User u = tuple.get(user);
            Double d = tuple.get(transaction.value.sum());
            if (Objects.nonNull(u)) {
                userWithValueDTOList.add(
                        new UserWithValueDTO(
                                userId,
                                preparedModelMapper.map(u, String.class),
                                d
                        )
                );
            }
        }
        return new ResponseEntity<>(userWithValueDTOList, HttpStatus.OK);
    }

    public ResponseEntity<UserSocialDTO> getUserSocial(String username) {
        User user;
        if (Objects.isNull(username) || username.isEmpty()) {
            user = Security.currentUser();
        } else {
            user = userRepository.findOneByLogin(username);
        }
        return getUserSocial(user);
    }

    public ResponseEntity<UserSocialDTO> getUserSocial(Long userId) {
        User user;
        if (Objects.isNull(userId) || userId < 0) {
            user = Security.currentUser();
        } else {
            user = userRepository.findOne(userId);
        }
        return getUserSocial(user);
    }

    public ResponseEntity removeSocialIntegration(User user, SocialEnum type) {
        switch (type) {
            case FACEBOOK:
                user.setFbId(null);
                userRepository.save(user);
                break;
            case GOOGLE:
            default:
                throw new AuthorizationMethodNotImplemented("Not implemented yet");
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity addSocialIntegration(User user, SocialEnum type, UserSocialDTO userSocialDTO) {
        switch (type) {
            case FACEBOOK:
                Long id = Long.parseLong(userSocialDTO.getFbId());
                if (facebookService.authTokenVerifyUserId(userSocialDTO.getFbToken(), id)) {
                    if (userRepository.exists(QUser.user.fbId.eq(id))) {
                        return new ResponseEntity(HttpStatus.CONFLICT);
                    }
                    user.setFbId(id);
                    userRepository.save(user);
                } else {
                    throw new FacebookLoginException("Cannot verify user id");
                }
                break;
            case GOOGLE:
            default:
                throw new AuthorizationMethodNotImplemented("Not implemented yet");
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity<UserSocialDTO> getUserSocial(User user) {
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return new ResponseEntity<>(
                new UserSocialDTO(
                        user.getFbId() == null ? null : String.valueOf(user.getFbId()),
                        null,
                        null
                ),
                HttpStatus.OK
        );
    }

    public User getUser(UserIdDTO userId) {
        if (Objects.isNull(userId.getId()) || userId.getId() < 0) {
            if (Objects.isNull(userId.getLogin()) || userId.getLogin().isEmpty()) {
                return Security.currentUser();
            } else {
                return userRepository.findOneByLogin(userId.getLogin());
            }
        } else {
            return userRepository.findOne(userId.getId());
        }
    }

}
