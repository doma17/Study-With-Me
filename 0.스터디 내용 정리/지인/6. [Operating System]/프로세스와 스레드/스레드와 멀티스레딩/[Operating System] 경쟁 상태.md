# 경쟁 상태

경쟁상태는 여러 프로세스 또는 스레드가 공유 자원에 동시에 접근할 때, 실행 순서에 따라 결과값이 달라지는 현상입니다.

동시 접근이 자료의 일관성과 무결성을 해칠 수 있습니다.

커널의 전역 변수, 시스템 콜 수행 도중 문맥 교환(Context Switch)가 일어나면 경쟁상태가 발생합니다.

<br/>

## 경쟁상태의 문제점

- 데이터 정합성 문제 발생(예상 값과 다른 결과)
- 데이터 무결성 침해(예: 계좌에서 0원에서도 출금되는 현상)
- 복제, 삭제, 갱신 등에서 의도하지 않은 결과 발생

<br/>

## 경쟁상태 해결 방안

이러한 경쟁상태는 동기화(Synchronization) 기법을 통해 예방하고 해결할 수 있습니다.

동기화는 여러 실행 흐름이 공유 자원에 안전하게 접근하도록 제한하는 것을 목표로 하며, 특히 임계구역(critical section)에서는 반드시 한 번에 하나의 프로세스나 스레드만 접근하도록 보장해야 합니다.

대표적으로 아래 세 가지가 있습니다.

- **상호 배제(Mutual Exclusion)**: 한 프로세스만 임계 영역(Critical Section)에 진입하도록 제한한다.
- **한정 대기(Bounded Waiting)**: 특정 프로세스의 임계영역 진입 요청이 무한정 대기하지 않도록 보장한다.
- **진행의 융통성(Progress)**: 임계영역에 진입하려는 프로세스들 간에 방해받지 않고 진입할 수 있도록 보장한다.

구현에는 **세마포어(Semaphore)**, **뮤텍스(Mutex)**, **모니터(Monitor)** 등이 활용됩니다.

<br/>

## 실제 예시

자바스크립트는 기본적으로 싱글스레드 실행 환경이지만, 웹 워커나 Node.js의 멀티 프로세스, 여러 비동기 함수 호출 시 경쟁상태와 유사한 현상이 나타날 수 있습니다.

아래 코드처럼, 비동기적으로 실행되는 함수에서 동기화가 이뤄지지 않으면 counter의 값이 예상과 다르게 나올 수 있습니다.

```jsx
let counter = 0;

function increment() {
  let temp = counter;
  setTimeout(() => {
    counter = temp + 1;
  }, Math.random() * 10);
}

for (let i = 0; i < 10; i++) {
  increment();
}
```

위 코드에서는 increment 함수가 동시에 여러 번 실행되어, 의도한 것과 달리 결과값이 불확실하게 변하는 경쟁상태가 발생합니다.

이 문제를 해결하고자 자바스크립트에서도 뮤텍스 패턴을 직접 구현할 수 있습니다.

예를 들어 Promise를 활용한 뮤텍스 클래스를 아래와 같이 작성하면, 각 함수가 공유 변수에 접근하는 동안에는 다른 함수가 접근하지 못하도록 보장할 수 있습니다.

```jsx
class Mutex {
  constructor() {
    this.locked = false;
    this.waiting = [];
  }
  lock() {
    return new Promise((resolve) => {
      if (!this.locked) {
        this.locked = true;
        resolve();
      } else {
        this.waiting.push(resolve);
      }
    });
  }
  unlock() {
    if (this.waiting.length > 0) {
      const next = this.waiting.shift();
      next();
    } else {
      this.locked = false;
    }
  }
}

const mutex = new Mutex();
let counter = 0;

function safeIncrement() {
  mutex.lock().then(() => {
    let temp = counter;
    setTimeout(() => {
      counter = temp + 1;
      mutex.unlock();
    }, Math.random() * 10);
  });
}

for (let i = 0; i < 10; i++) {
  safeIncrement();
}
```

이처럼 뮤텍스 구조를 적용하면 각 함수가 순차적으로 공유 변수에 접근하게 되어, 경쟁상태로 인한 오류 없이 안정적으로 결과값을 얻을 수 있습니다.

<br/>
<br/>

> 참고
>
> - https://daily-progress.tistory.com/65
> - https://charles098.tistory.com/88
> - https://rlaehddnd0422.tistory.com/242
> - https://luv-n-interest.tistory.com/1203
