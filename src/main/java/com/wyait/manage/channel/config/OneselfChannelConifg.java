package com.wyait.manage.channel.config;

import com.wyait.manage.channel.api.OneselfChannelApi;
import com.wyait.manage.utils.RetrofitUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;

/**
 * @author yeguojian
 * @date on 2018/7/8
 * @description
 */
@Configuration
public class OneselfChannelConifg {
    @Bean
    public Retrofit oneselfRetrofit() {
        return RetrofitUtil.createRetrofit(null, "http://api3.data2share.com/");
    }

    @Bean
    public OneselfChannelApi configOneselfApi() {
        return oneselfRetrofit().create(OneselfChannelApi.class);
    }
}
