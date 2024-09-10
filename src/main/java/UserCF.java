import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 基于用户的协同过滤算法
 */
public class UserCF {
    public static void main(String[] args) throws TasteException, IOException {
        long start = System.currentTimeMillis(); // 获取程序开始执行的时间戳

        //HikariConfig config = new HikariConfig();
        //config.setJdbcUrl("jdbc:mysql://localhost/cf");
        //config.setUsername("root");
        //config.setPassword("123");
        //HikariDataSource dataSource = new HikariDataSource(config);
        //
        //DataModel model = new MySQLJDBCDataModel(
        //        dataSource, "test", "userid",
        //        "itemid", "rating", null);


        // 读取用户评分数据
        DataModel model = new FileDataModel(new File("src/main/resources/output.csv"));
        // 计算用户之间的相似度
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        // 找到与用户最相似的K个用户
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(10, similarity, model);
        // 基于用户的协同过滤算法
        UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
        // 为用户推荐物品
        List<RecommendedItem> recommendations = recommender.recommend(13, 10);

        for (RecommendedItem recommendation : recommendations) {
            System.out.println(recommendation);
        }

        long end = System.currentTimeMillis(); // 获取程序结束执行的时间戳
        long timeUsed = end - start; // 计算程序执行耗时
        System.out.println("程序运行时间：" + timeUsed + "毫秒");
    }
}
