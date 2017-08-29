package me.pistofranco.mysql_api;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Core extends JavaPlugin {
    private Connection connection;
    private String host, database, username, password;
    private int port;

    public void onEnable() {
        makeConfigYml();
        connectToServer();
    }

    private void makeConfigYml(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void connectToServer() {
        try {
            host = "localhost"; //getConfig().getString("host");
            database = "bukkit"; //getConfig().getString("database");
            username = "root";//getConfig().getString("username");
            password = "root";//getConfig().getString("password");
            port = 3306; //getConfig().getInt("port");
        }catch (NumberFormatException e){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error reading config.yml");
        }
        try{
            synchronized (this) {
                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection("jdbc:mysql://"+this.host+":"+this.port+"/"+this.database,this.username,this.password));
                if(!connection.isClosed()){
                    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Connected to database!");
                }
            }
        }catch (SQLException e){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error while trying to get access to database!");
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
