import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 基于物品的协同过滤算法
 */
public class ItemCF {
    public static void main(String[] args) throws IOException, TasteException {

        long start = System.currentTimeMillis(); // 获取程序开始执行的时间戳
        // 创建DataModel，加载用户评分数据
        DataModel model = new FileDataModel(new File("src/main/resources/ratings.csv"));

        // 创建ItemSimilarity，并计算物品相似度矩阵
        ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);

        // 推荐器
        ItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);

        // 生成推荐列表，获取前n个推荐结果
        // userId 即我们要为哪个用户生成推荐列表
        //List<RecommendedItem> recommendations = recommender.recommend(userId, n);
        List<RecommendedItem> recommendations = recommender.recommend(5, 10);

        // 打印推荐列表
        for (RecommendedItem recommendation : recommendations) {
            System.out.println(recommendation);
        }

        long end = System.currentTimeMillis(); // 获取程序结束执行的时间戳
        long timeUsed = end - start; // 计算程序执行耗时
        System.out.println("程序运行时间：" + timeUsed + "毫秒");
    }
}

