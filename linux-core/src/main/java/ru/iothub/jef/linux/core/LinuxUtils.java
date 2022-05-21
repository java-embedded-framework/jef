

package ru.iothub.jef.linux.core;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.stream.Stream;

public class LinuxUtils {
    private static Boolean isNative = null;

    public static boolean isNative() {
        if (isNative == null) {
            synchronized (LinuxUtils.class) {
                if (isNative == null) {
                    isNative = "executable".equals(System.getProperty("org.graalvm.nativeimage.kind"));
                }
            }
        }
        return isNative;
    }

    public static void checkIOResult(String method, int result) throws NativeIOException {
        if (result < 0) {
            //String strerror = errno.get().strerror(result);
            ErrnoCode errnoCode = ErrnoCode.valueOf(-result).orElse(null);

            String strerror = errnoCode != null ? errnoCode.getMessage() : "Unknown error - " + result;
            //System.out.println("error out:" + result + " value:" + strerror);
            throw new NativeIOException(method + " failed("+result+"): " + strerror, result);
        }
    }

    public static void dump(String name, ByteBuffer buf) {
        buf.position(0);
        System.out.println("****** dump '" + name + "' ******");
        byte[] b = new byte[buf.capacity()];
        buf.get(b);
        for (int i = 0; i < b.length; i++) {
            System.out.printf("buf[%d]=0x%2h\n", i, (b[i] & 0xFF));
        }
        buf.position(0);
        System.out.println("*************" + name + "********");
    }

    public static String resolveHeader(String fileName) {
        final ProtectionDomain pd = LinuxUtils.class.getProtectionDomain();
        final CodeSource cs = pd.getCodeSource();

        InputStream resource;

        if (cs == null) {
            //code from class loaded by Bootsrap class loader;
            //System.out.println("RESOLVE: code from class loaded by Bootsrap class loader;" );
            resource = pd.getClassLoader().getResourceAsStream("headers/" + fileName);
            //System.out.println("resource = " + resource);
        } else {
            final URL location = cs.getLocation();
            //System.out.println("RESOLVE: location - " + location );
            resource = pd.getClassLoader().getResourceAsStream("headers/" + fileName);
            //System.out.println("resource = " + resource);
            //ну и там дальше обработка jar/не jar
        }

        if(resource != null) {
            return copyResource(fileName, resource);
        }

        /*File file = new File("");
        String absolutePath = file.getAbsolutePath();
        System.out.println("scan absolutePath = " + absolutePath);
        try {
            Stream<Path> pathStream = find(fileName, absolutePath);

            if (pathStream.count() == 0) {
                // runned by maven;
                file = new File(absolutePath);

                absolutePath = file.getParentFile().getParentFile().getAbsolutePath();
                System.out.println("scan absolutePath = " + absolutePath);

                pathStream = find(fileName, absolutePath);
                Path p = pathStream.findAny().orElse(null);
                if (p != null) {
                    String result = p.toFile().getAbsolutePath();
                    System.out.println("result = " + result);
                    return "\"" + result + "\"";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("fileName = " + fileName + " not found");*/
        throw new RuntimeException("File " + fileName + " not found");
    }

    private static String copyResource(String fileName, InputStream resource) {
        File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        //File jefDir = new File(tmpDir, "jef");

        Path path = Paths.get(tmpDir.getAbsolutePath() + File.separator + "jef");
        //System.out.println("Path to JEF: " + path.toAbsolutePath().toString());


        try {
            Files.createDirectories(path);

            byte[] buffer = new byte[resource.available()];
            resource.read(buffer);

            File targetFile = new File(path.toFile(), fileName);
            if(targetFile.exists()) {
                targetFile.delete();
            }
            targetFile.createNewFile();

            try(OutputStream outStream = new FileOutputStream(targetFile)) {
                outStream.write(buffer);
            }
            String absolutePath = targetFile.getAbsolutePath();
            //System.out.println("resolve header '" + absolutePath + "'");
            return "\"" + absolutePath + "\"";
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static Stream<Path> find(String fileName, String absolutePath) throws IOException {
        return Files.find(
                Paths.get(absolutePath),
                Integer.MAX_VALUE,
                (filePath, fileAttr) -> fileAttr.isRegularFile() && filePath.endsWith(fileName));
    }

    public static byte[] toBytes(ByteBuffer buf) {
        if (buf.hasArray()) {
            return buf.array();
        }
        byte[] b = new byte[buf.capacity()];
        buf.get(b);
        return b;
    }
}
