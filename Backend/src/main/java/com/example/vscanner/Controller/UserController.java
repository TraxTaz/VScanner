package com.example.vscanner.Controller;


import com.example.vscanner.Business.Exceptions.UserExistsException;
import com.example.vscanner.Business.UserInterface.*;
import com.example.vscanner.Domain.User;
import com.example.vscanner.Domain.UserRR.CreateUserRequest;
import com.example.vscanner.Domain.UserRR.CreateUserResponse;
import com.example.vscanner.Domain.UserRR.UpdateUserRequest;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
@AllArgsConstructor
public class UserController {
    private CreateUserUseCase createUserUseCase;
    private GetUsersUseCase getUsersUseCase;
    private GetUserByUseCase getUserUseCase;
    private DeleteUserUseCase deleteUserUseCase;
    private UpdateUserUseCase updateUserUseCase;
    private CheckUserNameUseCase checkUserNameUseCase;
    private CheckEmailExistsUseCase checkEmailExistsUseCase;

    @PostMapping("/create")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request){
        CreateUserResponse response = createUserUseCase.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @RolesAllowed({"Admin", "Manager"})
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(getUsersUseCase.getUsers());
    }

    @GetMapping("/{id}")
    @RolesAllowed({"User", "Admin", "Manager", "Shop_Owner"})
    public ResponseEntity<User> getUser(@PathVariable(value = "id") Long id){
        Optional<User> userOptional = getUserUseCase.getUserByID(id);
        if (userOptional.isEmpty()){
            throw new UserExistsException("User not found");
        }
        return ResponseEntity.ok().body(userOptional.get());
    }

    @DeleteMapping("/delete/{id}")
    @RolesAllowed({"User", "Admin", "Manager"})
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Long id){
        deleteUserUseCase.deleteUserByID(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable(value = "id") Long id, @RequestBody UpdateUserRequest request){
        request.setId(id);
        updateUserUseCase.updateUser(request);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check/username/{username}")
    public ResponseEntity<Boolean> checkUsernameExists(@PathVariable(value = "username") String username){
        return ResponseEntity.ok().body(checkUserNameUseCase.checkUsernameExists(username));
    }

    @GetMapping("/check/email/{email}")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable(value = "email") String email){
        return ResponseEntity.ok().body(checkEmailExistsUseCase.checkEmailExists(email));
    }
}
