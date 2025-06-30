# SOLID
객체지향 설계에서 권장되는 다섯 가지 원칙의 머리글자를 딴 용어. 이 원칙들은 유지보수성, 확장성, 재사용성을 높이는데 목적이 있다.

| 글자 | 원칙 이름                                         |
| -- | --------------------------------------------- |
| S  | 단일 책임 원칙 (Single Responsibility Principle)    |
| O  | 개방-폐쇄 원칙 (Open/Closed Principle)              |
| L  | 리스코프 치환 원칙 (Liskov Substitution Principle)    |
| I  | 인터페이스 분리 원칙 (Interface Segregation Principle) |
| D  | 의존 역전 원칙 (Dependency Inversion Principle)     |

## 1. 단일 책임 원칙 (SRP)
클래스는 하나의 책임만 가져야 한다는 원칙.
- 하나의 클래스가 하나의 역할만 하도록 만들어 변경의 이유를 하나로 제한
- 코드 변경 시 영향 범위 최소화, 높은 응집도 유지

### 나쁜 예
```java
class Report {
    void generate() {}
    void saveToFile() {}
}
```

### 좋은 예
```java
class ReportGenerator {
    void generate() {}
}

class ReportSaver {
    void saveToFile(Report report) {}
}
```

## 2. 개방-폐쇄 원칙 (OCP)
소프트웨어 요소는 확장에는 열려 있어야 하고 변경에는 닫혀 있어야 한다는 원칙
- 기존 코드를 변경하지 않고 새로운 기능을 추가할 수 있도록 설계
- 추상화와 다형성을 통해 구현

### 나쁜 예
```java
class DiscountService {
    int calculateDiscount(String type) {
        if (type.equals("VIP")) return 20;
        else if (type.equals("Normal")) return 5;
        else return 0;
    }
}
```

### 좋은 예
```java
interface DiscountPolicy {
    int getDiscount();
}

class GoldDiscount implements DiscountPolicy {
    public int getDiscount() { return 20; }
}

class SilverDiscount implements DiscountPolicy {
    public int getDiscount() { return 5; }
}

class DiscountService {
    public int calculate(DiscountPolicy policy) {
        return policy.getDiscount();
    }
}

```

## 3. 리스코프 치환 원칙 (LSP)
자식 클래스는 언제나 부모 클래스를 대치할 수 있어야 한다는 원칙
- 상속을 사용할 때 동작이 예기치 않게 깨지는 것을 방지

### 나쁜 예
```java
class Bird {
    void fly() { System.out.println("날기"); }
}

class Ostrich extends Bird {
    void fly() { throw new UnsupportedOperationException("못 날아요"); }
}
```

```Bird```는 ```fly()```를 이용할 수 있어야 하는데 ```Ostrich```는 예외를 발생 시킨다.
이럴땐 상속 보다는 인터페이스 분리나 구성을 고려하자.

## 4. 인터페이스 분리 원칙 (ISP)
클라이언트는 사용하지 않는 메서드에 의존하지 않아야 한다는 원칙
 - 너무 많은 기능이 한 인터페이스에 몰리는 것을 방지

### 나쁜 예
```java
interface Worker {
    void work();
    void eat();
}

class Robot implements Worker {
    public void work() {}
    public void eat() {}
}
```

### 좋은 예
```java
interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

class Robot implements Workable {
    public void work() {}
}
```

## 5. 의존 역전 원칙 (DIP)
고수준 모듈은 저수준 모듈에 의존하면 안되며, 둘 다 추상화에 의존해야 한다.
 - 유연한 구조를 위해 구현이 아닌 인터페이스에 의존

### 나쁜 예
```java
class Keyboard {}
class Computer {
    private Keyboard keyboard = new Keyboard();
}
```
KeyBoard 클래스를 Computer 클래스에서 직접 생성하고 있다. 이는 구현에 직접적으로 의존하고 있는 형태다.

### 좋은 예
```java
interface InputDevice {}
class Keyboard implements InputDevice {}

class Computer {
    private final InputDevice input;

    public Computer(InputDevice input) {
        this.input = input;
    }
}
```
생성자를 이용해 외부에서 주입을 받고 있다.