package pl.solutions.software.sokolik.bartosz.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.solutions.software.sokolik.bartosz.user.dto.UserDTO;
import pl.solutions.software.sokolik.bartosz.user.dto.UserNotFoundException;

@Service
@Transactional
public class UserFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserFacade.class);

    private final UserRepository userRepository;

    @Autowired
    public UserFacade(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable(value = "userCache", key = "#username")
    public UserDTO findByUsername(String username) {
        LOGGER.info("Fetching user from database");
        return new UserDTO(findUserByUsername(username).getUsername());
    }

    @CachePut(value = "userCache", key = "#newUsername")
    public UserDTO changeUsername(String username, String newUsername) {
        User updatedUser = userRepository.save(new User(findUserByUsername(username).getId(), newUsername));
        return new UserDTO(updatedUser.getId(), updatedUser.getUsername());
    }

    @CacheEvict(value = "userCache", key = "#username")
    public void deleteUser(String username) {
        User user = findUserByUsername(username);
        userRepository.deleteById(user.getId());
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with username %s not found", username)));
    }
}
