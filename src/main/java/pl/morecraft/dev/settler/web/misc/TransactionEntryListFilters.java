package pl.morecraft.dev.settler.web.misc;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.LocalDate;
import pl.morecraft.dev.settler.service.abstractService.annotation.BaseFilter;
import pl.morecraft.dev.settler.service.abstractService.singleFilters.ExtendedJodaDateAndTimeSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.singleFilters.standard.StringNumberPathSingleFilter;
import pl.morecraft.dev.settler.service.abstractService.singleFilters.standard.StringStringPathSingleFilter;
import pl.morecraft.dev.settler.web.utils.JsonJodaLocalDateDeserializer;
import pl.morecraft.dev.settler.web.utils.JsonJodaLocalDateSerializer;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntryListFilters {

    @BaseFilter(StringNumberPathSingleFilter.class)
    private Long id;

    @BaseFilter(StringStringPathSingleFilter.class)
    private String reference;

    @BaseFilter(StringStringPathSingleFilter.class)
    private String creator;

    @BaseFilter(StringStringPathSingleFilter.class)
    private String owners;

    @BaseFilter(StringStringPathSingleFilter.class)
    private String contractors;

    @BaseFilter(StringNumberPathSingleFilter.class)
    private String value;

    @BaseFilter(StringStringPathSingleFilter.class)
    private String description;

    @JsonSerialize(using = JsonJodaLocalDateSerializer.class)
    @JsonDeserialize(using = JsonJodaLocalDateDeserializer.class)
    @BaseFilter(ExtendedJodaDateAndTimeSingleFilter.class)
    private LocalDate evaluated;

    @BaseFilter(StringNumberPathSingleFilter.class)
    private String comments;

    @BaseFilter(StringStringPathSingleFilter.class)
    private String categories;

}
