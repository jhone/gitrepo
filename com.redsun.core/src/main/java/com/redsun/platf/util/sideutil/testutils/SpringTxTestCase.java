package com.redsun.platf.util.sideutil.testutils;

import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Spring的支持数据库访问,事务控制和依赖注入的JUnit4 集成测试基类,相比Spring原基类名字更短.
 *   
 * 子类需要定义applicationContext文件的位置, 如:
 * @ContextConfiguration(locations = { "/applicationContext-test.xml" })
 * 
 * @author calvin
 */
public abstract class SpringTxTestCase extends AbstractTransactionalJUnit4SpringContextTests {

//    @Autowired

	protected DataSource dataSource;

    @Resource
	@Override
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
		this.dataSource = dataSource;
	}
}
