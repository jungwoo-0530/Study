//package spring.jungwoo.proxyaop;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Service;
//
//@Primary
//@Service
//public class ProxyEventService implements EventService{
//
//    @Autowired
//    SimpleEventService simpleEventService;
//    //EventService simpleEventService; 두개 다 가능.
//    //proxy가 위임해서 해결.
//    @Override
//    public void createEvent() {
//        long begin = System.currentTimeMillis();
//        simpleEventService.createEvent();
//        System.out.println(System.currentTimeMillis() - begin);
//    }
//
//    @Override
//    public void publishEvent() {
//        long begin = System.currentTimeMillis();
//        simpleEventService.publishEvent();
//        System.out.println(System.currentTimeMillis() - begin);
//    }
//
//    @Override
//    public void deleteEvent() {
//        simpleEventService.deleteEvent();
//    }
//}

//////이렇게 할 수 있으나 문제.
//@AOP를 사용. PerfAspect