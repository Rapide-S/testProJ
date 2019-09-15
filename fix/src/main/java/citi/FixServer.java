package citi;

import quickfix.*;

public class FixServer {
    private static ThreadedSocketAcceptor acceptor = null;

    /**
     * 指定配置文件启动
     *
     * @param propFile
     * @throws ConfigError
     * @throws FieldConvertError
     */
    public FixServer(String propFile) throws ConfigError, FieldConvertError {
        // 设置配置文件
        SessionSettings settings = new SessionSettings(propFile);

        // 设置一个APPlication
        Application application = new FixServerApplication();

        /**
         *
         * quickfix.MessageStore 有2种实现。 quickfix.JdbcStore,quickfix.FileStore .
         * JdbcStoreFactory 负责创建JdbcStore ， FileStoreFactory 负责创建FileStorequickfix
         * 默认用文件存储，因为文件存储效率高。
         */
        MessageStoreFactory storeFactory = new FileStoreFactory(settings);

        LogFactory logFactory = new FileLogFactory(settings);

        MessageFactory messageFactory = new DefaultMessageFactory();

        acceptor = new ThreadedSocketAcceptor(application, storeFactory, settings, logFactory, messageFactory);

    }

    private void startServer() throws RuntimeError, ConfigError {
        acceptor.start();
    }

    /**
     * 测试本地使用的main方法
     *
     * @param args
     * @throws FieldConvertError
     * @throws ConfigError
     */
    public static void main(String[] args) throws ConfigError, FieldConvertError {
        FixServer fixServer = new FixServer("acceptor.properties");
        fixServer.startServer();
    }

}
