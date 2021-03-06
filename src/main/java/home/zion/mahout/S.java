package home.zion.mahout;

import com.google.common.base.Charsets;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

class S {
  static void writeResult(Recommender rec, String fp, int howmany) throws Exception {
    DataModel model = rec.getDataModel();
    FileOutputStream fos = new FileOutputStream(fp);
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, Charsets.UTF_8));
    for (LongPrimitiveIterator iterator = model.getUserIDs(); iterator.hasNext(); ) {
      long uid = iterator.next();
      List<RecommendedItem> reced = rec.recommend(uid, howmany);
      if (!reced.isEmpty()) {
        writer.write(String.valueOf(uid));
        for (RecommendedItem item : reced) {
          writer.write(",");
          writer.write(String.valueOf(item.getItemID())+"="+String.valueOf(item.getValue()));
        }
        writer.newLine();
        System.out.println("" + uid + " " + reced.size());
      }
    }
  }
}
