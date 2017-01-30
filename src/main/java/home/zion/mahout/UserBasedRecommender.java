package home.zion.mahout;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.*;

public class UserBasedRecommender {
  public static void main(String args[]) throws Exception{
    DataModel model = new FileDataModel(new File("/e/ml/ml-latest/ratings-headless.csv"));
    UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
    UserNeighborhood neibor = new NearestNUserNeighborhood(5, similarity, model);
    Recommender rec = new GenericUserBasedRecommender(model, neibor, similarity);

    String file = "/e/ml/ml-latest/user-result.csv";
    S.writeResult(rec, file);
  }
}
