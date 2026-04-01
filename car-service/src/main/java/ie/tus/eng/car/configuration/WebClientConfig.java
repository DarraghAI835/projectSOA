package ie.tus.eng.car.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
//	@Bean
//	public WebClient webClient() {
//		return WebClient.builder()
//				.baseUrl("http://dealership-service:8080")
//				.clientConnector(new ReactorClientHttpConnector(
//					HttpClient.create().responseTimeout(Duration.ofSeconds(5))
//				))
//				.build();
//	}
	@Bean
    public ModelMapper modelMapperBean() {
        return new ModelMapper();
    }
	@Bean
	public WebClient webClient() {
	  return WebClient.builder()
			  .baseUrl("http://dealership-service:8080")
			  .build();
	}
//	@Bean
//	public ShallowEtagHeaderFilter
//	shallowEtagHeaderFilter() {
//		return new ShallowEtagHeaderFilter();
//	}
//	@Bean
//	public FilterRegistrationBean<ShallowEtagHeaderFilter> shallowEtagHeaderFilter(){
//		FilterRegistrationBean<ShallowEtagHeaderFilter> filterRegistrationBean = new FilterRegistrationBean<>(new ShallowEtagHeaderFilter());
//		filterRegistrationBean.addUrlPatterns("/cars");
//		filterRegistrationBean.setName("etagFilter");
//		return filterRegistrationBean;
//	}
}
