# HTTP 버전 개요

---

## HTTP란 무엇인가
HTTP는 'Hypertext Transfer Protocol'의 줄임말이며 월드 와이드 웹(world-wide web, WWW, 인터넷과 햇갈리지 맙시다.)에서 데이터를 소통하는 방법입니다. 여기엔 3개의 주요 버전이 있으며 하나하나를 살펴보겠습니다.

간단하게 생각합시다. 프로토콜(통신 규약)은 브라우저가 서버에게 데이터를 주고 받는 방법을 정의하고 있습니다. 모든 버전의 브라우저 프로토콜은 무언가를 요청(request)하고 서버는 데이터를 전달 또는 응답(response)함을 규정하고 있습니다. 매우 간단한 개념이지만 시간이 지날수록 점점 복잡해졌으며 웹개발자로서 추후에 출시된 버전의 프로토콜이 어떻게 다른지 이해하는것도 중요해졌습니다.

## HTTP/1.0
- 1996년에 발표된 최초의 널리 사용된 HTTP 버전입니다.
- 요청마다 새로운 TCP 연결을 생성하여 비효율적이었습니다.
- 기본적으로 비연결성(stateless)이며, 요청과 응답 후 연결을 종료합니다.
- 캐싱, 요청 메서드(GET, POST, HEAD) 등의 기본 기능을 포함합니다.

## HTTP/1의 단점
상당히 간단하고 이해하기 쉬운 흐름입니다. 하지만 여기엔 몇기지 단점들이 있습니다.

1. 줄막힘: 각각의 HTTP/1.x 연결들은 오직 한번에 한가지 요청밖에 수행을 못합니다. 이러한 제한은 종종 네트워크 리소스의 비효율적인 사용으로 이어졌습니다. 그 후의 요청들은 이전의 요청이 완료되길 기다려야합니다.
2. 우선 순위 지정 부족: HTTP/1.x은 우선 순위를 두고 요청을 하지 않습니다. 덜 중요한 자료들이 중요한 자료들 보다 먼저 올 수 있습니다.
3. 특히 쿠키 사용 시 일반 텍스트 헤더가 커질때 문제가 생깁니다.
현대 웹 사용 시 위 같은 문제들은 큰 문제를 야기합니다.

---

## HTTP/1.1

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FcR2W5a%2FbtrYroBitLm%2FAAAAAAAAAAAAAAAAAAAAAEpbaggTHYrCo9ok0eq0uinyKraxSok1rkgH8-5T1WCI%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1753973999%26allow_ip%3D%26allow_referer%3D%26signature%3Dqp%252FXaKc3tGu5db3ell6VwYk7Ezo%253D)

- 1997년에 발표되어 HTTP/1.0의 한계를 개선했습니다.
- 지속 연결(Persistent Connection)을 지원하여 여러 요청을 하나의 TCP 연결로 처리할 수 있습니다.

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2Fb3B2zV%2FbtrYj0CzI8t%2FAAAAAAAAAAAAAAAAAAAAABfwWynBJhILS9yRLZf0m7Hz0fDEr_Mnr9c9pWQIkNOE%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1753973999%26allow_ip%3D%26allow_referer%3D%26signature%3D5%252B1zcGiH6EG4uWGnot4u0VHt0Lw%253D)

- 파이프라이닝(Pipelining) 기능으로 요청을 연속적으로 보낼 수 있지만, 널리 사용되지는 않았습니다.
- 추가 헤더와 캐싱 메커니즘, 호스트 헤더 지원 등 다양한 기능이 확장되었습니다.

---

## HTTP/2

- 2015년에 표준화된 프로토콜로, 성능 향상에 중점을 두었습니다.
- 바이너리 프레이밍 계층을 도입하여 효율적인 데이터 전송이 가능합니다.
- 멀티플렉싱을 지원하여 하나의 연결에서 여러 요청과 응답을 동시에 처리합니다.
- 헤더 압축(HPACK)을 통해 오버헤드를 줄이고, 서버 푸시 기능도 포함합니다.

