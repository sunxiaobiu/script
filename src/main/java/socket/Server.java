package socket;

import RemoteTest.SystemCommandExecutor;
import RemoteTest.TestShell;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class Server {
    public static final int PORT = 8888;//监听的端口号

    public static void main(String[] args) {
        System.out.println("【Socket Serve Start】...\n");
        Server server = new Server();
        server.init();
    }

    public void init() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                // 一旦有堵塞, 则表示服务器与客户端获得了连接
                Socket client = serverSocket.accept();
                // 处理这次连接
                new HandlerThread(client);
            }
        } catch (Exception e) {
            System.out.println("【Socket Serve Exception】: " + e.getMessage());
        }
    }

    private class HandlerThread implements Runnable {
        private Socket socket;
        public HandlerThread(Socket client) {
            socket = client;
            new Thread(this).start();
        }

        public void run() {
            try {
                // 读取客户端数据
                DataInputStream input = new DataInputStream(socket.getInputStream());
                String clientInputStr = input.readUTF();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFException
                // 处理客户端数据
                System.out.println("【Socket Client Message Content】:" + clientInputStr);

                //invoke clientInputStr
                String logRes = runCommand(clientInputStr);

                // 向客户端回复信息
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                //System.out.print("请输入:\t");
                // 发送键盘输入的一行
                //String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
                out.writeUTF(logRes);

                out.close();
                input.close();
            } catch (Exception e) {
                System.out.println("【Socket Server Run Exception】: " + e.getMessage());
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (Exception e) {
                        socket = null;
                        System.out.println("Socket Server finally Exception】:" + e.getMessage());
                    }
                }
            }
        }
    }

    public static String runCommand(String cmds) throws IOException, InterruptedException {

        List<String> commands = Arrays.asList(cmds.split(" "));

        // execute the command
        SystemCommandExecutor commandExecutor = new SystemCommandExecutor(commands, "qtfrmszSXY199231", "/home/ubuntu/monash/LazyCow");
        int result = commandExecutor.executeCommand();

        // get the stdout and stderr from the command that was run
        StringBuilder stdout = commandExecutor.getStandardOutputFromCommand();
        StringBuilder stderr = commandExecutor.getStandardErrorFromCommand();

        // print the stdout and stderr

        String res = "The numeric result of the command was: " + result + "STDOUT:" + stdout + "STDERR:" + stderr;
        return res;
    }
}
