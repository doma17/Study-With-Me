# 노출 모듈이란?
```노출 모듈```은 모듈 패턴에서 외부에 공개된 메서드나 속성을 의미한다. 즉, 모듈 안의 private 멤버는 숨기고, public 멤버만 return을 통해 외부에 노출시키는 구조다.
이 방식은 보통 즉시 실행 함수(IIFE, Immediately Invoked Function Expression) 와 함께 사용된다.

# 예시
```javascript
const CounterModule = (function () {
  // private 변수
  let count = 0;

  // private 함수
  function logCount() {
    console.log("Current count:", count);
  }

  // public API만 노출
  return {
    increment: function () {
      count++;
      logCount();
    },
    reset: function () {
      count = 0;
      logCount();
    }
  };
})();
```
```javascript
CounterModule.increment(); // Current count: 1
CounterModule.increment(); // Current count: 2
CounterModule.reset();     // Current count: 0

console.log(CounterModule.count); // undefined (외부에서 접근 불가)
```
여기서 ```increment```와 ```reset``` 함수는 노출된 모듈이며 ```count```와 ```logCount```는 비공개 상태로 은닉되어 있다.

# 사용하는 이유
 - 전역 네임스페이스 오염 방지
 - 내부 상태 보호 (직접 접근 불가)
 - 모듈화된 코드 작성
 - 테스트와 유지보수에 유리
