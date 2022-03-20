import lt.viko.eif.rcepauskas.blog.Blog;
import lt.viko.eif.rcepauskas.blog.DataService;
import lt.viko.eif.rcepauskas.blog.JaxbTransformer;

import javax.xml.bind.JAXBException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Java server class that runs a tcp server and sends a file to the client
 */
public class JavaServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedOutputStream out;
    private BufferedInputStream in;
    private FileService fileService;

    public JavaServer() {
        this.fileService = new FileService();
    }

    /**
     * Starts a tcp server, listens for a client connection and then sends a file to the client
     * @param port server port to run on
     * @throws IOException
     */
    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port: " + port);
        clientSocket = serverSocket.accept();
        System.out.println("Client connected");

        out = new BufferedOutputStream(clientSocket.getOutputStream());

        beginFileTransfer();
    }

    /**
     * Closes server connection
     */
    public void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        }
        catch (IOException ex) {
            System.out.println("Error stopping server: " + ex.getMessage());
        }
    }

    private void beginFileTransfer()  {

        Blog blog = DataService.createBlogData();

        try {
            JaxbTransformer.pojoToXml("blog.xml", blog);
        }
        catch (JAXBException ex) {
            System.out.println("Jaxb transformation error" + ex.getMessage());
            return;
        }

        fileService.sendFile("blog.xml", in, out);
    }
}
