# 노출 모듈 패턴 (Revealing Module Pattern)

자바스크립트는 접근 제어자가 존재하지 않고 전역 범위에서 스크립트가 실행되기 때문에 노출 모듈 패턴을 통해 접근 제어자를 구현합니다.

즉시 실행 함수와 클로저를 활용해 모듈 내부 상태를 은닉하고, 필요한 API만 명시적으로 노출하는 패턴입니다.

주로 전역 네임스페이스 오염을 막고, 정보 은닉을 구현하기 위해 사용합니다.

노출 모듈 패턴의 주요 목적은 다음과 같습니다.

- **전역 변수 충돌 방지**: 전역 공간에 불필요한 식별자가 남지 않도록 함
- **정보 은닉**: 외부에 노출해서는 안 될 상태와 로직을 숨김
- **명시적 API 제공**: 모듈 사용자에게 필요한 기능만 공개하여 오용 방지

<br/>

# 구현 구조

```tsx
// 1) CounterModuleType 인터페이스: 모듈이 제공할 메서드 정의
interface CounterModuleType {
  increment(): void;
  reset(): void;
  getCount(): number;
}

// 2) CounterModule 구현: 즉시 실행 함수로 스코프 생성
const CounterModule: CounterModuleType = (function () {
  // private 상태 및 내부 함수
  let count = 0;

  function increment(): void {
    count++;
    console.log(`현재 값: ${count}`);
  }

  function reset(): void {
    count = 0;
    console.log("리셋 완료");
  }

  // public API 노출: 필요한 메서드만 반환
  return {
    increment,
    reset,
    getCount: (): number => count,
  };
})();

// 클라이언트 코드 예시
function clientCode(module: CounterModuleType): void {
  module.increment();
  console.log("현재 count:", module.getCount());
  module.reset();
  console.log("리셋 후 count:", module.getCount());
}

// 사용 예시
console.log("CounterModule 테스트");
clientCode(CounterModule);
```

## 핵심 개념: 정보 은닉(Encapsulation) & 모듈화

- 클로저로 생성한 private 스코프에 상태와 내부 함수를 감춤
- 외부에는 명시적 API 객체만 반환하여 필요한 기능만 제공
- 전역 네임스페이스 오염 없이 모듈 단위로 코드를 관리

## 언제, 왜 사용하나?

- ES6(자바스크립트 표준 사양) 모듈 미지원 구형 환경에서 간편한 모듈화가 필요할 때
- 전역 변수 충돌 위험을 피하고 싶을 때
- 외부에 노출할 기능과 숨겨야 할 데이터를 명확히 분리하고 싶을 때
- 간단한 라이브러리, 위젯, 유틸리티 모듈을 빠르게 배포할 때

## 장점

- 정보 은닉: private 멤버가 외부에 절대 노출되지 않음
- 전역 오염 방지: 전역 네임스페이스를 하나의 모듈 객체로 제한
- 간단 구현: 빌드 도구 없이 바로 사용 가능

## 단점 및 주의사항

1. 단일 인스턴스 고정

   즉시 실행 함수 실행 시점에 모듈 초기화, 재생성 불가

2. 테스트 제약

   private 상태에 직접 접근할 수 없어 단위 테스트 어려움

3. 의존성 관리 미흡

   ES 모듈처럼 의존성 선언 체계를 제공하지 않음

<br/>

> **결론**:
>
> 노출 모듈 패턴은 ES5 환경에서 가볍게 모듈화와 은닉을 구현할 수 있는 기법입니다.
>
> 현대에는 ES6 모듈 시스템을 사용하되, 간단한 바로 가기 형태의 즉시 실행 함수 모듈이 필요할 때 유용합니다.
