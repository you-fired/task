package basic;

public class OrderManager {
    private KiaMaker maker;

    public OrderManager (){
        this.maker = new KiaMaker();
    }

    public void order(int cost){
        Money money = new Money(cost);
        Car car = maker.sell(money);
        System.out.println("판매상(인수) : " + car.getName());
    }
}
