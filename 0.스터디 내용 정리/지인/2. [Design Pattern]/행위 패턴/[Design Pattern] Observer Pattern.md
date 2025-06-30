## 옵서버(Observer) 패턴

> 한 객체의 상태가 변화하면, 해당 변화를 구독(subscribe) 중인 다른 객체들에게 자동으로 전달하는 패턴

옵서버 패턴은 주로 분산 시스템에서 이벤트를 발행(publish)-구독(subscribe)-수신(receive)하는 흐름을 구현할 때 사용합니다.

### 구독과 수신

- 구독(Subscribe)
  특정 이벤트 채널(토픽, 큐)에 “이 채널의 메시지를 받고 처리하겠다”는 의사를 메시지 브로커에 등록하는 단계입니다.

```tsx
consumer.subscribe("OrderCreated"); // 'OrderCreated' 토픽 구독 등록
consumer.on("message", handler); // 채널에 새 메시지가 도착하면 이 handler 함수를 실행
```

- 수신(Receive)
  브로커가 해당 채널에 도착한 메시지를 푸시(push) 또는 폴링(polling) 방식으로 전달하면, 등록된 리스너(handler)가 호출되어 실제 데이터를 처리합니다.

### 메시지 브로커(Message Broker)

발행자(Publisher)와 구독자(Subscriber) 사이에 위치해, 메시지의 전송/라우팅/저장/재시도/확인 등을 담당하는 중개 서버입니다.

### 이벤트 채널(Event Channel / Topic)

발행자가 메시지를 보내면 브로커가 이 메시지를 분류/저장해 두는 논리적 파이프입니다.

즉, 메시지 브로커는 ‘발행자 ↔ 구독자’를 이어주는 미들맨이고, 이벤트 채널은 그 안에서 메시지를 분류/전달하는 논리적 통로입니다.

이벤트가 브로커에 도착하면, 브로커는 이를 구독 등록한 모든 서비스에 푸시(또는 서비스가 풀링) 방식으로 전달합니다.

**“구독(Subscribe)”** 은 메시지 브로커(토픽/채널)에 “이 채널의 메시지를 받고 싶다”라고 등록하는 행위이고, **“수신(Receive)”** 은 브로커가 실제 메세지를 전달(push)했을 때, 구독 등록된 클라이언트가 메시지를 받아 처리하는 실행 단계의 행위입니다.

<br/>

### 예제로 구현하기

```tsx
// 1) Subject 역할: 블로그 게시자 클래스
class BlogPublisher {
	constructor() {
		this.subscribers = []; // 구독자 목록
	}

	// 구독자 등록
	subscribe(subscriber) {
		this.subscribers.push(subscriber);
	}

	// 구독 취소
	unsubscribe(subscriber) {
		this.subscribers = this.subscribers.filter(s => s!== subscriber)l
	}

	// 구독자 전체에 새 글 알림
	notify(article) {
		this.subscribers.forEach(sub => sub.update(article)); // 수신 단계
	}

	// 새 글 발행
	publish(article) {
		console.log(`블로그에 새 글 발행: ${article.title}`);
		this.notify(article);
	}
}

// 2) Observer 역할: 독자 클래스
class Reader {
	constructor(name) {
		this.name = name;
	}

	// 블로그에서 새 글이 오면 호출
	update(article) {
		console.log(`${this.name}에게 알림 -> 새 글 "${article.title}" 읽기`);
	}
}

// 3) 사용 예시
const techBlog = new BlogPublisher();

const alice = new Reader('Alice');
const bob = new Reader('Bob');

techBlog.subscribe(alice);
techBlog.subscribe(bob);

techBlog.publish({ title: 'Observer 패턴 이해하기' });
// Alice에게 알림 -> 새 글 "Observer 패턴 이해하기" 읽기
// Bob에게 알림 -> 새 글 "Observer 패턴 이해하기" 읽기

techBlog.unsubscrube(bob);

techBlog.publish({ title: '자바스크립트 예제 추가' });
// Alice에게 알림 -> 새 글 "자바스크립트 예제 추가" 읽기
```

<br/>

### 핵심 장점

1. **낮은 결합도**: 발행자는 구독자의 구체 구현을 몰라도 되므로, 모듈 간 변경이 서로에게 미치는 영향을 최소화합니다.
2. **1→N 이벤트 전파**: 하나의 상태 변화가 다수의 구독자에게 자동으로 전달되어, 다양한 후속 처리를 동시에 수행할 수 있습니다.
3. **동적 구독/해지**: 실행 중에도 구독자를 자유롭게 추가·삭제할 수 있어, 유연한 확장성과 유지보수가 가능합니다.
