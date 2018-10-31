package com.almybaties.session.aldefault;

import com.almybaties.entity.Configuration;
import com.almybaties.mapping.AlMappedStatement;
import com.almybaties.session.AlSqlSession;
import com.almybaties.session.RowBounds;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The default implementation for {@link AlSqlSession}
 * Note that this class is not Thread-Safe.
 * @author adonai
 */
public class AlDefaultSqlSession implements AlSqlSession{

    private final Configuration configuration;

    public AlDefaultSqlSession(Configuration configuration){
        this.configuration = configuration;
    }

    public <T> T selectOne(String statement) {
        return this.selectOne(statement,null);
    }

    public <T> T selectOne(String statement, Object parameter) {
        // Popular vote was to return null on 0 results and throw exception on too many.
        List<T> list = this.selectList(statement, parameter);
        if(list.size() == 1){
            return list.get(0);
        }else if(list.size() > 1){
            try {
                throw new Exception("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            return null;
        }
        return null;
    }

    public <E> List<E> selectList(String statement) {
        return this.selectList(statement,null);
    }

    public <E> List<E> selectList(String statement, Object parameter) {
        return this.selectList(statement,parameter,RowBounds.DEFAULT);
    }

    public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
        AlMappedStatement ms = configuration.getMappedStatement(statement);



        return null;
    }


    public void close() throws IOException {

    }
}
