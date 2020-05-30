package by.pub.bar.app.ingredient.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Document(collection = "ingredients")
public class Ingredient {

    @Id
    private String id;
    private String name;
    private Long amount;

    public static Ingredient of(Ingredient ingredient) {
        return new Ingredient().setAmount(ingredient.getAmount()).setName(ingredient.getName())
                .setId(ingredient.getId());
    }
}
