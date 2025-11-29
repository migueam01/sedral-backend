package com.uce.sedral;

import com.uce.sedral.models.entities.Responsable;
import com.uce.sedral.repositories.IResponsableRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SedralApplicationTests {

    @Autowired
    private IResponsableRepo repo;

    @Autowired
    private BCryptPasswordEncoder bcrypt;

    @Test
    void crearUsuario() {
        Responsable responsable = new Responsable();
        responsable.setNombre("Tatiana");
        responsable.setApellido("Moscoso");
        responsable.setUsername("taty");
        responsable.setPassword(bcrypt.encode("1234"));
        responsable.setTelefono("0999999999");
        responsable.setCorreo("miguemoscoso21@gmail.com");
        responsable.setHabilitado(true);

        Responsable guardado = repo.save(responsable);
        assertTrue(guardado.getPassword().equalsIgnoreCase(responsable.getPassword()));
    }

}
