package org.automation.atfs;


import org.automation.atfs.utils.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication()
@ComponentScan(basePackages ={ "org.automation.atfs"})
public class AtfsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtfsApplication.class, args);
	}

//	@RequestMapping(value = "/test",method = RequestMethod.GET)
//	public String test() throws Exception{
//		System.out.println("1111");
//		return SpringContextUtil.getBean("testbean").toString();
//	}
}
