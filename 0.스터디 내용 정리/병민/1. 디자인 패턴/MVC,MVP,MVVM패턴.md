## MVC, MVP, MVVM 패턴

**MVC, MVP, MVVM 패턴**은 애플리케이션의 **사용자 인터페이스와 비즈니스 로직을 분리**하기 위한 **아키텍처 패턴**들입니다. 이 패턴들은 **관심사의 분리(Separation of Concerns)**를 통해 **코드의 재사용성, 유지보수성, 테스트 용이성**을 향상시키는 것을 목표로 합니다.

### 제기된 문제점

초기 GUI 애플리케이션 개발에서는 사용자 인터페이스 코드와 비즈니스 로직이 하나의 클래스나 모듈에 뒤섞여 있었습니다. 이로 인해 다음과 같은 문제들이 발생했습니다:

- **높은 결합도**: UI 변경 시 비즈니스 로직도 함께 수정해야 함
- **재사용성 부족**: 동일한 비즈니스 로직을 다른 UI에서 사용하기 어려움
- **테스트 어려움**: UI와 로직이 섞여 있어 단위 테스트가 복잡함
- **유지보수 복잡성**: 코드 변경 시 예상치 못한 부작용 발생

![MVC패턴-예시1](https://miro.medium.com/v2/resize:fit:1100/format:webp/1*Ual_l5eFZLOYR-lagZe00Q.png)

## 1. MVC 패턴 (Model-View-Controller)

![MVC패턴-예시2](https://miro.medium.com/v2/resize:fit:720/format:webp/1*E7MOKsayQQvfUiSvvlT_YQ.png)

**MVC 패턴**은 애플리케이션을 **Model, View, Controller** 세 개의 구성 요소로 분리하는 패턴입니다.

### 구성 요소

- **Model** (모델)
  - **데이터와 비즈니스 로직을 담당**합니다.
  - **데이터의 상태 변화를 Observer들에게 알림**합니다.
  - **데이터베이스, 파일, 네트워크 등과의 상호작용**을 처리합니다.
- **View** (뷰)
  - **사용자 인터페이스를 담당**합니다.
  - **Model의 데이터를 사용자에게 시각적으로 표현**합니다.
  - **사용자의 입력을 Controller에게 전달**합니다.
- **Controller** (컨트롤러)
  - **사용자 입력을 처리하고 Model과 View를 조율**합니다.
  - **비즈니스 로직을 실행하고 Model을 업데이트**합니다.
  - **View를 선택하고 업데이트를 지시**합니다.

### 장단점

- **장점**
  - **명확한 역할 분리**: 각 구성 요소의 책임이 명확함
  - **재사용성**: Model과 View를 독립적으로 재사용 가능
  - **확장성**: 새로운 View나 Controller 추가 용이
- **단점**
  - **복잡성**: 간단한 애플리케이션에는 과도할 수 있음
  - **View와 Model 간 의존성**: Observer 패턴으로 인한 복잡성

## 2. MVP 패턴 (Model-View-Presenter)

![MVP패턴-예시1](https://miro.medium.com/v2/resize:fit:720/format:webp/1*CMvzuNhsdwb3uxV_wXwKKQ.png)

**MVP 패턴**은 MVC의 단점을 보완하기 위해 개발된 패턴으로, **Presenter가 View와 Model 사이의 모든 상호작용을 관리**합니다.

### 구성 요소

- **Model** (모델)
  - **데이터와 비즈니스 로직을 담당** (MVC와 동일)
- **View** (뷰)
  - **사용자 인터페이스만 담당**합니다.
  - **Presenter와 인터페이스를 통해 통신**합니다.
  - **수동적인 역할**: Model에 직접 접근하지 않음
- **Presenter** (프레젠터)
  - **View와 Model 사이의 중재자 역할**을 합니다.
  - **모든 UI 로직과 비즈니스 로직을 처리**합니다.
  - **View를 업데이트하고 Model을 조작**합니다.

### 장단점

- **장점**
  - **테스트 용이성**: View가 인터페이스로 추상화되어 Mock 객체 사용 가능
  - **View와 Model 완전 분리**: 의존성 제거
  - **단위 테스트**: Presenter 로직을 독립적으로 테스트 가능
- **단점**
  - **Presenter 비대화**: 복잡한 UI 로직이 집중될 수 있음
  - **View와 Presenter 강결합**: 1:1 관계로 인한 제약

## 3. MVVM 패턴 (Model-View-ViewModel)

![MVVM패턴-예시1](https://miro.medium.com/v2/resize:fit:720/format:webp/1*LBh0bmC5zBemQAeSuYZlRQ.png)

**MVVM 패턴**은 **데이터 바인딩**을 통해 View와 ViewModel을 연결하는 패턴입니다. 주로 **WPF, Angular, Vue.js** 등에서 사용됩니다.

### 구성 요소

- **Model** (모델)
  - **데이터와 비즈니스 로직을 담당** (이전 패턴들과 동일)
- **View** (뷰)
  - **사용자 인터페이스를 담당**합니다.
  - **ViewModel과 데이터 바인딩을 통해 연결**됩니다.
  - **선언적 방식으로 UI 정의**합니다.
- **ViewModel** (뷰모델)
  - **View를 위한 Model의 추상화**입니다.
  - **데이터 바인딩과 커맨드를 제공**합니다.
  - **View의 상태와 동작을 관리**합니다.

### 장단점

- **장점**
  - **데이터 바인딩**: 자동으로 UI 업데이트
  - **Declare UI**: 코드가 직관적이고 이해하기 쉬움
  - **테스트 용이성**: ViewModel을 독립적으로 테스트 가능
  - **개발 생산성**: 보일러플레이트 코드 감소
- **단점**
  - **프레임워크 의존성**: 데이터 바인딩을 지원하는 프레임워크 필요
  - **디버깅 어려움**: 바인딩으로 인한 복잡한 데이터 흐름
  - **성능 고려사항**: 대량 데이터 처리 시 바인딩 오버헤드

## 패턴 비교

| 특징 | MVC | MVP | MVVM |
|------|-----|-----|------|
| **View-Model 관계** | 직접 참조 | Presenter 중재 | 데이터 바인딩 |
| **테스트 용이성** | 보통 | 높음 | 높음 |
| **복잡성** | 보통 | 높음 | 낮음 (프레임워크 지원 시) |
| **데이터 바인딩** | 수동 | 수동 | 자동 |
| **주요 사용처** | 웹 애플리케이션 | 데스크톱 애플리케이션 | 모던 웹/모바일 프레임워크 |

## 현업에서 가장 많이 쓰이는 방식

### 1. **웹 개발**: MVVM

**현재 웹 개발에서는 MVVM 패턴이 가장 많이 사용**되고 있습니다

- **React**: 컴포넌트 기반 MVVM 구조
- **Vue.js**: 전형적인 MVVM 패턴 구현
- **Svelte**: 컴파일 타임 최적화된 MVVM

**이유:**
- **개발 생산성**: 데이터 바인딩으로 인한 코드 간소화
- **프레임워크 생태계**: 성숙한 도구와 라이브러리 지원
- **상태 관리**: Redux, Vuex 등과의 자연스러운 통합

### 2. **백엔드 웹 애플리케이션**: MVC

![MVC패턴-예시2](
https://blog.kakaocdn.net/dn/bkIZfq/btrFBKbEaPj/LMB6QN5oOAGNKicSt9mSk0/img.png)

**Spring MVC, Ruby on Rails** 등 대부분의 백엔드 프레임워크는 **MVC 패턴을 기반**으로 합니다

### 3. **모바일 개발**: MVVM

- **Android**: MVVM + Data Binding, Jetpack Compose
- **iOS**: SwiftUI를 통한 MVVM 패턴
- **Flutter**: BLoC 패턴 (MVVM의 변형)

### 4. **엔터프라이즈 환경**: MVP

**복잡한 비즈니스 로직**이 있는 엔터프라이즈 애플리케이션에서는 **MVP 패턴**이 선호되기도 합니다
- **명확한 테스트 경계**
- **복잡한 UI 로직 관리**
- **레거시 시스템과의 통합**

### 결론

**패턴 선택 기준:**

1. **웹 프론트엔드**: **MVVM** (React, Vue, Angular)
2. **웹 백엔드**: **MVC** (Spring, Django, Rails)
3. **모바일**: **MVVM** (Android, iOS 최신 개발)
4. **복잡한 엔터프라이즈**: **MVP** (테스트가 중요한 경우)

#### Reference

https://docs.microsoft.com/en-us/dotnet/architecture/modern-web-apps-azure/architectural-principles
https://vuejs.org/guide/introduction.html
https://spring.io/guides/gs/serving-web-content/
https://medium.com/delightroom/mvc-mvp-mvvm-mvi-%EC%95%84%ED%82%A4%ED%85%8D%EC%B3%90-%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C%EC%97%90%EC%84%9C-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0-1-2442a4189c79