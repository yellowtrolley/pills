package cucumber.examples.spring.txn;

import java.util.ArrayList;
import java.util.List;

import org.pablog.pills.domain.Product;
import org.pablog.pills.domain.User;
import org.pablog.pills.repositories.ProductRepository;
import org.pablog.pills.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import cucumber.api.java.en.Given;

@WebAppConfiguration
@ContextConfiguration("classpath:cucumber.xml")
public class UserStepdefs {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    private User user;

    public void thereIsAuser() {
        user = new User();
        user.setUsername("testuser");
        user.setProducts(new ArrayList<Product>());
        userRepository.save(user);
    }

    @Given("^a User has posted the following messages:$")
    public void a_User_has_posted_the_following_messages(List<Product> products) throws Throwable {
        thereIsAuser();
        for (Product p : products) {
            user.getProducts().add(p);
        }
        userRepository.save(user);
    }
}
