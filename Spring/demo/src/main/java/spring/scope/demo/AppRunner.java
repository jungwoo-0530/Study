package spring.scope.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;
//싱글톤일 경우 같은 Proto가 찍힘.
//@Component
//public class AppRunner implements ApplicationRunner {
//
//    @Autowired
//    Single single;
//
//    @Autowired
//    Proto proto;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        System.out.println(proto);
//        System.out.println(single.getProto());
//    }
//}

//현재 Proto는 프로토타입.
@Component
public class AppRunner implements ApplicationRunner{

    @Autowired
    ApplicationContext ctx;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("proto");
        System.out.println(ctx.getBean(Proto.class));
        System.out.println(ctx.getBean(Proto.class));
        System.out.println(ctx.getBean(Proto.class));

        System.out.println("single");
        System.out.println(ctx.getBean(Single.class));
        System.out.println(ctx.getBean(Single.class));
        System.out.println(ctx.getBean(Single.class));


        //문제발생.
        System.out.println("proto by single");

        System.out.println(ctx.getBean(Single.class).getProto());
        System.out.println(ctx.getBean(Single.class).getProto());
        System.out.println(ctx.getBean(Single.class).getProto());

    }
}