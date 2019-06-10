package examples;

import java.sql.*;
import java.util.Scanner;

public class Example {
    public static void main(String args[]){
        String path = "jdbc:mysql://localhost/" + "DBNAME"; //あらかじめMysql側でDBを用意しておきましょう DBNAMEはご自分のDBのお名前に置き換えてください
        String user = "USER"; //あらかじめMysql側で((
        String password = "PASSWORD"; //あ
        try {

            /*
            DBとのコネクションを確立します
            DriverManagerクラスのgetConnectionメソッドを呼び出すと、指定したDBとの接続を扱うConnectionクラスのオブジェクトを得ることができます
             */
            Connection con = DriverManager.getConnection(path,user,password);

            /*
            Connection型のオブジェクトで利用できるcreateStatementメソッドを呼び出すと、接続したDBの状態を扱うStatementクラスのオブジェクトを得ることができます
             */
            Statement stat = con.createStatement();

            /*
            Statement型のオブジェクトで利用できるexecuteメソッドを呼び出すと、指定したSQL文を実行することができます
            ここでは整数型のidと文字列型のnameをもつnekoというテーブルを作成しています 詳しいことはCREATE文とかでググるといいと思います
             */
            stat.execute("CREATE TABLE neko (id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,name VARCHAR(50))");

            /*
            テーブルに変更を加える際にはexecuteではなくexecuteUpdateを呼び出します
             */
            stat.executeUpdate("INSERT INTO neko(name) VALUES ('MikeNeko')");
            stat.executeUpdate("INSERT INTO neko(name) VALUES ('ToraNeko')");


            /*
            データを取得する際にはexecuteではなくexecuteQueryを呼び出します
            executeQueryを呼び出すと、取得したデータを格納するResultSetクラスのオブジェクトを得ることができます
             */
            ResultSet result = stat.executeQuery("SELECT id,name FROM neko");


            /*
            ResultSet型のオブジェクトは内部に取得したデータの何番目の要素を参照するかのカウンターを持っており、nextを呼び出すことで参照する位置を次の要素に移動させます
            executeQueryでオブジェクトを得た段階では先頭の要素の一つ前の要素を参照していることになっています
            参照している位置が一番最後である時にnextを呼び出すとfalseが得られるため、要素を全て見終わった段階でwhileループを抜け出すことができます
             */
            while (result.next()){
                /*
                getIntメソッドを呼び出すと、ResultSet型のオブジェクトが現在参照している要素の中身を取得することができます
                 */
                int id = result.getInt("id");

                /*
                おなじく
                 */
                String name = result.getString("name");

                System.out.println(id + "/" + name);
            }

            /*
            nekoテーブルにさよならします
             */
            stat.execute("DROP TABLE neko");

            /*
            closeメソッドを呼び出すことで接続を終了します
             */
            con.close();

            /*
            乙でした
             */
        } catch (SQLException e) {
            //なんかあって死んだ時にエラーを吐きます
            e.printStackTrace();
        }
    }
}
