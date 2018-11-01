import com.almybaties.session.AlSqlSession;
import com.almybaties.session.AlSqlSessionFactory;
import com.almybaties.session.AlSqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AlBootStrap {

    private static Logger logger = LoggerFactory.getLogger(AlBootStrap.class);
    private static final String XML_APTH = "C:\\Users\\Administrator\\Desktop\\Mybaties-resource\\AlMybaties\\src\\main\\resources\\mybaties-config.xml";

    public static void main(String[] args){
        //Create SqlSessionFactory
        AlSqlSessionFactory build = new AlSqlSessionFactoryBuilder().build(XML_APTH);
        //Create SqlSession by SqlSessionFactory
        //AlSqlSession alSqlSession = build.openSession();


    }
}
