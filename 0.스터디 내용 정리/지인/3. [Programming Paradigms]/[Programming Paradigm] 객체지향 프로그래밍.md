# 객체지향 프로그래밍

## 객체지향의 등장 배경

객체지향의 특징 및 원칙을 이해하기 전, 객체지향 패러다임이 등장하게 된 배경을 먼저 살펴보도록 하겠습니다.

### 순차적 프로그래밍과 goto

객체지향 패러다임이 존재하기 이전 초창기 프로그래밍에서는 프로그램이 순서대로 실행되었습니다.

함수라는 개념이 없던 시절에는 **특정 위치로 실행 순서를 강제 변경하는** **goto** 문을 만들어내게 되고, 이를 통해 강제로 실행 순서를 바꾸다 보니 코드가 커져가며 코드의 흐름을 제어하기가 힘들어집니다.

### 절차적(구조적) 프로그래밍

이를 해결하기 위해 **절차적 프로그래밍** 패러다임이 등장합니다. 실행순서를 강제로 바꾸는 것이 아니라 일정하게 반복되는 코드를 따로 만들어두고, 해당 코드를 호출하고 나서 다시 원래 자리로 돌아오는 방식의 **프로시저(함수)** 를 통해서 개발을 하게 되며 지금의 **함수**와 같은 개념이 등장합니다.

즉 절차적 프로그래밍은 **데이터와 데이터를 처리하는 동작을 함수 단위로 분리하고 재사용하는 형태**로 프로그래밍을 하는 방식이 됩니다.

절차적 프로그래밍에서는 **전역 변수**를 사용했는데, 프로그램의 덩치가 커지면 커질수록 동일한 변수명을 사용할 수 없어 변수명 관리가 굉장히 복잡해집니다.

이에 따라 이름 앞에 foo_x, foo_y처럼 prefix가 늘게 되어 이를 해결하기 위해 하나의 파일 단위 혹은 모듈 단위로 prefix를 부여해서 관리하는 **namespace(네임스페이스)** 방식이 등장합니다.

### 구조체

그러나 네임스페이스만으로는 비슷한 형태의 데이터들을 쉽게 다룰 수 없었는데요. 게임을 만든다고 가정해보면 하나의 캐릭터가 가지는 이름, hp, item 등 변수를 만들기 위해서는 여전히 prefix를 붙여서 만들어야 했습니다.

```tsx
var character1_name = "jean"
var character1_hp = 300
var character1_mp = 500

function character1_useSkill() {
  ...
  character1_mp -= 100 // 변수를 직접 수정하게 됨.
}

character1_do_somthing()
```

위와 같은 방식의 프로그래밍은 캐릭터가 2개, 3개만 되어도 코드가 늘어나게 되고 중복될 내용이 많아집니다.

이를 해결하기 위해 **서로 연관이 있는 데이터들을 하나로 묶어 네임스페이스처럼 관리하며 해당 변수에 접근할 수 있는 구조체**라는 형식을 고안하게 됩니다.

```tsx
// struct
// (사실 구조체는 엄밀히 말해 하나의 Type인데 js라서 그냥 이렇게 쓰겠습니다. 찰떡같이 이해해주세요.)
var character = {
  name: "jean"
  hp: 300
  mp: 500
}

function useSkill(character) {
  ...
  character.mp -= 100 // 변수를 직접 수정하게 됨.
}

do_somthing(character)
```

이를 통해 **의미 있는 단위로 변수들을 묶어 변수명의 중복을 줄이고, 함수나 배열 등에서도 하나의 변수처럼 활용**할 수 있게 됩니다. 이를 통해 프로그램의 볼륨이 늘어도 **일관성을 유지**하며 코드를 짤 수 있게 되었죠.

### 객체 지향 프로그래밍의 등장

구조체를 통해 산재해 있는 데이터들을 의미있는 데이터로 구조화시켜 프로그래밍을 하니, **동작보다는 데이터 중심으로 코딩을 하게 되면 프로그램이 커져도 일관성을 유지하기 쉬움**을 깨닫게 됩니다.

```tsx
// struct
var character = {
  name: "jean"
  hp: 300
  mp: 500
}

function character_attck(character) {...}
function character_useSkill(character) {...}
function character_moveTo(character, toX, toY) {...}
```

그러면서 데이터 중심으로 코드를 모으다 보니 위와 같이 **특정 구조체만 가지고 동작을 하는 함수군**들이 만들어짐을 깨닫게 되었고, 함수 역시 전역 네임스페이스를 사용했기에 character\_와 같은 prefix를 달아야 함을 깨닫게 됩니다.

