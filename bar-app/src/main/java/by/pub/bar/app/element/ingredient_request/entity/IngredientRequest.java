package by.pub.bar.app.element.ingredient_request.entity;

import by.pub.bar.app.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Document(collection = "ingredient_requests")
public class IngredientRequest {
    @Id
    private String requestId;
    private String ingredientName;
    private Long ingredientAmount;
    @Transient
    private Status status = Status.NOT_ACCEPTED;
}
