package pl.morecraft.dev.settler.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.ResultHandler;
import pl.morecraft.dev.settler.web.dto.QueryContextDTO;
import pl.morecraft.dev.settler.web.misc.TransactionListFilters;

import java.util.List;

public interface TransactionMapper {

    Long countTransactions(
            @Param("context") QueryContextDTO context,
            @Param("filters") TransactionListFilters filters
    );

    List<Long> getTransactionsPaged(
            @Param("context") QueryContextDTO context,
            @Param("offset") Integer offset,
            @Param("limit") Integer limit,
            @Param("sort") String sort,
            @Param("filters") TransactionListFilters filters
    );

    void getTransactions(
            @Param("context") QueryContextDTO context,
            @Param("sort") String sort,
            @Param("filters") TransactionListFilters filters,
            ResultHandler<Long> callback
    );

}