### 클래스의 등장

그래서 구조체와 항상 쓰이는 함수들을 하나로 묶어 **구조체와 함께 함수까지 포함하는 개념**을 만들게 되고, 이것이 바로 **Class(클래스)** 입니다.

```tsx
// class
class Character {
  name = "jean"
  hp = 300
  mp = 500

  attack() {...}
  useSkill() {...}
  moveTo(toX, toY) {...}
}

// object
var character = new Character();
character.attack();
character.useSkill()
character.jump();
```

데이터와 처리방법을 분리해서 개발하던 기존의 절차식 프로그래밍과 달리 **데이터와 처리방식이 하나의 모듈**로 관리되며 마치 작은 프로그램들이 독립적으로 돌아가는 형태를 띠게 됩니다.

작은 부품들이 조립되고 결합되어 큰 프로그램을 만드는 방식으로 개발을 시작하게 된 것이죠.

이러한 부품을 만드는 설계도를 작성하고 공장에서 부품을 찍어내듯이, **설계도의 역할을 하는 클래스**와 그러한 **클래스로 찍어낸 객체**의 개념이 등장합니다.

작은 문제를 해결하는 독립된 객체를 먼저 만들고, 이러한 객체들을 조립하자믄 개발 방식은 다음과 같이 개념이 확장됩니다.

> 프로그램은 모두 객체로 만들어져 있고 객체들 간의 메시지를 주고받는 상호작용으로 이루어진다.

이렇듯 프로그램을 객체로 바라보는 관점에서 프로그래밍 하는 것을 **Object-Oriented Programming(OOP), 객체지향 프로그래밍**이라고 부르게 되었습니다.

<br/>

## 4가지 특징

독립된 객체를 조립해서 사용하는 방식은 곧 레고처럼, 재사용이 가능한 객체들을 많이 만들어 놓는 것이 중요함을 알게 됩니다. 따라서 객체의 재사용성을 높이기 위해 객체지향 프로그래밍에서는 4가지 개념들이 등장하게 됩니다.

### 추상화(Abstraction)

추상이라는 용어의 사전적 의미는 “사물이나 표상을 어떤 성질, 공통성, 본질에 착안하여 그것을 추출하여 파악하는 것”이라 정의합니다. 여기서 핵심은 **“공통성과 본질을 모아 추출”** 한다는 것입니다.

예를 들면, 서울의 지하철 노선도는 서울의 지리를 추상화시켜 보여주는 대표적인 예입니다. **중요한 부분을 강조하기 위해 불필요한 세부 사항들은 제거하고, 가장 본질적이고 공통적인 부분만을 추출하여 표현**하는 것과 관련이 있습니다.

같은 맥락에서, 객체지향 프로그래밍에서 의미하는 추상화는 **객체의 공통적인 속성과 기능을 추출하여 정의하는 것**을 의미합니다.

다시 게임 예제를 들어보자면, **검**과 **활**은 모두 캐릭터가 사용할 수 있는 무기입니다. 모든 무기는 **공격을 할 수 있**다는 공통점을 가집니다.

인터페이스(interface)를 통해 추상화를 구현해 보겠습니다.

```tsx
interface Weapon {
  name: string;
  attack();
  void;
}
```

검과 활의 **공통적인 기능을 추출**하여 무기 인터페이세 정의합니다. 객체 지향적 설계에 있어 인터페이스는 어떤 **객체의 역할만을 정의**하여 객체들 간의 관계를 보다 유연하게 연결하는 역할을 담당합니다.

다른 말로 표현하면, 인터페이스에는 추상 메서드나 상수를 통해서 **어떤 객체가 수행해야 하는 핵심적인 역할만을 규정**해두고, **실제적인 구현은 해당 인터페이스를 구현하는 각각의 객체들에서 하도록** 프로그램을 설계하는 것을 의미합니다.

위에서 정의한 `Weapon` 인터페이스를 실제로 구현한 예제를 보며 좀 더 이해해보도록 하겠습니다.

```tsx
// Sword 클래스
class Sword implements Weapon {
  name: string;

  constrcutor(name: string) {
    this.name = name;
  }

  attack(): void {
    console.log(`${this.name}로 벤다!`);
  }
}

// Bow 클래스
class Bow implements Weapon {
  name: string;

  constrcutor(name: string) {
    this.name = name;
  }

  attack(): void {
    console.log(`${this.name}로 쏜다!`);
  }
}
```

