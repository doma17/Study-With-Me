# CPU 작동원리

CPU(중앙처리장치, Central Processing Unit)는 프로그램 명령어를 해석하고 실행하는 핵심 부품입니다.

![CPU](https://velog.velcdn.com/images/kio0207/post/00df9b21-5a3b-455a-a7c9-0ad7e7a65584/image.png)

## 1. CPU 구성요소

- 연산 장치(ALU, Arithmetic Logic Unit)
	산술 연산(덧셈, 뺄셈 등)과 논리 연산(AND, OR 등)을 수행합니다. 레지스터에서 데이터를 가져와 연산한 후 결과를 다시 레지스터에 저장합니다.
- 제어 장치(Control Unit)
    명령어를 순서대로 실행하도록 전체를 제어합니다. 메모리에서 명령어를 가져와 해독하고, 필요한 신호를 ALU나 다른 장치로 보냅니다.
- 레지스터(Register): 고속 임시 저장 공간으로, 명령어 주소, 데이터, 연산 결과를 보관합니다.
  - PC(Program Counter): 다음 명령어의 메모리 주소를 저장합니다.
  - IR(Instruction Register): 현재 실행 중인 명령어를 저장합니다.
  - MAR(Memory Address Register): 메모리 접근 주소를 저장합니다.
  - MBR(Memory Buffer Register): 메모리에서 읽거나 쓸 데이터를 임시 저장합니다.
  - AC(Accumulator): 연산 결과를 임시 저장합니다.

이 요소들은 CPU 내부 버스를 통해 연결되어 데이터를 주고받습니다

## 2. CPU 동작과정 (명령어 사이클)

CPU는 명령어를 반복적으로 처리하는 '명령어 사이클'을 따릅니다. 이는 Fetch-Decode-Execute-Store 단계로 구성되며, 각 사이클은 클럭 신호에 맞춰 동기화됩니다.
- Fetch (인출): 메모리에서 다음 명령어를 가져옵니다. PC에 저장된 주소를 MAR로 옮기고, 메모리에서 명령어를 MBR로 읽어 IR에 저장합니다. 이후 PC를 증가시켜 다음 명령어를 준비합니다.
- Decode (해독): IR에 있는 명령어를 제어 장치가 분석합니다. 연산 코드(OpCode)와 피연산자(Operand)를 분리해 어떤 연산을 할지 결정합니다.
- Execute (실행): ALU가 실제 연산을 수행합니다. 예를 들어, 덧셈 명령이라면 레지스터에서 데이터를 가져와 계산하고 결과를 AC에 저장합니다.
- Store (저장): 연산 결과를 메모리나 레지스터에 저장합니다. 필요 시 인터럽트(I/O event)를 처리한 후 다음 사이클로 넘어갑니다.

이 과정은 반복되며, 인터럽트가 발생하면 현재 작업을 중단하고 우선 처리 루틴(ISR)을 실행합니다. 인터럽트는 CPU 효율을 높여 비동기 작업(Non-blocking I/O)을 가능하게 합니다

## Reference

https://velog.io/@kio0207/%EC%BB%B4%ED%93%A8%ED%84%B0-%EA%B5%AC%EC%A1%B0-CPU-%EC%9E%91%EB%8F%99%EC%9B%90%EB%A6%AC