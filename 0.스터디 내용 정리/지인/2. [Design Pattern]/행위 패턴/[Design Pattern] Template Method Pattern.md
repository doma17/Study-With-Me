# 템플릿 메소드(Template Method) 패턴

> 상위 클래스에서 알고리즘의 구조를 정의하고, 하위 클래스에서 구체적으로 처리하는 패턴

여러 클래스에서 공통으로 사용하는 메서드를 템플릿화하여 상위 클래스에서 정의하고, 하위 클래스마다 세부 동작 사항을 다르게 구현하는 패턴입니다.

상속을 통해 슈퍼클래스의 기능을 확장할 때 사용하는 가장 대표적인 방법으로, 상속이라는 기술을 극대화합니다.

<br/>

## 구성 요소

- **AbstractClass(추상 클래스)**: 템플릿 메소드를 구현하고, 템플릿 메소드에서 돌아가는 추상 메소드를 선언합니다. 이 추상 메소드는 하위 클래스인 ConcreteClass 역할에 의해 구현됩니다.
- **ConcreteClass(구현 클래스)**: AbstractClass를 상속하고 추상 메소드를 구체적으로 구현합니다. ConcreteClass에서 구현한 메소드는 AbstractClass의 템플릿 메소드에서 호출됩니다.

<br/>

## hook 메소드

훅(hook) 메소드는 부모의 템플릿 메소드의 영향이나 순서를 제어하고 싶을 때 사용되는 메소드 형태를 말합니다.

알고리즘의 핵심 흐름은 바꾸지 않으면서, 중간중간 “필요한 추가 작업”을 삽입할 수 있는 지점을 제공합니다.

<br/>

## 예제로 구현하기

```tsx
// 샌드위치 만들기
// 1) 추상 클래스 역할: 샌드위치 제작의 골격(템플릿 메소드) 정의
class Sandwich {
  // 템플릿 메소드: 전체 흐름을 고정
  make() {
    this.sliceBread();
    this.addMainFilling();
    this.addVegetables();
    if (this.customerWantsExtra()) {
      // 훅 메소드로 선택적 추가 여부 결정
      this.hookExtra();
    }
    this.wrap();
  }

  sliceBread() {
    console.log("빵을 자릅니다");
  }

  addMainFilling() {
    throw new Error("addMainFilling()을 구현해 주세요!");
  }

  addVegetables() {
    console.log("양상추와 토마토를 넣습니다");
  }

  wrap() {
    console.log("샌드위치를 포장합니다");
  }

  // 훅 메소드: 기본은 true, 필요 시 오버라이드해 추가 작업 제어
  customerWantsExtra() {
    return true;
  }

  // 훅 메소드: 기본 구현은 빈 메소드
  hookExtra() {}
}

// 2) 훅을 사용하지 않는 서브클래스
class VeggieSandwich extends Sandwich {
  addMainFilling() {
    console.log("치즈와 구운 야채를 추가합니다");
  }

  // 야채 샌드위치는 추가 소스 없음 -> 훅 비활성화
  customerWantsExtra() {
    return false;
  }
}

// 3) 훅을 활용하는 서브클래스
class ClubSandwich extends Sandwich {
  addMainFilling() {
    console.log("터키, 베이컨, 햄을 추가합니다");
  }
  // 머스타드 소스를 추가하는 훅 구현
  hookExtra() {
    console.log("머스타드 소스를 추가합니다");
  }
}

// 4) 사용 예시
console.log("Veggie Sandwich");
new VeggieSandwich().make();
/* 출력:
   빵을 슬라이스합니다
   치즈와 구운 야채를 추가합니다
   양상추와 토마토를 추가합니다
   샌드위치를 포장합니다
*/

console.log("\nClub Sandwich");
new ClubSandwich().make();
/* 출력:
   빵을 슬라이스합니다
   터키, 베이컨, 햄을 추가합니다
   양상추와 토마토를 추가합니다
   머스타드 소스를 추가합니다
   샌드위치를 포장합니다
*/
```

<br/>

## 핵심 장점

1. **알고리즘 구조의 일관성:** 전체 흐름(템플릿 메소드)을 상위 클래스에 정의해, 모든 서브클래스가 동일한 순서로 처리하고 유지보수가 용이합니다.
2. **중복 코드 제거 & 재사용성:** 공통 로직(sliceBread, addVegetables, wrap 등)을 상위 클래스에 한 번만 구현해 중복을 없애고, 재사용성을 높입니다.
3. **유연한 확장 지점 제공:** 빈 훅 메서드(hookExtra, customerWantsExtra)를 통해, 서브클래스가 필요할 때만 오버라이드해 추가 기능을 삽입할 수 있습니다.

<br/>

<br/>

> 참고
>
> - https://steady-coding.tistory.com/384
> - https://inpa.tistory.com/entry/GOF-%F0%9F%92%A0-%ED%85%9C%ED%94%8C%EB%A6%BF-%EB%A9%94%EC%86%8C%EB%93%9CTemplate-Method-%ED%8C%A8%ED%84%B4-%EC%A0%9C%EB%8C%80%EB%A1%9C-%EB%B0%B0%EC%9B%8C%EB%B3%B4%EC%9E%90#%EC%98%88%EC%A0%9C%EB%A5%BC_%ED%86%B5%ED%95%B4_%EC%95%8C%EC%95%84%EB%B3%B4%EB%8A%94_template_method_%ED%8C%A8%ED%84%B4
