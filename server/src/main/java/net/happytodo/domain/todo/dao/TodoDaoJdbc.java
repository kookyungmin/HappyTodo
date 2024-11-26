package net.happytodo.domain.todo.dao;

import lombok.RequiredArgsConstructor;
import net.happytodo.domain.todo.dto.Todo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile({ "!jpa" })
@RequiredArgsConstructor
public class TodoDaoJdbc implements TodoDao {
    private final SqlSession sqlSession;
    @Override
    public List<Todo.Domain> findAllDomain() {
        return sqlSession.selectList("findAllDomain");
    }
}
