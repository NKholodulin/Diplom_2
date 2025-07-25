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

    public static UserData userWithoutPassword(String email, String name) {
        UserData data = new UserData();
        data.email = email;
        data.name = name;
        return data;
    }
}
