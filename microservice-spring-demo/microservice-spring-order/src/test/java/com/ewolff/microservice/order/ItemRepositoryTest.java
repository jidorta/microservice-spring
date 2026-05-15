package com.ewolff.microservice.order;

import com.ewolff.microservice.order.entity.Item;
import com.ewolff.microservice.order.item.ItemRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest(classes = CustomerTestDataGeneratorTest.class)
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void shouldFindByName(){
        List<Item> items = itemRepository.findByName("iPod");

        assertThat(items).isNotEmpty();
    }
}
