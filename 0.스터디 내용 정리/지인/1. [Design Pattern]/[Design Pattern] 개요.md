# 디자인 패턴이란?

> 프로그램 설계 시 발생하는 **반복적인 문제들을 어떻게 해결할 것인가**에 대해 규약 형태로 만들어 놓은 것

일종의 설계 기법이자, 특정 구현이 아닌 아이디어입니다.

부품이 어떻게 조립되어 있고, 각각의 부품이 어떻게 관련해서 큰 기능을 발휘하는지 표현합니다.

프로그램을 완성품으로 보지 않고 “부품”으로 나눔으로써 여러 장점을 가집니다.

<br/>

## 디자인 패턴의 장점

1. 재사용성: 반복적인 문제에 대한 일반적인 해결책을 제공하므로, 이를 재사용하여 유사한 상황에서 코드를 더 쉽게 작성할 수 있습니다.
2. 가독성: 일정한 구조로 정리하고 명확하게 작성하여 개발자가 코드를 이해하고 유지보수하기 쉽게 만듭니다.
3. 유지보수성: 코드를 쉽게 모듈화할 수 있으며, 변경이 필요한 경우 해당 모듈만 수정하여 유지보수가 쉬워집니다.
4. 확장성: 새로운 기능을 추가하거나 변경할 떄 기존 코드를 변경하지 않고도 새로운 기능을 통합할 수 있습니다.
5. 안정성과 신뢰성: 수많은 사람들이 인정한 모범사례로 검증된 솔루션을 제공합니다.

<br/>

## 디자인 패턴과 객체지향

모든 디자인 패턴이 객체지향적인 것은 아니나, 많은 디자인 패턴이 객체지향 패러다임을 기반으로 설계되었습니다.

특히 **GoF(Gang of Four)** 의 디자인 패턴은 객체지향 설계 원칙, 특히 SOLID 원칙과 객체지향의 4대 특성(캡슐화, 상속, 추상화, 다형성)을 적극 활용합니다.

### 객체지향 설계 원칙(SOLID)

**S** : Single Responsibilty Principle, 단일 책임 원칙

하나의 클래스는 하나의 책임만 가져야 한다.

- 예제 코드

  ```tsx
  // ❌ 위반 예시 - UserManager가 너무 많은 책임을 가짐
  class UserManager {
    createUser() {
      /* 사용자 생성 */
    }
    validateEmail() {
      /* 이메일 유효성 검사 */
    }
    sendWelcomeEmail() {
      /* 환영 이메일 전송 */
    }
  }
  ```

  ```tsx
  // ✅ 개선 예시
  class UserCreator {
    createUser() {
      /* 사용자 생성 */
    }
  }

  class EmailValidator {
    validateEmail() {
      /* 이메일 유효성 검사 */
    }
  }

  class EmailService {
    sendWelcomeEmail() {
      /* 이메일 전송 */
    }
  }
  ```

**O** : Open/Closed Principle, 개방/폐쇄 원칙

기존 코드를 변경하지 않고도 기능을 확장할 수 있어야 한다.

- 예제 코드

  ```tsx
  // ❌ 위반 예시 - 새로운 할인 유형 추가 시마다 if 문 수정 → 폐쇄 위반
  class DiscountCalculator {
    calculate(price: number, type: string) {
      if (type === "BlackFriday") return price * 0.7;
      if (type === "NewYear") return price * 0.8;
      return price;
    }
  }
  ```

  ```tsx
  // ✅ 개선 예시 - 전략 객체를 주입받아 동작 → 새로운 할인 정책을 추가만 하면 됨
  interface DiscountStrategy {
    calculate(price: number): number;
  }

  class BlackFridayDiscount implements DiscountStrategy {
    calculate(price: number) {
      return price * 0.7;
    }
  }

  class NewYearDiscount implements DiscountStrategy {
    calculate(price: number) {
      return price * 0.8;
    }
  }

  class DiscountCalculator {
    constructor(private strategy: DiscountStrategy) {}

    calculate(price: number) {
      return this.strategy.calculate(price);
    }
  }
  ```

**L** : Liskov Substitution Principle, 리스코프 치환 원칙

자식 클래스는 부모 클래스의 기능을 대체할 수 있어야 한다.

- 예제 코드

  ```tsx
  // ❌ 위반 예시 - Bird를 기대하고 Ostrich를 넣었는데 에러 발생
  class Bird {
    fly() {} // "Bird는 날 수 있다"는 계약
  }

  // 상속받은 Ostrich는 계약을 지켜야 하나 오히려 예외를 던져버림
  class Ostrich extends Bird {
    fly() {
      throw new Error("Ostriches can't fly!");
    }
  }
  ```

  ```tsx
  // ✅ 개선 예시 - 날 수 있는 새와 날 수 없는 새를 분리 → 안전한 치환 가능
  interface Flyable {
    fly(): void;
  }

  class Sparrow implements Flyable {
    fly() {
      /* 날기 구현 */
    }
  }

  class Ostrich {
    walk() {
      /* 걷기 구현 */
    }
  }
  ```

