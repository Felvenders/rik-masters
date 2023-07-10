package com.example.rikMasters;

import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "RikMasters services",
                version = "1.0",
                description = "Сервисы для работы с водителями и автомобилями, а также с аккаунтами водителей"
        )
)
public class SwaggerConfig {
    static {
        SpringDocUtils.getConfig().addAnnotationsToIgnore(jakarta.persistence.Entity.class);
    }
}
