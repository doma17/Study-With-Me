# 반복자(Iterator) 패턴

> 자료구조와 같이 접근이 잦은 객체에 대해 동일한 인터페이스를 사용하는 패턴

반복자 패턴은 **집합 객체의 내부 구조를 노출하지 않고**, 외부에서는 일관된 반복 인터페이스만으로 요소에 접근하도록 돕습니다. 배열, 링크드 리스트, 트리 등 어떤 자료구조든 `iterator.next()`·`hasNext()` 같은 공통 메서드만 있으면, 순회 로직을 그대로 재사용할 수 있습니다.

여기서 “반복”은 **컬렉션을 여러 번 생성**하는 것이 아니라, 한 번 만들어진 컬렉션의 요소들을 **필요할 때마다 순차적으로 꺼내 보는** 동작을 말합니다. 예를 들어 화면 렌더링, 합계 계산, 필터링·매핑 작업 등에서 매번 `for`문을 작성하는 대신, 반복자를 사용하면 자료구조 종류에 상관없이 동일한 방식으로 요소를 처리할 수 있습니다.

또한, 같은 컬렉션에 대해 서로 다른 위치에서 **독립적으로 순회**할 수 있기 때문에, A→B 순서대로 두 개의 반복기를 동시에 돌리거나, 서로 다른 목적의 순회를 병행할 때도 각 반복기가 **독립된 커서 상태**를 유지합니다.

반복자는 **지연 평가(lazy evaluation)** 를 지원해, 필요할 때만 요소를 꺼내므로 메모리와 연산을 절약할 수 있습니다. 이 덕분에 무한 시퀀스나 가상화 리스트(vitrualized list) 같은 시나리오에서도 안전하게 사용할 수 있습니다.

마지막으로, 새로운 자료구조가 등장하더라도 **반복자 구현만 추가**하면 기존 순회 코드는 전혀 수정할 필요가 없으므로, 개방-폐쇄 원칙(Open/Closed Principle)을 준수한 확장 가능한 설계를 제공합니다.

<br/>

## 예제로 구현하기

```tsx
// 1) Iterator 인터페이스를 따르는 클래스
class ShoppingListIterator {
	constructor(items) {
		this.items = items; // 순회할 목록
		this.position = 0; // 현재 위치(커서)
	}

	// 다음 요소를 꺼내 반환
	next() {
		return this.items[this.position++];
	}

	// 더 꺼낼 요소가 남아있는지 확인
	hasNext() {
		return this.position < this.items.lenth;
	}
}

// 2) 컬렉션 역할을 하는 ShoppingList 클래스
class ShoppingList {
	constructor(items) {
		this.items = items;
	}

	// 반복자 생성 메소드
	getIterator()	{
		return new ShoppingListIterator(this.items);
	}
}

// 3) 사용 예시
const groceries = new ShoppingList([
	'우유', '달걀', '빵', '버터', '치즈'
]);

const tomIterator = groceries.getIterator();
const jerryIterator = groceries.getIterator();

// Tom의 쇼핑
console.log('Tom의 쇼핑 시작');
while (tomIterator.hasNext()) m{
	console.log('Tom buys:', tomIterator.next());
}

// Jerry의 쇼핑 (Tom과 독립된 순회 상태)
console.log('\nJerry의 쇼핑 시작');
while (jerryIterator.hasNext()) {
	console.log('Jerry buys:', jerryIterator.next());
}
```

<br/>

## 핵심 장점

1. **내부 구현 은닉**: 클라이언트는 `next()`와 `hasNext()`만 알면 되고, 자료구조는 몰라도 됩니다.
2. **독립된 상태**: 반복기를 생성할 때마다 새로운 커서를 갖기 때문에, 여러 사용자나 로직이 간섭 없이 순회할 수 있습니다.M
3. **재사용성·확장성**: 새로운 컬렉션 타입에 `getIterator()`만 구현하면, 기존 순회 코드를 그대로 사용합니다.
