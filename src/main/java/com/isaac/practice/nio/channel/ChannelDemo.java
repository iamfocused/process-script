package com.isaac.practice.nio.channel;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelDemo {
    @Test
    public void testFileChannel() throws IOException {
        String filePath = "/Users/eorionx/Downloads/test/2019.8.5-2019.8.9.txt";
        RandomAccessFile aFile = new RandomAccessFile(filePath, "rw");
        FileChannel inChannel = aFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(48);

        int bytesRead = inChannel.read(buf);

        while (bytesRead != -1) {
            System.out.println("Read " + bytesRead);
            buf.flip();

            while (buf.hasRemaining())
                System.out.println((char) buf.get());

            buf.clear();
            bytesRead = inChannel.read(buf);
        }

        aFile.close();
    }
}