// 패키지 선언: 현재 클래스는 'com.moa.user.config.mapper' 패키지에 위치합니다.
package com.moa.user.config.mapper;

// 필요한 라이브러리 및 클래스들을 임포트합니다.
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration 어노테이션: 이 클래스는 Spring의 Java 기반 설정 클래스임을 나타냅니다.
// 이러한 설정 클래스는 XML 기반의 설정을 대체하여 프로젝트의 구성 요소를 정의하고 구성합니다.
@Configuration
public class ModelMapperConfig {

    // @Bean 어노테이션: 이 메서드가 Spring IoC 컨테이너에 의해 Bean 객체로 생성되어 관리됨을 나타냅니다.
    // Bean 객체는 Singleton으로 생성되어, 어플리케이션 컨텍스트 내에서 공유되며 재사용됩니다.
    @Bean
    public ModelMapper modelMapper(){
        // ModelMapper 객체를 생성하여 반환합니다.
        // ModelMapper는 객체 간의 자동 매핑을 도와주는 라이브러리로,
        // 예를 들면, Entity 객체를 DTO로 변환하거나 그 반대의 작업을 수행할 때 유용합니다.
        return new ModelMapper();
    }
}