`Weapon` 인터페이스를 구현한 구현체 `Sword`와 `Bow` 모두 인터페이스에 정의한 역할을 각각의 클래스의 맥락에 맞게 구현하고 있습니다.

검과 활 모두 공격의 기능을 공통적으로 가지지만 검은 베고, 활은 쏴야 하기 때문에 그 구현은 각 클래스에 따라 달라야 할 것입니다.

이것을 객체지향 프로그래밍에서는 역할과 구현의 분리라고 하며, 이 부분이 아래에서 살펴볼 다형성과 함께 유연하고 변경이 용이한 프로그램을 설계하는 데 가장 핵심적인 부분이라 할 수 있습니다.

### 상속(Inheritance)

상속이란 기존의 클래스를 재활용하여 새로운 클래스를 작성하는 것입니다.

상속은 클래스 간 공유될 수 있는 속성과 기능은 상위 클래스로 추상화 시켜 상위 클래스로부터 확장된 여러 개의 하위 클래스들이 모두 상위 클래스의 속성과 기능들을 간편하게 사용할 수 있도록 합니다.

즉, 클래스 간 공유되는 속성과 기능이 재사용될 수 있기 때문에 반복적인 코드를 최소화하고 공유하는 속성과 기능에 간편하게 접근할 수 있죠.

이전의 예제에서 검과 활에 공통적인 부분, 그렇지 않은 부분이 존재함을 확인했습니다.

```tsx
// Sword 클래스
class Sword implements Weapon {
  name: string;
  bladeLength: number; // 검만의 고유 속성: 검의 길이

  constructor(name: string, bladeLength: number) {
    this.name = name;
    this.bladeLength = bladeLength;
  }

  attack(): void {
    console.log(`${this.name}로 공격한다!`);
  }

  sharpen(): void {
    // 칼만의 고유 기능: 칼 갈기
    console.log(`${this.name}을(를) 숫돌로 갈아 날카롭게 합니다.`);
  }
}
```

```tsx
// Bow 클래스
class Bow implements Weapon {
  name: string;
  range: number; // 활만의 고유 속성: 사정거리

  constructor(name: string, range: number) {
    this.name = name;
    this.range = range;
  }

  attack(): void {
    console.log(`${this.name}로 공격한다!`);
  }

  aim(): void {
    // 활만의 고유 기능: 조준
    console.log(`${this.name}로 목표를 조준합니다.`);
  }
}
```

위 예제를 확인하면 각 클래스마다 `name` 속성과 `attack()` 기능이 완전히 동일한 코드임에도 불구하고 반복되고 있음을 확인할 수 있습니다.

이는 하나의 코드에서 변경 사항이 일어나면 해당 코드의 변경 사항을 다른 클래스에서도 일일이 수정해주어야 함을 의미하죠.

그럼 추상화와 상속을 활용하여 앞선 코드를 재정의하도록 하겠습니다.

```tsx
// Weapon 클래스 - 공통 속성 및 기능을 가진 추상화 상위 클래스
class Weapon {
  name: string;

  constructor(name: string) {
    this.name = name;
  }

  attack(): void {
    console.log(`${this.name}로 공격한다!`);
  }
}
```

```tsx
// Sword 클래스
class Sword extends Weapon {
  bladeLength: number; // 고유 속성: 검의 길이

  constructor(name: string, bladeLength: number) {
    super(name); // 상위 클래스 생성자 호출
    this.bladeLength = bladeLength;
  }

  sharpen(): void {
    console.log(`${this.name}을(를) 숫돌로 갈아 날카롭게 합니다.`);
  }
}
```

```tsx
// Bow 클래스
class Bow extends Weapon {
  range: number; // 고유 속성: 활의 사정거리

  constructor(name: string, range: number) {
    super(name);
    this.range = range;
  }

  aim(): void {
    console.log(`${this.name}로 목표를 조준합니다.`);
  }
}
```

```tsx
// 테스트 예시
const sword = new Sword("엑스칼리버", 90);
sword.attack(); // 엑스칼리버로 공격합니다! ⚔️
sword.sharpen(); // 엑스칼리버을(를) 숫돌로 갈아 날카롭게 합니다.

const bow = new Bow("엘프 활", 300);
bow.attack(); // 엘프 활로 공격합니다! ⚔️
bow.aim(); // 엘프 활로 목표를 조준합니다.
```

위 코드를 보면 `Sword`와 `Bow`의 공통적인 속성과 기능은 추출하여 `Weapon` 클래스에 정의하였고, `extends` 키워드를 통해 각각의 하위 클래스로 확장하여 해당 기능과 속성들을 매번 반복으로 정의해야 하는 번거로움을 제거했습니다.

