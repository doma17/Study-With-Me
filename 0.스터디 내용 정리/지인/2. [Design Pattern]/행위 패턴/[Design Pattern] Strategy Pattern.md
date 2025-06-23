# 전략(Strategy) 패턴

> 동일한 계열의 알고리즘들을 개별적으로 캡슐화하여 상호 교환할 수 있게 정의하는 패턴

알고리즘 군(群)을 객체화하여, 서로 교체 가능하게 만드는 패턴입니다. 클라이언트는 구체적인 알고리즘 구현을 몰라도 되고, 런타임에 전략을 바꿔가며 동작을 변경할 수 있습니다.

여기서 ‘전략’이란 일종의 알고리즘이 될 수도 있으며, 기능이나 동작이 될 수도 있는 특정한 목표를 수행하기 위한 행동 계획을 말합니다.

<br/>

## 전략 패턴은 OOP의 집합체

GoF의 디자인 패턴 책에서는 전략 패턴을 다음과 같이 정의합니다.

1. 동일 계열의 알고리즘군을 정의하고
2. 각각의 알고리즘을 캡슐화하여
3. 이들을 상호 교환이 가능하도록 만든다.
4. 알고리즘을 사용하는 클라이언트과 상관없이 독립적으로
5. 알고리즘을 다양하게 변경할 수 있게 한다.

이를 다음과 같이 빗대어 설명하면 더욱 이해하기 쉽습니다.

1. 동일 계열의 알고리즘군을 정의하고 → 전략 구현체로 정의
2. 각각의 알고리즘을 캡슐화하여 → 인터페이스로 추상화
3. 이들을 상호 교환이 가능하도록 만든다. → 합성(composition)으로 구성
4. 알고리즘을 사용하는 클라이언트과 상관없이 독립적으로 → 컨텍스트 객체 수정 없이
5. 알고리즘을 다양하게 변경할 수 있게 한다. → 메소드를 통해 전략 객체를 실시간으로 변경함으로써 전략을 변경

<br/>

## 구성 요소

- **Strategy 인터페이스**: 알고리즘이 지켜야 할 메서드 시그니처를 선언
- **ConcreteStrategy**: Strategy 인터페이스를 구현한 실제 알고리즘 클래스들
- **Context**: Startegy 참조를 보관하고, 클라이언트 요청을 전략에 위임

<br/>

## 예제로 구현하기

```tsx
// Payment(결제) 전략
// 1) Strategy 인터페이스
class PaymentStrategy {
  pay(amount) {
    throw new Error("구현 필요");
  }
}

// 2) ConcreteStrategies
class CreditCardStrategy extends PaymentStrategy {
  constructor(cardNumber) {
    super();
    this.cardNumber = cardNumber;
  }
  pay(amount) {
    console.log(`신용카드(${this.cardNumber})로 ${amount}원 결제`);
  }
}

class PaypalStrategy extends PaymentStrategy {
  constructor(email) {
    super();
    this.email = email;
  }
  pay(amount) {
    console.log(`Paypal(${this.email})로 ${amount}원 결제`);
  }
}

// 3) Context 역할: 쇼핑 카트
class ShoppingCart {
  constructor() {
    this.items = [];
    this.strategy = null;
  }

  addItem(item) {
    this.items.push(item);
  }
  setStrategy(strategy) {
    this.strategy = strategy;
  }

  checkout() {
    const total = this.items.reduce((sum, i) => sum + i.price, 0);
    if (!this.strategy) throw new Error("결제 전략 미설정");
    this.strategy.pay(total);
  }
}

// 4) 사용 예시
const cart = new ShoppingCart();
cart.addItem({ name: "Book", price: 15000 });
cart.addItem({ name: "Pen", price: 2000 });

// 신용카드로 결제
cart.setStrategy(new CreditCardStrategy("1234-5678-9012-3456"));
cart.checkout();
// 출력: 신용카드(1234-5678-9012-3456)로 17000원 결제

// PayPal로 결제 방식 변경
cart.setStrategy(new PaypalStrategy("user@example.com"));
cart.checkout();
// 출력: PayPal(user@example.com)로 17000원 결제
```

<br/>

## 핵심 장점

1. **알고리즘 캡슐화로 OCP 준수:** 결제 로직 등 전략을 `Strategy` 인터페이스 구현체로 분리해 두면, 새로운 알고리즘을 추가할 때 컨텍스트 코드를 전혀 수정하지 않아도 됩니다.
2. **유연한 런타임 교체:** `setStrategy()` 같은 메서드 호출만으로 실행 중에 알고리즘을 바꿀 수 있어, 상황에 따라 동적으로 동작 방식을 전환할 수 있습니다.
3. **테스트·유지보수 용이성:** 각 전략을 독립된 클래스로 분리했기 때문에, 단위 테스트(Mock 전략) 작성이 쉽고, 개별 알고리즘을 독립적으로 유지·보수할 수 있습니다.

<br/>

<br/>

> 참고
>
> - https://inpa.tistory.com/entry/GOF-%F0%9F%92%A0-%EC%A0%84%EB%9E%B5Strategy-%ED%8C%A8%ED%84%B4-%EC%A0%9C%EB%8C%80%EB%A1%9C-%EB%B0%B0%EC%9B%8C%EB%B3%B4%EC%9E%90
