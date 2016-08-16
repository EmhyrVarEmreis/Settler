package pl.morecraft.dev.settler.service.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class GsonBeansService {

    @Bean(name = "gson")
    public Gson getGson() {
        return new GsonBuilder().serializeNulls().create();
    }

    @Bean(name = "prettyGson")
    public Gson getPrettyGson() {
        return new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    }

}
