package com.coco.modules.user.application.port;

import com.coco.modules.user.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Optional;


public interface UserRepositoryPort {

    // Guardar un nuevo usuario o actualizar uno existente
    User save(User user);

    // Obtener un usuario por su ID
    Optional<User> findById(Long id);

    // Obtener un usuario por su Email
    Optional<User> findByEmail(@Email @NotBlank String email);

    // Eliminar un usuario por su ID
    void deleteById(Long id);
}
