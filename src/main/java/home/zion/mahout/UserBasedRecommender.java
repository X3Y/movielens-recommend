package home.zion.mahout;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CachingUserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;

public class UserBasedRecommender {
  public static void main(String args[]) throws Exception{
    Commanders cmds = Commanders.make(args);

    DataModel model = new FileDataModel(new File(cmds.ratingPath()));

    //UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
    UserSimilarity similarity = new CachingUserSimilarity(new EuclideanDistanceSimilarity(model), model);
    UserNeighborhood neibor = new NearestNUserNeighborhood(cmds.neibors,
        cmds.neibor_min,
        similarity,
        model,
        cmds.sampling_rate);
    Recommender rec = new GenericUserBasedRecommender(model, neibor, similarity);

    String file = cmds.targetFile("euc-nnu-user.recommends");
    S.writeResult(rec, file, cmds.count);
  }
}
