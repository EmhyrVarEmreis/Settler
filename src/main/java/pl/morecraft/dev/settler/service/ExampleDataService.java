package pl.morecraft.dev.settler.service;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.morecraft.dev.settler.dao.repository.*;
import pl.morecraft.dev.settler.domain.*;
import pl.morecraft.dev.settler.domain.dictionaries.OperationType;
import pl.morecraft.dev.settler.domain.dictionaries.TransactionType;
import pl.morecraft.dev.settler.domain.dictionaries.UserStatus;

import java.util.Arrays;
import java.util.Collections;

@Service
public class ExampleDataService {

    private static Logger log = LoggerFactory.getLogger(ExampleDataService.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final SequenceManager sequenceManager;
    private final TransactionService transactionService;
    private final CategoryRepository categoryRepository;
    private final PrivilegeRepository privilegeRepository;
    private final TransactionRepository transactionRepository;
    private final RoleAssignmentRepository roleAssignmentRepository;


    @Autowired
    public ExampleDataService(
            RoleRepository roleRepository,
            UserRepository userRepository,
            SequenceManager sequenceManager,
            TransactionService transactionService,
            CategoryRepository categoryRepository,
            PrivilegeRepository privilegeRepository,
            TransactionRepository transactionRepository,
            RoleAssignmentRepository roleAssignmentRepository
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.sequenceManager = sequenceManager;
        this.transactionService = transactionService;
        this.categoryRepository = categoryRepository;
        this.privilegeRepository = privilegeRepository;
        this.transactionRepository = transactionRepository;
        this.roleAssignmentRepository = roleAssignmentRepository;
    }

    @Transactional
    public void createExampleData() {
        Role r1 = Role.builder().name("Administrator").build();
        r1 = roleRepository.save(r1);
        Role r2 = Role.builder().name("Moderator").build();
        r2 = roleRepository.save(r2);
        Role r3 = Role.builder().name("User").build();
        r3 = roleRepository.save(r3);

        privilegeRepository.save(Privilege.builder().prvOwner(r1).operationType(OperationType.ADM).build());
        privilegeRepository.save(Privilege.builder().prvOwner(r1).operationType(OperationType.EDT).build());
        privilegeRepository.save(Privilege.builder().prvOwner(r1).operationType(OperationType.RDM).build());
        privilegeRepository.save(Privilege.builder().prvOwner(r1).operationType(OperationType.CRT).build());
        privilegeRepository.save(Privilege.builder().prvOwner(r2).operationType(OperationType.EDT).build());
        privilegeRepository.save(Privilege.builder().prvOwner(r2).operationType(OperationType.RDM).build());
        privilegeRepository.save(Privilege.builder().prvOwner(r2).operationType(OperationType.CRT).build());
        privilegeRepository.save(Privilege.builder().prvOwner(r3).operationType(OperationType.RDM).build());

        User admin1 = User.builder()
                .login("admin1")
                .email("mateusz.stefaniak@morecraft.pl")
                .firstName("John")
                .lastName("Snow")
                .status(UserStatus.ACT)
                .password("password")
                .created(LocalDateTime.now())
                .build();
        admin1 = userRepository.save(admin1);

        User admin2 = User.builder()
                .login("admin2")
                .email("codemarket@morecraft.pl")
                .firstName("John")
                .lastName("Smith")
                .status(UserStatus.ACT)
                .password("password")
                .created(LocalDateTime.now())
                .build();
        admin2 = userRepository.save(admin2);

        User user1 = User.builder()
                .login("user1")
                .email("mateusz.stefaniak@medicover.pl")
                .firstName("Adam")
                .lastName("Snow")
                .status(UserStatus.ACT)
                .password("password")
                .created(LocalDateTime.now())
                .build();
        user1 = userRepository.save(user1);

        User user2 = User.builder()
                .login("user2")
                .email("stefanm1@ee.pw.edu.pl")
                .firstName("Adam")
                .lastName("Smith")
                .status(UserStatus.ACT)
                .password("password")
                .created(LocalDateTime.now())
                .build();
        user2 = userRepository.save(user2);

        User user3 = User.builder()
                .login("user3")
                .email("mateusz.stefaniak@primaris.eu")
                .firstName("John")
                .lastName("John")
                .status(UserStatus.ACT)
                .password("password")
                .created(LocalDateTime.now())
                .build();
        user3 = userRepository.save(user3);

        roleAssignmentRepository.save(RoleAssignment.builder().role(r1).user(admin1).build());
        roleAssignmentRepository.save(RoleAssignment.builder().role(r1).user(admin2).build());
        roleAssignmentRepository.save(RoleAssignment.builder().role(r2).user(user1).build());

        Category c1 = Category.builder()
                .code("CAA")
                .description("CA1D")
                .build();
        c1 = categoryRepository.save(c1);

        Category c2 = Category.builder()
                .code("CAB")
                .description("CA2D")
                .build();
        c2 = categoryRepository.save(c2);

        Transaction t1 = Transaction.builder()
                .creator(admin1)
                .owners(Arrays.asList(
                        Redistribution.builder().percentage(50.00).id(Redistribution.RedistributionId.builder().user(admin1).build()).build(),
                        Redistribution.builder().percentage(50.00).id(Redistribution.RedistributionId.builder().user(admin2).build()).build()
                ))
                .contractors(Arrays.asList(
                        Redistribution.builder().percentage(75.00).id(Redistribution.RedistributionId.builder().user(user1).build()).build(),
                        Redistribution.builder().percentage(25.00).id(Redistribution.RedistributionId.builder().user(user2).build()).build()
                ))
                .categories(Collections.singletonList(c1))
                .type(TransactionType.NOR)
                .value(100.00)
                .build();
        transactionService.getSaveSavePreProcessingFunction().apply(t1);
        t1 = transactionRepository.save(t1);

        Transaction t2 = Transaction.builder()
                .creator(admin1)
                .owners(Collections.singletonList(
                        Redistribution.builder().percentage(100.00).id(Redistribution.RedistributionId.builder().user(admin1).build()).build()
                ))
                .contractors(Arrays.asList(
                        Redistribution.builder().percentage(50.00).id(Redistribution.RedistributionId.builder().user(user1).build()).build(),
                        Redistribution.builder().percentage(25.00).id(Redistribution.RedistributionId.builder().user(user2).build()).build(),
                        Redistribution.builder().percentage(25.00).id(Redistribution.RedistributionId.builder().user(admin2).build()).build()
                ))
                .categories(Collections.singletonList(c1))
                .type(TransactionType.NOR)
                .value(100.00)
                .build();
        transactionService.getSaveSavePreProcessingFunction().apply(t2);
        t2 = transactionRepository.save(t2);

        Transaction t3 = Transaction.builder()
                .creator(user1)
                .owners(Collections.singletonList(
                        Redistribution.builder().percentage(100.00).id(Redistribution.RedistributionId.builder().user(user1).build()).build()
                ))
                .contractors(Arrays.asList(
                        Redistribution.builder().percentage(50.00).id(Redistribution.RedistributionId.builder().user(user1).build()).build(),
                        Redistribution.builder().percentage(50.00).id(Redistribution.RedistributionId.builder().user(user2).build()).build()
                ))
                .categories(Collections.singletonList(c2))
                .type(TransactionType.NOR)
                .value(100.00)
                .build();
        transactionService.getSaveSavePreProcessingFunction().apply(t3);
        t3 = transactionRepository.save(t3);

        Transaction t4 = Transaction.builder()
                .creator(user2)
                .owners(Collections.singletonList(
                        Redistribution.builder().percentage(100.00).id(Redistribution.RedistributionId.builder().user(user2).build()).build()
                ))
                .contractors(Collections.singletonList(
                        Redistribution.builder().percentage(100.00).id(Redistribution.RedistributionId.builder().user(user1).build()).build()
                ))
                .categories(Collections.singletonList(c2))
                .type(TransactionType.NOR)
                .value(100.00)
                .build();
        transactionService.getSaveSavePreProcessingFunction().apply(t4);
        t4 = transactionRepository.save(t4);

        privilegeRepository.save(Privilege.builder().prvOwner(user3).prvObject(user2).operationType(OperationType.RDM).build());
    }

}
