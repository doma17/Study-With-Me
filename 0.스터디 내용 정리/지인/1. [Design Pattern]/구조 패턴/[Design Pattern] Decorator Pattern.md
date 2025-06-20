# 데코레이터 패턴 (Decorator Pattern)

객체에 추가 기능을 동적으로 부여할 수 있도록 하는 패턴입니다.

기존 클래스를 수정하지 않고도 기능을 확장하거나 변경할 때 유용합니다.

데코레이터 패턴의 주요 목적은 다음과 같습니다.

- **기능 확장**: 런타임에 객체에 새로운 책임을 추가
- **개방/폐쇄 원칙 준수**: 기존 코드를 변경하지 않고 기능을 확장
- **조합을 통한 유연성**: 여러 데코레이터를 조합하여 다양한 기능 구성

<br/>

# 구현 구조

```tsx
// 1) Component 인터페이스: 공통 메서드 정의
interface Component {
  operation(): string;
}

// 2) ConcreteComponent: 기본 구현 클래스
class ConcreteComponent implements Component {
  public operation(): string {
    return "ConcreteComponent";
  }
}

// 3) Decorator 추상 클래스: Component를 래핑하고 Component 인터페이스 구현
abstract class Decorator implements Component {
  protected component: Component;

  constructor(component: Component) {
    this.component = component;
  }

  public operation(): string {
    return this.component.operation();
  }
}

// 4) ConcreteDecoratorA: 추가 책임을 구현
class ConcreteDecoratorA extends Decorator {
  public operation(): string {
    const base = super.operation();
    return `DecoratorA(${base})`;
  }
}

// 5) ConcreteDecoratorB: 또 다른 추가 책임 구현
class ConcreteDecoratorB extends Decorator {
  public operation(): string {
    const base = super.operation();
    return `DecoratorB(${base})`;
  }
}

// 클라이언트 코드 예시
function clientCode(component: Component) {
  console.log(component.operation());
}

// 사용 예시
const simple = new ConcreteComponent();
console.log("단일 구성: ", simple.operation());

const decoratedA = new ConcreteDecoratorA(simple);
console.log("데코레이터 A 적용: ", decoratedA.operation());

const decoratedAB = new ConcreteDecoratorB(decoratedA);
console.log("데코레이터 A + B 적용: ", decoratedB.operation());
```

## 핵심 개념: 조합(composition)

- 데이코레이터는 상속 대신 조합을 사용하여 기능을 확장
- 원래 객체(ConcreteComponent)를 감싸고, 필요한 시점에 추가 로직을 호출
- 런타임에 여러 데코레이터를 중첩하여 유연한 기능 구성 가능

## 언제, 왜 사용하나?

- 기능을 여러 조합으로 확장해야 할 때
- 서브클래스 수를 줄이고 기능 확장의 유연성 확보
- 기존 클래스를 수정하지 못하거나 수정하기 어려운 경우

## 장점

- 개방/폐쇄 원칙 준수: 기존 코드를 변경하지 않고 기능을 추가
- 조합의 유연성: 여러 데코레이터를 중첩하여 원하는 기능 구성
- 서브클래스 폭발 방지: 다양한 기능 조합을 위한 서브클래스 생성을 줄임

## 단점 및 주의사항

1. 복잡한 구조

   데코레이터와 컴포넌트가 많아지면 설계 복잡도 증가

2. 디버깅 어려움

   중첩된 데코레이터 흐름을 따라가기가 번거로움

3. 인터페이스 일관성

   데코레이터끼리 호한 가능한지, 같은 인터페이스를 만족하는지 주의 필요

<br/>

> **결론**:
>
> 데코레이터 패턴은 동적 기능 확장과 OCP 준수를 동시에 만족시키는 유용한 패턴입니다.
>
> 다만 중첩 구조로 인한 복잡성을 관리할 수 있을 때 적용하는 것이 좋습니다.
