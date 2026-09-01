package functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assume;
import org.junit.Test;

import data.Params;
import data.SerializableList;
import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ByteProcessor;

public class VideoRecognitionTest {

  @Test
  public void detectsAndTracksBinaryForegroundAcrossFrames() {
    float originalMinSize = Params.minSize;
    float originalMaxSize = Params.maxSize;
    double originalMicronPerPixel = Params.micronPerPixel;
    float originalMaxDisplacement = Params.maxDisplacement;
    int originalMinTrackLength = Params.minTrackLength;

    try {
      Params.minSize = 1;
      Params.maxSize = 1000;
      Params.micronPerPixel = 1;
      Params.maxDisplacement = 10;
      Params.minTrackLength = 3;

      ImageStack stack = new ImageStack(64, 64);
      for (int frame = 0; frame < 3; frame++) {
        ByteProcessor processor = new ByteProcessor(64, 64);
        processor.setValue(0);
        processor.fill();
        processor.setValue(255);
        processor.fillOval(10 + frame, 20, 5, 5);
        stack.addSlice(processor);
      }

      SerializableList tracks = new VideoRecognition().analyzeVideo(new ImagePlus("test", stack));

      assertEquals(1, tracks.size());
      assertEquals(3, ((java.util.List<?>) tracks.get(0)).size());
    } finally {
      Params.minSize = originalMinSize;
      Params.maxSize = originalMaxSize;
      Params.micronPerPixel = originalMicronPerPixel;
      Params.maxDisplacement = originalMaxDisplacement;
      Params.minTrackLength = originalMinTrackLength;
    }
  }

  @Test
  public void detectsTracksInBundledAviFixture() {
    float originalMinSize = Params.minSize;
    float originalMaxSize = Params.maxSize;
    double originalMicronPerPixel = Params.micronPerPixel;
    float originalMaxDisplacement = Params.maxDisplacement;
    int originalMinTrackLength = Params.minTrackLength;

    try {
      Params.minSize = 1;
      Params.maxSize = 1000;
      Params.micronPerPixel = 1;
      Params.maxDisplacement = 20;
      Params.minTrackLength = 2;

      File fixture = new File("..", "Test_data/Chemotaxis/Experiment/control/sample1.avi");
      ImagePlus video = new FileManager().getAVI(fixture.getPath());
      assertNotNull(video);
      assertFalse(new VideoRecognition().analyzeVideo(video).isEmpty());
    } finally {
      Params.minSize = originalMinSize;
      Params.maxSize = originalMaxSize;
      Params.micronPerPixel = originalMicronPerPixel;
      Params.maxDisplacement = originalMaxDisplacement;
      Params.minTrackLength = originalMinTrackLength;
    }
  }

  @Test
  public void detectsTracksInSuppliedMotilityFixture() {
    String fixturePath = System.getProperty("opencasa.motility.fixture");
    Assume.assumeTrue(fixturePath != null);

    float originalMinSize = Params.minSize;
    float originalMaxSize = Params.maxSize;
    double originalMicronPerPixel = Params.micronPerPixel;
    float originalMaxDisplacement = Params.maxDisplacement;
    int originalMinTrackLength = Params.minTrackLength;
    float originalFrameRate = Params.frameRate;

    try {
      Params.minSize = 10;
      Params.maxSize = 100;
      Params.micronPerPixel = 0.481;
      Params.maxDisplacement = 20;
      Params.minTrackLength = 10;
      Params.frameRate = 60;

      File fixture = new File(fixturePath);
      assertTrue(fixture.isFile());
      ImagePlus video = new FileManager().getAVI(fixture.getPath());
      assertNotNull(video);
      assertFalse(new VideoRecognition().analyzeVideo(video).isEmpty());
    } finally {
      Params.minSize = originalMinSize;
      Params.maxSize = originalMaxSize;
      Params.micronPerPixel = originalMicronPerPixel;
      Params.maxDisplacement = originalMaxDisplacement;
      Params.minTrackLength = originalMinTrackLength;
      Params.frameRate = originalFrameRate;
    }
  }

