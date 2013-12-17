package cucumber.examples.spring.txn;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.pablog.pills.domain.Product;
import org.pablog.pills.domain.User;
import org.pablog.pills.repositories.ProductRepository;
import org.pablog.pills.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@WebAppConfiguration
@ContextConfiguration("classpath:cucumber.xml")
public class SeeMessagesStepdefs {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MockMvc mockMvc;

    private User user;

    private ResultActions resultActions;

    @Given("^there is a User$")
    public void there_is_a_User() {
    	user = new User();
        user.setUsername("testuser");
        user.setProducts(new ArrayList<Product>());
        userRepository.save(user);
    }

    @Given("^the User has added the following products:$")
    public void the_User_has_added_the_following_products(List<Product> products) {
    	for(Product p : products) {
    		user.getProducts().add(p);
    	}
    	userRepository.save(user);
    }

    @When("^I visit the page for the User$")
    public void I_visit_the_page_for_the_User() throws Exception {
        resultActions = mockMvc
                .perform(get("/users/" + user.getId()))
                .andExpect(status().isOk());
    }

    @Then("^I should see \"([^\"]*)\"$")
    public void I_should_see(String name) throws Exception {
        resultActions.andExpect(content().string(containsString(name)));
    }

}
