package home.zion.mahout;
import org.apache.mahout.cf.taste.impl.recommender.svd.Factorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDPlusPlusFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;

import java.io.File;

public class SVDPPRecommender {
  public static void main(String[]args)throws Exception{
    Commanders cmds = Commanders.make(args);

    DataModel model = new FileDataModel(new File(cmds.ratingPath()));
    Factorizer factorizer = new SVDPlusPlusFactorizer(model, cmds.features, cmds.iterations);
    Recommender recommender = new SVDRecommender(model, factorizer);

    String file = cmds.targetFile("svd.recommends");
    S.writeResult(recommender, file, cmds.count);
  }
}
