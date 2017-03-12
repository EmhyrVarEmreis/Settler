package pl.morecraft.dev.settler.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeDTO {

    private long usersCount;
    private long transactionsCount;
    private double totalValue;

}