**I** : Interface Segregation Principle, 인터페이스 분리 원칙

클라이언트는 자신이 사용하지 않는 메서드에 의존하지 않아야 한다.

- 예제 코드

  ```tsx
  // ❌ 위반 예시
  interface Machine {
    print(): void;
    scan(): void;
    fax(): void;
  }

  class SimplePrinter implements Machine {
    print() {}
    scan() {
      throw new Error("Not supported");
    }
    fax() {
      throw new Error("Not supported");
    }
  }
  ```

  ```tsx
  // ✅ 개선 예시 - 기능별 인터페이스로 분리 → 불필요한 의존 제거
  interface Printer {
    print(): void;
  }

  interface Scanner {
    scan(): void;
  }

  class SimplePrinter implements Printer {
    print() {}
  }
  ```

**D** : Dependency Inversion Principle, 의존성 역전 원칙

고수준 모듈은 저수준 모듈에 의존하면 안 되며, 둘 다 추상화에 의존해야 한다.

- 예제 코드

  ```tsx
  // ❌ 위반 예시 - UserService가 MySQLDatabase에 강하게 결합
  class MySQLDatabase {
    save(data: string) {
      /* 저장 */
    }
  }

  class UserService {
    private db = new MySQLDatabase();
    saveUser(data: string) {
      this.db.save(data);
    }
  }
  ```

  ```tsx
  // ✅ 개선 예시 - 추상화(Database 인터페이스)를 통해 느슨한 결합 → 교체 용이
  interface Database {
    // 추상화
    save(data: string): void;
  }

  class MySQLDatabase implements Database {
    save(data: string) {
      /* 저장 */
    }
  }

  class UserService {
    constructor(private db: Database) {} // 추상화에 의존

    saveUser(data: string) {
      this.db.save(data);
    }
  }
  ```

<br/>

### 객체지향의 4대 특성

**캡슐화** : 데이터와 행위를 하나의 단위로 묶고, 외부에는 필요한 부분만 공개하여 내부 구현을 숨기는 것

- 외부에서 직접 속성에 접근하지 않고, 메서드를 통해 조작
- 객체의 무결성 보장 및 유지보수성 향상

**상속** : 기존 클래스를 재사용해 새로운 클래스를 정의하는 방식

- 코드 재사용
- 기능 확장을 위한 기반 제공

**추상화 :** 불필요한 세부 구현은 숨기고, 핵심 개념만 드러내는 것

- 객체의 본질적 특징만 외부에 노출
- 인터페이스 또는 추상 클래스를 통해 공통된 동작 규약만 제공

**다형성** : 동일한 인터페이스를 통해 서로 다른 방식으로 동작할 수 있도록 하는 것

- 하나의 인터페이스로 다양한 구현을 다룰 수 있음
- 대표적으로 오버라이딩, 오버로딩, 인터페이스 다형성

<br/>

# GoF 디자인 패턴

> **G**ang **o**f **F**our - 객체지향 설계를 체계화한 가장 대표적인 디자인 패턴 모음

※ 생성/구조/행위 분류는 GoF 패턴을 정의하기 위해 사용된 분류 기준이자, 이후 모든 디자인 패턴 논의에서 공통적으로 활용되는 분류 체계입니다.

## 생성 패턴

객체 생성 과정을 캡슐화하거나 유연하게 처리하는 것이 목적입니다.

- **Singleton**: 하나의 인스턴스만 존재
- **Factory Method**: 객체 생성을 서브클래스에 위임
- **Builder**: 복잡한 객체 생성 분리
- **Prototype**: 복사(clone)로 객체 생성
- **Abstract Factory**: 관련 객체들을 팩토리로 생성

<br/>

## 구조 패턴

클래스/객체를 결합해 더 큰 구조를 생성하는 것이 목적입니다.

- **Decorator**: 기능을 동적으로 확장
- **Proxy**: 대리 객체를 통한 접근 제어
- **Adapter**: 인터페이스 호환성 해결
- **Composite**: 트리 구조 표현
- **Facade**: 복잡한 서브시스템을 단순화

<br/>

## 행위 패턴

객체 간의 상호작용과 책임 분배를 최적화하는 것이 목적입니다.

- **Observer**: 상태 변경을 구독자에게 알림
- **Strategy**: 알고리즘을 캡슐화하여 교체 가능
- **Command**: 요청을 객체로 캡슐화(실행·취소 지원)
- **State**: 객체 상태에 따라 동작을 변경
- **Iterator**: 내부 표현을 노출하지 않고 순회
- **Mediator**: 중재자를 통해 객체 간 통신 관리
- **Template Method**: 알고리즘 구조를 정의하고 일부 단계만 서브클래스에 위임
- **Visitor**: 객체 구조에 새로운 연산을 추가
