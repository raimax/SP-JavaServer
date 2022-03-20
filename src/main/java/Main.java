public class Main {
    public static void main(String[] args) {
        JavaServer javaServer = new JavaServer();

        try {
            javaServer.start(6666);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            javaServer.stop();
        }
    }
}
