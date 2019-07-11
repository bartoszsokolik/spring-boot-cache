package pl.solutions.software.sokolik.bartosz.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.solutions.software.sokolik.bartosz.user.dto.UpdateUsernameDTO;
import pl.solutions.software.sokolik.bartosz.user.dto.UserDTO;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
class UserController {

    private final UserFacade userFacade;

    @Autowired
    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping("/{username}")
    ResponseEntity<UserDTO> findUser(@PathVariable String username) {
        return new ResponseEntity<>(userFacade.findByUsername(username), HttpStatus.OK);
    }

    @PutMapping
    ResponseEntity<Void> changeUsername(@RequestBody UpdateUsernameDTO dto) {
        UserDTO updatedUser = userFacade.changeUsername(dto.getUsername(), dto.getNewUsername());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(updatedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{username}")
    ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userFacade.deleteUser(username);
        return ResponseEntity.ok().build();
    }
}