이를 통해 공통적인 코드는 상위 클래스의 수정으로 모든 클래스에 변경 사항이 반영될 수 있습니다.

### 다형성

다형성은 "같은 인터페이스 또는 상위 타입을 참조하더라도 실제 동작은 객체의 타입에 따라 다르게 실행되는 성질"을 의미합니다.

즉, `Weapon`이라는 공통 타입(상위 클래스)으로 묶인 다양한 하위 클래스들이 **같은 `attack()` 메서드를 호출해도 각기 다르게 동작**할 수 있습니다.

```tsx
// 부모 클래스
class Weapon {
  name: string;

  constructor(name: string) {
    this.name = name;
  }

  attack(): void {
    console.log(`${this.name}로 기본 공격!`);
  }
}

// Sword 클래스
class Sword extends Weapon {
  bladeLength: number;

  constructor(name: string, bladeLength: number) {
    super(name);
    this.bladeLength = bladeLength;
  }

  override attack(): void {
    console.log(`${this.name}로 벤다!`);
  }
}

// Bow 클래스
class Bow extends Weapon {
  range: number;

  constructor(name: string, range: number) {
    super(name);
    this.range = range;
  }

  override attack(): void {
    console.log(`${this.name}로 쏜다!`);
  }
}
```

```tsx
const weapons: Weapon[] = [
  new Sword("엑스칼리버", 90),
  new Bow("엘프 활", 300),
];

for (const weapon of weapons) {
  weapon.attack(); // 다형적 호출: 실제 객체 타입에 따라 다르게 동작
}

// 엑스칼리버로 벤다!
// 엘프 활로 쏜다!
```

이처럼 같은 타입(Weapon)으로 `attack()`을 호출하지만, **실제 객체(Sword, Bow)에 따라 동작이 다릅니다.**

### 캡슐화

캡슐화는 객체의 속성을 외부로부터 숨기고, **공식적인 인터페이스를 통해서만 접근**하도록 만드는 원칙입니다.

내부 상태(데이터)는 `private`으로 선언하고, 접근자(`get`, `set`)나 메서드를 통해서만 접근 가능하게 합니다.

```tsx
class MagicBow extends Bow {
  private durability: number;

  constructor(name: string, range: number, durability: number) {
    super(name, range);
    this.durability = durability;
  }

  override attack(): void {
    if (this.durability > 0) {
      console.log(
        `${this.name}로 마법 화살을 발사! (내구도: ${this.durability})`
      );
      this.durability--;
    } else {
      console.log(`${this.name}은(는) 내구도가 없어 사용할 수 없습니다.`);
    }
  }

  getDurability(): number {
    return this.durability;
  }

  repair(): void {
    this.durability += 1;
    console.log(`${this.name}을(를) 수리하여 내구도 증가!`);
  }
}
```

```tsx
const magicBow = new MagicBow("마나 활", 200, 2);
magicBow.attack(); // 마나 활로 마법 화살을 발사! (내구도: 2)
magicBow.attack(); // 마나 활로 마법 화살을 발사! (내구도: 1)
magicBow.attack(); // 마나 활은(는) 내구도가 없어 사용할 수 없습니다.

console.log(magicBow.getDurability()); // 0
magicBow.repair(); // 마나 활을(를) 수리하여 내구도 증가!
```

위 코드에서 `durability`는 외부에서 직접 접근이 불가하고, `getDurability()`, `repair()` 같은 공식 메서드를 통해서만 관리됩니다.

이를 통해 프로그램의 안정성과 사용성을 향상시킬 수 있고, 필요한 메서드만 열어둠으로써 객체의 재사용성 또한 높일 수 있습니다.

## 좋은 설계란?

좋은 설계란 **시스템에 새로운 요구사항이나 변경사항이 발생하더라도 영향을 받는 범위가 최소화되는 구조**를 말합니다. 이러한 구조는 변화에 유연하게 대처할 수 있으며, 이후에도 **확장 가능한 시스템**으로 발전시킬 수 있습니다.

객체지향 프로그래밍에서는 **SOLID 원칙**을 적용함으로써 코드의 **확장성과 유지 보수성**을 높일 수 있으며, 불필요한 복잡성을 제거하여 **개발 생산성**을 향상시킬 수 있습니다.

## SOLID 원칙

### 1. SRP (Single Responsibility Principle) - 단일 책임 원칙