  @Test
  public void comparesSample21VideosWithAuthorMotilityBaseline() throws Exception {
    String fixtureDirectory = System.getProperty("opencasa.motility.fixtureDirectory");
    String baselinePath = System.getProperty("opencasa.motility.baseline");
    Assume.assumeTrue(fixtureDirectory != null && baselinePath != null);

    float originalMinSize = Params.minSize;
    float originalMaxSize = Params.maxSize;
    double originalMicronPerPixel = Params.micronPerPixel;
    float originalMaxDisplacement = Params.maxDisplacement;
    int originalMinTrackLength = Params.minTrackLength;
    float originalFrameRate = Params.frameRate;
    int originalWindowSize = Params.wSize;
    float originalMinVcl = Params.vclMin;

    try {
      Params.minSize = 10;
      Params.maxSize = 100;
      Params.micronPerPixel = 0.481;
      Params.maxDisplacement = 20;
      Params.minTrackLength = 10;
      Params.frameRate = 60;
      Params.wSize = 4;
      Params.vclMin = 10;

      Map<String, String[]> baseline = readBaseline(new File(baselinePath));
      File[] videos = new File(fixtureDirectory).listFiles();
      assertNotNull(videos);
      int compared = 0;
      int unreadable = 0;
      for (File videoFile : videos) {
        if (!videoFile.getName().toLowerCase().endsWith(".avi"))
          continue;
        String videoId = new FileManager().getFilename(videoFile.getName()).toLowerCase();
        String[] expected = baseline.get(videoId);
        assertNotNull("Missing author baseline for " + videoId, expected);

        ImagePlus video = new FileManager().getAVI(videoFile.getPath());
        if (video == null || video.getStackSize() == 0) {
          unreadable++;
          continue;
        }
        SerializableList tracks = new VideoRecognition().analyzeVideo(video);
        SerializableList motileTracks = new SignalProcessing().filterTracksByMotility(tracks);
        assertFalse("No motile tracks detected for " + videoId, motileTracks.isEmpty());
        float[] means = calculateMeans(motileTracks);
        float motility = tracks.isEmpty() ? 0 : 100f * motileTracks.size() / tracks.size();

        assertEquals(Float.parseFloat(expected[0]), motileTracks.size(), 0.001f);
        assertEquals(Float.parseFloat(expected[1]), means[0], 0.001f);
        assertEquals(Float.parseFloat(expected[2]), means[1], 0.001f);
        assertEquals(Float.parseFloat(expected[3]), means[2], 0.001f);
        assertEquals(Float.parseFloat(expected[4]), motility, 0.001f);

        System.out.printf("AUTHOR-COMPARISON %s: tracks %d/%s, VSL %.3f/%s, VCL %.3f/%s, VAP %.3f/%s, motility %.3f/%s%n",
            videoId, motileTracks.size(), expected[0], means[0], expected[1], means[1], expected[2], means[2],
            expected[3], motility, expected[4]);
        compared++;
      }
      assertEquals("Seven supplied files are valid AVI videos", 7, compared);
      assertEquals("One supplied file is a tab-separated export named as AVI", 1, unreadable);
    } finally {
      Params.minSize = originalMinSize;
      Params.maxSize = originalMaxSize;
      Params.micronPerPixel = originalMicronPerPixel;
      Params.maxDisplacement = originalMaxDisplacement;
      Params.minTrackLength = originalMinTrackLength;
      Params.frameRate = originalFrameRate;
      Params.wSize = originalWindowSize;
      Params.vclMin = originalMinVcl;
    }
  }

  private float[] calculateMeans(SerializableList tracks) {
    float totalVsl = 0;
    float totalVcl = 0;
    float totalVap = 0;
    Kinematics kinematics = new Kinematics();
    SignalProcessing signalProcessing = new SignalProcessing();
    for (Object trackObject : tracks) {
      List<?> track = (List<?>) trackObject;
      totalVsl += kinematics.vsl(track);
      totalVcl += kinematics.vcl(track);
      totalVap += kinematics.vcl(signalProcessing.movingAverage(track));
    }
    return new float[] { totalVsl / tracks.size(), totalVcl / tracks.size(), totalVap / tracks.size() };
  }

  private Map<String, String[]> readBaseline(File baselineFile) throws Exception {
    Map<String, String[]> baseline = new HashMap<String, String[]>();
    BufferedReader reader = new BufferedReader(new FileReader(baselineFile));
    try {
      String header = reader.readLine();
      String[] headings = header.split(",", -1);
      Map<String, Integer> columns = new HashMap<String, Integer>();
      for (int index = 0; index < headings.length; index++)
        columns.put(headings[index], index);
      String line;
      while ((line = reader.readLine()) != null) {
        String[] values = line.split(",", -1);
        baseline.put(values[columns.get("ID")].toLowerCase(), new String[] {
            values[columns.get("Motile trajectories")], values[columns.get("VSL Mean (um/s)")],
            values[columns.get("VCL Mean (um/s)")], values[columns.get("VAP Mean (um/s)")],
            values[columns.get("Motility (%)")] });
      }
    } finally {
      reader.close();
    }
    return baseline;
  }
}