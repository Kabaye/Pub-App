package by.pub.bar.app.element.product.entity;

import by.pub.bar.app.element.ingredient.entity.Ingredient;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document(collection = "products")
public class Product {

    @Id
    private String name;
    private List<Ingredient> usedIngredients;
    private Double price = 0D;
}
