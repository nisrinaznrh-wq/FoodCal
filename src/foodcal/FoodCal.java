
package foodcal;

import config.SpringConfig;
import controller.FoodCalController;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class FoodCal {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(
                SpringConfig.class
            );

        FoodCalController controller =
            context.getBean(FoodCalController.class);

        controller.jalankanAplikasi();
    }
}
