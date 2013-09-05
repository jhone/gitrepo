package com.redsun.platf.groovy;

/**
 * Created with IntelliJ IDEA. </br>
 * To change this template use File | Settings | File Templates.</br>
 * User: joker pan</br>
 * Date: 13-7-10</br>
 * Time: 下午12:27</br>
 * ------------------------------------------------------------------------------------</br>
 * Program ID   :                                                                      </br>
 * Program Name :                                                                      </br>
 * ------------------------------------------------------------------------------------</br>
 * <H3> Modification log </H3>
 * <pre>
 * Ver.    Date       Programmer    Remark
 * ------- ---------  ------------  ---------------------------------------------------
 * 1.0     13-7-10    joker pan    created
 * <pre/>
 */

//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {
//        "classpath*:applicationContext.xml",
//        "classpath*:spring/spring-groovy.xml"})

//@Component
public class DefaultBookingService implements IGroovyBookService {
    //    @Resource
    private IMessager messenger;

    public void setMessenger(IMessager messenger) {
        this.messenger = messenger;
    }

    public void testMessage() {

        System.out.println(messenger.getMessage());
    }

    @Override
    public  void service() throws Exception {

//                ApplicationContext ctx = new ClassPathXmlApplicationContext(
//                        new String[]{"classpath*:applicationContext.xml", "classpath*:spring/spring-groovy.xml"}
//                );
//        IMessager messenger = (IMessager) ctx.getBean("messenger");
        System.out.println(messenger.getMessage());
//        System.in.read();
//        System.out.println(messenger.getMessage());
    }
}
