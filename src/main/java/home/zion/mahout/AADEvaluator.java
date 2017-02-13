package home.zion.mahout;

import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;

public class AADEvaluator {
  public static void main(String[] args) throws Exception{
    Commanders cmds = Commanders.make(args);
    DataModel model = new FileDataModel(new File(cmds.ratingPath()));

    RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();

    evaluator.evaluate(dataModel -> {
      UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
      UserNeighborhood neibor = new ThresholdUserNeighborhood(0.2, similarity, model);
      return new GenericUserBasedRecommender(model, neibor, similarity);
    }, null, model,0.9, 1.0);
  }
}
