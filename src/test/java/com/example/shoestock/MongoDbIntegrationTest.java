package com.example.shoestock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.example.shoestock.model.ShoeType;
import com.example.shoestock.repo.ShoeRepository;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MongoDbIntegrationTest {

    @Autowired
    private ShoeRepository shoeRepository;

    @Test
    public void testMongoDbConnectivity() {
        // Clear any existing shoes first
        shoeRepository.deleteAll();
        
        // Create a new shoe
        ShoeType shoe = new ShoeType();
        shoeRepository.save(shoe);

        // Retrieve all shoes
        List<ShoeType> shoes = shoeRepository.findAll();
        assertFalse(shoes.isEmpty());
        assertEquals(1, shoes.size());
    }
}
