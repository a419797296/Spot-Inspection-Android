package service;

/**
 * Created by Blue on 2016/5/24.
 */

import android.content.Context;
import android.graphics.Bitmap;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

import bean.UserInfo;
import constant.ContentFlag;
import impl.IhandleMessge;


public class MessageService {
    private Context context;
    private UserInfo user;
    private static Socket socket;
    private DataOutputStream output;
    private DataInputStream input;
    private Map<Integer, Bitmap> imgMap = new HashMap<Integer, Bitmap>();	//缓存在线用户头像数据
    public MessageService(Context context) {
        this.context = context;
    }
    private final String ip="192.168.31.204";
    private final String port="3333";

    private static final MessageService msg = new MessageService();

    public static MessageService getMsgServ() {
        return msg;
    }
    public MessageService() {}

    /**
     * 建立连接
     * @return
     */
    public void startConnect(IhandleMessge handle) throws IOException{
        try {
            SocketAddress socAddress = new InetSocketAddress(InetAddress.getByName(ip), Integer.parseInt(port));
            socket = new Socket();
            socket.connect(socAddress, 5*1000);
            output = new DataOutputStream(socket.getOutputStream());
            input = new DataInputStream(socket.getInputStream());
//            sendMsg("can you recieved this message".getBytes());
//            //处理用户登录
//            String str = ContentFlag.ONLINE_FLAG + id;
//            output.writeUTF(str);

            //接收消息
            receiveMsg(handle);
        } catch (IOException e) {
            throw new IOException("fail connect to the server");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 应用退出
     */
    public void quitApp() {
        String sendStr="";
        if(null!= user){
            sendStr = ContentFlag.OFFLINE_FLAG + this.user.getId();
        }
        if (!socket.isClosed()) {
            if (socket.isConnected()) {
                if (!socket.isOutputShutdown()) {
                    try {
                        output.writeUTF(sendStr);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (null != input)  input.close();
                            if (null != output) output.close();
                            if (null != socket) socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * 接收消息
     * @return
     * @throws IOException
     */
    public static void receiveMsg(IhandleMessge handle) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        try {
//            byte[] readBuffer = new byte[10000];
//            int nbytes;
//            while((nbytes = socket.getInputStream().read(readBuffer))!=0){
////                String msgCtn = input.readUTF();
//                String line = new String(readBuffer, "UTF-8");
//                String msgCtn=line.substring(0, nbytes);
//                System.out.println("recieved msg is" + msgCtn);
//
//                //if(msgCtn.startsWith(ContentFlag.CONTROL_FLAG)){														//处理普通消息
//                    handle.handleMsg(msgCtn);
//                //}

//            br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            String line;
            while ((line =br.readLine()) != null)
            {
                handle.handleMsg(line);
            }

        } catch (Exception e) {
            if (!socket.isClosed()) {
                throw new IOException("fail connect to the server");
            }
            socket.close();
            br.close();
        }
    }


    /**
     * 发送消息
     * @param ctn
     * @throws IOException
     */
    public static void sendMsg(byte[] ctn) throws Exception {
//        ctn[ctn.length]='\r';
//        ctn[ctn.length+1]='\n';
        socket.getOutputStream().write(ctn);
    }
    public static void sendMsg(String ctn) throws Exception {
        String out=ctn+"\r\n";
        socket.getOutputStream().write(out.getBytes());
    }


}
