package com.wyait.manage.channel.config;

import com.wyait.manage.channel.api.QianxinDataChannelApi;
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
public class QianXinChannelConifg {
    @Bean
    public Retrofit qinxinRetrofit() {
        return RetrofitUtil.createRetrofit(null, "https://211.95.17.123:29088/");
    }

    @Bean
    public QianxinDataChannelApi configQinXinApi() {
        return qinxinRetrofit().create(QianxinDataChannelApi.class);
    }
}
