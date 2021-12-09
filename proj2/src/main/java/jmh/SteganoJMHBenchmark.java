package jmh;

import org.openjdk.jmh.annotations.*;
import steg.Stegano;
import steg.SteganoLSB;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SteganoJMHBenchmark {

    @State(Scope.Thread)
    public static class EncodeExecutionPlan2000x600 {

        @Param({"2000x600_BW_MIX.png",
                "2000x600_COLOR_MIX.png",
        })
        public String source;

        @Param({"2000x600_BW_MOUNTAINS.png",
                "2000x600_COLOR_MOUNTAINS.png"
        })
        public String secret;

        private final Stegano stegano = new SteganoLSB();
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Threads(3)
    @Measurement(time = 1, timeUnit = TimeUnit.SECONDS, iterations = 1)
    @Warmup(iterations = 1, timeUnit = TimeUnit.MILLISECONDS, time = 1)
    public void encode2000x600THRPT(EncodeExecutionPlan2000x600 executionPlan) throws IOException {
        String path = "proj2/src/test/to_test_pics/2000x600/";
        BufferedImage source = ImageIO.read(new File(path + executionPlan.source));
        BufferedImage secret = ImageIO.read(new File(path + executionPlan.secret));

        executionPlan.stegano.encode(source, secret);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Measurement(time = 1, timeUnit = TimeUnit.SECONDS, iterations = 1)
    @Warmup(iterations = 1, timeUnit = TimeUnit.MILLISECONDS, time = 1)
    public void encode2000x600AVG(EncodeExecutionPlan2000x600 executionPlan) throws IOException {
        String path = "proj2/src/test/to_test_pics/2000x600/";
        BufferedImage source = ImageIO.read(new File(path + executionPlan.source));
        BufferedImage secret = ImageIO.read(new File(path + executionPlan.secret));

        executionPlan.stegano.encode(source, secret);
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @Measurement(time = 1, timeUnit = TimeUnit.SECONDS, iterations = 1)
    @Warmup(iterations = 1, timeUnit = TimeUnit.MILLISECONDS, time = 1)
    public void encode2000x600AVGSPLT(EncodeExecutionPlan2000x600 executionPlan) throws IOException {
        String path = "proj2/src/test/to_test_pics/2000x600/";
        BufferedImage source = ImageIO.read(new File(path + executionPlan.source));
        BufferedImage secret = ImageIO.read(new File(path + executionPlan.secret));

        executionPlan.stegano.encode(source, secret);
    }

    @State(Scope.Thread)
    public static class DecodeExecutionPlan200x200 {


        @Param({"200x200_BW_COLOR_COWS.png",
                "200x200_COLOR_HORSE.png"
        })
        public String secret;

        private final Stegano stegano = new SteganoLSB();
    }

    @State(Scope.Thread)
    public static class DecodeExecutionPlan2000x600 {


        @Param({"2000x600_BW_MIX.png",
                "2000x600_COLOR_MIX.png",
        })
        public String secret;

        private final Stegano stegano = new SteganoLSB();
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Threads(3)
    @Measurement(time = 1, iterations = 1)
    @Warmup(iterations = 1, timeUnit = TimeUnit.MILLISECONDS, time = 1)
    public void decode200x200THRPT(DecodeExecutionPlan200x200 executionPlan) throws IOException {
        String path = "proj2/src/test/to_test_pics/200x200/";
        BufferedImage secret = ImageIO.read(new File(path + executionPlan.secret));

        executionPlan.stegano.decode(secret);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Measurement(time = 1, iterations = 1)
    @Warmup(iterations = 1, timeUnit = TimeUnit.MILLISECONDS, time = 1)
    public void decode200x200AVG(DecodeExecutionPlan200x200 executionPlan) throws IOException {
        String path = "proj2/src/test/to_test_pics/200x200/";
        BufferedImage secret = ImageIO.read(new File(path + executionPlan.secret));

        executionPlan.stegano.decode(secret);
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @Measurement(time = 1, iterations = 1)
    @Warmup(iterations = 1, timeUnit = TimeUnit.MILLISECONDS, time = 1)
    public void decode200x200AVGSPLT(DecodeExecutionPlan200x200 executionPlan) throws IOException {
        String path = "proj2/src/test/to_test_pics/200x200/";
        BufferedImage secret = ImageIO.read(new File(path + executionPlan.secret));

        executionPlan.stegano.decode(secret);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Threads(3)
    @Measurement(time = 1, iterations = 1)
    @Warmup(iterations = 1, timeUnit = TimeUnit.MILLISECONDS, time = 1)
    public void decode2000x600THRPT(DecodeExecutionPlan2000x600 executionPlan) throws IOException {
        String path = "proj2/src/test/to_test_pics/200x200/";
        BufferedImage secret = ImageIO.read(new File(path + executionPlan.secret));

        executionPlan.stegano.decode(secret);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Measurement(time = 1, iterations = 1)
    @Warmup(iterations = 1, timeUnit = TimeUnit.MILLISECONDS, time = 1)
    public void decode2000x600AVG(DecodeExecutionPlan2000x600 executionPlan) throws IOException {
        String path = "proj2/src/test/to_test_pics/200x200/";
        BufferedImage secret = ImageIO.read(new File(path + executionPlan.secret));

        executionPlan.stegano.decode(secret);
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @Measurement(time = 1, iterations = 1)
    @Warmup(iterations = 1, timeUnit = TimeUnit.MILLISECONDS, time = 1)
    public void decode2000x600AVGSPLT(DecodeExecutionPlan2000x600 executionPlan) throws IOException {
        String path = "proj2/src/test/to_test_pics/200x200/";
        BufferedImage secret = ImageIO.read(new File(path + executionPlan.secret));

        executionPlan.stegano.decode(secret);
    }
}