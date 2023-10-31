package es;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchAction;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/9/5 9:53
 */
public class EsClient {

    public static RestHighLevelClient getClient(){
        BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();
        basicCredentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic","1JGlYwxQiwuQLukz"));
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("10.31.113.18",9200, "http")
                ).setHttpClientConfigCallback(httpAsyncClientBuilder -> httpAsyncClientBuilder.setDefaultCredentialsProvider(basicCredentialsProvider))
        );
    }

    public static Connection getCon() throws Exception {
        Connection con = null;
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        // mysql驱动
        return (Connection) DriverManager.getConnection("jdbc:mysql://10.31.117.95:3306/ADSStageBusiness?serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull",
                "adsstageuser", "1lh2ytfeikdv6rRa");
    }

    public static void main(String[] args) throws Exception {
//        try(RestHighLevelClient client =getClient()){
//            SearchRequest request = new SearchRequest();
//            SearchSourceBuilder sourceBuilder = SearchSourceBuilder.searchSource();
//            sourceBuilder.fetchSource(true).query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("projectId","480048164093919232"))).from(0).size(10000);
//            request.indices("logstash-lhkf-project").source(sourceBuilder);
//            SearchResponse resp = client.search(request);
//            SearchHit hit = resp.getHits().getHits()[0];
//            System.out.println(hit.getSourceAsString());
//        }
//            bulkInsert();
//        List<Integer> integers = Arrays.asList(5601228, 5601622, 5601634, 5601613, 5601624, 5601615, 5601237, 5601238, 5601236, 5601239, 5601242, 5601625, 5601242, 5601616, 5601619, 5601620, 5601627, 5601618, 5601626, 5601617, 5601621, 5601614);
//        integers.sort(Comparator.reverseOrder());
//        integers.forEach(System.out::println);
        for (int i = 0; i <5; i=++i) {
            System.out.println(i);
        }

    }

    public void a(){
        int a = 1+2;
        return;
    }

    public static void transfer(){
        RestHighLevelClient client = null;
        Connection con = null;
        try {

            client = getClient();
            SearchRequest request = new SearchRequest();
            SearchSourceBuilder sourceBuilder = SearchSourceBuilder.searchSource();
            sourceBuilder.fetchSource(true).query(QueryBuilders.matchAllQuery()).from(0).size(10000);
            request.indices("lhkf-project").source(sourceBuilder);
            SearchResponse resp = client.search(request);
            con = getCon();
            SearchHit[] hits = resp.getHits().getHits();
            for (SearchHit hit : hits) {
                System.out.println(hit.getId() + ","+hit.getSourceAsString());
                try {
                    Statement statement = con.createStatement();
                    boolean execute = statement.execute("insert into es_project (id,json) values (\'" + hit.getId() + "\',\'" + hit.getSourceAsString() + "\')");
                    System.out.println(execute);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){

        }finally {
            if(client != null){
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(con != null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void bulkInsert(){
        try(RestHighLevelClient client = getClient();){
            BulkRequest inserReq = new BulkRequest();
            List<String> list = new ArrayList<>();
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 1单元 1012");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 1单元 1021");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 1单元 1022");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 1单元 1031");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 1单元 1032");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 1单元 1041");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 1单元 1042");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 1单元 1051");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 1单元 1052");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 2单元 2011");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 2单元 2012");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 2单元 2021");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 2单元 2022");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 2单元 2031");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 2单元 2032");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 2单元 2041");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 2单元 2042");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 2单元 2051");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 2单元 2052");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 3单元 3011");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 3单元 3012");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 3单元 3021");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 3单元 3022");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 3单元 3031");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 3单元 3032");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 3单元 3041");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 3单元 3042");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 3单元 3051");
            list.add("鞍山 公园墅 1组团 洋房 B1号楼 3单元 3052");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 1单元 1011");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 1012");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 1021");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 1022");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 1031");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 1032");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 1041");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 1042");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 1051");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 1052");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 2011");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 2012");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 2021");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 2单元 2022");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 1单元 2031");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 1单元 2032");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 1单元 2041");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 1单元 2042");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 1单元 2051");
            list.add("鞍山 公园墅 1组团 洋房 B2号楼 1单元 2052");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 1011");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 1012");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 1021");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 1022");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 1031");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 1032");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 1041");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 1042");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 1051");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 1052");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 2011");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 2012");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 2021");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 2022");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 2031");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 2032");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 2041");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 2042");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 2051");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 2052");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 3011");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 3012");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 3021");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 3022");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 3031");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 3032");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 3041");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 3042");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 3051");
            list.add("鞍山 公园墅 1组团 洋房 B3号楼 1单元 3052");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 1011");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 1012");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 1021");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 1022");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 1031");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 1032");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 1041");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 1042");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 1051");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 1052");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 2011");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 2012");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 2021");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 2022");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 2031");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 2032");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 2041");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 2042");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 2051");
            list.add("鞍山 公园墅 1组团 洋房 B5a号楼 2单元 2052");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 1011");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 1012");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 1021");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 1022");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 1031");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 1032");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 1041");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 1042");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 1051");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 1052");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 2011");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 2012");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 2021");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 2单元 2022");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 2031");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 2032");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 2041");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 2042");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 2051");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 2052");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 3011");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 3012");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 3021");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 3022");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 3031");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 3032");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 3041");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 3042");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 3051");
            list.add("鞍山 公园墅 1组团 洋房 B5号楼 3单元 3052");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 1011");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 1012");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 1021");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 1022");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 1031");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 1032");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 1041");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 1042");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 1051");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 1052");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 2011");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 2012");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 2021");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 2022");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 2031");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 3单元 2032");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 1单元 2041");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 1单元 2042");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 1单元 2051");
            list.add("鞍山 公园墅 1组团 洋房 B6号楼 1单元 2052");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 1011");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 1012");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 1021");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 1022");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 1031");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 1032");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 1041");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 1042");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 1051");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 1052");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 2011");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 2012");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 2021");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 2022");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 2031");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 2032");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 2041");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 1单元 2042");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 2单元 2051");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 2单元 2052");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 2单元 3011");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 2单元 3012");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 2单元 3021");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 2单元 3022");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 2单元 3031");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 2单元 3032");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 2单元 3041");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 2单元 3042");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 2单元 3051");
            list.add("鞍山 公园墅 1组团 洋房 B7号楼 2单元 3052");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 2单元 1011");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 2单元 1012");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 2单元 1021");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 2单元 1022");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 2单元 1031");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 2单元 1032");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 2单元 1041");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 2单元 1042");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 2单元 1051");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 2单元 1052");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 1单元 2011");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 1单元 2012");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 1单元 2021");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 1单元 2022");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 1单元 2031");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 1单元 2032");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 1单元 2041");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 1单元 2042");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 1单元 2051");
            list.add("鞍山 公园墅 1组团 洋房 B8号楼 1单元 2052");
            for(int i = 0; i < list.size();i++){
                inserReq.add(
                        new IndexRequest("room_test_index", "_doc",(i+1)+"")
                        .source(XContentType.JSON,
                                "roomNo", list.get(i),
                                "phoneList",new StringJoiner(
                                        ","
                                ).add("1"+RandomUtil.randomNumbers(10))
                                .add("1"+RandomUtil.randomNumbers(10))
                                .add("1"+RandomUtil.randomNumbers(10))
                                .toString()
                        )
                );
            }

            BulkResponse bulkResponse = client.bulk(inserReq);
            //4、处理响应
            if(bulkResponse != null) {
                for (BulkItemResponse bulkItemResponse : bulkResponse) {
                    DocWriteResponse itemResponse = bulkItemResponse.getResponse();

                    if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.INDEX
                            || bulkItemResponse.getOpType() == DocWriteRequest.OpType.CREATE) {
                        IndexResponse indexResponse = (IndexResponse) itemResponse;
                        System.out.println(indexResponse);

                    } else if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.UPDATE) {
                        UpdateResponse updateResponse = (UpdateResponse) itemResponse;
                        //TODO 修改成功的处理
                        System.out.println(updateResponse);

                    } else if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.DELETE) {
                        DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
                        //TODO 删除成功的处理
                        System.out.println(deleteResponse);
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
