# HTTP
HTTP(HyperText Transfer Protocol)는 웹에서 데이터를 주고받기 위한 대표적인 통신 규약

## HTTP/1.0
- 요청마다 새로운 TCP 연결 생성
- `GET`, `POST`, `HEAD` 메서드 지원
- 비효율적인 연결 방식 (매 요청마다 TCP 연결/종료 반복)
- 캐싱, 가상 호스팅, 압축 미지원

## HTTP/1.1
- Persistent Connection (Keep-Alive) 지원 → 연결 재사용 가능
- Host 헤더 필수 → 하나의 IP에 여러 도메인 연결 가능 (가상 호스팅)
- 파이프라이닝 도입 (연속 요청 가능하지만, 응답 순서 제한으로 실효성 낮음)
- PUT, DELETE, OPTIONS 등 메서드 추가
- 캐싱, 압축, 부분 콘텐츠 전송 (Range 요청) 등 다양한 기능 추가
- HOL Blocking 발생

## HTTP/2
- 텍스트 기반 → 이진(Binary) 프레이밍 구조로 전환
- 멀티플렉싱(Multiplexing) 지원
- 하나의 TCP 연결로 동시에 여러 요청/응답 처리
- 헤더 압축 (HPACK) 적용 → 대용량 헤더 전송 비용 절감
- 서버 푸시(Server Push) 기능 도입 → 서버가 클라이언트 요청 없이 리소스 푸시
- 브라우저는 대부분 HTTPS에서만 HTTP/2 사용 가능
- TCP의 한계로 인한 HOL Blocking 발생

## HTTP/3
- 기존 TCP → QUIC(UDP 기반 프로토콜)로 변경
- TLS 1.3 기본 포함 → 빠르고 안전한 암호화 연결
- 연결 복원 및 지연 최소화에 강함 → 모바일 환경 등에서 유리
- 멀티플렉싱 시 헤드 오브 라인 블로킹 문제 해결
- 최신 브라우저와 CDN(Cloudflare, Google 등) 중심으로 점진적 확산 중

## Head-of-Line Blocking(HOL Blocking)
HOL Blocking은 앞선 요청이 지연되면 뒤따르는 요청들도 함께 지연되는 현상을 말함.

### HTTP/1.1 에서의 HOL Blocking
- HTTP/1.1은 기본적으로 한 연결당 하나의 요청/응답만 처리
- 파이프라이닝을 도입했지만, 응답 순서를 보장해야 하기 때문에 앞선 요청이 지연되면 뒤에 있는 요청들도 기다려야함
- 즉, 응답 순서 제약 때문에 멀티플렉싱이 제대로 되지 않고, HOL Blocking 현상이 심각하게 발생

### HTTP/2 에서의 HOL Blocking - 부분 해소
- HTTP/2는 멀티플렉싱을 통해 한 TCP 연결 내에서 여러 요청을 동시에 처리할 수 있게 되었지만 TCP 자체의 한계로 인해 여전히 HOL Blocking 문제가 존재
    - 예: 하나의 패킷이 손실되면 해당 패킷 이후의 데이터도 도착할 때까지 모두 대기해야 함 (TCP의 순서 보장 특성 때문)
- 결과적으로, HTTP 레벨에서는 해결됐지만, 전송 계층에서는 문제 지속

### HTTP/3 에서의 HOL Blocking - 해결
- HTTP/3는 TCP가 아닌 QUIC(UDP 기반) 프로토콜을 사용
- QUIC은 스트림 단위로 독립적인 데이터 전송이 가능하여 하나의 스트림이 지연되어도 다른 스트림에 영향이 없음