package xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestClient {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("car-config.xml");

        OrderManager manager = context.getBean("manager",OrderManager.class);
        manager.order(30000);
//        OrderManager manager = new OrderManager();
//        manager.setMaker(new HyundaiMaker());
//        manager.order(20000);

    }
}
