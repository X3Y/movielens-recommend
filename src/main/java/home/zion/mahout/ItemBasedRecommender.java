package home.zion.mahout;

import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.precompute.FileSimilarItemsWriter;
import org.apache.mahout.cf.taste.impl.similarity.precompute.MultithreadedBatchItemSimilarities;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.similarity.precompute.BatchItemSimilarities;

import java.io.File;

public class ItemBasedRecommender {
  public static void main(String[] args) throws Exception{
    DataModel model = new FileDataModel(new File("/e/ml/ml-latest/ratings-headless.csv"));
    ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
    org.apache.mahout.cf.taste.recommender.ItemBasedRecommender rec = new GenericItemBasedRecommender(model, similarity);

    BatchItemSimilarities batch= new MultithreadedBatchItemSimilarities(rec, 5);
    int sims = batch.computeItemSimilarities(2, 1,
        new FileSimilarItemsWriter(new File("/e/ml/ml-latest/item-sim.csv")));
    System.out.println("/e/ml/ml-latest/item-sim.csv "+sims);

    String file = "/e/ml/ml-latest/item-result.csv";
    S.writeResult(rec, file);
  }
}
