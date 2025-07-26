package Data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class IngredientData {

    private List<String> ingredients;

    public IngredientData() {
    }
}
