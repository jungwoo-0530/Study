package spring.jungwoo.proxyaop;


import org.springframework.stereotype.Service;

//이 메소드들이 실행되는 시간을 측정하는 기능을 real subject를 안건들이고 만드는 것. AOP.
//real subject
@Service
public class SimpleEventService implements EventService {

    @Override
    @PerfLogging//delete에는 기능을 주기 싫으므로.
    public void createEvent() {
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("create an event");
    }

    @Override
    @PerfLogging
    public void publishEvent() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("published an event");
    }

    @Override
    public void deleteEvent(){
        System.out.println("delete an event");
    }

}
