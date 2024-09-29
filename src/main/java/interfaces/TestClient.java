package interfaces;

public class TestClient {
    public static void main(String[] args) {
        OrderManager manager = new OrderManager();
        manager.setMaker(new HyundaiMaker());
        manager.order(20000);

    }
}
