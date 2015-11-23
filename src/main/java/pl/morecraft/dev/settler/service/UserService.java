package pl.morecraft.dev.settler.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.morecraft.dev.settler.dao.repository.UserRepository;
import pl.morecraft.dev.settler.domain.QUser;
import pl.morecraft.dev.settler.domain.User;
import pl.morecraft.dev.settler.web.dto.UserDTO;
import pl.morecraft.dev.settler.web.dto.UserListDTO;
import pl.morecraft.dev.settler.web.misc.ListPage;
import pl.morecraft.dev.settler.web.misc.ListPageConverter;
import pl.morecraft.dev.settler.web.misc.UserListFilters;

import javax.inject.Inject;
import java.io.IOException;

@Service
@Transactional
public class UserService {

    @Inject
    private UserRepository userRepository;

    public UserDTO get(Long userId) {
        ModelMapper mapper = new ModelMapper();
        User user = userRepository.findOne(userId);
        return mapper.map(user, UserDTO.class);
    }

    public ListPage<UserListDTO> getUsers(Integer page, Integer limit, String sortBy, String filters) {
        QUser user = QUser.user;
        Page<User> userPage = userRepository.findAll(applyFilters(filters, user), new QPageRequest(page - 1, limit, applySorting(sortBy, user)));
        return new ListPageConverter<User, UserListDTO>().convert(userPage, UserListDTO.class);
    }

    private OrderSpecifier<?> applySorting(String sortBy, QUser user) {
        boolean isDesc = sortBy.startsWith("-");
        switch (sortBy.substring(1)) {
            case "username":
                return isDesc ? user.login.desc() : user.login.asc();
            case "firstName":
                return isDesc ? user.firstName.desc() : user.firstName.asc();
            case "lastName":
                return isDesc ? user.lastName.desc() : user.lastName.asc();
            case "email":
                return isDesc ? user.email.desc() : user.email.asc();
            default:
                return isDesc ? user.login.desc() : user.login.asc();
        }
    }

    private BooleanExpression applyFilters(String filtersJson, QUser user) {
        BooleanExpression predicate = user.id.eq(user.id);
        if (filtersJson.length() == 0)
            return predicate;

        UserListFilters filters;

        try {
            filters = new ObjectMapper().readValue(filtersJson, UserListFilters.class);

            if (filters.getLogin() != null) {
                predicate = predicate.and(user.login.contains(filters.getLogin()));
            }
            if (filters.getName() != null) {
                if (filters.getName().contains(" ")) {
                    String[] strings = filters.getName().split("\\s+");
                    predicate = predicate.and(
                            user.firstName.contains(strings[0]).and(
                                    user.lastName.contains(strings[1]))
                    );

                } else {
                    predicate = predicate.and(
                            user.firstName.contains(filters.getName()).or(
                                    user.lastName.contains(filters.getName()))
                    );
                }
            }
            if (filters.getEmail() != null) {
                predicate = predicate.and(user.email.contains(filters.getEmail()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return predicate;
    }

}
