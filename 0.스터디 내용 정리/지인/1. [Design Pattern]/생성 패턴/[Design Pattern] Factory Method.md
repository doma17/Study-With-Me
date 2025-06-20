# 팩토리 메소드 (Factory Method)

객체 생성 로직을 서브클래스에 위임하여, 클라이언트 코드가 구체 클래스에 의존하지 않고도 다양한 객체를 생성할 수 있게 하는 디자인 패턴입니다.

팩토리 메서드 패턴의 주요 목적은 다음과 같습니다.

- **생성 책임 분리**: 객체 생성 코드를 캡슐화하여 변경에 유연하게 대응
- **개방/폐쇄 원칙 준수**: 새로운 제품 클래스를 추가할 때 기존 코드를 수정하지 않고 확장
- **클라이언트-구체 클래스 분리**: 클라이언트가 구체 클래스에 직접 의존하지 않음

<br/>

# 구현 구조

```tsx
// 1) Product 인터페이스: 생성될 객체가 준수할 계약
interface Product {
	operation: string;
}

// 2) ConcreteProduct: Product 인터페이스를 구현한 구체 클래스
class ConcreteProductA implements Product {
	publice operation(): string {
		return 'Result of ConcreProductA';
	}
}

// 3) Creator 추상 클래스: factoryMethod를 선언
abstract class Creator {
	// 서브클래스에서 구체 제품 생성 책임을 구현
	// 팩토리메소드를 선언만 하고 구체 생성 로직은 서브클래스에 위임
	public abstract factoryMethod(): Product;

	// factoryMethod로 생성된 객체를 사용해 작업을 수행
	public someOperation(): string {
		const product: this.factoryMethod();
		return `Creator: 작업 결과 -> ${product.operation}`;
	}
}

// 4) ConcreteCreator: factoryMethod를 오버라이드하여 구체 제품 생성
class ConcreteCreatorA extends Creator {
	public factoryMethod(): Product {
		return new ConcreteProductA();
	}
}

// 클라이언트 코드: Creator 타입에만 의존
function clientCode(creator: Creator) {
	console.log(creator.someOperation());
}

// 사용 예시
clientCode(new ConcreteCreatorA());
```

## 언제, 왜 사용하나?

- **생성 로직 분리**: 클라이언트 코드에서 객체 생성 방식을 제거
- **확장성 확보**: 새로운 제품 추가 시 ConcreteCreator만 추가하면 됨
- **개방-폐쇄 원칙 실현**: 기존 코드 수정 없이 기능 확장

## 장점

- **개방/폐쇄 원칙 준수**: 새로운 제품 추가 시 기존 Creator/클라이언트 코드 수정 불필요
- **생성 책임 캡슐화**: 객체 생성 코드를 일관성 있게 관리
- **클라이언트-구체 클래스 분리**: 클라이언트는 Creator 인터페이스만 알면 됨

## 단점 및 주의사항

1. 클래스 수 증가

   Creator와 ConcreteCreator가 늘어나 설계 복잡도 상승

2. 사용 난이도

   단순 객체 생성에는 오히려 코드가 과도하게 복잡해질 수 있음

3. 캡슐화 경계 주의

   팩토리 메서드가 너무 많은 책임을 가지지 않도록 설계해야 함

<br/>

> **결론**:
>
> 팩토리 메서드 패턴은 생성 로직의 유연성을 극대화하고 OCP를 확실히 지키는 데 유용합니다.
>
> 다만 단순한 상황에서는 오히려 복잡도를 높일 수 있으므로, 확장 가능성이 분명할 때 적절히 적용해야 합니다.
