package com.ufscar.queimadas;

import com.ufscar.queimadas.model.User;
import com.ufscar.queimadas.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=validate"
})
@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    public void setup() {

    }

    @Test
    public void firstTry() {
        userRepository.save(new User(
                "Zaphod Beeblebrox",
                "zaphod@galaxy.net"));
        //assertThat(userRepository.findByName("Zaphod Beeblebrox")).isNotNull();
    }
}
