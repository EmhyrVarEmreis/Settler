@TypeDefs({
        @TypeDef(name = "localDateType",
                defaultForType = LocalDate.class,
                typeClass = org.jadira.usertype.dateandtime.joda.PersistentLocalDate.class),
        @TypeDef(name = "localDateTimeType",
                defaultForType = LocalDateTime.class,
                typeClass = org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime.class),
        @TypeDef(name = "localTimeType",
                defaultForType = LocalTime.class,
                typeClass = org.jadira.usertype.dateandtime.joda.PersistentLocalTime.class)
})
package pl.morecraft.dev.settler.domain.misc;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;