import com.almybaties.alxmlparser.AlXmlParse;
import com.almybaties.entity.Configuration;
import com.almybaties.jdbc.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class AlBootStrap {

    private static Logger logger = LoggerFactory.getLogger(AlBootStrap.class);

    public static void main(String[] args) throws SQLException {
        AlXmlParse parse = new AlXmlParse();
        //mybaties-config核心配置解析
        Configuration configuration = parse.doParse();
        logger.info("mybaties config has been parsed");

        parse.doParseMapper(configuration.getMapperResource());


        //初始化数据库
        Connection connection = Connector.getConnection(configuration.getJdbcConnectInfo());

        /* //3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
         Statement st = connection.createStatement();
         ResultSet rs=st.executeQuery("select * from chatanddoc");
         //4.处理数据库的返回结果(使用ResultSet类)
         while(rs.next()){
                 System.out.println(rs.getString("username")+" " +rs.getString("username"));
             }*/



    }
}
