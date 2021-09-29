package boot.jungwoo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(HolomanProperties.class)
public class HolomanConfiguration {

    @Bean
    //@ConditionalOnMissingBean
    //이 에노테이션을 사용하면
    //내가 컴포넌트 스캔때 등록이 안되었다면
    //이 빈을 등록해라라는 뜻.
    //컴포넌트 스캔 -> Auto
    public Holoman holoman(HolomanProperties properties){
        Holoman holoman = new Holoman();
        holoman.setHowLong(properties.getHowLong());
        holoman.setName(properties.getName());
        return holoman;
    }

}
