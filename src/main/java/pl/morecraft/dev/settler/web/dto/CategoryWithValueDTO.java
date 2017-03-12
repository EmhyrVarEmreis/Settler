package pl.morecraft.dev.settler.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryWithValueDTO {

    private Long userId;
    private CategoryDTO category;
    private Double value;

}
