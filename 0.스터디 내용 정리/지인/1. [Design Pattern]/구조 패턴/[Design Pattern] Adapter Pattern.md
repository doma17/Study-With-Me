# 어댑터 패턴 (Adapter Pattern)

호환되지 않는 인터페이스를 가진 클래스(Adaptee)를 클라이언트가 기대하는 인터페이스(Target)로 변환해 주는 패턴입니다.

어댑터 패턴의 주요 목적은 다음과 같습니다.

- **인터페이스 호환**: 기존 클래스의 인터페이스를 변경하지 않고 클라이언트와 연결
- **개방/폐쇄 원칙 준수**: 기존 코드를 변경하지 않고 기능을 확장
- **코드 재사용**: 레거시(오래된 시스템이나 과거에 작성된 코드) 또는 서브파티 코드(라이브러리나 모듈)를 수정 없이 활용

<br/>

# 구현 구조

```tsx
// 1) Target 인터페이스: 클라이언트가 기대하는 메서드 시그니처
// 메서드 시그니처란 메서드(또는 함수)를 식별하는 이름(name) + 매개변수(parameter) 정보 조합
interface Target {
  request(): string;
}

// 2) Adaptee 클래스: 호환되지 않는 기존 클래스
class Adaptee {
  public specificRequest(): string {
    return "Adaptee: 특정 요청 처리";
  }
}

// 3) Adapter 클래스: Target 클래스를 구현하여 Adaptee를 래핑
// Target을 구현하면서 내부에 Adaptee 인스턴스를 보유
class Adapter implements Target {
  private adaptee: Adaptee;

  constructor(adaptee: Adaptee) {
    this.adaptee = adaptee;
  }

  public request(): string {
    // Target의 reqeust를 Adaptee의 specificRequest로 변환
    return `Adapter: (${this.adaptee.specificRequest()})`;
  }
}

// 클라이언트 코드 예시
function clientCode(target: Target) {
  console.log(target.request());
}

// 사용 예시
const adaptee = new Adaptee();
const adapter = new Adapter(adaptee);
clientCode(adapter);
```

## 핵심 개념: 인터페이스 변환(Interface Mapping)

- 조합을 사용해 Adaptee를 래핑
- Client ↔ Target 인터페이스와 Adaptee ↔ specificRequest 사이의 중계자 역할
- 런타임에 동적으로 변환 로직 주입 가능

## 언제, 왜 사용하나?

- 레거시 코드나 서드파티 라이브러리 인터페이스가 클라이언트 요구와 불일치할 때
- 시스템 통합 시 서로 다른 모듈 사이의 인터페이스 불일치를 해결해야 할 때
- 새로운 인터페이스를 도입하면서 기존 코드를 수정하지 않고 재사용해야 할 때

## 장점

- 코드 재사용: 기존 코드를 변경하지 않고 활용
- 개방/폐쇄 원칙 준수: 어댑터만 추가하면 되고, 기존 클래스는 불변
- 유연성: 여러 Adaptee에 대해 다양한 어댑터 구현 가능

## 단점 및 주의사항

1. 클래스 수 증가

   어댑터 클래스가 많아지면 유지보수 부담 증가

2. 성능 오버헤드

   호출이 어댑터를 거쳐 Adaptee로 전달되므로 약간의 비용 발생

3. 설계 복잡도

   인터페이스 매핑 로직이 복잡해지면 가독성과 디버깅이 어려울 수 있음

<br/>

> **결론**:
>
> 어댑터 패턴은 호환되지 않는 인터페이스 사이의 매끄러운 상호운용성을 제공하여 기존 코드를 안전하게 재사용할 수 있게 하지만, 어댑터 클래스 수와 복잡도를 고려해 적절히 적용해야 합니다.
