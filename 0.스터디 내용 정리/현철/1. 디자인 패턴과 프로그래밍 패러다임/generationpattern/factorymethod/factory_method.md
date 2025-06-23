# 팩토리 패턴이란?
객체 생성 로직을 별도의 클래스(팩토리)로 분리하여, 클라이언트 코드에서 객체 성생 과정을 감추는 패턴. 객체 생성 코드를 분리해서 유지보수성과 확장성을 높일 수 있다.

오리를 만드는 코드를 예시로 보자
```java
interface Duck {
    ...
}

class MallardDuck {
    ...
}

public static void main(String[] args) {
    Duck duck = new MallardDuck();   
}
```
위 코드는 인터페이스를 사용하여 코드를 유연하게 만들려고 한다. 그럼에도 구상 클래스의 인스턴스를 만들어야 한다.

일련의 구상클래스가 있다면 어쩔 수 없이 다음과 같은 코드를 만들어야 한다.
```java
Duck duck;

if (picnic) {
    duck = new MallardDuck();
} else if (hunting) {
    duck = new DecoyDuck();    
} else {
    duck = new RubberDuck();
}
```
구상 클래스의 인스턴스가 여러개 있으며 그 인스턴스의 형식은 실행 시에 주어진 조건에 따라 결정된다. 이런 코드를 변경하거나 확장해야 할 때는 코드를 다시 확인하고 새로운 코드를 추가하거나 기존 코드를 제거해야한다. 띠라서 오류가 생길 가능성도 커진다.

```java
Pizza orderPizza() {
    Pizza pizza = new Pizza();
    
    pizza.prepare();
    pizza.bake();
    pizza.cut();
    pizza.box();
    
    return pizza;
}
```
위와 같은 코드가 있을 때 다양한 피자를 고르고 그에 맞게 피자를 만드는 코드고 추가해야 한다.
```java
Pizza orderPizza(String type) {
    Pizza pizza;

    if (type.equals("cheese")) {
        pizza = new CheesePizza();
    } else if (type.equals("greek")) {
        pizza = new GreekPizza();
    } else if (type.equals("pepperoni")){
        pizza = new PepperoniPizza();
    }
    
    pizza.prepare();
    pizza.bake();
    pizza.cut();
    pizza.box();
    
    return pizza;
}
```
여기서 새로운 메뉴가 추가된다면 코드를 직접 고쳐야한다. 피자 종류가 바뀔 때 마다 코드를 계속 고쳐야 한다.

```java
    pizza.prepare();
    pizza.bake();
    pizza.cut();
    pizza.box();
```
하지만 위 부분은 바뀌지 않는다. 따라서 이 코드는 고칠 일이 거의 없다. 호출하는 피자 클래스의 메서드만 달라질 뿐이다.

여기서 가장 문제가 되는 부분은 인스턴스를 만드는 구상 클래스를 선택하는 부분이다. 이 부분 때문에 상황이 변하면 코드를 변경해야 한다.
변하는 부분과 변하지 않는 부분을 파악했으니 캡슐화를 해보자.
```java
public class SimplePizzaFactory {
    public Pizza createPizza(String type) {
        Pizza pizza = null;

        if (type.equals("cheese")) {
            pizza = new CheesePizza();
        } else if (type.equals("greek")) {
            pizza = new GreekPizza();
        } else if (type.equals("pepperoni")){
            pizza = new PepperoniPizza();
        }
        
        return pizza;
    }
}

public class PizzaStore {
    SimplePizzaFactory factory;
    
    public PizzaStore(SimplePizzaFactory factory) {
        this.factory =factory;
    }
    
    public Pizza orderPizza(String type) {
        Pizza pizza;
        
        pizza = factory.createPizza(type);

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }
}
```
orderPizza() 메서드는 팩토리로 피자 객체를 만든다. orderPizza() 에서 new 연산자 대신 팩토리 객체에 있는 create() 메서드를 사용했다. 더 이상 구상 클래스의 인스턴스를 만들 필요가 없어졌다.

# 다양한 팩토리 만들기
SimplePizzaFactory 대신 서로 다른 팩토리를 만들어서 PizzaStore에서 사용하면 괜찮을까? 만약 각 팩토리를 사용하는 코드에서 prepare() 나 cut() 메서드를 호출하는 것을 깜빡하게 된다면 혼란이 생길 것이다. 이 문제를 해결하려면 PizzaStore와 피자 제작 코드 전체를 하나로 묶어주는 프레임워크를 만들어야한다.

피자 만드는 일 자체는 전부 PizzaStore 에서 진행하면서도 각 팩토리에서 커스텀을 할수있게 만들어보자.
```java
public abstract class PizzaStore {
    public Pizza orderPizza(String type) {
        Pizza pizza;
        
        pizza = createPizza(type);

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }
    
    abstract Pizza createPizza(String type); // 팩토리 객체 대신 이 메서드를 사용한다. 팩토리 메서드가 이제 추상 메서드로 변경됐다.
}
```
## 서브클래스가 결정
각 팩토리마다 피자 스타일을 다르게 할 수 있다. PizzaStore 의 서브클래스에서 createPizza() 메서드를 구현해보자.
```java
public class NYPizzaStore extends PizzaStore {

    public Pizza createPizza(String type) {
        if (type.equals("cheese")) {
            return new NYStyleCheesePizza();
        } else {
            return null;
        }
    }
}
```
createPizza() 는 Pizza의 서브 클래스 중 어느 구상 클래스 객체의 인스턴스를 만들어서 리턴할지는 전적으로 PizzaStore의 서브클래스에 의해 결정된다.

이제 피자 클래스를 만들어보자

```java
import java.util.ArrayList;
import java.util.List;

public abstract class Pizza {
    String name;
    String dough;
    String sauce;
    List<String> toppings = new ArrayList<>();
    
    void prepare() {
        System.out.println("준비 중: " + name);
        System.out.println("도우를 돌리는 중...");
        System.out.println("소스를 뿌리는 중...");
        System.out.println("토핑을 올리는 중...");
        
        for (String topping : toppings) {
            System.out.println(" " + topping);
        }
    }
    
    void bake() {
        System.out.println("175도에서 25분간 굽기");
    }
    
    void cut() {
        System.out.println("피자를 사선으로 자르기");
    }
    
    void box() {
        System.out.println("상자에 피자 담기");
    }
    
    public String getName() {
        return name;
    }
}
```
이번엔 서브 클래스를 구상해보자.
```java
public class NYStyleCheesePizza extends Pizza {
    public NYStyleCheesePizza() {
        name = "뉴욕 스타일 소스와 치즈 피자";
        dough = "씬 크러스트 도우";
        sauce = "마리나라 소스";
        
        toppings.add("잘게 썬 레지아노 치즈");
    }
}
```

이제 테스트 해보자.
```java
public class PizzaTest {
    public static void main(String[] args) {
        PizzaStore nyStore = new NYPizzaStore();
        
        Pizza pizza = nyStore.orderPizza("cheese");
        System.out.println(pizza.getName());
    }
}
```
```text
준비 중: 뉴욕 스타일 소스와 치즈 피자
도우를 돌리는 중...
소스를 뿌리는 중...
토핑을 올리는 중...
 잘게 썬 레지아노 치즈
175도에서 25분간 굽기
피자를 사선으로 자르기
상자에 피자 담기
뉴욕 스타일 소스와 치즈 피자
```
모든 팩토리 패턴은 객체 생성을 캡슐화한다. 팩토리 메서드 패턴은 서브클래스에서 어떤 클래스를 만들지 결정함으로써 객체 생성을 캡슐화할 수 있는 패턴이다.