package jp.co.jri.internship.fintech_sample1.login;

import android.content.Context;

import java.io.File;
import java.io.IOException;

import jp.co.jri.internship.fintech_sample1.CsvReader;
import jp.co.jri.internship.fintech_sample1.FintechData;
import jp.co.jri.internship.fintech_sample1.UserData;

//public class LoginDataSource {
//
//    public Result<LoggedInUser> login(String userId, String password, Context context) {
//
//        try {
//            /* 仮ログイン機能実装として、fakeUserを作成して一律ログイン成功するよう制御 */
//            LoggedInUser fakeUser =
//                    new LoggedInUser(
//                            java.util.UUID.randomUUID().toString(),
//                            "フェイクユーザー太郎");
//            return new Result.Success<>(fakeUser); //認証結果成功として仮ログインユーザ（fakeUser）の情報をリターン
//
//        } catch (Exception e) {
//            return new Result.Error(new IOException("Error logging in", e)); //エラー処理：認証結果失敗としてリターン
//        }
//    }
//}

public class LoginDataSource {

    public Result<LoggedInUser> login(String userId, String password, Context context) {
        CsvReader parser = new CsvReader();
        parser.readerUserDataBase(context);

        for (UserData userData : parser.userObjects) {
            if (userData.getUserId().equals(userId) && userData.getPassword().equals(password)) {
                // ユーザーIDとパスワードが一致した場合
                LoggedInUser loggedInUser = new LoggedInUser(java.util.UUID.randomUUID().toString(), userData.getDisplayName());
                return new Result.Success<>(loggedInUser);
            }
        }

        // ユーザーIDとパスワードが一致する行がなかった場合
        return new Result.Error(new IOException("Login failed"));
    }
}