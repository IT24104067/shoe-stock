package com.example.shoestock;

import com.example.shoestock.model.ShoeType;
import com.example.shoestock.repo.ShoeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MongoDbIntegrationTest {

    @Autowired
    private ShoeRepository shoeRepository;

    @Test
    public void testMongoDbConnectivity() {
        // Create a new shoe
        ShoeType shoe = new ShoeType();
        shoe.setName("Test Shoe");
        shoe.setBrand("Test Brand");
        shoeRepository.save(shoe);

        // Retrieve all shoes
        List<ShoeType> shoes = shoeRepository.findAll();
        assertFalse(shoes.isEmpty());
        assertEquals(1, shoes.size());
        assertEquals("Test Shoe", shoes.get(0).getName());
    }
}
