package com.example.suzuki.memoprot001;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import jp.ne.docomo.smt.dev.characterrecognition.SceneCharacterRecognition;
import jp.ne.docomo.smt.dev.characterrecognition.data.CharacterRecognitionMessageData;
import jp.ne.docomo.smt.dev.characterrecognition.data.CharacterRecognitionStatusData;
import jp.ne.docomo.smt.dev.characterrecognition.param.CharacterRecognitionRequestParam;
import jp.ne.docomo.smt.dev.common.exception.SdkException;
import jp.ne.docomo.smt.dev.common.exception.ServerException;
import jp.ne.docomo.smt.dev.common.http.AuthApiKey;

/**
 * Created by Admin on 2015/11/09.
 */
public class RecognitionAsyncTask extends AsyncTask<RecognitionAsyncTaskParam,Integer,CharacterRecognitionStatusData>{
    private String jobid="";
    static final String INTENT_JOBID_KEY = "jobid";
    private AlertDialog.Builder _dlg;
    private final ProgressDialog mDialog;
    private boolean isSdkException = false;
    private String exceptionMessage = null;
    private RecognitionCallback callback;
    public RecognitionAsyncTask(Context context,AlertDialog.Builder dlg, RecognitionCallback c) {
        super();
        _dlg = dlg;
        callback = c;
        mDialog = new ProgressDialog(context);
        mDialog.setMax(100);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setProgress(0);
        //mDialog.show();
    }

    public interface RecognitionCallback{
        void callback();
    }

    @Override
    protected CharacterRecognitionStatusData doInBackground(RecognitionAsyncTaskParam... params) {
        CharacterRecognitionStatusData statusData = null;
        RecognitionAsyncTaskParam req_param = params[0];
        try {

            // パラメータを設定する
            CharacterRecognitionRequestParam param = new CharacterRecognitionRequestParam();
            param.setLang(req_param.getLang());
            param.setFilePath(req_param.getImagePath());
            param.setImageContentType(req_param.getImageType());
            AuthApiKey.initializeAuth(req_param.getKey());
            // 情景画像認識要求のリクエスト送信
            SceneCharacterRecognition Recognition = new SceneCharacterRecognition();
            statusData = Recognition.recognize(param);

        } catch (SdkException ex) {
            isSdkException = true;
            exceptionMessage = "ErrorCode: " + ex.getErrorCode() + "\nMessage: " + ex.getMessage();

        } catch (ServerException ex) {
            exceptionMessage = "ErrorCode: " + ex.getErrorCode() + "\nMessage: " + ex.getMessage();
        }

        return statusData;
    }

    @Override
    protected void onCancelled() {
    }



    @Override
    protected void onPostExecute(CharacterRecognitionStatusData statusData) {

        if(statusData == null){
            if(isSdkException){
                _dlg.setTitle("SdkException 発生");

            }else{
                _dlg.setTitle("ServerException 発生");
            }
            _dlg.setMessage(exceptionMessage + " ");
            _dlg.show();
            jobid = "";

        }else{

            // 結果表示
            _dlg.setTitle("処理結果");

            StringBuffer sb = new StringBuffer();
            sb.append("認識ジョブの出力 :\n");
            sb.append("認識ジョブID : " + statusData.getJob().getId() + "\n");
            sb.append("進行状況 : " + statusData.getJob().getStatus() + "\n");
            sb.append("リクエスト受け付け時刻 : " + statusData.getJob().getQueueTime() + "\n");
            jobid = statusData.getJob().getId();
            // メッセージの出力
            CharacterRecognitionMessageData message = statusData.getMessage();
            if (message != null) {
                sb.append("メッセージ : " + message.getText() + "\n");
            }
            callback.callback();
            _dlg.setMessage(new String(sb));
            _dlg.show();
        }
    }

    public String getJobid(){
        return jobid;
    }
}