> 클래스는 오직 하나의 책임만 가져야 한다.

여기서 '책임'이란 하나의 기능 또는 역할을 의미합니다. 즉, **하나의 클래스는 하나의 기능에만 집중하여 이를 수행해야** 합니다.

여러 책임을 가진 클래스는 하나의 기능 변경에도 **불필요한 다른 기능까지 영향을 받을 수 있으므로**, 유지보수가 어려워집니다. SRP는 이러한 문제를 방지하여 **변경의 이유가 하나뿐인 구조**를 만드는 것을 목표로 합니다.

### 2. OCP (Open/Closed Principle) - 개방-폐쇄 원칙

> 소프트웨어 요소는 확장에는 열려 있어야 하며, 수정에는 닫혀 있어야 한다.

즉, 새로운 기능을 추가할 수 있도록 **코드를 확장할 수는 있어야 하지만**, 기존 코드를 **수정하지 않고도 동작**해야 한다는 원칙입니다. 이 원칙을 지키기 위해서는 **추상화**를 활용한 설계가 필요하며, 이는 객체지향의 핵심인 **다형성**을 통해 구현됩니다.

OCP는 기능 추가 시 기존 코드를 건드리지 않고 새로운 클래스를 작성하여 기능을 확장하는 구조를 유도함으로써, **변경에 강한 아키텍처**를 만드는 데 기여합니다.

### 3. LSP (Liskov Substitution Principle) - 리스코프 치환 원칙

> 하위 타입은 상위 타입으로 대체할 수 있어야 한다.

이 원칙은 다형성을 올바르게 활용하기 위한 기본 전제입니다. 즉, 상위 클래스 타입으로 선언된 객체에 **하위 클래스의 인스턴스를 대입했을 때도 프로그램의 동작이 일관되어야** 합니다.

하위 클래스는 상위 클래스의 계약(계약된 행위와 규칙)을 **위반하지 않아야** 하며, **동일한 방식으로 동작해야** 다형성을 안전하게 구현할 수 있습니다.

### 4. ISP (Interface Segregation Principle) - 인터페이스 분리 원칙

> 클라이언트는 자신이 사용하지 않는 메서드에 의존하지 않아야 한다.

하나의 커다란 인터페이스를 여러 개의 **작고 목적에 맞는 인터페이스로 분리**함으로써, 클라이언트는 **자신이 필요한 기능만 접근**할 수 있습니다. 이를 통해 **불필요한 의존성을 줄이고**, 기능 확장이나 수정 시에도 다른 클라이언트에게 **영향을 최소화**할 수 있습니다.

결국 ISP는 인터페이스의 **단일 책임과 응집도**를 강조하는 원칙입니다.

### 5. DIP (Dependency Inversion Principle) - 의존 역전 원칙

> 고수준 모듈은 저수준 모듈에 의존해서는 안 되며, 둘 다 추상화에 의존해야 한다.

쉽게 말해, **구현 클래스에 의존하지 말고 추상화(인터페이스)에 의존하라**는 의미입니다.

기능을 구현하는 하위 모듈(저수준)은 상위 로직(고수준)에 맞춰 교체될 수 있어야 하며, 양쪽 모두 **구체적인 구현이 아닌 추상적인 계약에 의존함으로써 결합도를 낮출 수 있습니다.**

이 원칙은 의존성 주입(Dependency Injection)과 함께 사용되어, **유연하고 테스트 가능한 코드 구조**를 만드는 데 핵심적인 역할을 합니다.

<br/>
<br/>

> 참고
>
> - https://www.codestates.com/blog/content/%EA%B0%9D%EC%B2%B4-%EC%A7%80%ED%96%A5-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D-%ED%8A%B9%EC%A7%95
> - https://velog.io/@teo/oop#%EC%99%B8%EB%B6%80%EC%97%90%EC%84%9C-%EC%95%8C-%ED%95%84%EC%9A%94-%EC%97%86%EB%8A%94-%EA%B2%83%EB%93%A4%EC%9D%80-%EC%88%A8%EA%B2%A8%EB%86%93%EC%9E%90---%EC%BA%A1%EC%8A%90%ED%99%94
> - https://inpa.tistory.com/entry/OOP-%F0%9F%92%A0-%EA%B0%9D%EC%B2%B4-%EC%A7%80%ED%96%A5-%EC%84%A4%EA%B3%84%EC%9D%98-5%EA%B0%80%EC%A7%80-%EC%9B%90%EC%B9%99-SOLID
> - https://mangkyu.tistory.com/194
