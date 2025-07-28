# ARM 프로시저

ARM 프로시저는 주로 ARM CPU 아키텍처에서 함수 호출 및 반환을 규정하는 ARM Procedure Call Standard를 의미합니다.
별도로 컴파일된 프로그램 간의 호출을 용 용이하게 하는 명령어 규칙 집합입니다. ARM 기반의 저수준 언어에서 사용됩니다.

즉, ARM Processor에서 함수를 호출하는 표준입니다.

## 1. APCS(ARM Procedure Call Standard)

APCS는 ARM 아키텍처에서 프로시저(함수) 호출 시 레지스터 사용, 스택 관리, 매개변수 전달 등을 표준화합니다. 이는 컴파일러나 어셈블러가 일관된 방식으로 코드를 생성하도록 돕습니다. 

### 주요 목적

- 호환성 확보: 다른 컴파일러나 언어 간 상호 운용성을 보장
- 효율성: RISC 기반 ARM의 저전력, 고속 특성을 활용
- 오류 최소화: 스택 오버플로나 레지스터 충돌을 방지

![ARM 프로시저](https://img1.daumcdn.net/thumb/R720x0.q80/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F16473C0E4BBAB46E07)

## 주요 구성요소

APCS의 핵심 규칙은 레지스터 할당, 매개변수 전달, 반환 값 처리 등입니다. ARM은 32비트 또는 64비트 레지스터를 사용하며, 프로시저 호출 시 특정 레지스터를 호출자(caller)와 피호출자(callee) 간에 나누어 관리합니다.

- 레지스터 사용 규칙:
  - 호출자 저장 레지스터 (Caller-saved): r0-r3 (ARMv7 기준). 매개변수 전달과 반환 값에 사용되며, 호출자가 값을 보존해야 합니다.
  - 피호출자 저장 레지스터 (Callee-saved): r4-r11. 피호출 프로시저가 값을 보존합니다.
  - 특수 레지스터: r12 (Intra-procedure call scratch), r13 (스택 포인터, SP), r14 (링크 레지스터, LR, 반환 주소 저장), r15 (프로그램 카운터, PC).
  - 예: 함수 호출 시 첫 4개 매개변수는 r0-r3에 전달되고, 추가 매개변수는 스택에 푸시됩니다.

- 매개변수 전달:
  - 최대 4개의 매개변수는 레지스터(r0-r3)를 통해 전달됩니다.
  - 5개 이상의 매개변수는 스택에 저장되며, 스택은 호출자가 관리합니다.
  - 구조체나 큰 데이터는 포인터로 전달하는 것이 일반적입니다.

- 반환 값 처리:
  - 단일 값은 r0에 반환됩니다.
  - 여러 값은 r0-r3 또는 스택을 사용합니다.
  - LR 레지스터를 통해 호출 주소로 복귀합니다 (예: BX LR 명령어).

- 스택 관리:
  - 스택은 아래로 성장하며, 호출 시 로컬 변수나 오버플로 매개변수를 저장합니다. 
  - 프로시저 진입 시 프롤로그(prologue)에서 스택 프레임을 설정하고, 종료 시 에필로그(epilogue)에서 복원합니다

## 프로시저 호출 과정 예시

```assembly
; 호출자 코드
MOV r0, #5      ; 첫 번째 매개변수
MOV r1, #10     ; 두 번째 매개변수
BL add_func     ; add_func 프로시저 호출 (Branch with Link)

; 피호출자 (add_func) 코드
add_func:
    ADD r0, r0, r1  ; r0 = r0 + r1
    BX LR           ; 반환 (Link Register로 복귀)
```

## Reference

https://shinluckyarchive.tistory.com/292