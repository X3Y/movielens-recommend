package home.zion.mahout;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import java.io.File;

public class ItemBasedRecommender {
  public static void main(String[] args) throws Exception{
    Commanders cmds = Commanders.make(args);

    DataModel model = new FileDataModel(new File(cmds.ratingPath()));

    ItemSimilarity similarity = new UncenteredCosineSimilarity(model);
    Recommender rec = new GenericItemBasedRecommender(model, similarity);

    String file = cmds.targetFile("cos-item.recommends");
    S.writeResult(rec, file, cmds.count);
  }
}
