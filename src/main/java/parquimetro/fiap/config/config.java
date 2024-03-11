package parquimetro.fiap.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import parquimetro.fiap.utils.ServiceUtils;

@Configuration

public class config {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ServiceUtils serviceUtils(){return new ServiceUtils();}
}
