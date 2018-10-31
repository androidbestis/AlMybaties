import com.almybaties.alxmlparser.AlXmlConfigBuilder;
import com.almybaties.entity.Configuration;
import com.almybaties.jdbc.Connector;
import com.almybaties.session.AlSqlSessionFactory;
import com.almybaties.session.AlSqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;


public class AlBootStrap {

    private static Logger logger = LoggerFactory.getLogger(AlBootStrap.class);

    public static void main(String[] args) throws SQLException {

        //初始化数据库
        //Connection connection = Connector.getConnection(configuration.getJdbcConnectInfo());
        /* //3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
         Statement st = connection.createStatement();
         ResultSet rs=st.executeQuery("select * from chatanddoc");
         //4.处理数据库的返回结果(使用ResultSet类)
         while(rs.next()){
                 System.out.println(rs.getString("username")+" " +rs.getString("username"));
         }*/

        AlSqlSessionFactory build = new AlSqlSessionFactoryBuilder().build("C:\\Users\\Administrator\\Desktop\\Mybaties-resource\\AlMybaties\\src\\main\\resources\\mybaties-config.xml");
    }
}
