import java.io.*;

public class FileService {
    public void sendFile(String fileName, BufferedInputStream in, BufferedOutputStream out) {
        try {
            in = new BufferedInputStream(new FileInputStream("blog.xml"));
        }
        catch (FileNotFoundException ex) {
            System.out.println(String.format("File \"%s\" not found", fileName));
            return;
        }

        try {
            byte[] b = new byte[8 * 1024];
            int len;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
                out.flush();
            }
        }
        catch (IOException ex) {
            System.out.println("Error sending file: " + ex.getMessage());
        }

        System.out.println("File sent");
    }

    public void receiveFile(BufferedInputStream in, BufferedOutputStream out) {
        try {
            byte[] b = new byte[8 * 1024];

            int len;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }

            System.out.println("File received");
        }
        catch (IOException ex) {
            System.out.println("Error receiving file: " + ex.getMessage());
        }
    }
}
