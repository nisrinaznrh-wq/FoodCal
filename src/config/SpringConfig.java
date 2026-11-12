
package config;

import controller.FoodCalController;
import model.FoodCalModel;
import model.UserDAO;
import model.MakananDAO;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public UserDAO userDAO() {
        return new UserDAO();
    }

    @Bean
    public MakananDAO makananDAO() {
        return new MakananDAO();
    }

    @Bean
    public FoodCalModel foodCalModel(
            UserDAO userDAO,
            MakananDAO makananDAO) {

        return new FoodCalModel(userDAO, makananDAO);
    }

    @Bean
    public FoodCalController foodCalController(
            FoodCalModel model) {

        return new FoodCalController(model);
    }
}
