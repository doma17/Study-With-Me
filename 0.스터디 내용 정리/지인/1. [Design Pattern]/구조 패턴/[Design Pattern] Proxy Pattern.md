# 프록시 패턴 (Proxy Pattern)

실제 객체(Real Subject)에 대한 접근을 제어하거나 추가 기능을 삽입하기 위해 대리인(Proxy) 객체를 두는 패턴입니다.

클라이언트는 실제 객체 대신 프록시를 통해 요청을 보내며, 프록시는 요청 전후에 필요한 작업(권한 검사, 캐싱, 로깅 등)을 수행할 수 있습니다.

프록시 패턴의 주요 목적은 다음과 같습니다.

- **접근 제어**: 실제 객체에 접근 전에 권한 확인
- **지연 로딩(Lazy Initialization)**: 필요한 때만 실제 객체 생성
- **부가 기능 삽입**: 캐싱, 로깅, 트랜잭션 관리 등

<br/>

# 구현 구조

```tsx
// 1) Subject 인터페이스: RealSubject와 Proxy가 공유할 공통 인터페이스
// RealSubject와 Proxy가 모두 구현해야 하는 공통 계약(메서드 시그니처)을 정의
interface Subject {
  request(): string;
}

// 2) RealSubject: 실제 작업(비즈니스 로직)을 수행하는 클래스
class RealSubject implements Subject {
  public request(): string {
    return "RealSubject: 실제 작업 수행";
  }
}

// 3) Proxy 클래스 Subject 인터페이스를 구현하여 RealSubject 접근을 제어
class ProxySubject implements Subject {
  private realSubject: RealSubject | null = null;

  // 접근 전후로 추가 작업 처리
  public request(): string {
    // 권한 확인
    this.checkAccess();

    if (this.realSubject === null) {
      this.realSubject = new RealSubject(); // 지연 로딩 -> 불필요한 객체 생성 방지
    }
    const result = this.realSubject.reques();
    this.logAccess();

    return result;
  }

  private checkAccess(): void {
    console.log("Proxy: 접근 권한 확인");
    // 권한 검사 로직
  }
}

// 클라이언트 코드 예시
function clientCode(subject: Subject) {
  console.log(subject.request());
}

// 사용 예시
const proxy = new ProxySubject();
clientCode(proxy);
```

## 핵심 개념: 대리인(Proxy)의 역할

- **권한 체크(Protection Proxy)**: 사용자가 특정 리소스에 접근하기 전 권한을 검증
- **지연 초기화(Virtual Proxy)**: 실제 객체 생성 비용이 클 때, 필요 시점까지 생성 지연
- **캐싱(Cache Proxy)**: 동일한 요청에 대해 결과를 캐싱하여 성능 향상
- **로깅(Logging Proxt)**: 요청 전후로 로그를 남겨 감사(Audit) 기능 제공

## 언제, 왜 사용하나?

- 비용이 큰 객체를 즉시 생성하기 어려울 때 (ex. 이미지 로딩, 대용량 데이터)
- 보안 / 권한 관리가 필요한 경우
- 부가 기능(캐싱, 로깅, 트랜잭션 등)을 분리하고 싶을 때
- 통신(네트워크, 원격 호출)에서 실제 호출 전후 처리 제어

## 장점

- **단일 책임 분리**: 실제 로직과 접근 제어 / 부가 기능을 분리
- **개방/폐쇄 원칙 준수**: RealSubject 코드를 수정하지 않고 기능 확장
- **유연한 제어**: 다양한 유형의 프록시를 상황에 맞게 적용

## 단점 및 주의사항

1. 추가 객체 비용

   프록시 클래스를 하나 더 두므로 클래스 수 증가

2. 성능 지연

   프록시 로직으로 실제 호출 오버헤드 발생

3. 설계 복잡도

   프록시 종류가 많아지면 구조 파악이 어려워질 수 있음

<br/>

> **결론**:
>
> 프록시 패턴은 실제 객체에 대한 제어와 부가 기능 삽입을 깔끔하게 분리해 주지만, 오버헤드와 설계 복잡도를 고려해 적절히 적용해야 합니다.
