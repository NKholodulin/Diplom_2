package Data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserData {
    private String email;
    private String password;
    private String name;

    public UserData() {
    }
}
