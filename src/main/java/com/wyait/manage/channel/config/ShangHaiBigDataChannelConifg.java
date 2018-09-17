package com.wyait.manage.channel.config;

import com.wyait.manage.channel.api.ShangHaiBigDataChannelApi;
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
public class ShangHaiBigDataChannelConifg {
    @Bean
    public Retrofit retrofit() {
        return RetrofitUtil.createRetrofit(null, "https://211.95.17.123:29088/");
    }

    @Bean
    public ShangHaiBigDataChannelApi configApi() {
        return retrofit().create(ShangHaiBigDataChannelApi.class);
    }
}
