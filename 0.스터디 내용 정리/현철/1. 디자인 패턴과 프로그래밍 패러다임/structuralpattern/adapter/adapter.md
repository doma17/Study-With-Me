# 어댑터 패턴이란?

해외에 나가 휴대폰을 충전할려고 하면 보통 돼지코라고 부르는 어댑터를 끼우게 된다. 어댑터 패턴 또한 이와 같은 역할을 한다. 서로 호환되지 않는 인터페이스를 가진 클래스들을 연결해주는 디자인 패턴으로
기존 코드를 변경하지 않고 새로운 코드와 연결할 수 있게 해준다.

아래에서 Player를 예시로 알아보자.
# 문제 상황
 - CD : AUDIO 출력. 리모컨과 버튼 두 컨트롤러 보유
 - VCR : RCA 출력. 리모컨만 있음
 - VHS : RCA 출력. 리모컨과 녹화 기능 있음.

 세가지 서로 다른 기기가 있는데 이를 동일한 방식으로 제어할려고 한다.

우선 Player 인터페이스를 도입하여 모든 기기를 동일한 방식으로 제어할 수 있도록 하자.
```java
public interface Player {
    void controller();
    void output();
    int remoteController(int onDevice);
    void powerSwitch();
}
```
```java
public class CD {
    Output output;
    Controller remote;
    Controller button;
    boolean powerSwitch;

    public CD() {
        this.output = Output.AUDIO;
        this.remote = Controller.REMOTE;
        this.button = Controller.BUTTON;
        this.powerSwitch = false;
    }

    public void getOutput() {
        System.out.println("CD의 출력형식은 " + this.output + "입니다.");
    }

    public void getController() {
        System.out.println("CD의 컨트롤러는 " + this.remote + ", " + this.button + "입니다.");
    }

    public void setPowerSwitch(boolean powerSwitch) {
        this.powerSwitch = powerSwitch;
    }

    public boolean isPowerSwitch() {
        return powerSwitch;
    }
}
```
CD 플레이어를 어디서든 사용할 수 있게 Player 인터페이스를 구현하여 어댑터를 만든다.
```java
public class CDAdapter implements Player {
    CD cd;

    public CDAdapter(CD cd) {
        this.cd = cd;
    }

    @Override
    public void controller() {
        cd.getController();
    }

    @Override
    public void output() {
        if (cd.isPowerSwitch()) {
            cd.getOutput();

            return;
        }

        System.out.println("전원이 꺼져있습니다.");
    }

    @Override
    public int remoteController(int onDevice) {
        if (onDevice == 2) {
            throw new NotEnoughEnergy();
        }
        if (cd.isPowerSwitch()) {
            cd.setPowerSwitch(false);

            return onDevice - 1;
        }

        cd.setPowerSwitch(true);

        return onDevice + 1;
    }

    @Override
    public void powerSwitch() {
        if (cd.isPowerSwitch()) {
            System.out.println("전원이 켜졌습니다.");
        } else {
            System.out.println("전원이 꺼졌습니다.");
        }
    }
}

public class VCRAdapter implements Player {
    VCR vcr;

    public VCRAdapter(VCR vcr) {
        this.vcr = vcr;
    }

    @Override
    public void controller() {
        vcr.getController();
    }

    @Override
    public void output() {
        if (vcr.isPowerSwitch()) {
            vcr.getOutput();

            return;
        }

        System.out.println("전원이 꺼져있습니다.");
    }

    @Override
    public int remoteController(int onDevice) {
        if (onDevice == 2) {
            throw new NotEnoughEnergy();
        }
        if (vcr.isPowerSwitch()) {
            vcr.setPowerSwitch(false);

            return onDevice - 1;
        }

        vcr.setPowerSwitch(true);

        return onDevice + 1;
    }

    @Override
    public void powerSwitch() {
        if (vcr.isPowerSwitch()) {
            System.out.println("전원이 켜졌습니다.");
        } else {
            System.out.println("전원이 꺼졌습니다.");
        }
    }
}

...
```
각 어댑터는 내부에 원래 기기 인스턴스를 갖고 있고, Player 인터페이스의 요구사항을 해당 기기 방식에 맞게 구현한다.
```java
public class NoteBook {
    public static void main(String[] args) throws IOException {
        CD cd = new CD();
        VCR vcr = new VCR();
        VHS vhs = new VHS();
        int onDevice = 0;
        Player[] players = new Player[3];

        players[0] = new CDAdapter(cd);
        players[1] = new VCRAdapter(vcr);
        players[2] = new VHSAdapter(vhs);

        try {
            onDevice = players[0].remoteController(onDevice);
            players[0].output();
            players[1].output();
            onDevice = players[1].remoteController(onDevice);
            players[1].output();
            onDevice = players[2].remoteController(onDevice);
        } catch (NotEnoughEnergy notEnoughEnergy) {
            System.out.println("너무 많은 화면이 동작 중입니다.");
        }
    }
}

```
클라이언트는 어떤 기기를 사용하는지 몰라도 Player 인터페이스만 알고 있으면 작동시킬 수 있다.

# 마무리
어댑터 패턴은 서로 다른 인터페이스를 가진 클래스들을 연결해주는 디자인 패턴이다. 기존 코드를 변경하지 않고, 새로운 인터페이스에 맞춰 중간 연결자(Adapter) 를 두어 재사용성과 유연성을 확보할 수 있다.

이번 예제에서는 CD, VCR, VHS처럼 동작 방식이 제각각인 기기들을 Player 인터페이스로 통일해 제어했다.
어댑터를 통해 각 기기의 전원 관리, 출력 방식, 컨트롤러 정보도 클라이언트 코드에서는 일관된 방식으로 다룰 수 있게 되었다.

어댑터는 CD와 Player 인터페이스를 연결하고 그 결과로 NoteBook(클라이언트)이 CD를 직접 알 필요없이 Player 인터페이스를 통해 제어할 수 있게 되었다.


