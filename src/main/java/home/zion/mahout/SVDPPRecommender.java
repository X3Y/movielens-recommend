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
    DataModel model = new FileDataModel(new File("/e/ml/ml-latest/rating"));
    Factorizer factorizer = new SVDPlusPlusFactorizer(model, 10, 30);
    Recommender recommender = new SVDRecommender(model, factorizer);
    String file = "/e/ml/ml-latest/svd-result.csv";
    S.writeResult(recommender, file);
  }
}
