package org.example;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MaxTemperature {

    // The Mapper class
    public static class MaxTempMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

        private Text year = new Text();
        private IntWritable temperature = new IntWritable();

        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            // Assuming the year is the first 4 characters and temp is at a fixed position
            // A more robust solution would use split(), but this is simple for an example.
            String yearStr = line.substring(0, 4);
            int tempValue = Integer.parseInt(line.substring(5).trim());

            year.set(yearStr);
            temperature.set(tempValue);

            // Emit (year, temperature)
            context.write(year, temperature);
        }
    }

    // The Reducer class
    public static class MaxTempReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

        private IntWritable result = new IntWritable();

        @Override
        public void reduce(Text key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException {

            int maxTemp = Integer.MIN_VALUE;

            // Iterate through all temperatures for the given year (key)
            for (IntWritable val : values) {
                if (val.get() > maxTemp) {
                    maxTemp = val.get();
                }
            }

            result.set(maxTemp);
            // Emit (year, max_temperature)
            context.write(key, result);
        }
    }

    // The driver (main method)
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: MaxTemperature <input path> <output path>");
            System.exit(-1);
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Max Temperature");

        job.setJarByClass(MaxTemperature.class);
        job.setMapperClass(MaxTempMapper.class);
        // We can use a combiner for this job, as finding the max is associative.
        // The combiner runs on the map-side and reduces network traffic.
        job.setCombinerClass(MaxTempReducer.class);
        job.setReducerClass(MaxTempReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
