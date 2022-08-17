
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mangapunch.mangareaderbackend.models.User;

@Service
public interface UserService {
    List<User> getAllUsers();
    void addUser(User User);
    Optional<User> getUserByid(Long UserId);
    void deleteUser(Long UserId);
}
