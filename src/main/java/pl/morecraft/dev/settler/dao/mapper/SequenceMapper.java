package pl.morecraft.dev.settler.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceMapper {

    Long getSequenceByName(
            @Param("sequenceName") String sequenceName
    );

    void incrementSequence(
            @Param("sequenceName") String sequenceName,
            @Param("incrementValue") Long incrementValue
    );

    void initSequence(
            @Param("sequenceName") String sequenceName,
            @Param("initialValue") Long initialValue
    );

}
