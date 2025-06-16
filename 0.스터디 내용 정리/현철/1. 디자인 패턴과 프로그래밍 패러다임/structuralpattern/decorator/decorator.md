# 데코레이터 패턴이란?
데코레이터(Decorator) 패턴은 기존 객체에 새로운 기능을 동적으로 추가할 수 있도록 해주는 디자인 패턴이다.
이 패턴은 상속보다 ```조합(Composition)```을 활용하여 유연하게 기능을 확장할 수 있게 해주며, OCP(Open/Closed Principle, 개방-폐쇄 원칙)를 따른다.

# 문제 상황
자동차를 구매한다고 해보자. 기본 차량에 네비게이션, 디지털 라이트 등 여러 기능을 선택적으로 추가할 수 있다.

```java
public abstract class Car {
    String description = "자동차";

    public String getDescription() {
        return description;
    }

    public abstract double cost();
}
```
Car는 기본 자동차 클래스로 모든 차량이 공통적으로 가져야 할 메서드를 정의한다. ```getDescription()```은 차량의 설명, ```cost()```는 가격을 반환한다.
```java
public class Genesis extends Car{

    public Genesis() {
        description = "제네시스";
    }
    @Override
    public double cost() {
        return 9445.5;
    }
}

public class MercedesBenz extends Car {
    public MercedesBenz() {
        description = "벤츠";
    }

    @Override
    public double cost() {
        return 15950.5;
    }
}
```
```Genesis```와 ```MercedesBenz``` 는 구체적인 차량 클래스다. 각각 기본 설명과 가격을 반환한다. 이제 여기에 옵션을 추가해보자.
```java
public abstract class FuncDecorator extends Car {
    Car car;
    public abstract String getDescription();
}
```
```FuncDecorator``` 는 모든 기능 추가 클래스가 상속받을 추상 클래스다. 내부에 Car 타입을 가지고 있고 이를 통해 기존 차량을 감싼다.
```java
public class DigitalLight extends FuncDecorator{

    public DigitalLight(Car car) {
        this.car = car;
    }

    @Override
    public double cost() {
        return car.cost() + 150.3;
    }

    @Override
    public String getDescription() {
        return car.getDescription() + ", 디지털 라이트";
    }
}

public class Nav extends FuncDecorator{

    public Nav(Car car) {
        this.car = car;
    }

    @Override
    public double cost() {
        return car.cost() + 100.0;
    }

    @Override
    public String getDescription() {
        return car.getDescription() + ", 네비게이션";
    }
}
```
```DigitalLight```와 ```Nav```는 각각 디지털 라이트 기능과 네비게이션을 추가하는 데코레이터다. 내부적으로 감싼 객체의 기능을 호출하고 자신의 기능을 덧붙인다.
```java
public class Main {
    public static void main(String[] args) {
        Car car = new Genesis();
        System.out.println(car.getDescription() + " $" + car.cost());

        car = new DigitalLight(car);
        car = new Nav(car);
        System.out.println(car.getDescription() + " $" + car.cost());
        car = new Nav(car);
        System.out.println(car.getDescription() + " $" + car.cost());

        Car car1 = new MercedesBenz();
        System.out.println(car1.getDescription() + " $" + car1.cost());
        car1 = new DigitalLight(car1);

        System.out.println(car1.getDescription() + " $" + car1.cost());
    }
}
```
```text
출력 결과

제네시스 $9445.5
제네시스, 디지털 라이트, 네비게이션 $9695.8
제네시스, 디지털 라이트, 네비게이션, 네비게이션 $9795.8
벤츠 $15950.5
벤츠, 디지털 라이트 $16100.8
```
처음에는 제네시스만 출력되지만 이후 데코레이터로 덧붙이며 기능이 누적된다. ```car = new Nav(car);``` 처럼 중복된 기능 추가도 가능하며, 실행 결과는 누적된 옵션 기준으로 계산된다. 중복이 불가능하다면 추가적인 처리가 필요하다.

# 어댑터패턴과 데코레이터 패턴의 차이점
| 항목        | 데코레이터 패턴             | 어댑터 패턴                    |
| --------- | -------------------- | ------------------------- |
| **목적**    | 기존 객체에 **기능을 확장**    | 기존 객체의 **인터페이스 변환**       |
| **역할**    | 객체를 감싸서 추가 기능을 붙임    | 객체를 감싸서 **다른 인터페이스로 호환**  |
| **기반 구조** | 같은 인터페이스를 구현         | 기존 인터페이스 → 클라이언트가 요구하는 형태 |
| **예시**    | 자동차에 옵션 기능 추가        | 외부 API 포맷을 우리 시스템에 맞게 변환  |
| **관계**    | is-a 관계 (같은 타입으로 확장) | 변환 관계 (타입을 맞춰줌)           |

데코레이터는 확장, 어댑터는 호환에 중점을 둔다.

# 마무리
데코레이터 패턴은 기능 조합이 자주 필요한 시스템에 매우 적합한 패턴이다.
특히 옵션 구성, UI 컴포넌트 조합, 필터 체인 등에서 자주 사용된다.