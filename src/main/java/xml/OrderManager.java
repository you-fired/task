package xml;

public class OrderManager {
    private CarMaker maker;

    public OrderManager() {}
    public OrderManager (CarMaker maker) {
        this.maker = maker;
    }

    public void order(int cost){
        Money money = new Money(cost);
        Car car = maker.sell(money);
        System.out.println("판매상(인수) : " + car.getName());
    }

    public void setMaker(CarMaker maker) {
        this.maker = maker;
    }
}
