# HTTPS
HTTPS(HyperText Transfer Protocol Secure)는 HTTP에 보안 계층인 TLS(Transport Layer Security)를 추가한 프로토콜.
사용자의 정보(비밀번호, 카드번호 등)가 중간자 공격, 도청, 위조로부터 보호한다.

## HTTPS 가 제공하는 보안 기능
1. 기밀성 (Confidentiality) → 데이터를 암호화하여 제3자가 내용을 볼 수 없음
2. 무결성 (Integrity) → 전송 도중 데이터가 변조되지 않았는지 검증
3. 인증 (Authentication) → 서버가 진짜인지 확인 (보안서버 인증서 사용)

## TLS 핸드셰이크
HTTPS 통신을 시작하면, TLS 핸드셰이크라는 과정을 먼저 수행하여 양측이 안전하게 암호화된 통신을 할 수 있도록 설정.

아래는 TLS 1.2 기준의 핸드셰이크 순서

1. 클라이언트 Hello (Client Hello)
    - 클라이언트가 서버에게 인사하며 다음 정보 전송
        - 지원 가능한 암호화 방식(Cipher Suites) 목록
        - TLS 버전
        - 랜덤 값 (Client Random)
        - SNI (서버 도메인 정보)

2. 서버 Hello (Server Hello)
    - 서버가 응답하면서 다음 정보 전송
        - 선택한 암호화 방식
        - 랜덤 값 (Server Random)
    - Certificate 메시지: 서버의 디지털 인증서 (공개키 포함) 전송
    - Server Hello Done 메시지: 서버의 초기 핸드셰이크 메시지 전송 완료

3. 인증서 검증
   - 클라이언트는 서버가 보낸 인증서의 유효성을 확인
        - 유효 기간, 도메인 일치 여부, 신뢰할 수 있는 기관인지 확인

4. Pre-Master Secret 생성
    - 클라이언트가 임시 비밀키(Pre-Master Secret)를 생성하고, 서버 인증서 내의 공개키로 암호화하여 Key Exchange 메시지로 서버에 전송

5. 대칭키 생성 (Master Secret 및 세션 키 파생)
    - 클라이언트와 서버는 Client Random, Server Random, 그리고 Pre-Master Secret을 사용하여 동일한 Master Secret을 생성
    - 이 Master Secret으로부터 실제 데이터를 암호화하는 데 사용될 대칭키(세션 키)를 파생시킴. 이 대칭키를 통해 이후 암호화된 통신이 이루어짐

6. Finished 메시지 교환
    - Change Cipher Spec: 양측이 이제부터 암호화된 통신을 시작할 것임을 알림
    - Finished 메시지: 양측은 위에서 파생된 세션 키를 사용하여 암호화된 "Finished" 메시지를 주고받아 핸드셰이크가 정상적으로 완료되었는지 확인 (이 메시지는 핸드셰이크 전체 기록에 대한 해시 값을 포함하여 무결성 확인)