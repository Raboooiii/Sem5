
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MatrixMultiply {

    // Mapper Class
    public static class MatrixMultiplyMapper extends Mapper<Object, Text, Text, Text> {
        private int n; // Number of columns in A / rows in B
        private int p; // Number of columns in B

        @Override
        protected void setup(Context context) {
            Configuration conf = context.getConfiguration();
            n = conf.getInt("matrix.n", 0);
            p = conf.getInt("matrix.p", 0);
        }

        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            // Input format: MatrixName,i,j,value
            // e.g. A,0,1,5  or B,1,0,7
            String[] parts = value.toString().split(",");
            if(parts.length != 4) return;

            String matrixName = parts[0];
            int i = Integer.parseInt(parts[1]);
            int j = Integer.parseInt(parts[2]);
            String val = parts[3];

            if(matrixName.equals("A")) {
                // Emit (i,k) keys for all columns k in B (0..p-1)
                for (int k = 0; k < p; k++) {
                    Text outputKey = new Text(i + "," + k);
                    // Tag value with 'A' and column j of A
                    Text outputValue = new Text("A," + j + "," + val);
                    context.write(outputKey, outputValue);
                }
            } else if(matrixName.equals("B")) {
                // Emit (i,k) keys for all rows i in A (0..m-1)
                // For matrix B, we do not know m here, so assume mapper runs after knowing m as well.
                // We'll just emit (i,j) keys with all rows i from 0 to m-1 passed as config parameter.

                int m = context.getConfiguration().getInt("matrix.m", 0);

                for (int row = 0; row < m; row++) {
                    Text outputKey = new Text(row + "," + j);
                    // Tag value with 'B' and row i of B
                    Text outputValue = new Text("B," + i + "," + val);
                    context.write(outputKey, outputValue);
                }
            }
        }
    }

    // Reducer Class
    public static class MatrixMultiplyReducer extends Reducer<Text, Text, Text, Text> {

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            // key = i,j
            // values = list of A and B values tagged with row/col indexes

            HashMap<Integer, Double> mapA = new HashMap<>();
            HashMap<Integer, Double> mapB = new HashMap<>();

            for (Text val : values) {
                String[] parts = val.toString().split(",");
                String matrixName = parts[0];
                int index = Integer.parseInt(parts[1]);
                double v = Double.parseDouble(parts[2]);

                if(matrixName.equals("A")) {
                    mapA.put(index, v);
                } else if(matrixName.equals("B")) {
                    mapB.put(index, v);
                }
            }

            // Multiply matching indices
            double result = 0;
            for(Integer k : mapA.keySet()) {
                double aVal = mapA.get(k);
                double bVal = mapB.getOrDefault(k, 0.0);
                result += aVal * bVal;
            }

            context.write(key, new Text(Double.toString(result)));
        }
    }

    // Driver Main Method
    public static void main(String[] args) throws Exception {
        if(args.length != 5) {
            System.err.println("Usage: MatrixMultiply <input path> <output path> <m> <n> <p>");
            System.exit(-1);
        }

        Configuration conf = new Configuration();
        conf.setInt("matrix.m", Integer.parseInt(args[2])); // Rows in A
        conf.setInt("matrix.n", Integer.parseInt(args[3])); // Columns in A / Rows in B
        conf.setInt("matrix.p", Integer.parseInt(args[4])); // Columns in B

        Job job = Job.getInstance(conf, "Matrix Multiply");
        job.setJarByClass(MatrixMultiply.class);

        job.setMapperClass(MatrixMultiplyMapper.class);
        job.setReducerClass(MatrixMultiplyReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
