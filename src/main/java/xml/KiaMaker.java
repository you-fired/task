package xml;

public class KiaMaker implements CarMaker {
    public Car sell(Money money){
        System.out.println("기아 자동차(입금) : " + money.getAmount());
        Car car = new Car("K5");
        return car;
    }
}
