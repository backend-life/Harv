package com.nowcoder.community;

import com.nowcoder.community.dao.AlphaDao;
import com.nowcoder.community.dao.AlphaDaoHibernateImpl;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.AlphaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;

import java.text.SimpleDateFormat;

@SpringBootTest
//希望启动测试类进行测试的时候也能启动上面的配置类CommunityApplication，就相当于启动测试类的时候也执行了CommunityApplication类里面的main方法
@ContextConfiguration(classes = CommunityApplication.class)

//(4)如何获得Spring容器？
// 哪个类想得到Spring容器，哪个类就实现这个接口，然后重写setApplicationContext方法,ApplicationContext接口就是Spring容器，
// 如果有一个类实现了ApplicationContextAware 接口的setApplicationContext方法，Spring容器建立的时候就会扫描到CommunityApplicationTests这个类，
// 然后把Spring容器作为实参传入这个方法的ApplicationContext形参,我们还需要在这个类创建一个属性来维护Sprin容器
class CommunityApplicationTests implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	//这个单元测试用来测试Spring容器第一个作用
	@Test
	public void testApplicationContext() {

		System.out.println(applicationContext);


		AlphaDao alphaDao = applicationContext.getBean(AlphaDao.class);
		System.out.println(alphaDao.select());

		//(5)IOC的好处?
		// 比如我AlphaDao有两个实现类，我想从AlphaDaoHibernateImpl切换到获取AlphaDaoMyBatisImpl实现类的bean实例，我只需要在AlphaDaoMyBatisImpl上面加一个@Primary，其他地方不用动
		//但如果有某一个地方就是想获取AlphaDaoHibernateImpl的bean实例，那么可以用getBean的第二种方法

		alphaDao = applicationContext.getBean("alphaHibernate", AlphaDao.class);
		System.out.println(alphaDao.select());
	}

	//这个单元测试用来测试Spring容器管理bean的初始化和销毁方法，第二个作用
	@Test
	public void testBeanManagement() {
		AlphaService alphaService = applicationContext.getBean(AlphaService.class);
		System.out.println(alphaService);

		alphaService = applicationContext.getBean(AlphaService.class);
		System.out.println(alphaService);
	}



	@Autowired
	@Qualifier("alphaHibernate")
	private AlphaDao alphaDao;

	@Autowired
	private AlphaService alphaService;

	@Autowired
	private SimpleDateFormat simpledf;


	//这个单元测试是测试依赖注入，第三个作用
	@Test
	public void testDI() {
		System.out.println(alphaDao);
		System.out.println(alphaService);
		System.out.println(simpledf);
	}

	@Autowired
	private UserMapper userMapper;


	//    测试Mapper接口中的查询方法
	@Test
	public void testSelectUser() {
		User user = userMapper.selectById(101);
		System.out.println(user);

		user = userMapper.selectByName("liubei");
		System.out.println(user);

		user = userMapper.selectByEmail("nowcoder101@sina.com");
		System.out.println(user);
	}
}
