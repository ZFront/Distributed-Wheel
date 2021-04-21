package com.wheel.demo.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @desc 实现服务暴露功能
 * @author: zhouf
 */
public class RpcFramework {

    /**
     * 服务暴露
     *
     * @param service
     * @param port
     */
    public static void expose(Object service, int port) throws Exception {
        ServerSocket server = new ServerSocket(port);
        while (true) {
            Socket socket = server.accept();
            new Thread(() -> {
                try {
                    //反序列化
                    ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                    //读取方法名
                    String methodName = (String) input.readObject();
                    //参数类型
                    Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                    //参数
                    Object[] arguments = (Object[]) input.readObject();
                    //找到方法
                    Method method = service.getClass().getMethod(methodName, parameterTypes);
                    //调用方法
                    Object result = method.invoke(service, arguments);
                    // 返回结果
                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                    output.writeObject(result);
                } catch (Exception e) {
                    System.out.println("暴露异常, e:" + e);
                }
            }).start();
        }
    }

    public static <T> T refer(Class<T> interfaceClass, String host, int port) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass},
                (proxy, method, arguments) -> {
                    Socket socket = new Socket(host, port);  //指定 provider 的 ip 和端口
                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                    output.writeObject(method.getName());  //传方法名
                    output.writeObject(method.getParameterTypes());  //传参数类型
                    output.writeObject(arguments);  //传参数值
                    ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                    Object result = input.readObject();  //读取结果
                    return result;
                });
    }

}
