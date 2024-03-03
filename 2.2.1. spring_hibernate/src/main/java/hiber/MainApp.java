package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import java.sql.SQLException;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        User user1 = new User("User1", "Lastname1", "user1@mail.ru");
        User user2 = new User("User2", "Lastname2", "user2@mail.ru");
        User user3 = new User("User3", "Lastname3", "user3@mail.ru");
        User user4 = new User("User4", "Lastname4", "user4@mail.ru");

        Car car1 = new Car("Car1", 1);
        Car car2 = new Car("Car2", 2);
        Car car3 = new Car("Car3", 3);
        Car car4 = new Car("Car4", 4);

        userService.add(user1.setCar(car1).setUser(user1));
        userService.add(user2.setCar(car2).setUser(user2));
        userService.add(user3.setCar(car3).setUser(user3));
        userService.add(user4.setCar(car4).setUser(user4));

        String[] cars = {"Car1", "Car2", "Car3", "Car4"};
        int[] userIds = {1, 2, 3, 4};
        for (int i = 0; i < cars.length; i++) {
            System.out.println(userService.getUserByCar(cars[i], userIds[i]));
        }


        for (User user : userService.listUsers()) {
            System.out.println(user + " " + user.getCar());
        }

        try {
            User notFoundUser = userService.getUserByCar("Car5", 5);
        } catch (NoResultException e) {
            System.out.println("User not found");
        }

        context.close();
    }
}