HTTP2는 2015년에 출시되었다. HTTP2의 목표는 이전에 발생했던 문제들을 해결하는 것이다.
HTTP1.1이 군림하는 동안 웹은 16년동안 많은 변화가 있었고, 사람들은 조금은 덜 안정적인 4G 통신을 사용하는 모바일 기기를 사용하며 유선은 90년대에 비호 큰 처리가 가능하게 되었다. 하지만 웹사이트 또한 그들이 사용 되던 것에 비해 큰 비교가 생겼다.

웹의 평균 크기는 이전과 비교해서 매우 거대해졌다. 추가적으로, 웹 페이지는 우리의 연결 속도가 향상 되는 만큼 적제되는 자료의 양도 커지고 있다. HTTP 아카이브에 따르면 웹페이지의 이미지 사이즈는 8,000%나 커졌으며 이미지의 개수 또한 마찬가지입니다. 아직 자바스크립트나 기타 자료들을 살펴보기도 전입니다.
그래서 HTTP/2는 어떻게 할까요?
몇가지의 특징을 소개할 것이지만, 사람들이 집중할만한 주요 장점인 '멀티플렉싱'이 있습니다.

## 멀티플렉싱
- HTTP1를 예시로 봅시다. 당신의 브라우저가 다수의 문서를 요청했습니다. HTTP1은 실행 과 요청 한번에 한번씩 합니다.
- HTTP2의 방식은 조금 다릅니다. 브라우저는 멀티플렉싱을 사용하여 같은 연결선상에서 효과적으로 자료들을 '같이' 요청하며 같은 방법으로 자료를 받습니다.

![](https://velog.velcdn.com/images/404/post/33b1e416-5a6b-468f-82eb-304e512767da/image.png)


---

## HTTP/3
HTTP3은 TCP를 사용하지 않는다. 대신 Quick UDP Internet Connections라는 UDP 버전을 사용합니다.

QUIC는 몇가지 장점이 있습니다.

1. 내장 암호화 기능: QUIC는 Transport Layer Security (TLS) 1.3을 기본으로 통합이 되어있습니다. TLS의 핸드셰이크 분리가 필요없이 확실한 보안 연결을 보장합니다. 이것은 지연 시간을 줄여주고 연결 준비 시간도 줄여줍니다.
2. 헤드오브라인블로킹을 줄여준다: TCP와 달리 QUIC는 개별 스트림 수준에서 패킷 손실을 처리합니다. 이 뜻은 패킷 하나를 손실해도 전체 연결에 영향을 주지 않습니다. 그래서 헤드오브라인 차단 문제가 더욱 줄어듭니다.
3. 커넥션 마이그레이션: QUIC는 클라이언트 IP 주소 변경시 연결 끊김 및 연결 지연 현상이 없도록 커넥션 마이그레이션 지원이 잘 되도록 설계되었습니다. 모바일 또는 연결 셀룰러 현상이 이 여기서 장점이 될것입니다.
4. 0-RTT 연결 장착: QUIC는 특정 상황에서 0-RTT (zero round trip time)연결이 설립되며 이것은 이전 방분 서버 연결시 지연 시간을 매우 줄여준다.
5. 혼잡 컨트롤 향상: QUIC는 더욱 발전된 혼잡 제어 메커니즘을 제공하며, 다양한 네트워크 연결과 모든 행위들을 향상시킵니다.

이론적으로는 좋지만, 문제는 이 기능을 넣을 브라우저와 공급자의 큰 임무가 있습니다. 네트워크 인프라와의 호환성 문제는 말할 것도 없습니다.

많은 네트워크들은 UDP를 지원 안하며, UDP위에 있는 새로운 프로토콜 위에 트래픽을 연결하는것은 불가능합니다.
