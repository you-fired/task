package basic;

public class HyundaiMaker {
    public Car sell(Money money){
        System.out.println("현대 자동차(입금) : " + money.getAmount());
        Car car = new Car("아이오닉 5");
        return car;
    }
}
