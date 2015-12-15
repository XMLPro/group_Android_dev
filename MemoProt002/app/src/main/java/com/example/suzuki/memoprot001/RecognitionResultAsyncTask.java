package com.example.suzuki.memoprot001;

import android.os.AsyncTask;
import android.app.AlertDialog;

import java.util.ArrayList;

import jp.ne.docomo.smt.dev.characterrecognition.SceneCharacterRecognition;
import jp.ne.docomo.smt.dev.characterrecognition.data.CharacterRecognitionMessageData;
import jp.ne.docomo.smt.dev.characterrecognition.data.CharacterRecognitionPointData;
import jp.ne.docomo.smt.dev.characterrecognition.data.CharacterRecognitionResultData;
import jp.ne.docomo.smt.dev.characterrecognition.data.CharacterRecognitionShapeData;
import jp.ne.docomo.smt.dev.characterrecognition.data.CharacterRecognitionWordData;
import jp.ne.docomo.smt.dev.characterrecognition.data.CharacterRecognitionWordsData;
import jp.ne.docomo.smt.dev.characterrecognition.param.CharacterRecognitionJobInfoRequestParam;
import jp.ne.docomo.smt.dev.common.exception.SdkException;
import jp.ne.docomo.smt.dev.common.exception.ServerException;

/**
 * Created by Admin on 2015/11/10.
 */


public class RecognitionResultAsyncTask extends AsyncTask<RecognitionAsyncTaskParam, Integer, CharacterRecognitionResultData> {
    private AlertDialog.Builder _dlg;

    private boolean isSdkException = false;
    private String exceptionMessage = null;
    private CharacterRecognitionWordsData wordsData;
    private ReconitionResultCallback callback;
    public CharacterRecognitionWordsData getWordsData() {
        return wordsData;
    }

    public RecognitionResultAsyncTask(AlertDialog.Builder dlg, ReconitionResultCallback c) {
        super();
        _dlg = dlg;
        callback = c;
    }

    public interface ReconitionResultCallback{
        void resultcallback();
    }

    @Override
    protected CharacterRecognitionResultData doInBackground(RecognitionAsyncTaskParam... params) {
        CharacterRecognitionResultData resultData = null;
        RecognitionAsyncTaskParam req_param = params[0];
        try {

            // パラメータを設定する
            CharacterRecognitionJobInfoRequestParam param = new CharacterRecognitionJobInfoRequestParam();
            param.setJobId(req_param.getJobId());
            // 情景画像認識結果取得のリクエスト送信
            SceneCharacterRecognition recognize = new SceneCharacterRecognition();
            resultData = recognize.getResult(param);

        } catch (SdkException ex) {
            isSdkException = true;
            exceptionMessage = "ErrorCode: " + ex.getErrorCode() + "\nMessage: " + ex.getMessage();
        } catch (ServerException ex) {
            exceptionMessage = "ErrorCode: " + ex.getErrorCode() + "\nMessage: " + ex.getMessage();
        }
        return resultData;
    }

    @Override
    protected void onCancelled() {
    }

    @Override
    protected void onPostExecute(CharacterRecognitionResultData resultData) {

        if (resultData == null) {
            if (isSdkException) {
                _dlg.setTitle("SdkException 発生");

            } else {
                _dlg.setTitle("ServerException 発生");
            }
            _dlg.setMessage(exceptionMessage + " ");
            _dlg.show();

        } else {
            // 結果表示
            StringBuffer sb = new StringBuffer();
            sb.append("認識ジョブの出力 :\n");
            sb.append("認識ジョブID : " + resultData.getJob().getId() + "\n");
            sb.append("進行状況 : " + resultData.getJob().getStatus() + "\n");
            sb.append("リクエスト受け付け時刻 : " + resultData.getJob().getQueueTime() + "\n");

            // 抽出した全ての単語の情報の出力
            wordsData = resultData.getWords();
            if (wordsData != null) {
                sb.append("単語情報 :\n");
                sb.append("単語の情報の数 :" + wordsData.getCount() + "\n");
                ArrayList<CharacterRecognitionWordData> wordList = wordsData.getWord();
                for (CharacterRecognitionWordData wordData : wordList) {
                    sb.append("認識した単語の情報 :\n");
                    sb.append("認識した単語 : " + wordData.getText() + "\n");
                    sb.append("認識結果の信頼度 : " + wordData.getScore() + "\n");
                    sb.append("抽出した単語のカテゴリ : " + wordData.getCategory() + "\n");

                    sb.append("抽出した単語の形状を表す座標情報 :\n");
                    CharacterRecognitionShapeData shapeData = wordData.getShape();
                    ArrayList<CharacterRecognitionPointData> pointList = shapeData.getPoint();
                    if (pointList == null) {
                        continue;
                    }
                    sb.append("頂点情報の数 : " + shapeData.getCount() + "\n");
                    sb.append("頂点情報 : " + shapeData.getCount() + "\n");
                    for (CharacterRecognitionPointData pointData : pointList) {
                        sb.append("x座標, y座標(ピクセル単位) : " + pointData.getX() + ", " + pointData.getY() + "\n");
                    }
                }
            }

            // メッセージの出力
            CharacterRecognitionMessageData message = resultData.getMessage();
            if (message != null) {
                sb.append("メッセージ : " + message.getText() + "\n");
            }
            _dlg.setTitle("成功");
            _dlg.setMessage(new String(sb));
            _dlg.show();
            callback.resultcallback();
        }
    }
}