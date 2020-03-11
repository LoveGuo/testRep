package com.bjtu.edu.test;

import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.stat.TableStat;
import org.junit.Test;

import java.text.Normalizer;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DruidSqlParse {

    @Test
    public void TestStatements(){
        String sql = "select a.name as aa ,b.age from person a inner join person2 b on a.id = b.id where a.name = 'z3' or (b.age > 11 and sex in (1)) and account between 2 and 3 and name is null";
//        String sql = "select * from (select a.id from app_meeting_room a inner join app_meeting b on a.id=b.meeting_room_id) c  where c.id = '1' and c.name between 1 and 2";
//        String sql = "select * from user where id between #{id1} and #{id2} and name like '%c' and age in (e,l)";
        SQLStatementParser parser = new MySqlStatementParser(sql);
        SQLSelectStatement sqlSelectStatement = (SQLSelectStatement) parser.parseSelect();

        SQLSelect sqlSelect = sqlSelectStatement.getSelect();
        SQLSelectQuery sqlSelectQuery = sqlSelect.getQuery();
        if (sqlSelectQuery instanceof MySqlSelectQueryBlock) {
            SQLSelectQueryBlock mySqlSelectQueryBlock = (MySqlSelectQueryBlock) sqlSelectQuery;
            SQLTableSource from = mySqlSelectQueryBlock.getFrom();
            System.out.println(from);

        }


    }

    @Test
    public void testParseWhere(){
        //解析where分为两种情况：一种是使用如SQLSelectQueryBlock具体块，另一种是使用visitor方式，两种方式需要互相补充
        //block方式解析between等复杂条件比较复杂，block解析条件全面
        //visitor方式解析between等复杂条件方便，解析条件不全面
        String str = "(  ( GLDWH = #{aaaa}-#{bbb} '14000' )  )";

        Pattern pattern = Pattern.compile("#\\{[\\w]*\\}");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            String match = matcher.group();
            match = match.substring(2,match.length() - 1).trim();
            System.out.println(match);
        }
    }



    public static void main(String[] args) {
//        String sql = "select a.name ,b.age from person a inner join person2 b on a.id = b.id where a.name = 'z3' or (b.age > 11 and sex in (1)) and account between 2 and 3 and name is null";
//        String sql = "select * from (select name from app where app.age = '#{age}' ) a where id = '#{id}'";
        String sql = "select * from (select a.id from app_meeting_room a inner join app_meeting b on a.id=b.meeting_room_id) c  where c.id = '1' and c.name in ('1','2')";
        // 新建 MySQL Parser
        SQLStatementParser parser = new MySqlStatementParser(sql);

        // 使用Parser解析生成AST，这里SQLStatement就是AST
//        SQLStatement statement = parser.parseStatement();
        SQLSelectStatement statement = (SQLSelectStatement) parser.parseSelect();
        // 使用visitor来访问AST
        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
        statement.accept(visitor);

        // 从visitor中拿出你所关注的信息
        System.out.println(visitor.getConditions());



    }
}
