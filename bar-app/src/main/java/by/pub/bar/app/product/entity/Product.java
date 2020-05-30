package by.pub.bar.app.product.entity;

import by.pub.bar.app.ingredient.entity.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private List<Ingredient> usedIngredients;
    @Indexed(unique = true)
    private String name;
    private Double price = 0D;
}
