# SOLID 원칙

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2Fk3UNK%2FbtsEqxEBTdU%2FAAAAAAAAAAAAAAAAAAAAAHFqiiMLju0imo--AuLBfFGn-feL27BwtpaAbaLChQKe%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1751295599%26allow_ip%3D%26allow_referer%3D%26signature%3DmuMJr7aZByZxy6kGjVr34jRNVCE%253D)

> 개념 : SOLID는 객체 지향 설계의 5가지 기본 원칙으로, 유지보수성과 확장성, 가독성이 높은 시스템을 만들기 위한 가이드라인입니다.

---

## 1. 단일 책임 원칙 (Single Responsibility Principle, SRP)
- **정의**: 클래스는 단 하나의 책임만 가져야 하며, 변경 이유 또한 한 가지여야 한다.
- **효과**:
  - 모듈화 강화
  - 변경 영향 범위 최소화
  - 테스트 및 유지보수 용이

```java
// SRP 예시
class Journal {
    private final List<String> entries = new ArrayList<>();
    public void addEntry(String text) { entries.add(text); }
    public void removeEntry(int index) { entries.remove(index); }
    @Override
    public String toString() { return String.join(System.lineSeparator(), entries); }
}

class Persistence {
    public void saveToFile(Journal journal, String filename, boolean overwrite) throws IOException {
        if (overwrite || new File(filename).exists()) {
            try (PrintWriter out = new PrintWriter(filename)) {
                out.println(journal.toString());
            }
        }
    }
}
```

1. **Journal 클래스**
 - 오로지 “저널(일기) 항목을 관리”하는 역할만 합니다.
 - entries라는 리스트를 내부에 두고 addEntry(), removeEntry()로 항목을 추가·삭제
 - 따라서 “저널 데이터의 CRUD(생성·읽기·삭제)만 변경 사유”로 삼을 수 있습니다. 
2. **Persistence 클래스**
 - 오직 “저널 객체를 파일로 저장”하는 역할만 합니다.
 - saveToFile() 메서드는 덮어쓰기(overwrite) 여부와 파일 존재 체크
 파일 출력 스트림을 열고 Journal.toString() 결과를 씁니다.
 - 저널 항목 관리(데이터 구조)나 문자열 포매팅과 같은 책임을 전혀 가지지 않습니다.
 - 따라서 “저장 방식(I/O 로직)이 바뀔 때만 이 클래스만 수정”하면 됩니다.

---

## 2. 개방-폐쇄 원칙 (Open-Closed Principle, OCP)

- **정의** : 소프트웨어 요소(클래스, 모듈 등)는 확장에 대해 열려 있어야 하고(open for extension), 수정에 대해 닫혀 있어야 한다(closed for modification).
- **효과** :
  - 기능 추가 시 기존 코드 변경 없이 확장 가능
  - 예측 가능한 변경 관리

```java
// OCP 예시
interface Shape {
    double area();
}

class Rectangle implements Shape {
    private double width, height;
    public Rectangle(double width, double height) { this.width = width; this.height = height; }
    public double area() { return width * height; }
}

class Circle implements Shape {
    private double radius;
    public Circle(double radius) { this.radius = radius; }
    public double area() { return Math.PI * radius * radius; }
}

class AreaCalculator {
    public double totalArea(List<Shape> shapes) {
        double total = 0;
        for (Shape s : shapes) {
            total += s.area();
        }
        return total;
    }
}
```

---

## 3. 리스코프 치환 원칙 (Liskov Substitution Principle, LSP)
- **정의** : 서브타입은 언제나 기반 타입의 규약을 위반하지 않아야 하며, 기반 타입의 객체를 서브타입 객체로 대체해도 프로그램이 정상 동작해야 한다.

- **효과** :
  - 상속 구조의 안정성 보장
  - 다형성 활용 시 예측 가능한 동작

```java
// LSP 예시
class Bird {
    public void fly() {
        System.out.println("날다!");
    }
}

class Sparrow extends Bird {
    @Override
    public void fly() {
        System.out.println("참새가 날다!");
    }
}

class Ostrich extends Bird {
    // Ostrich는 날 수 없으므로 LSP 위반이 발생할 수 있음.
    // LSP를 지키려면 Bird 계층을 'Flyable' 인터페이스 등으로 분리하는 것이 좋음.
}

// 사용 예:
void letBirdFly(Bird bird) {
    bird.fly(); // Ostrich가 들어오면 논리적 오류 발생 가능
}
```

- 위 경우에 Flyable 인터페이스를 선언해 Sparrow 클래스만 구현
- Ostrich 클래스는 추가적으로 Movable 인터페이스를 구현

---

## 4. 인터페이스 분리 원칙 (Interface Segregation Principle, ISP)
- **정의**: 클라이언트는 자신이 사용하지 않는 메서드에 의존하도록 강제되어서는 안 된다. 하나의 범용 인터페이스보다 여러 개의 특화된 인터페이스가 낫다.
**효과**:
  - 경량 인터페이스 설계
  - 불필요한 의존성 감소
  - 유연한 모듈 구성

```java
// ISP 예시
interface Printer {
    void print(Document d);
}

interface Scanner {
    void scan(Document d);
}

class MultiFunctionPrinter implements Printer, Scanner {
    public void print(Document d) { /* 인쇄 구현 */ }
    public void scan(Document d) { /* 스캔 구현 */ }
}

class SimplePrinter implements Printer {
    public void print(Document d) { /* 인쇄 구현 */ }
}
```

---

## 5. 의존 역전 원칙 (Dependency Inversion Principle, DIP)
- **정의**: 고수준 모듈은 저수준 모듈에 의존해서는 안 되며, 둘 다 추상화에 의존해야 한다. 또한, 추상화는 세부 사항에 의존해서는 안 된다.
- **효과**:
  - 모듈 간 결합도 감소
  - 테스트 용이성 및 확장성 향상
  - 유연한 아키텍처 구현

```java
// DIP 예시
interface Keyboard { }
interface Monitor { }

class StandardKeyboard implements Keyboard { }
class LEDMonitor implements Monitor { }

class Computer {
    private final Keyboard keyboard;
    private final Monitor monitor;
    public Computer(Keyboard keyboard, Monitor monitor) {
        this.keyboard = keyboard;
        this.monitor = monitor;
    }
}
// 사용 예: Computer 객체 생성 및 의존성 주입
public class Main {
    public static void main(String[] args) {
        Keyboard keyboard = new StandardKeyboard();
        Monitor monitor  = new LEDMonitor();
        Computer computer = new Computer(keyboard, monitor);
        System.out.println("Computer initialized with: "
            + keyboard.getClass().getSimpleName() + " and "
            + monitor.getClass().getSimpleName());
    }
}
``` 
- Computer는 구체적인 구현 대신 인터페이스(추상화)에 의존


Reference
- https://cyyyummy.tistory.com/33